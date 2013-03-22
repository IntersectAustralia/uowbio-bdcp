package au.org.intersect.bdcp

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import java.awt.event.ItemEvent;
import java.io.File;
import java.io.InputStream;
import java.util.Set
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


import org.springframework.http.HttpStatus

import uk.co.desirableobjects.ajaxuploader.AjaxUploaderService
import uk.co.desirableobjects.ajaxuploader.exception.FileUploadException
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest


class SessionFileController
{
	AjaxUploaderService ajaxUploaderService
	
	def fileService
	
	def roleCheckService
	
	def grailsApplication
	
	
	
//	def pattern = Pattern.compile("^(*)/(.*)\$")
	
	def createContext()
	{
		return fileService.createContext( "session")
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
		def context = createContext()
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
			
			render(view:'list', model:[studyInstance: study, folders:componentFolders] )
		}
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def browseFiles =
	{
		cache false
		def sessionObj = Session.findById(params.sessionId)
		def path = sessionObj?.component.name + "/" + sessionObj?.name +"/" + params.directory
		
		['path': path,'sessionId':sessionId]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def downloadFiles =
	{
		
		cache false
		
		def context = createContext()
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
		File theFileOrDir = fileService.getFileReference( grailsApplication.config.files.session.location, thePath)
		def lastMod = theFileOrDir.lastModified()
		if (!theFileOrDir.isDirectory() && !added.contains(theFileOrDir) )
		{
			def zipEntry = new ZipEntry(thePath)
			long fileSize = theFileOrDir.length()
			zipEntry.setTime(lastMod)
			zipEntry.setSize(fileSize)
			zipOs.putNextEntry(zipEntry)
			zipOs << new FileInputStream(theFileOrDir)
			zipOs.closeEntry()
			added.add(theFileOrDir)
		}
		else if (theFileOrDir.isDirectory() && !added.contains(theFileOrDir) )
		{
			addDirectoryToZip (theFileOrDir, study, context, zipOs, file, added)
		}
	}
	
	private void addDirectoryToZip(File directory, Study study, Object context, ZipOutputStream zipOs, String file, Set added)
	{
		directory.eachFile { it -> 
			if( !added.contains(it.toString()) )
			{
				if(it.isDirectory()) added.add(it.toString())
				addFileToZip( study, context, zipOs, 
					it.toString().substring (new Integer(it.toString().indexOf("sessions")).intValue() + (new Integer("sessions".size()).intValue() + 1)), 
					added)
			}
		}
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
			def file = fileService.getFileReference( grailsApplication.config.files.session.location, name)
			if (file.isDirectory()) {
			   def folders = file.listFiles().collect { f ->
                   if (f.isDirectory()) {
                       ['data':f.getName(),'icon':'folder','state':'closed','attr':['rel':'folder'],
                           'metadata':['folderPath':params.folderPath + '/' +f.getName()]] 
                   } else {
                       ['data':f.getName(),'attr':['rel':'file'],'metadata':['folderPath':params.folderPath +"/"+f.getName()]]
                   }
               }
			   render folders as JSON
			}
            else
            {
			   def folders = []
               render folders as JSON
            }
		}
	}
    
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def upload =
	{
		securedBasic { study, context ->fileService
			if (params.done != null || params.cancel != null) {
				flash.message = params.done != null ? message(code:'study.analysed.upload.done') : message(code:'study.analysed.upload.cancel') 
				redirect url:createLink(mapping: "sessionFileList", action:"list", params:["studyId":study.id] )
			}
			[studyInstance: study, errors:false, folderName:new FolderCommand(folder:params.folder)]
		}
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def doDeleteFolder =
	{
		securedBasic { study, context ->
			def baseDir = fileService.getFileReference( grailsApplication.config.files.session.location, rootPath(study) )
			def folderPath = new File(baseDir, params.folderPath)
			if (folderPath.exists()) {
				if (deleteRecursive(folderPath)) {
					flash.message = params.folderPath + " deleted"
				} else {
					flash.message = "Delete: error deleting " + params.folderPath
				}
			} else {
				flash.message = "Delete: resource not found"
			}
			redirect(params:[studyId:study.id, action:'list'])
		}
	}
	
	private String rootPath(studyInstance)
	{
		return "${studyInstance.id}/";
	}
	
	def deleteRecursive(File f) {
		if (!f.exists()) { return true; }
		if (!f.isDirectory()) {
			return f.delete();
		}
		return !f.listFiles().find({ File child ->
			if (!".".equals(child.getName()) && !"..".equals(child.getName())) {
				return !deleteRecursive(child);
			} else {
				return false;
			}
		}) && f.delete()
	}
	
	def uploadFiles_previous =
	{	
		cache false
		def studyInstance = Study.get(Long.parseLong(params.studyId))
		def sessionInstance = Session.get(Long.parseLong(params.sessionId))
		if (studyInstance == null) {fileService
			flash.message = message(code:'study.analysed.invalidId')
			redirect controller:'login', action: 'invalid'
			return
		}
		def context = createContext()
		ensureFilesRootSession(context, studyInstance, sessionInstance)
		def dirstruct = params.dirStruct
		def upload_component_session_root = componentStudySessionPath(studyInstance, sessionInstance) + "/" + params.destDir
		dirstruct = JSON.parse(dirstruct)
        def success = (fileService.createAllFolders( c, dirstruct, upload_component_session_root) == true) ? true : false
        success = success && (fileService.createAllFiles( dirstruct, upload_component_session_root, params) == true)
		success = success && (fileService.moveDirectoryFromTmp( grailsApplication.config.files.session.location, upload_component_session_root, upload_component_session_root) == true)
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
	   def dir = fileService.getFileReference( grailsApplication.config.files.session.location, componentStudyPath(studyInstance))
	   if (!dir.exists())
	   {
		   dir.mkdirs()
	   }
   }
   
   private void ensureFilesRootSession(context, studyInstance, sessionInstance)
   {
	   def dir = fileService.getFileReference( grailsApplication.config.files.session.location, componentStudySessionPath(studyInstance, sessionInstance))
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
   
	def uploadFiles =
	{
		try{
			cache false
			
			def studyInstance = Study.get(Long.parseLong(params.studyId))
			def sessionInstance = Session.get(Long.parseLong(params.sessionId))
			if (studyInstance == null) {fileService
				return render(text: [success:study.analysed.invalidId] as JSON, contentType:'text/json')
			}
			
			def context = createContext()
			ensureFilesRootSession(context, studyInstance, sessionInstance)
			
			def upload_component_session_root = componentStudySessionPath(studyInstance, sessionInstance) + "/" + params.directory
			
			
			
		    File uploaded = createTemporaryFile(upload_component_session_root,params.fileName)
		   
		    InputStream inputStream = selectInputStream(request)

		    ajaxUploaderService.upload(inputStream, uploaded)

		    return render(text: [success:true] as JSON, contentType:'text/json')

	    } catch (FileUploadException e) {

		    log.error("Failed to upload file.", e)
		    return render(text: [success:false] as JSON, contentType:'text/json')
 
	    }

   }

   private InputStream selectInputStream(HttpServletRequest request) {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartFile uploadedFile = ((MultipartHttpServletRequest) request).getFile('qqfile')
            return uploadedFile.inputStream
        }
        return request.inputStream
    }

    private File createTemporaryFile(String upload_component_session_root, String fileName) {
		
        File uploaded
		
        if (grailsApplication.config.files.session.location !=null) {
			//uploaded = fileService.getFileReference( grailsApplication.config.files.session.location, upload_component_session_root)
            uploaded = new File(grailsApplication.config.files.session.location + "/" + upload_component_session_root +"/"+fileName)//+fileName
        } else {
            uploaded = File.createTempFile('grails', 'ajaxupload')
        }
        return uploaded
    }
	
}
