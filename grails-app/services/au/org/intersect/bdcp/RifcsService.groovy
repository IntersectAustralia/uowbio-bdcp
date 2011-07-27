package au.org.intersect.bdcp

import java.io.File
import java.util.concurrent.Executors

import au.org.intersect.bdcp.ldap.LdapUser
import groovy.xml.StreamingMarkupBuilder

class RifcsService
{
	private static final int THREADS = 3
	private static final String FILEPREFIX_STUDY = "study"
	private def executor = Executors.newFixedThreadPool(THREADS)
	
	private Map common = [
			'originatingSource' : 'http://www.uow.edu.au',
			'@group' : 'University of Wollongong',
			'collection.email.address' : 'email-TBA@uow.edu.au',
			'collection.accessRights' : 'Access rights to be announced',
			'collection.physical.address' : """Biomechanics Research Laboratory
School of Health Sciences
University of Wollongong
Northfields Avenue
Wollongong N.S.W. 2522"""
			]
	
    static transactional = false

    def fileService
	
	def schedulePublishing = 
	{
		ctxPath ->
		def staticCtx = makeContext(ctxPath)
		executor.submit {
			try {
			   studiesToXml(staticCtx)
			   usersToXml(staticCtx)
			   staticsToXml(staticCtx)
			} catch(Exception e) {
				e.printStackTrace(System.err)
			}
		}
	}
	
	private def makeContext =
	{	baseCtxPath ->
		def staticCtx = fileService.createContext(baseCtxPath, "rifcs")
		fileService.getFileReference(staticCtx, ".").mkdirs()
		return staticCtx
	}
	
	private def makeKey =
	{
		obj ->
		return "oai:au.edu.uow:biomechanics:" + makeName(obj)
	}
	
	private def makeName =
	{
		obj ->
		String cname = obj.getClass().getSimpleName()
		return cname + "~" + obj.id
	}
	
	private def makeFilename =
	{
		obj ->
		return makeName(obj) + ".xml"
	}
	
	
	private def makeRelation =
	{
		obj, type ->
		[key:makeKey(obj), type:type]
	}
	
	private def studiesToXml =
	{
		staticCtx ->
		def studies = null
		Study.withTransaction {
			studies = Study.findAllByPublished(true).findAll {
				File f = fileService.getFileReference(staticCtx, makeFilename(it));
				println it.project.owner
				!f.exists() || f.lastModified() < it.lastUpdated.getTime()
			}
		}
		studies.each {
			def related = [
				makeRelation(it.project.owner, "isManagedBy"),
				makeRelation(StaticMetadataObject.findByShortDescription('bml'), "isOwnedBy")
				]
			objectToXml(staticCtx, createStudyXml, it, related)
		}
	}
	
	private def usersToXml =
	{
		staticCtx ->
		def users = null
		UserStore.withTransaction() {
			users = UserStore.findAllByPublished(true).findAll {
				File f = fileService.getFileReference(staticCtx, makeFilename(it));
				!f.exists() || f.lastModified() < it.lastUpdated.getTime()
			}
		}
		users.each {
			def related = Study.findAll("from Study as study where study.published and study.project.owner.id=" + it.id).collect {
				makeRelation(it,"isManagerOf")
			}
			objectToXml(staticCtx, createUserXml, it, related)
		}
	}
	
	private def staticsToXml =
	{
		staticCtx ->
		staticUOWToXml(staticCtx, StaticMetadataObject.findByShortDescription('uow'))
		staticBMLToXml(staticCtx, StaticMetadataObject.findByShortDescription('bml'))
	}
	
	private def staticUOWToXml =
	{
		staticCtx, staticData ->
		def bml = StaticMetadataObject.findByShortDescription('bml')
		def related = [makeRelation(makeKey(bml), "hasPart")]
		objectToXml(staticCtx, createStaticXml, staticData, related)
	}

	private def staticBMLToXml =
	{
		staticCtx, staticData ->
		def uow = StaticMetadataObject.findByShortDescription('uow')
		def related = [makeRelation(makeKey(uow), "hasPart")]
		related.plus(Study.findAllByPublished(true).collect {
			makeRelation(makeKey(it),"isOwnerOf")
			})
		objectToXml(staticCtx, createStaticXml, staticData, related)
	}
	
	private def objectToXml =
	{
		staticCtx, Closure closure, object, related ->
		println("Publishing " + makeName(object) + ' @ ' + (new Date()))
		File file = fileService.getFileReference(staticCtx, makeFilename(object))
		def xml = closure(object, related)		
		new FileOutputStream(file) << streamXml(xml)
	}
	
	private def createStaticXml =
	{
		obj, related ->
		def root = new XmlSlurper().parseText(obj.xmlContent)
		related.each { 
			root.registryObjects.registryObject.appendNode {
					relatedObject {
						key(it['key'])
						relation(type:it['type'])
					}
			}
		}
		return root
	}
	
	public def streamXml =
	{
		xml ->
		new StreamingMarkupBuilder().bind(xml)
	}
	
	public def createStudyXml =
	{
		Study study, related ->
		println related
		def root = { builder ->
			mkp.xmlDeclaration()
			mkp.declareNamespace('':'http://ands.org.au/standards/rif-cs/registryObjects')
			registryObjects {
				registryObject(group:common['@group']) {
					key(makeKey(study))
					originatingSource(type:"authoritative") { mkp.yield(common['originatingSource'])}
					collection(type:"collection") {
						name(type:"primary") {
							namePart { mkp.yield(study.studyTitle)}
						}
						identifier(type:"local") { mkp.yield(makeName(study)) }
						location {
							address {
								physical(type:"streetAddress") {
									value { mkp.yield(common['collection.physical.address']) }
								}
							}
							address {
								electronic(type:"email") {
									value { mkp.yield(common['collection.email.address']) }
								}
							}
						}
						description(type:"full") { mkp.yield(study.description) }
						description(type:"rights") { mkp.yield(common['collection.accessRights']) }
					}
					related.each { relatedAssoc ->
						relatedObject {
							key(relatedAssoc['key'])
							relation('type':relatedAssoc['type'])
						}
					}
				}
	
			}
		}
		println '>>------------ ' + (new Date()) + ' -------------->>'
		System.out << streamXml(root)
		println '....................................................'

		return root
	}
	
	private def createUserXml =
	{
		UserStore user, related ->
		def builder = new StreamingMarkupBuilder()
		builder.encoding = "UTF-8"
		def ldapUser = LdapUser.find({ like "id:" + user.username })
		def root = {
			mkp.xmlDeclaration()
			mkp.declareNamespace('':'http://ands.org.au/standards/rif-cs/registryObjects')
			registryObjects {
				registryObject(group:common['@group']) {
					key(makeKey(user))
					originatingSource(type:"authoritative") { mkp.yield(common['originatingSource'])}
					party(type:"person") {
						name(type:"primary") {
							namePart(type:"first") { mkp.yield(ldapUser.firstName)}
							namePart(type:"last") { mkp.yield(ldapUser.lastName)}
						}
						identifier(type:"local") { mkp.yield(user.username) }
						location {
							address {
								physical(type:"streetAddress") {
									addressPart(type:"text") { mkp.yield(common['collection.physical.address']) }
								}
							}
							address {
								electronic(type:"email") {
									addressPart(type:"text") { mkp.yield(ldapUser.email) }
								}
							}
						}
					}
				}
			}
		}
		return root
	}

}
