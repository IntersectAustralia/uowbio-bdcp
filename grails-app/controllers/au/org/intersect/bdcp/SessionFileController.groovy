package au.org.intersect.bdcp

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

class SessionFileController
{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def fileService
    
    def createContext(def servletRequest)
    {
        return fileService.createContext(servletRequest.getSession().getServletContext().getRealPath("/"))
    }
    
	def upload =
	{
        if (params.sessionId != null && params.studyId != null && params.dirStruct != null)
        {
            def context = createContext(request)
            def dirstruct = params.dirStruct
            def parsed_json = JSON.parse(dirstruct)
            def sessionObj = Session.findById(params.sessionId)
            def upload_root = "${params.studyId}/${sessionObj.component.id}/${params.sessionId}/"
            if (params.destDir != "")
            {
                upload_root = upload_root + "${params.destDir}/"
            }
            def success = (fileService.createAllFolders(context,parsed_json, upload_root) == true) ? true: false
            success = (success == true && fileService.createAllFiles(context,parsed_json, upload_root, params) == true) ? true:false
            def final_location_root = "${params.studyId}/${sessionObj.component.id}/"
            
            success = (success == true && fileService.moveDirectoryFromTmp(context,upload_root, upload_root) == true)? true: false
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

	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def index =
	{
		cache false
        redirect(action: "fileList", params: params)
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def fileList =
	{
		cache false
        def studyInstance = Study.get(params.studyId)
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
        def context = createContext(request)
        
		def sessionFiles = [:]
		
		studyInstance.components.each {
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

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def createDirectory =
	{ 
        cache false
        def sessionObj = Session.findById(params.sessionId);
	    [directory: params.directory, sessionObj: sessionObj, component: sessionObj?.component]
    }

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def saveDirectory =
    { 
        
        directoryCommand dirCmd ->
        cache false
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
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def browseFiles =
	{
        cache false
        def sessionObj = Session.findById(params.sessionId)
        def path = sessionObj.component.name + "/" + sessionObj.name +"/" + params.directory
        
        ['path': path]
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