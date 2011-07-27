package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured
import au.org.intersect.bdcp.rifcs.Rifcs
import au.org.intersect.bdcp.ldap.LdapUser

class StudyController
{
	
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def rifcsService
	
	def getContextRootPath(def servletRequest)
	{
		return servletRequest.getSession().getServletContext().getRealPath("/")
	}
	

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def index =
	{
		cache false
		redirect(action: "list", params: params)
	}


	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def list =
	{
		cache false
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[studyInstanceList: Study.list(params), studyInstanceTotal: Study.count()]
	}

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
    def device = 
    {
        cache false
        
    }
    
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def create =
	{
		cache false
		def studyInstance = new Study()
		studyInstance.properties = params
		return [studyInstance: studyInstance, projectid: params.projectid]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def listCollaborators =
	{
		cache false
		def studyInstance = Study.get(params.studyId)
		params.max = Math.min(params.max ? params.int('max') : 10, 100)

		def collaborators = studyInstance.studyCollaborators.collect { it.collaborator }
		collaborators = collaborators.sort {x,y -> x.username <=> y.username}

		[studyInstance: studyInstance, collaboratorInstanceList: collaborators, collaboratorInstanceTotal: collaborators.size()]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def searchCollaborators =
	{
		cache false
		def studyInstance = Study.get(params.studyId)
		def matches = []

		[matches:matches, studyInstance: studyInstance]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def addCollaborator = 
	{
		cache false
		def studyInstance = Study.get(params.studyId)
	
		def userStore = UserStore.findByUsername(params.username)

		def studyCollaborator = new StudyCollaborator(studyInstance,userStore)
		studyCollaborator.save(flush: true)

		
		[studyInstance: studyInstance, username: params.username]
	}
	
	private String normalizeValue(value)
	{
		value = value.replaceAll(/[^A-Za-z0-9-]/, '')
		return value
	}
	
	@Secured(['IS_AUTHENTICATED_FULLY', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def listUsers =
	{
		cache false
		def studyInstance = Study.get(params.studyId)
		
		if (params.firstName != null)
		{
			session.firstName = params.firstName
		}
		else
		{
			session.firstName = ""
		}
		if (params.surname != null)
		{
			session.surname = params.surname
		}
		else
		{
			session.surname=""
		}
		if (params.userid != null)
		{
			session.userid = params.userid
		}
		else
		{
			session.userid = ""
		}
		
		def ldapUsers = []
		ldapUsers = LdapUser.findAll()
		{
			and
			{
				if (!session.userid?.isEmpty())
				{
					like "uid", "*" + normalizeValue(session.userid) + "*"
				}
				else
				{
					like "uid", "*"
				}
			}
			and
			{
				if (!session.surname?.isEmpty())
				{
					like "sn", "*" + normalizeValue(session.surname) + "*"
				}
				else
				{
					like "sn", "*"
				}
			}
			and
			{
				if (!session.firstName?.isEmpty())
				{
					like "givenName", "*" + normalizeValue(session.firstName) +"*"
				}
				else
				{
					like "givenName", "*"
				}
			}
		}
		
		def activatedMatches = []
		UserStore.list().each
		{
			// if not deactivated user and user not the owner of the study
			if ((!it?.deactivated) && (it?.username != studyInstance.project.owner.username))
			{
				activatedMatches << LdapUser.find(filter: "(uid=${it?.username})")
			}
		}
		
		activatedMatches = removeExistingCollaboratorsFromMatches(studyInstance, activatedMatches)
		
		ldapUsers.retainAll(activatedMatches)
		
		def sortedActivatedMatches = ldapUsers.sort
		{x,y -> x.sn <=> y.sn}
		
		render (view: "searchCollaborators", model: [firstName: params.firstName, surname:params.surname, userid:params.userid, matches: sortedActivatedMatches, studyInstance: studyInstance])

	}
	
	private Object removeExistingCollaboratorsFromMatches(studyInstance, activatedMatches)
	{
		for (collaboratorName in studyInstance.studyCollaborators.collaborator.username) {
			activatedMatches = activatedMatches.findAll { !(it?.uid == collaboratorName)}
		}
		return activatedMatches;
	}
	
	private Object matchWithSearchParameters(activatedMatches, matches)
	{
		for (match in matches) {
			println "the match was..." + match
		}
		return activatedMatches;
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def save =
	{
		cache false
		def studyInstance = new Study(params)
		if (studyInstance.save(flush: true))
		{
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'study.label', default: 'Study'), studyInstance.studyTitle])}"
			redirect(action: "show", id: studyInstance.id)
		}
		else
		{
			render(view: "create", model: [studyInstance: studyInstance, projectid: params.projectid])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def show =
	{
		cache false
		def studyInstance = Study.get(params.id)
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		if (!studyInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'study.label', default: 'Study'), params.id])}"
			redirect(controller: "project", action: "list")
		}
		else
		{
			[studyInstance: studyInstance, participantInstanceList: Participant.findAllByStudy(studyInstance), participantInstanceTotal: Participant.countByStudy(studyInstance),projectid: params.projectid]
		}
	}


	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def showRifcs = 
	{
		cache false
		def studyInstance = Study.get(params.id)
		if (!studyInstance)
		{
			response.contentType = "text/plain"
			render "ERROR"
			return
		}
		else
		{
			render(template:'rifcsPreview',model:buildRifcs(studyInstance))
		}
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def publishRifcs = 
	{
		cache false
		def studyInstance = Study.get(params.id)
		if (!studyInstance)
		{
			response.contentType = "text/plain"
			render "ERROR"
			return
		}
		else
		{
			def rifcs = buildRifcs(studyInstance)
			rifcsService.scheduleStudyPublishing(getContextRootPath(request), rifcs, studyInstance.id)
			response.contentType = "text/plain"
			render "OK"
			return
		}
	}
	
	def buildRifcs =
	{ studyInstance ->
		def rifcs = new Rifcs()
		def specials = [
			'collection.identifier.local' :  createLink(mapping:'studyDetails', action:'show', params:[id:studyInstance.id, projectId:studyInstance.project.id])
			]
		def rifcsModel = rifcs.fromStudy(studyInstance, specials)
		return [rifcs:rifcsModel]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def edit =
	{
		cache false
		def studyInstance = Study.get(params.id)
		if (!studyInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'study.label', default: 'Study'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			return [studyInstance: studyInstance]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def update =
	{
		cache false
		def studyInstance = Study.get(params.id)
		if (studyInstance)
		{
			if (params.version)
			{
				def version = params.version.toLong()
				if (studyInstance.version > version)
				{

					studyInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'study.label', default: 'Study')]
					as Object[], "Another user has updated this Study while you were editing")
					render(view: "edit", model: [studyInstance: studyInstance])
					return
				}
			}
			studyInstance.properties = params
			if (!studyInstance.hasErrors() && studyInstance.save(flush: true))
			{
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'study.label', default: 'Study'), studyInstance.studyTitle])}"
				redirect(action: "show", id: studyInstance.id)
			}
			else
			{
				render(view: "edit", model: [studyInstance: studyInstance])
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'study.label', default: 'Study'), params.id])}"
			redirect(action: "list")
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def delete =
	{
		cache false
		def studyInstance = Study.get(params.id)
		if (studyInstance)
		{
			try
			{
				studyInstance.delete(flush: true)
				//				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'study.label', default: 'Study'), params.id])}"
				//				redirect(action: "list")
				redirect(controller:"project", action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e)
			{
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'study.label', default: 'Study'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'study.label', default: 'Study'), params.id])}"
			redirect(action: "list")
		}
	}
}
