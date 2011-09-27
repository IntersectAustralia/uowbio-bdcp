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
	
	def securedBasic = { block ->
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
	
	// common security validation and context initialization
	def secured = { block ->
		securedBasic { studyInstance, context ->
			def studyAnalysedData = StudyAnalysedData.findByStudyAndFolder(studyInstance, params.folder)
			def studyAnalysedDataFields = []
			def resultFields = ResultsDetailsField.findAll()
			if (studyAnalysedData == null) {
				studyAnalysedData = new StudyAnalysedData(study:studyInstance, folder:params.folder)
				resultFields.each {  fieldDef ->
					def domObj = new StudyAnalysedDataField(studyAnalysedData: studyAnalysedData, resultsDetailsField: fieldDef)
					studyAnalysedDataFields.add(domObj)
				}
			} else {
				resultFields.each {  fieldDef ->
					def domObj = studyAnalysedData.studyAnalysedDataFields.find { StudyAnalysedDataField sdf -> sdf.resultsDetailsField.equals(fieldDef) }
					if (domObj == null) {
						// newly created field
					    domObj = new StudyAnalysedDataField(studyAnalysedData: studyAnalysedData, resultsDetailsField: fieldDef)
					}
					studyAnalysedDataFields.add(domObj)
				}
			}
			studyAnalysedDataFields.sort {x,y -> x.resultsDetailsField.dateCreated <=> y.resultsDetailsField.dateCreated}		
			block(studyInstance, studyAnalysedData, studyAnalysedDataFields, context)
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
			def studyAnalysedFolders = StudyAnalysedData.findAllByStudy(study)
			render(view:'list', model:[studyInstance: study, folders:studyAnalysedFolders])
		}
	}
	
	def listFolder =
	{
		securedBasic { studyInstance, context ->
			def studyAnalysedData = StudyAnalysedData.findById(params.id)
			def name = params.folderPath
			if (''.equals(name)) {
				def resp = ['data':studyAnalysedData.folder,'state':'closed',metadata:['folderPath':'/']]
				render resp as JSON
				return
			}
			def folderPath = params.folderPath
			folderPath = studyInstance.id + "/" + studyAnalysedData.folder + folderPath  
			def file = fileService.getFileReference(context, folderPath)
			if (file.isDirectory()) {
			   def folders = file.listFiles().collect { f ->
				   f.isDirectory() ? ['data':f.getName(),'icon':'folder','state':'closed',
					   'metadata':['folderPath':('/'.equals(name) ? '' : name)+'/' +f.getName()]] : ['data':f.getName(),'icon':'file']
			   }
			   render folders as JSON
			} else {
			   def folders = ['data':name,'icon':'file']
			   render folders as JSON
			}
		}
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def createFolder =
	{
		securedBasic { study, context ->
			[studyInstance: study, errors:false, folderName:new FolderCommand(folder:'')]
		}
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def doCreateFolder =
	{	FolderCommand folderV ->
		securedBasic { study, context ->
			if (folderV.hasErrors()) {
				render(view:'createFolder',model:[studyInstance: study, errors:true, folderName:folderV])
				return
			}
			def ok = true
			StudyAnalysedData.withTransaction { tx ->
				def saf = new StudyAnalysedData(study:study, folder:folderV.folder)
				ok = saf.save(flush:true) && fileService.createDirectory(context, folderV.folder, rootPath(study))
				if (!ok) {
					tx.setRollbackOnly()
				}
				if (!ok) {
					flash.error = "Error saving data to DB"
					render(view:'createFolder',model:[studyInstance: study, errors:true, folderName:folderV])
					return
				}
			}
			redirect(params:[studyId:study.id, action:'list'])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def editData =
	{
		secured { study, studyAnalysedData, studyAnalysedDataFields, context ->
			def nextAction = studyAnalysedData.id == null ? 'update' : 'save'
			[studyInstance: study, studyAnalysedData: studyAnalysedData, studyAnalysedDataFields:studyAnalysedDataFields, nextAction:nextAction]
		}
	}
	
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
    def updateData = {
		
		secured {  study, studyAnalysedData, studyAnalysedDataFields, context ->
			def fieldsSize = params["fieldsSize"] as Integer
			if (studyAnalysedDataFields.size() != fieldsSize) {
				flash.message = 'Number of fields in request does not match database. Editing cancelled'
				redirect url:createLink(mapping:'studyAnalysedData', params:[studyId:studyInstance.id, folder:studyAnalysedData.folder, action:'editData'])
				return				
			}
			def ok = true
			for (i in 0..fieldsSize-1) {
				def saf = studyAnalysedDataFields[i]
				def safParams = params["studyAnalysedDataFields["+i+"]"]
				if (saf.id != null) {
					if (saf.version > safParams.version) {
						saf.errors.reject('updated','Someone updated this in the dabase')
						ok = false
						continue
					}
				}
				saf.properties = safParams
				ok = saf.validate() && ok  // note: we want to validate always, so Ok should be last
			}
			if (!ok) {
				def nextAction = studyAnalysedData.id == null ? 'create' : 'update'
				render(view:'editData', model:[studyInstance: study, studyAnalysedData: studyAnalysedData, studyAnalysedDataFields:studyAnalysedDataFields, nextAction:nextAction])
				return
			}
			def allOk = true
			StudyAnalysedData.withTransaction { dbt ->
				if (studyAnalysedData.id == null) {
					allOk = studyAnalysedData.save(flush:true) && allOk
				}
				studyAnalysedDataFields.each { allOk = it.save(flush:true) && allOk }
				if (!allOk) {
					dbt.setRollbackOnly()
				}
			}
			if (!allOk) {
				def nextAction = studyAnalysedData.id == null ? 'create' : 'update'
				flash.error = message(code: 'study.analysed.'+nextAction+'.db.error')
				render(view:'editData', model:[studyInstance: study, studyAnalysedData: studyAnalysedData, studyAnalysedDataFields:studyAnalysedDataFields, nextAction:nextAction])
				return
			}
            redirect url:createLink(mapping: "studyAnalysedData", action:"upload", params:["studyId":study.id, "folder":studyAnalysedData.folder] )
		}
        
    }
    
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def upload =
	{
		secured { study, studyAnalysedData, studyAnalysedDataFields, context ->
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
