package au.org.intersect.bdcp

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import groovy.xml.MarkupBuilder

import java.io.File
import java.io.IOException

import org.apache.commons.io.FileUtils

class SessionFileController
{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	static int MAX_DEPTH = 5
	
	private String getTmpPath()
	{
		return (request.getSession().getServletContext().getRealPath("/") + grailsApplication.config.tmp.location.toString())
	}

	private String getRealPath()
	{
		return (request.getSession().getServletContext().getRealPath("/") + grailsApplication.config.files.location.toString())
	}

	private boolean createAllFolders(parsed_json, upload_root)
	{
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
		return true;
	}
	
	private boolean createAllFiles(parsed_json, upload_root)
	{
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
		return true
	}
	
	
	private boolean moveDirectory(upload_root, final_location_root)
	{
		try
		{
			File oldDir = new File(upload_root)
			File newDir = new File(final_location_root)
			FileUtils.moveDirectory(oldDir, newDir)
			FileUtils.deleteDirectory(oldDir)
		}
		catch (IOException ex)
		{
			return false
		}
		return true;
	}
	
	public static List listRecursively(File fdir, int depth) {
		
        def map
		def files = []
		if (fdir.isDirectory() && depth < MAX_DEPTH) {
            for (File f : fdir.listFiles()) {  // Go over each file/subdirectory.
                files.addAll(listRecursively(f, depth+1));
            }
			map = ['name':fdir.getName(), 'path':fdir.getPath() , 'directory':true, 'parent': fdir.getParent()]
			
        }
		else
		{
			map = ['name':fdir.getName(), 'path':fdir.getPath() , 'directory':false, 'parent': fdir.getParent()]
		}
		
		files.addAll(map)
		
		return files
	}
	

	def upload =
	{
		if (params.sessionId != null && params.studyId != null && params.dirStruct != null)
		{
			def dirstruct = params.dirStruct
			def parsed_json = JSON.parse(dirstruct)

			def upload_root = "${getTmpPath()}/${params.studyId}/${params.sessionId}/"

			def success = (createAllFolders(parsed_json, upload_root) == true) ? true: false
			success = (success == true && createAllFiles(parsed_json, upload_root) == true) ? true:false
			
			def final_location_root = "${getRealPath()}${params.studyId}/${params.sessionId}/"
			
			success = (success == true && moveDirectory(upload_root, final_location_root) == true)? true: false
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

	def test3 = 
	{
		
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def index =
	{
		redirect(action: "list", params: params)
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def test =
	{
		def studyInstance = Study.get(params.studyId)
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		def sessionFiles = [:]
		studyInstance.components.each {
			it.sessions.each {
				File rootDir = new File("${getRealPath()}/${studyInstance.id}/${it.id}");
				def files = []
				def map
				rootDir.listFiles().each
				{
					if (it != null && it.isDirectory()) {
						files.addAll(listRecursively(it, 0))
					} else {
						map = ['name':it.getName(), 'directory':false, 'parent': it.getParent()]
						files.addAll(map)
					}
				}
				
				if (files != null)
				{
					sessionFiles.putAt "${it.id}",files
				}
				
			}
		}
		
		[componentInstanceList: Component.findAllByStudy(studyInstance), componentInstanceTotal: Component.countByStudy(studyInstance), studyInstance: studyInstance, sessionFiles: sessionFiles]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def test2 =
	{
		def studyInstance = Study.get(params.studyId)
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		def sessionFiles = [:]
		studyInstance.components.each {
			it.sessions.each {
				File rootDir = new File("${getRealPath()}/${studyInstance.id}/${it.id}");
				def files = []
				def map
				rootDir.listFiles().each
				{
					if (it != null && it.isDirectory()) {
						files.addAll(listRecursively(it, 0))
					} else {
						map = ['name':it.getName(), 'path':it.getPath() , 'directory':false, 'parent': it.getParent()]
						files.addAll(map)
					}
				}
				
				if (files != null)
				{
					sessionFiles.putAt "${it.id}",files
				}
				
			}
		}
		
		
		
		def writer = new StringWriter()
		def builder = new groovy.xml.MarkupBuilder(writer)
		builder.root() {
			for (componentInstance in studyInstance.components)
			{
				
				item(id: "component_" + componentInstance.id, parent_id:0, rel:"drive")
				
				{
					content
					{
						name(componentInstance.name)
					}
				}
				
				for (sessionInstance in componentInstance.sessions)
				{
					item(id:"${getRealPath()}${studyInstance.id}/${sessionInstance.id}" , parent_id:"component_"+componentInstance.id, rel:"drive")
					
					{
						content
						{
							name(sessionInstance.name)
						}
					}
					
					for (e in sessionFiles.getAt(sessionInstance.id.toString())) 
					 { 
						 
						 def rel="file"
						 if (e.directory == true)
						 {
							 rel = "folder"
						 }
						 item(id: e.path, parent_id:e.parent, rel:rel)
						 
						 {
							 content
							 {
								 name(e.name)
							 }
						 }
					 }
				}
			}
		}
	  
		def nodes = writer.toString()
		println nodes
		nodes = nodes.replaceAll("[\r\n]+", " ")
		[componentInstanceList: Component.findAllByStudy(studyInstance), componentInstanceTotal: Component.countByStudy(studyInstance), studyInstance: studyInstance, sessionFiles: sessionFiles, nodes: nodes]
	}
	
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def fileList =
	{
		def studyInstance = Study.get(params.studyId)
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		def sessionFiles = [:]
		studyInstance.components.each {
			it.sessions.each {
				File rootDir = new File("${getRealPath()}/${studyInstance.id}/${it.id}");
				def files = []
				def map
				rootDir.listFiles().each
				{
					if (it != null && it.isDirectory()) {
						files.addAll(listRecursively(it, 0))
					} else {
						map = ['name':it.getName(), 'directory':false, 'parent': it.getParentFile()?.getName()]
						files.addAll(map)
					}
				}
				
				if (files != null)
				{
					sessionFiles.putAt "${it.id}",files
				}
				
			}
		}
		[componentInstanceList: Component.findAllByStudy(studyInstance), componentInstanceTotal: Component.countByStudy(studyInstance), studyInstance: studyInstance, sessionFiles: sessionFiles]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def showTempFiles =
	{
		File rootDir = new File("${getTmpPath()}/${params.studyId}/${params.sessionId}");
		// This filter only returns directories
		FileFilter fileFilter = new FileFilter() {
					public boolean accept(File file)
					{
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

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def generateFileList =
	{
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def clearTempFiles =
	{
		println "Clear Temp Files..."
		File rootDir = new File("${getTmpPath()}/${params.studyId}/${params.sessionId}");
		rootDir.deleteDir()
		render (view: "uploadFiles")
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
