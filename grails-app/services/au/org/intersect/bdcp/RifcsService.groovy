package au.org.intersect.bdcp

import java.io.File
import java.util.concurrent.Executors
import groovy.xml.StreamingMarkupBuilder

class RifcsService
{
	private static final int THREADS = 3
	private static final String FILEPREFIX_STUDY = "study"
	private static final String EMPTY_RO = """<?xml version="1.0" encoding="UTF-8"?>
<registryObjects xmlns="http://ands.org.au/standards/rif-cs/registryObjects" 
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
                 xsi:schemaLocation="http://ands.org.au/standards/rif-cs/registryObjects http://services.ands.org.au/home/orca/schemata/registryObjects.xsd">
</registryObjects>  
"""
	private def executor = Executors.newFixedThreadPool(THREADS)
	
    static transactional = false

    def fileService
	
	def scheduleStudyPublishing = 
	{
		ctxPath, rifcs, id ->
		def staticCtx = ensureDirs(ctxPath)
		executor.submit {
			studyToXml(staticCtx, rifcs, id)
		}
	}
	
	private def ensureDirs =
	{	baseCtxPath ->
		def staticCtx = fileService.createContext(baseCtxPath, "rifcs")
		fileService.getFileReference(staticCtx, ".").mkdirs()
		return staticCtx
	}
	
	private String studyName(Long id) {
		return FILEPREFIX_STUDY + "~" + id;
	}
	
	private def studyToXml =
	{
		staticCtx, Map rifcs, Long id ->
		println("Publishing study " + id)
		File file = fileService.getFileReference(staticCtx, studyName(id) + ".xml")
		def xml = createStudyXml(rifcs, id)
		new FileOutputStream(file) << new StreamingMarkupBuilder().bind { xml }
		// link static objects
	}
	
	private def createStudyXml =
	{
		Map rifcs, Long id ->
		def root = createRoot()
		root.appendNode {
			registryObject(group:rifcs['@group']) {
				key { item("oai:au.edu.uow.dbcp:" + studyName(id)) }
				originatingSource(type:"authoritative") { item(rifcs['originatingSource']) }
				party(type:"collection") {
					name(type:"primary") {
						namePart(type:"title") { item(rifcs['collection.name']) }
					}
				}
			}
		}
		return root
	}
	
	private def createRoot =
	{
		return new XmlSlurper().parseText(EMPTY_RO)
	}
	
}
