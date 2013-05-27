package au.org.intersect.bdcp

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import java.io.File;
import java.io.InputStream;
import java.util.Set
import java.util.regex.Pattern
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

import uk.co.desirableobjects.ajaxuploader.AjaxUploaderService;
import uk.co.desirableobjects.ajaxuploader.exception.FileUploadException

import au.org.intersect.bdcp.constraints.FilterSpecialCharsOfFilename
import au.org.intersect.bdcp.constraints.ValidFilenameConstraint


class StudyAnalysedDataController
{
	AjaxUploaderService ajaxUploaderService
	
    def fileService
    
    def roleCheckService
    
    def grailsApplication
    
//  def pattern = Pattern.compile("^(*)/(.*)\$")
    
    def createContext()
    {
        return fileService.createContext( "analysed")
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
            block(studyInstance, context)
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
            render(view:'list', model:[studyInstance: study])
        }
    }
    
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
    def downloadFiles =
    {
        
        cache false
		def filterSpecialChars = new FilterSpecialCharsOfFilename()
		
        def context = createContext()
        def study = Study.findById(params.studyId)
        def files = params.list('files')
        def zipName = System.getProperty("java.io.tmpdir") + File.separator + filterSpecialChars.filterSpecialChars(study.studyTitle) + "_analysed.zip"
        def rootPath = rootPath(study)
		
		def zipOs = new ZipOutputStream(new FileOutputStream(zipName))
		def added = new HashSet()
		zipOs.setComment "Created with BDCP web application"
        files.each { String file ->
                addFileToZip(study, context, zipOs, rootPath + file, added)
            }
		zipOs.close()

		def file = new File(zipName)
		
		response.setContentType "application/zip"
		response.setHeader "Content-Disposition", "attachment; filename=\"" + filterSpecialChars.filterSpecialChars(study.studyTitle) + "_analysed.zip\""
		response.setHeader "Content-Description", "File download for BDCP"
		response.setHeader "Content-Transfer-Encoding", "binary"
		response.outputStream << file.newInputStream()
		file.delete()

		response.flushBuffer()
		response.close()
		
        
    }
    
    private void addFileToZip(Study study, Object context, ZipOutputStream zipOs, String file, Set added)
    {
        def thePath = file
		def zipName = getZipName(thePath)
        File theFileOrDir = fileService.getFileReference( grailsApplication.config.files.analysed.location, thePath)
		
        def lastMod = theFileOrDir.lastModified()
        if (!theFileOrDir.isDirectory() && !added.contains(theFileOrDir) )
        {
            def zipEntry = new ZipEntry(zipName)
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
	
	private String getZipName(String thePath){// e.g.     1/tools
		
		def dirs = thePath.split("/")
		if(dirs.length >= 1){
			dirs[0] = Study.findById(dirs[0]).studyTitle
		}
		if(dirs.length >= 2){
			dirs[1] = "Analysed Data"
		}
		
		def zipName = ""
		def filterSpecialChars = new FilterSpecialCharsOfFilename()
		for(int i = 0; i < dirs.length; i++){
			def dir = filterSpecialChars.filterSpecialChars(dirs[i])
			zipName = (i == 0) ? dir : zipName + "/" + dir
		}
		return zipName
	}
    
    private void addDirectoryToZip(File directory, Study study, Object context, ZipOutputStream zipOs, String file, Set added)
    {
        directory.eachFile { it -> 
            if( !added.contains(it.toString()) )
            {
                if(it.isDirectory()) added.add(it.toString())
                addFileToZip( study, context, zipOs, 
                    it.toString().substring (new Integer(it.toString().indexOf("analysed")).intValue() + (new Integer("analysed".size()).intValue() + 1)), 
                    added)
            }
        }
    }
    
    def listFolder =
    {
        securedJson { studyInstance, context ->

            if (''.equals(params.folderPath)) {
                def resp = ['data':'Analysed Data','state':'closed','attr':['rel':'analysed'],'metadata':['folderPath':'.']]
                render resp as JSON
                return
            }
            def rootPath = rootPath(studyInstance)
            def file = fileService.getFileReference( grailsApplication.config.files.analysed.location, rootPath)
                        file = new File(file, params.folderPath)
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
            } else {
               def folders = ['data':file.getName(),'attr':['rel':'file'], 'metadata':['folderPath':params.folderPath]]
               render folders as JSON
            }
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
    def createFolder =
    {
        securedBasic { study, context ->
            [studyInstance: study, 'formErrors':formErrors([:]), folderPath:params.folderPath, folderName:params.folderName]
        }
    }
    
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
    def doCreateFolder =
    {   
        securedBasic { study, context ->
            def validator = new ValidFilenameConstraint()
            def folderName = params.folderName.trim()
            def msg = null
            if (folderName.length() == 0) {
                msg = 'study.files.analysed.folder.create.folderName.blank';
            } else if (!validator.validate(folderName)) {
                msg = 'study.files.analysed.folder.create.folderName.invalid';
            } else {
                def baseDir = fileService.getFileReference( grailsApplication.config.files.analysed.location, rootPath(study) )
                def folderPath = new File(baseDir, params.folderPath)
                def dir = new File(folderPath, folderName)
                if (dir.exists() && dir.isDirectory()) {
                   flash.message = "Folder already exists"
                } else {
                   def ok = dir.mkdir();
                   if (!ok) {
                       msg = 'study.files.analysed.folder.create.folderName.systemerror';
                   } else {
                       flash.message = "Folder created"
                   }
                }
            }
            if (msg != null) {
                render(view:'createFolder',model:[studyInstance: study, 'formErrors':formErrors(['folderName':msg]), folderName:folderName, folderPath:params.folderPath])
                return
            }
            redirect(params:[studyId:study.id, action:'list'])
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
    def doDeleteFolder =
    {   
        securedBasic { study, context ->
            def baseDir = fileService.getFileReference( grailsApplication.config.files.analysed.location, rootPath(study) )
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

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
    def upload =
    {
        secured { study, context ->
            if (params.done != null || params.cancel != null) {
                flash.message = params.done != null ? message(code:'study.analysed.upload.done') : message(code:'study.analysed.upload.cancel') 
                redirect url:createLink(mapping: "studyAnalysedData", action:"list", params:["studyId":study.id] )
            }
            [studyInstance: study, errors:false]
        }
    }
    
    def uploadFiles_previous =
    {   
        cache false
        def studyInstance = Study.get(Long.parseLong(params.studyId))
        if (studyInstance == null) {
            flash.message = message(code:'study.analysed.invalidId')
            redirect controller:'login', action: 'invalid'
            return
        }
        def context = createContext()
        ensureFilesRoot(context, studyInstance)
        def dirstruct = params.dirStruct
        def upload_root = rootPath(studyInstance) + params.destDir
        dirstruct = JSON.parse(dirstruct)
        def success = (fileService.createAllFolders( grailsApplication.config.files.analysed.location, dirstruct, upload_root) == true) ? true : false
        success = success && (fileService.createAllFiles( dirstruct, upload_root, params) == true)
        success = success && (fileService.moveDirectoryFromTmp( grailsApplication.config.files.analysed.location, upload_root, upload_root) == true)
        if (success)
        {
            render "Successfully Uploaded Files!"
        }
        else
        {
            response.sendError 500
        }
    }
	def uploadFiles =
	{
		try{
			cache false
			
			def studyInstance = Study.get(Long.parseLong(params.studyId))
			if (studyInstance == null) {
				flash.message = message(code:'study.analysed.invalidId')
				redirect controller:'login', action: 'invalid'
				return
			}
			
			def upload_root = rootPath(studyInstance)  + params.folderPath
		
			File uploaded = null
			//firefox uploading
			if(params.filePath.equals("") || params.filePath == null){
				uploaded = createTemporaryFile(upload_root,params.qqfile.getOriginalFilename())
			}
			else{//Chrome uploading
				createDirectories(params.filePath, upload_root)
				uploaded = createTemporaryFile(upload_root,params.filePath)
			}
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
			uploaded = new File(grailsApplication.config.files.analysed.location  + upload_component_session_root +"/"+fileName)//+fileName
		} else {
			uploaded = File.createTempFile('grails', 'ajaxupload')
		}
		return uploaded
	}
	
   private void ensureFilesRoot(context, studyInstance)
   {
       def dir = fileService.getFileReference( grailsApplication.config.files.analysed.location, rootPath(studyInstance))
       if (!dir.exists())
       {
           dir.mkdirs()
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



   def formErrors =
   {   errorMap ->
       def allErrors = errorMap.collect { key, value -> 
           value
       }
           def objErrors = [hasErrors: { -> errorMap.size()!=0}, hasFieldErrors: { String p -> errorMap[p] != null }, 'allErrors': allErrors ]  
       return objErrors
   }

   /*
	* iteratively find out root path, super folder and current folder so that it could be created by  doCreateMutipleFolder()
	* example : fileFullpath = "/tools/jquery/abc.js" & upload_root = "1/1/1/"
	*/
   private void createDirectories(String fileFullpath, String upload_root){
	   fileFullpath = fileFullpath.substring(1)
	   String [] dirctories = fileFullpath.trim().split("/")
	   if(dirctories.length > 0){
		   String folderName = dirctories[0]  // "tools"
		   String superFoldersdir = ""
		   
		   for(int i = 0; i < dirctories.length - 1 ; i++){
			   folderName = dirctories[i].trim()
			   doCreateMutipleFolder(upload_root, superFoldersdir, folderName)
			   superFoldersdir = superFoldersdir + "/" + folderName
		   }
	   }
	   
   }
   
   
   /*
	* create fold before file is uploaded
	* example: tmp/analysedData 	+ /1/1/1 		+ /tools 			+ /jquery.1001
	* 								upload_root		+ superFoldersdir 	+ folderName
	*/
   private void doCreateMutipleFolder(String upload_root, String superFoldersdir, String folderName)
   {
	   def validator = new ValidFilenameConstraint()
	   def msg = null
	   if (folderName.length() == 0) {
		   msg = 'study.files.analysed.folder.create.folderName.blank';
	   } else if (!validator.validate(folderName)) {
		   msg = 'study.files.analysed.folder.create.folderName.invalid';
	   } else {
		   def baseDir = fileService.getFileReference( grailsApplication.config.files.analysed.location, upload_root + superFoldersdir )
		   def dir = new File(baseDir, folderName)
		   if (dir.exists() && dir.isDirectory()) {
			  msg = "Folder already exists"
		   } else {
			  def ok = dir.mkdir();
			  if (!ok) {
				  msg = 'study.files.analysed.folder.create.folderName.systemerror';
			  } else {
				  msg = "Folder created"
			  }
		   }
	   }
	   
   }
   
}



class FolderCommand {
    String folder
    
    static constraints =
    {
        folder(nullable:false, blank:false, size:1..255, validFilename:true)
    }
}
