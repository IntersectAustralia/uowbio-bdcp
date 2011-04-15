package au.org.intersect.bdcp

import grails.converters.JSON

class SessionFileController
{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

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
			def dirstruct = params.dirStruct
			def parsed_json = JSON.parse(dirstruct)

			def upload_root = "${getTmpPath()}/${params.studyId}/${params.sessionId}/"

			// Create all folders
			parsed_json.findAll { p,q ->
				p.startsWith("folder")
			}.each
			{ key, val ->
				def path = val[0]
				def filepath = upload_root + path
				def directory = new File(filepath)
				if (!directory.exists())
				{
					directory.mkdirs()
				}
			}

			// Create all files
			parsed_json.findAll { p,q ->
				p.startsWith("file")
			}.each
			{ key, val ->
				def path = val[0]
				def file = params[key]
				def filepath = new File(upload_root + path)
				file.transferTo(filepath)
			}
			
			render "Successfully Uploaded!"
		}
		else
		{
			response.sendError 404
		}
	}


	def index =
	{
		redirect(action: "list", params: params)
	}

	def fileList =
	{
		def studyInstance = Study.get(params.studyId)
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[componentInstanceList: Component.findAllByStudy(studyInstance), componentInstanceTotal: Component.countByStudy(studyInstance), studyInstance: studyInstance]
	}

	def showTempFiles =
	{ 
		File rootDir = new File("${getTmpPath()}/${params.studyId}/${params.sessionId}");
		// This filter only returns directories
		FileFilter fileFilter = new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();
			}
		};
		def dirsToOpen = rootDir.listFiles(fileFilter);
		StringBuffer sb = new StringBuffer()
		dirsToOpen.each 
		{ 
				sb.append "'"+it.toString() + '/'+"'" +","
		}
		if (sb.length() > 0)
		{
			sb.setLength(sb.length() -1)
		}
		def expandedDirs = "[${sb.toString()}]"
		render (view: "FileBrowser", model: [path: "${getTmpPath()}/${params.studyId}/${params.sessionId}", expandedDirs:expandedDirs]) 
	}

	def generateFileList = {
	
	}

	def clearTempFiles = {
		println "Clear Temp Files..."
		File rootDir = new File("${getTmpPath()}/${params.studyId}/${params.sessionId}");
		rootDir.deleteDir()
		render (view: "uploadFiles")
	}
	
	def uploadFiles =
	{
	}

	def createDirectory =
	{
	}

	def browseFiles =
	{
	}

	def list =
	{
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[sessionFileInstanceList: SessionFile.list(params), sessionFileInstanceTotal: SessionFile.count()]
	}

	def create =
	{
		def sessionFileInstance = new SessionFile()
		sessionFileInstance.properties = params
		return [sessionFileInstance: sessionFileInstance]
	}

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
