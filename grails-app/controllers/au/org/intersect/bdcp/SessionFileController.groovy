package au.org.intersect.bdcp

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import java.util.Set
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class SessionFileController
{

	def fileService
	
	def roleCheckService
	
//	def pattern = Pattern.compile("^(*)/(.*)\$")
	
	def createContext(def servletRequest)
	{
		return fileService.createContext(servletRequest.getSession().getServletContext().getRealPath("/"), "session")
	}

	def securedCommon = { onErrors, block ->
		cache false
		
		def studyInstance = Study.get(Long.parseLong(params.studyId))
		if (studyInstance == null) {
			onErrors.badStudyId()
			return
		}
		
		def canDo = roleCheckService.checkUserRole('ROLE_LAB_MANAGER');
		canDo = canDo || roleCheckService.checkSameUser(studyInstance.project.owner.username) || roleCheckService.isCollaborator(studyInstance)
		if (!canDo) {
			onErrors.unauthorised()
			return
		}
		def context = createContext(request)
		ensureFilesRoot(context, studyInstance)
		block(studyInstance, context)
	}
	
	def securedBasic = securedCommon.curry([
		'badStudyId' : {
			flash.message = message(code:'study.analysed.invalidId')
			redirect controller:'login', action: 'invalid'
		},
		'unauthorised' : {
			redirect controller:'login', action: 'denied'
		}
		])
	
	def securedJson = securedCommon.curry([
		'badStudyId' : {
			def resp = ['error': 'bad parameters']
			render resp as JSON
		},
		'unauthorised' : {
			def resp = ['error': 'unauthorised']
			render resp as JSON
		}
		]) 
		
	// common security validation and context initialization
	def secured = { block ->
		securedBasic { studyInstance, context ->
			def component = Component.findByStudy(studyInstance)
			def resultFields = ResultsDetailsField.findAll()
			if (component == null) {
				component = new Component(study:studyInstance)
			} 
			block(studyInstance, component, context)
	    }
	}
	

	def index =
	{
		redirect(action: "list", params: params)
	}


	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def list =
	{
		securedBasic { study, context ->
			def componentFolders = Component.findAllByStudy(study)
			render(view:'list', model:[studyInstance: study, folders:componentFolders])
		}
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def browseFiles =
	{
		cache false
		def sessionObj = Session.findById(params.sessionId)
		def path = sessionObj?.component.name + "/" + sessionObj?.name +"/" + params.directory
		
		['path': path]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def downloadFiles =
	{
		
		cache false
		
		def context = createContext(request)
		def study = Study.findById(params.studyId)
		def files = params.list('files')
		def zipName = study.studyTitle + ".zip"
		
		response.setContentType "application/zip"
		response.setHeader "Content-Disposition", "attachment; filename=\"" + zipName + "\""
		response.setHeader "Content-Description", "File download for BDCP"
		response.setHeader "Content-Transfer-Encoding", "binary"

		def zipOs = new ZipOutputStream(response.outputStream)
		def added = new HashSet()
		zipOs.setComment "Created with BDCP web application"
		files.each { String file ->
				addFileToZip(study, context, zipOs, file, added)
			}
		zipOs.close()
		response.flushBuffer()
		
		return true
		
	}
	
	private void addFileToZip(Study study, Object context, ZipOutputStream zipOs, String file, Set added)
	{
		def thePath = file
		File theFile = fileService.getFileReference(context, thePath)
		def lastMod = theFile.lastModified()
		if (!theFile.isDirectory())
		{
			def zipEntry = new ZipEntry(thePath)
			long fileSize = theFile.length()
			zipEntry.setTime(lastMod)
			zipEntry.setSize(fileSize)
			zipOs.putNextEntry(zipEntry)
			zipOs << new FileInputStream(theFile)
			zipOs.closeEntry()
		}
		else
		{
			addDirectoryToZip(zipOs, "/" + thePath, added, lastMod)
		}
	}
	
	private void addDirectoryToZip(ZipOutputStream zipOs, String directory, Set added, long lastMod)
	{
		def zipEntry = new ZipEntry(directory + "/")		
		zipEntry.setComment directory
		zipEntry.setTime(lastMod)
		zipOs.putNextEntry(zipEntry)
		added.add(directory)
	}
	
	def listFolder =
	{
		securedJson { studyInstance, context ->
			def component = Component.findById(params.id)
			def name = params.folderPath
			def folderPath = studyInstance.id + "/" + component.id

			// case of base directory display component name for folder
			if (''.equals(name)) {
				def resp = ['data':component.name,'state':'closed','attr':['rel':'root'],'metadata':['folderPath':folderPath]]
				render resp as JSON
				return
			}
			
			// case for directory that is a session that hangs off component directory
			if (name ==~ /[0-9]+[\/][0-9]+/) {
				def resp = component.sessions.collect { session ->
					 ['data':session.name,'icon':'folder','state':'closed','attr':['rel':'folder'],'metadata':['folderPath':studyInstance.id + '/' + component.id + '/' + session.id]]
				}
				render resp as JSON
				return
			 }

			// other directories and files under the component/session directory trunk
			def file = fileService.getFileReference(context, name)
			if (file.isDirectory()) {
			   def folders = file.listFiles().collect { f ->
				   f.isDirectory() ? ['data':f.getName(),'icon':'folder','state':'closed','attr':['rel':'folder'],
					   'metadata':['folderPath':('/'.equals(name) ? '' : name)+'/' +f.getName()]] : ['data':f.getName(),'attr':['rel':'file', 'folderPath':('/'.equals(name) ? '' : name)+"/"+f.getName()]]
			   }
			   render folders as JSON
			}
		}
	}
    
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def upload =
	{
		securedBasic { study, context ->
			if (params.done != null || params.cancel != null) {
				flash.message = params.done != null ? message(code:'study.analysed.upload.done') : message(code:'study.analysed.upload.cancel') 
				redirect url:createLink(mapping: "sessionFileList", action:"list", params:["studyId":study.id] )
			}
			[studyInstance: study, errors:false, folderName:new FolderCommand(folder:params.folder)]
		}
	}
	
	def uploadFiles =
	{	
		cache false
		def studyInstance = Study.get(Long.parseLong(params.studyId))
		def sessionInstance = Session.get(Long.parseLong(params.sessionId))
		if (studyInstance == null) {
			flash.message = message(code:'study.analysed.invalidId')
			redirect controller:'login', action: 'invalid'
			return
		}
		def context = createContext(request)
		ensureFilesRootSession(context, studyInstance, sessionInstance)
		def dirstruct = params.dirStruct
		def upload_component_session_root = componentStudySessionPath(studyInstance, sessionInstance) + "/" + params.destDir
		dirstruct = JSON.parse(dirstruct)
        def success = (fileService.createAllFolders(context, dirstruct, upload_component_session_root) == true) ? true : false
        success = success && (fileService.createAllFiles(context, dirstruct, upload_component_session_root, params) == true)
		success = success && (fileService.moveDirectoryFromTmp(context, upload_component_session_root, upload_component_session_root) == true)
		if (success)
		{
			render "Successfully Uploaded Files!"
		}
		else
		{
			response.sendError 500
		}
	}

   private void ensureFilesRoot(context, studyInstance)
   {
	   def dir = fileService.getFileReference(context, componentStudyPath(studyInstance))
	   if (!dir.exists())
	   {
		   dir.mkdirs()
	   }
   }
   
   private void ensureFilesRootSession(context, studyInstance, sessionInstance)
   {
	   def dir = fileService.getFileReference(context, componentStudySessionPath(studyInstance, sessionInstance))
	   if (!dir.exists())
	   {
		   dir.mkdirs()
	   }
   }
   
   private String componentStudyPath(studyInstance)
   {
	   return "${studyInstance.id}/";
   }
   
   private String componentStudySessionPath(studyInstance, sessionInstance)
   {
	   return "${studyInstance.id}/${sessionInstance.component.id}/${sessionInstance.id}/";
   }
   
   
}
