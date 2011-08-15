package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured

class StudyDeviceFieldController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def studyDeviceFieldService
	
	def roleCheckService
    
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def index = {
        redirect(controller:"studyDevice", action: "create", params: params)
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
    def create = {
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		
        def studyDeviceFieldInstance = new StudyDeviceField()
        studyDeviceFieldInstance.properties = params
        def studyDeviceFields = []
        def checked = []
        return [studyDeviceFieldInstance: studyDeviceFieldInstance, studyDeviceFields: studyDeviceFields, checked: checked]
    }

    def saveAllStudyDeviceFields(studyDeviceFields)
    {
        studyDeviceFields.every {
           if (!it.save(flush: true))
           {
               return false
           }
        }
        return true
    }
    
    def validStudyDeviceFields(studyDeviceFields)
    {
        def studyDeviceInstance = StudyDevice.link(Study.findById(params.study.id),Device.findById(params.device.id))
        def deviceFields = DeviceField.findAllByDevice(Device.findById(params.device.id))
        def allValid = true
        
        for (int i=0; i < deviceFields.size(); i++)
        {
            if (!studyDeviceFields[i].validate())
            {
                allValid = false
            }
        }
        
        return allValid
    }
    
    def laterVersion(studyDeviceFields)
    {
        def isLaterVersion = false
        def deviceFields = DeviceField.findAllByDevice(Device.findById(params.device.id))
        for (int i=0; i < deviceFields.size(); i++)
                        {
                            
                            if (params["studyDeviceFields["+i+"]"].version) {
                                def version = params["studyDeviceFields["+i+"]"].version.toLong()
                                if (studyDeviceFields[i].version > version)
                                {
                                    studyDeviceFields[i].errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'studyDeviceField.label', default: 'StudyDeviceField')] as Object[], "Another user has updated this StudyDeviceField while you were editing")
                                    studyDeviceFields[i].hasErrors()
                                    isLaterVersion = true    
                                }
                            }
                        }
        return isLaterVersion
    }
    
    
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
    def save = {
        
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		
        def result = studyDeviceFieldService.save(params)
        
        if (result.successful)
        {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'studyDevice.label', default: 'Device'), result.studyDeviceInstance.device])}"
            redirect(action: "list", controller:"studyDevice", mapping: "studyDeviceDetails", params:["studyId":params.studyId, "device.id": params.device.id, "study.id": params.study.id] )
        }
        else
        {
            render(view: "create", model: [studyDeviceFields: result.studyDeviceFields, studyDeviceFieldInstance: result.studyDeviceFields, 'study.id': params.study.id])
        }

    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def show = {
       def studyDeviceFields = []
        def studyDeviceFieldInstance = []
         if (!studyDeviceFields)
         {  
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyDeviceField.label', default: 'StudyDeviceField'), params.id])}"
            redirect(action: "list")
        }
        else {
            [studyDeviceFields: studyDeviceFields, studyDeviceFieldInstance: studyDeviceFieldInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
    def edit = {
        def studyDeviceFields = []
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		
        studyDeviceFields.addAll(StudyDevice.findByStudyAndDevice(Study.findById(params.studyId), Device.findById(params.device.id))?.studyDeviceFields)
        studyDeviceFields.sort {x,y -> x.deviceField.dateCreated <=> y.deviceField.dateCreated}
        def deviceFields = DeviceField.findAllByDevice(Device.findById(params.device.id))
        if (studyDeviceFields == null) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyDeviceField.label', default: 'StudyDeviceField'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [studyDeviceFields: studyDeviceFields, deviceFields: deviceFields]
        }
    }
	
	/**
	* Display Study only to research owner or research collaborator
	* @param _studyInstance
	*/
   private void redirectNonAuthorizedResearcherAccessStudy(Study _studyInstance)
   {
	   def userStore = UserStore.findByUsername(principal.username)
	   def studyCollaborator = StudyCollaborator.findByStudyAndCollaborator(_studyInstance,userStore)

	   if(!_studyInstance.project.owner.username.equals(principal.username) && !studyCollaborator){
		   redirect controller:'login', action: 'denied'
	   }
   }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
    def update = {
        
        def result = studyDeviceFieldService.update(params)
		
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
        
        if (result.successful)
        {
            flash.message = "${message(code: 'default.updated.message', args: [message(code: 'studyDevice.label', default: 'Device'), result.studyDeviceInstance.device])}"
            redirect(action: "list", controller:"studyDevice", mapping: "studyDeviceDetails", params:["studyId":params.studyId, "device.id": params.device.id, "study.id": params.study.id] )
        }
        else
        {
            render(view: "edit", model: [studyDeviceFields: result.studyDeviceFields, deviceFields: result.deviceFields])
        }
         
    }
    
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def delete = {
        def studyDeviceFieldInstance = StudyDeviceField.get(params.id)
        if (studyDeviceFieldInstance) {
            try {
                studyDeviceFieldInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'studyDeviceField.label', default: 'StudyDeviceField'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'studyDeviceField.label', default: 'StudyDeviceField'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyDeviceField.label', default: 'StudyDeviceField'), params.id])}"
            redirect(action: "list")
        }
    }
}
