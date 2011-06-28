package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured

class SessionController
{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def fileService
    
    def createContext(def servletRequest)
    {
        return fileService.createContext(servletRequest.getSession().getServletContext().getRealPath("/"))
    }
    
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def index =
	{
		redirect(action: "list", params: params)
	}

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def list =
	{
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[sessionInstanceList: Session.list(params), sessionInstanceTotal: Session.count()]
	}

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def create =
	{
		def sessionInstance = new Session()
		sessionInstance.properties = params
		return [sessionInstance: sessionInstance]
	}

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def save =
	{
        def context = createContext(request)
        def sessionInstance = new Session(params)
        def path = params.studyId +"/" + sessionInstance.component.id + "/"
		if (sessionInstance.save(flush: true))
		{
            fileService.createDirectory(context,params.studyId.toString(),"")
            fileService.createDirectory(context,sessionInstance.id.toString(), path)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'session.label', default: 'Session'), sessionInstance.name])}"
			redirect(mapping:"componentDetails",controller: "component", action: "list", id: params.studyId, params:[studyId: params.studyId])
		}
		else
		{
			render(view: "create", model: [sessionInstance: sessionInstance])
		}
	}

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def show =
	{
		def sessionInstance = Session.get(params.id)
		if (!sessionInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'session.label', default: 'Session'), params.name])}"
			redirect(action: "list")
		}
		else
		{
			[sessionInstance: sessionInstance]
		}
	}

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def edit =
	{
		def sessionInstance = Session.get(params.id)
		if (!sessionInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'session.label', default: 'Session'), params.name])}"
			redirect(mapping:"componentDetails",controller: "component", action: "list", id: params.studyId, params:[studyId: params.studyId])
		}
		else
		{
			return [sessionInstance: sessionInstance]
		}
	}

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def update =
	{
		def sessionInstance = Session.get(params.id)
		if (sessionInstance)
		{
			if (params.version)
			{
				def version = params.version.toLong()
				if (sessionInstance.version > version)
				{

					sessionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'session.label', default: 'Session')]
					as Object[], "Another user has updated this Session while you were editing")
					render(view: "edit", model: [sessionInstance: sessionInstance])
					return
				}
			}
			sessionInstance.properties = params
			if (!sessionInstance.hasErrors() && sessionInstance.save(flush: true))
			{
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'session.label', default: 'Session'), sessionInstance.name])}"
				redirect(mapping:"componentDetails",controller: "component", action: "list", id: params.studyId, params:[studyId: params.studyId])
			}
			else
			{
				render(view: "edit", model: [sessionInstance: sessionInstance])
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'session.label', default: 'Session'), params.name])}"
			redirect(mapping:"componentDetails",controller: "component", action: "list", id: params.studyId, params:[studyId: params.studyId])
		}
	}

}
