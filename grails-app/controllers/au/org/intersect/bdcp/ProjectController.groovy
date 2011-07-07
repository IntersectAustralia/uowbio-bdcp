package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.commons.ApplicationHolder

class ProjectController
{
	def springSecurityService
	
	def roleCheckService
	
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def index =
	{
		cache false
		redirect(action: "list", params: params)
	}

	
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def list =
	{
		cache false
						
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def projectInstanceList = Project.list(params);

		// A researcher can look at their own project
		if(roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			projectInstanceList = Project.findAllByOwner(UserStore.findByUsername(principal.username));
		}
		
		[projectInstanceList: projectInstanceList, projectInstanceTotal: Project.count()]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def create =
	{
		cache false
		def projectInstance = new Project()
		projectInstance.properties = params
		
		return [projectInstance: projectInstance]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def save =
	{
		cache false
		def projectInstance = new Project(params)
		
		def userStore = UserStore.findByUsername(principal.username)
		projectInstance.setOwner(userStore);
		
		if (projectInstance.save(flush: true))
		{
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'project.label', default: 'Project'), projectInstance.projectTitle])}"
			redirect(action: "list", id: projectInstance.id)
		}
		else
		{
			render(view: "create", model: [projectInstance: projectInstance])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def show =
	{
		cache false
		def projectInstance = Project.get(params.id)
		
		if (!projectInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			// if researcher role, display research project to researcher who owns project, else redirect to non authorized access page.
			if(roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
				redirectNonAuthorizedResearcherAccessProject(projectInstance)
			}
			[projectInstance: projectInstance]
		}
	}
	
	/**
	 * Display project only to research owner
	 * @param _projectInstance
	 */
	private void redirectNonAuthorizedResearcherAccessProject(Project _projectInstance)
	{
		if(!_projectInstance.researcherName.equals(principal.username)){
			redirect controller:'login', action: 'denied'
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def edit =
	{
		cache false
		def projectInstance = Project.get(params.id)
		if (!projectInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			return [projectInstance: projectInstance]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def update =
	{
		cache false
		def projectInstance = Project.get(params.id)
		if (projectInstance)
		{
			if (params.version)
			{
				def version = params.version.toLong()
				if (projectInstance.version > version)
				{

					projectInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'project.label', default: 'Project')]
					as Object[], "Another user has updated this Project while you were editing")
					render(view: "edit", model: [projectInstance: projectInstance])
					return
				}
			}
			projectInstance.properties = params
			if (!projectInstance.hasErrors() && projectInstance.save(flush: true))
			{
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'project.label', default: 'Project'), projectInstance.projectTitle])}"
				redirect(action: "show", id: projectInstance.id)
			}
			else
			{
				render(view: "edit", model: [projectInstance: projectInstance])
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
			redirect(action: "list")
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def delete =
	{
		cache false
		def projectInstance = Project.get(params.id)
		if (projectInstance)
		{
			try
			{
				projectInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e)
			{
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
			redirect(action: "list")
		}
	}
	
	private currentUser() {
		return User.get (springSecurityService.principal.id)
	}
}
