package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured

class ProjectController
{
	def springSecurityService
	
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def index =
	{
		cache false
		redirect(action: "list", params: params)
	}

	
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
	def list =
	{
		cache false
		
		// TODO only a researcher can look at their own project
//		checkRoleResearcher();
				
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[projectInstanceList: Project.list(params), projectInstanceTotal: Project.count()]
	}

	/**
	 * Assign username to all projects owned by a researcher...
	 */
	private void checkRoleResearcher()
	{
		println "Hi there \n"
		def auth = springSecurityService.authentication;
		def role = auth.getPrincipal().getAuthorities()[0];
		if(role.equals('ROLE_RESEARCHER')) {
			println "Role is researcher!!!! \n"
		}
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def create =
	{
		cache false
		
		// TODO only a researcher can look at their own project
		/*def myUser = currentUser()*/
//		print "the userKarlxss is: \n" 
//		def userDetailsService = springSecurityService.getUserDetailsService();
//		print "the userDetailsService is: " + userDetailsService.getPrincipal();
//		print "the userDetailsService.dump() is: " + userDetailsService.dump();
//		def auth = springSecurityService.authentication;
//		println "the auth is:" + auth.toString();
//		println "the credentials is: " + auth.getCredentials();
//		println "the principal is: " + auth.getPrincipal();
//		println "the principal.grantedAuthorities is: " + auth.getPrincipal().getAuthorities();
//		def authority1 = auth.getPrincipal().getAuthorities()[0];
		
//		println "This is my role: " + authority1
//		if(authority1.equals('ROLE_LAB_MANAGER')) {
//			println "this is my role..."
//		}
		
		def projectInstance = new Project()
		projectInstance.properties = params
		return [projectInstance: projectInstance]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def save =
	{
		cache false
		def projectInstance = new Project(params)
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
			[projectInstance: projectInstance]
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
