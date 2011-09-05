package au.org.intersect.bdcp

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

import grails.plugins.springsecurity.Secured

import au.org.intersect.bdcp.rifcs.Rifcs
import au.org.intersect.bdcp.ldap.LdapUser

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
		// if ur a researcher or system administrator and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER') || roleCheckService.checkUserRole('ROLE_SYS_ADMIN')) {
			redirectNonAuthorizedAccessStudy(studyInstance)
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
			[studyInstance: study, dirFiles: dirFiles['files']]
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
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def uploadFiles =
	{	
		secured { study, context ->
			render "Successfully Uploaded Files!"
		}
	}

	/**
	* Display project only to owner or collaborator
	* @param _projectInstance
	*/
   private void redirectNonAuthorizedAccessStudy(Study _studyInstance)
   {
	   def userStore = UserStore.findByUsername(principal.username)
	   def studyCollaborator = StudyCollaborator.findByStudyAndCollaborator(_studyInstance,userStore)

	   if(!_studyInstance.project.owner.username.equals(principal.username) && !studyCollaborator){
		   redirect controller:'login', action: 'denied'
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
