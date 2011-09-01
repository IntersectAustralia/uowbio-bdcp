package au.org.intersect.bdcp

import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.zip.ZipOutputStream
import java.util.zip.ZipEntry

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

class SessionFileController
{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST", downloadFiles: "POST"]

	def fileService
	
	def roleCheckService

	def pattern = ~/^([DF])\/(\d+)\/(.*)$/

    
    def createContext(def servletRequest)
    {
        return fileService.createContext(servletRequest.getSession().getServletContext().getRealPath("/"), "session")
    }
    
	def upload =
	{
        if (params.sessionId != null && params.studyId != null && params.dirStruct != null)
        {
            def context = createContext(request)
            def dirstruct = params.dirStruct
            def sessionObj = Session.findById(params.sessionId)
            def upload_root = "${params.studyId}/${sessionObj.component.id}/${params.sessionId}/"
            if (params.destDir != "")
            {
                upload_root = upload_root + "${params.destDir}/"
            }
			dirstruct = JSON.parse(dirstruct)
            def success = (fileService.createAllFolders(context, dirstruct, upload_root) == true) ? true: false
            success = (success == true && fileService.createAllFiles(context, dirstruct, upload_root, params) == true) ? true:false
            def final_location_root = "${params.studyId}/${sessionObj.component.id}/"
            
            success = (success == true && fileService.moveDirectoryFromTmp(context, upload_root, upload_root) == true)? true: false
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

	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_SYS_ADMIN'])
	def index =
	{
		cache false
        redirect(action: "fileList", params: params)
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def fileList =
	{
		cache false
        def studyInstance = Study.get(params.studyId)
		// if ur a researcher or system administrator and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER') || roleCheckService.checkUserRole('ROLE_SYS_ADMIN')) {
			redirectNonAuthorizedAccessStudy(studyInstance)
		}
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
        def context = createContext(request)
        
		def sessionFiles = [:]
		
		studyInstance?.components.each {
			def componentId = it.id
            it.sessions.each {
                def dirFiles = fileService.listFiles(context,"${params.studyId}/${componentId}/${it.id}")
				sessionFiles.putAt "${it.id}", dirFiles
			}
		}
        
        def componentInstanceList = Component.findAllByStudy(studyInstance)
        
        def sortedComponentInstanceList = componentInstanceList.sort {x,y -> x.name <=> y.name}
        
		[componentInstanceList: sortedComponentInstanceList, componentInstanceTotal: Component.countByStudy(studyInstance), studyInstance: studyInstance, sessionFiles: sessionFiles]
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

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def createDirectory =
	{ 
        cache false
		
		def studyInstance = Study.get(params.studyId)
		// if ur a researcher or system administrator and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER') || roleCheckService.checkUserRole('ROLE_SYS_ADMIN')) {
			redirectNonAuthorizedAccessStudy(studyInstance)
		}
		
        def sessionObj = Session.findById(params.sessionId);
	    [directory: params.directory, sessionObj: sessionObj, component: sessionObj?.component]
    }

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def saveDirectory =
    { 
        
        directoryCommand dirCmd ->
        cache false
		
		def studyInstance = Study.get(params.studyId)
		// if ur a researcher or system administrator and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER') || roleCheckService.checkUserRole('ROLE_SYS_ADMIN')) {
			redirectNonAuthorizedAccessStudy(studyInstance)
		}
		
        def context = createContext(request)
        def sessionObj = Session.findById(params.sessionId)
        def path = params.studyId +"/" + sessionObj.component.id + "/" + sessionObj.id +"/" + dirCmd?.path
        def containsDuplicateName = fileService.checkIfDirectoryExists(context, dirCmd.name.trim(), path)
        
		if (dirCmd.hasErrors() || (containsDuplicateName))
		{
            if (containsDuplicateName)
            {
                flash.error = g.message(code:"directoryCommand.name.unique")
            }
            render(view: "createDirectory", model: [directoryCommand: dirCmd, studyId: params.studyId, sessionId: params.sessionId, directory:params.directory, sessionObj: sessionObj, component: sessionObj?.component])
		    return
        }
		else
		{
            if (!fileService.createDirectory(context,dirCmd.name.trim(), path))
            {
                flash.error = g.message(code:"directoryCommand.problem.creating.dir" ,args:[dirCmd.name])
                render(view: "createDirectory", model: [directoryCommand: dirCmd, studyId: params.studyId, sessionId: params.sessionId, directory:params.directory, sessionObj: sessionObj, component: sessionObj?.component])
                return
            }
            
            redirect( mapping:"sessionFileDetails", controller: "sessionFile", action: "fileList", params: [studyId: params.studyId, sessionId: params.sessionId])
		}
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def browseFiles =
	{
        cache false
		
		def studyInstance = Study.get(params.studyId)
		// if ur a researcher or system administrator and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER') || roleCheckService.checkUserRole('ROLE_SYS_ADMIN')) {
			redirectNonAuthorizedAccessStudy(studyInstance)
		}
		
        def sessionObj = Session.findById(params.sessionId)
        def path = sessionObj.component.name + "/" + sessionObj.name +"/" + params.directory
        
        ['path': path]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def downloadFiles =
	{   
		cache false

		def studyInstance = Study.get(params.studyId)
		// if ur a researcher or system administrator and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER') || roleCheckService.checkUserRole('ROLE_SYS_ADMIN')) {
			redirectNonAuthorizedAccessStudy(studyInstance)
		}
		
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
		def matcher = file =~ pattern
		if (!matcher.matches())
		{
			// this shouldn't happen
			throw new RuntimeException("Incorrect request [req:" + file + "]")
		}
		def isDirectory = "D".equals(matcher[0][1])
		def session = Session.findById(matcher[0][2].toLong())
		def component = session.component
		def thePath = matcher[0][3]
		File theFile = fileService.getFileReference(context, study.id + "/"
			+ component.id + "/" + session.id + "/" + thePath)
		def lastMod = theFile.lastModified()
		if (isDirectory != theFile.isDirectory())
		{
			throw new RuntimeException("Incorrect file store state")
		}
		if (!isDirectory)
		{
			def zipEntry = new ZipEntry(component.name + "/" + session.name + "/" + thePath)
			long fileSize = theFile.length()
			zipEntry.setTime(lastMod)
			zipEntry.setSize(fileSize)
			zipOs.putNextEntry(zipEntry)
			zipOs << new FileInputStream(theFile)
			zipOs.closeEntry()
		}
		else
		{
			addDirectoryToZip(zipOs, component.name + "/" + session.name + "/" + thePath, added, lastMod)
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
	

}
class directoryCommand
{
	String name
	String path
	
	static constraints =
	{
		name(blank:false, size:1..255, matches:/^[a-zA-Z0-9-_\s]+/)
		path()
	}
}
