package au.org.intersect.bdcp

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import java.io.File

class SessionFileController
{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def fileService
    
	private String getTmpPath()
	{
		return (request.getSession().getServletContext().getRealPath("/") + grailsApplication.config.tmp.location.toString())
	}

	private String getRealPath()
	{
		return (request.getSession().getServletContext().getRealPath("/") + grailsApplication.config.files.location.toString())
	}

	def upload =
	{
        if (params.sessionId != null && params.studyId != null && params.dirStruct != null)
        {
            def context = fileService.createContext(request.getSession().getServletContext().getRealPath("/"))
            fileService.setParams(params)
            def dirstruct = params.dirStruct
            def parsed_json = JSON.parse(dirstruct)
            def upload_root = "${params.studyId}/${params.sessionId}/"
            def success = (fileService.createAllFolders(context,parsed_json, upload_root) == true) ? true: false
            success = (success == true && fileService.createAllFiles(context,parsed_json, upload_root) == true) ? true:false
            def final_location_root = "${params.studyId}"
            success = (success == true && fileService.moveDirectory(context,upload_root, final_location_root) == true)? true: false
            if (success)
            {
                render "Successfully Uploaded Files!"
            }
            else
            {
                response.sendError 500
            }
        }
        else
        {
            response.sendError 500
        }
		
	}

	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def index =
	{
		redirect(action: "list", params: params)
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def fileList =
	{
		def studyInstance = Study.get(params.studyId)
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		def sessionFiles = [:]
		
		studyInstance.components.each {
			it.sessions.each {
				def dir = new File("${getRealPath()}${studyInstance.id}/${it.id}")
				sessionFiles.putAt "${it.id}", dir.listFiles()
			}
		}
		[componentInstanceList: Component.findAllByStudy(studyInstance), componentInstanceTotal: Component.countByStudy(studyInstance), studyInstance: studyInstance, sessionFiles: sessionFiles]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def uploadFiles =
	{
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def createDirectory =
	{ 
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def saveDirectory =
	{ directoryCommand dirCmd ->
		
		if (dirCmd.hasErrors())
		{
			render(view: "createDirectory", model: [directoryCommand: dirCmd, studyId: params.studyId, sessionId: params.sessionId])
		}
		else
		{
			println dirCmd.name
			redirect( mapping:"sessionFileDetails", controller: "sessionFile", action: "fileList", params: [studyId: params.studyId, sessionId: params.sessionId])
		}
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def browseFiles =
	{
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def list =
	{
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[sessionFileInstanceList: SessionFile.list(params), sessionFileInstanceTotal: SessionFile.count()]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def create =
	{
		def sessionFileInstance = new SessionFile()
		sessionFileInstance.properties = params
		return [sessionFileInstance: sessionFileInstance]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def save =
	{
		def sessionFileInstance = new SessionFile(params)
		if (sessionFileInstance.save(flush: true))
		{
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'sessionFile.label', default: 'SessionFile'), sessionFileInstance.id])}"
			redirect(action: "show", id: sessionFileInstance.id)
		}
		else
		{
			render(view: "create", model: [sessionFileInstance: sessionFileInstance])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def show =
	{
		def sessionFileInstance = SessionFile.get(params.id)
		if (!sessionFileInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sessionFile.label', default: 'SessionFile'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			[sessionFileInstance: sessionFileInstance]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def edit =
	{
		def sessionFileInstance = SessionFile.get(params.id)
		if (!sessionFileInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sessionFile.label', default: 'SessionFile'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			return [sessionFileInstance: sessionFileInstance]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def update =
	{
		def sessionFileInstance = SessionFile.get(params.id)
		if (sessionFileInstance)
		{
			if (params.version)
			{
				def version = params.version.toLong()
				if (sessionFileInstance.version > version)
				{

					sessionFileInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'sessionFile.label', default: 'SessionFile')]
					as Object[], "Another user has updated this SessionFile while you were editing")
					render(view: "edit", model: [sessionFileInstance: sessionFileInstance])
					return
				}
			}
			sessionFileInstance.properties = params
			if (!sessionFileInstance.hasErrors() && sessionFileInstance.save(flush: true))
			{
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'sessionFile.label', default: 'SessionFile'), sessionFileInstance.id])}"
				redirect(action: "show", id: sessionFileInstance.id)
			}
			else
			{
				render(view: "edit", model: [sessionFileInstance: sessionFileInstance])
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sessionFile.label', default: 'SessionFile'), params.id])}"
			redirect(action: "list")
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def delete =
	{
		def sessionFileInstance = SessionFile.get(params.id)
		if (sessionFileInstance)
		{
			try
			{
				sessionFileInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'sessionFile.label', default: 'SessionFile'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e)
			{
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'sessionFile.label', default: 'SessionFile'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sessionFile.label', default: 'SessionFile'), params.id])}"
			redirect(action: "list")
		}
	}
}

class directoryCommand
{
	String name
	String path
	
	static constraints =
	{
		name(blank:false, size:1..255)
		path(blank:false)
	}
}