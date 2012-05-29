package au.org.intersect.bdcp

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import java.io.File;
import java.util.Set
import java.util.regex.Pattern
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import au.org.intersect.bdcp.constraints.ValidFilenameConstraint


class StudyAnalysedDataController
{
    
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
        File theFileOrDir = fileService.getFileReference( grailsApplication.config.files.analysed.location, thePath)
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
    
    def uploadFiles =
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

   def formErrors =
   {   errorMap ->
       def allErrors = errorMap.collect { key, value -> 
           value
       }
	   def objErrors = [hasErrors: { -> errorMap.size()!=0}, hasFieldErrors: { String p -> errorMap[p] != null }, 'allErrors': allErrors ]	
       return objErrors
   }

 
   
}

class FolderCommand {
    String folder
    
    static constraints =
    {
        folder(nullable:false, blank:false, size:1..255, validFilename:true)
    }
}
