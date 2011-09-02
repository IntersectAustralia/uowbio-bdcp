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
	

	def index =
	{
		cache false
		redirect(action: "list", params: params)
	}


	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def list =
	{
		cache false
		def studyInstance = Study.get(params.studyId)
		// if ur a researcher or system administrator and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER') || roleCheckService.checkUserRole('ROLE_SYS_ADMIN')) {
			redirectNonAuthorizedAccessStudy(studyInstance)
		}
		def context = createContext(request)
		ensureFilesRoot(context, studyInstance)
		def dirFiles = fileService.listFiles(context, rootPath(studyInstance))
		[studyInstance: studyInstance, dirFiles: dirFiles['files']]
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
