package au.org.intersect.bdcp

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

class StudyAnalysedDataController
{
	
	def fileService
	
	def roleCheckService
	
	def createContext(def servletRequest)
	{
		return fileService.createContext(servletRequest.getSession().getServletContext().getRealPath("/"), "analysed")
	}
	
	// common security validation and context initialization
	def secured = { block ->
		cache false
		def studyInstance = Study.get(Long.parseLong(params.studyId))
		if (studyInstance == null) {
			flash.message = message(code:'study.analysed.invalidId')
			redirect controller:'login', action: 'invalid'
			return
		}
		def canDo = roleCheckService.checkUserRole('ROLE_LAB_MANAGER');
		canDo = canDo || (roleCheckService.checkUserRole('ROLE_RESEARCHER') && roleCheckService.checkSameUser(studyInstance.project.owner.username))
		if (!canDo) {
		    redirect controller:'login', action: 'denied'
			return
		}
		def context = createContext(request)
		ensureFilesRoot(context, studyInstance)
		block(studyInstance, context)		
	}
	

	def index =
	{
		secured { study, context ->
			redirect(action: "list", params: params)
		}
	}


	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def list =
	{
		secured { study, context ->
			def dirFiles = fileService.listFiles(context, rootPath(study))
			render(view:'list', model:[studyInstance: study, dirFiles: dirFiles['files']])
		}
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def create =
	{
		secured { study, context ->
			[studyInstance: study, errors:false, folderName:new FolderCommand(folder:'')]
		}
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def createFolder =
	{	FolderCommand folderV ->
		secured { study, context ->
			if (folderV.hasErrors()) {
				render(view:'create',model:[studyInstance: study, errors:true, folderName:folderV])
			} else {
				def ok = fileService.createDirectory(context, folderV.folder, rootPath(study))
				if (!ok) {
					flash.message = "Error creating folder"
				}
				redirect(params:[studyId:study.id, action:'list'])
			}
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def upload =
	{
		secured { study, context ->
			[studyInstance: study, errors:false, folderName:new FolderCommand(folder:params.folder)]
		}
	}
	
	def uploadFiles =
	{	
		cache false
		def studyInstance = Study.get(Long.parseLong(params.studyId))
		if (studyInstance == null) {
			flash.message = message(code:'study.analysed.invalidId')
			redirect controller:'login', action: 'invalid'
			return
		}
		def context = createContext(request)
		ensureFilesRoot(context, studyInstance)
		def dirstruct = params.dirStruct
		def upload_root = rootPath(studyInstance) + "/" + params.destDir
		dirstruct = JSON.parse(dirstruct)
        def success = (fileService.createAllFolders(context, dirstruct, upload_root) == true) ? true : false
        success = success && (fileService.createAllFiles(context, dirstruct, upload_root, params) == true)
		success = success && (fileService.moveDirectoryFromTmp(context, upload_root, upload_root) == true)
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
	   def dir = fileService.getFileReference(context, rootPath(studyInstance))
	   if (!dir.exists())
	   {
		   dir.mkdirs()
	   }
   }
   
   private String rootPath(studyInstance)
   {
	   return "${studyInstance.id}/";
   }
   
   
}

class FolderCommand {
	String folder
	
	static constraints =
	{
		folder(nullable:false, blank:false, size:1..255, validFilename:true)
	}
}
