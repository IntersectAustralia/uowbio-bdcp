package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured

class ComponentController
{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def fileService
    
    def createContext(def servletRequest)
    {
        return fileService.createContext(servletRequest.getSession().getServletContext().getRealPath("/"))
    }
    
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def index =
	{
		redirect(action: "list", params: params)
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def list =
	{
		def studyInstance = Study.get(params.studyId)
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[componentInstanceList: Component.findAllByStudy(studyInstance), componentInstanceTotal: Component.countByStudy(studyInstance), studyInstance: studyInstance]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def create =
	{
		def componentInstance = new Component()
		componentInstance.properties = params
		return [componentInstance: componentInstance]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def save =
	{
		def componentInstance = new Component(params)
        def context = createContext(request)
        def path = params.studyId +"/"
        if (componentInstance.save(flush: true))
        {
            fileService.createDirectory(context,params.studyId.toString(),"")
            fileService.createDirectory(context,componentInstance.id.toString(), path)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'component.label', default: 'Component'), componentInstance.name])}"
			//            redirect(action: "show", id: componentInstance.id)
			redirect(mapping:"componentDetails",controller: "component", action: "list", id: params.studyId, params:[studyId: params.studyId])
		}
		else
		{
			render(view: "create", model: [componentInstance: componentInstance])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def show =
	{
		def componentInstance = Component.get(params.id)
		if (!componentInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'component.label', default: 'Component'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			[componentInstance: componentInstance]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def edit =
	{
		def componentInstance = Component.get(params.id)
		if (!componentInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'component.label', default: 'Component'), params.name])}"
			redirect(mapping:"componentDetails",controller: "component", action: "list", id: params.studyId, params:[studyId: params.studyId])
		}
		else
		{
			return [componentInstance: componentInstance, studyId: params.studyId]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def update =
	{
		def componentInstance = Component.get(params.id)
		if (componentInstance)
		{
			if (params.version)
			{
				def version = params.version.toLong()
				if (componentInstance.version > version)
				{

					componentInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'component.label', default: 'Component')]
					as Object[], "Another user has updated this Component while you were editing")
					render(view: "edit", model: [componentInstance: componentInstance])
					return
				}
			}
			componentInstance.properties = params
			if (!componentInstance.hasErrors() && componentInstance.save(flush: true))
			{
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'component.label', default: 'Component'), componentInstance.name])}"
				redirect(mapping:"componentDetails",controller: "component", action: "list", id: params.studyId, params:[studyId: params.studyId])
			}
			else
			{
				render(view: "edit", model: [componentInstance: componentInstance])
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'component.label', default: 'Component'), params.id])}"
			redirect(mapping:"componentDetails",controller: "component", action: "list", id: params.studyId, params:[studyId: params.studyId])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def delete =
	{
		def componentInstance = Component.get(params.id)
		if (componentInstance)
		{
			try
			{
				componentInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'component.label', default: 'Component'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e)
			{
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'component.label', default: 'Component'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'component.label', default: 'Component'), params.id])}"
			redirect(action: "list")
		}
	}
}
