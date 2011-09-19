package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured

class StudyDeviceFieldController {

    static allowedMethods = [update: "POST", delete: "POST"]

    def studyDeviceFieldService
	
	def roleCheckService
	
	// common security validation and context initialization
	def secured = { block ->
		cache false
		
		def studyInstance = Study.get(Long.parseLong(params.studyId))
		if (studyInstance == null) {
			flash.message = message(code:'study.deviceFields.studyId.invalid')
			redirect controller:'login', action: 'invalid'
			return
		}
		
		def deviceInstance = Device.get(Long.parseLong(params.deviceId))
		if (deviceInstance == null) {
			flash.message = message(code:'study.deviceFields.deviceId.invalidId')
			redirect controller:'login', action: 'invalid'
			return
		}
		
		def userStore = UserStore.findByUsername(roleCheckService.getUsername())
		def studyCollaborator = StudyCollaborator.findByStudyAndCollaborator(studyInstance,userStore)
		def canDo = roleCheckService.checkUserRole('ROLE_LAB_MANAGER') || 
		        (roleCheckService.checkUserRole('ROLE_RESEARCHER') && (roleCheckService.checkSameUser(studyInstance.project.owner.username) || studyCollaborator))
		if (!canDo) {
		    redirect controller:'login', action: 'denied'
			return
		}
		def studyDevice = StudyDevice.findByStudyAndDevice(studyInstance, deviceInstance)
        def studyDeviceFields = []		
        def deviceFields = DeviceField.findAllByDevice(deviceInstance)
        if (deviceFields.size() == 0) {
            flash.message = message(code: 'studyDevice.noFields', args: [deviceInstance.name])
		    redirect url:createLink(mapping:'studyDeviceDetails', action:'create', params:[studyId:studyInstance.id])
			return
        }
		if (studyDevice == null) {
			studyDevice = new StudyDevice(study:studyInstance, device:deviceInstance)
			deviceFields.each {  fieldDef ->
				def domObj = new StudyDeviceField(studyDevice: studyDevice, deviceField: fieldDef)
				studyDeviceFields.add(domObj)
			}
		} else {
			deviceFields.each {  fieldDef ->
				def domObj = studyDevice.studyDeviceFields.find { StudyDeviceField sdf -> sdf.deviceField.equals(fieldDef) }
				if (domObj == null) {
					// newly created field
					domObj = new StudyDeviceField(studyDevice: studyDevice, deviceField: fieldDef)
				} 
				studyDeviceFields.add(domObj)
			}
		}
        studyDeviceFields.sort {x,y -> x.deviceField.dateCreated <=> y.deviceField.dateCreated}
		block(studyInstance, deviceInstance, studyDeviceFields)
	}

    
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def index = {
        redirect(controller:"studyDevice", action: "create", params: params)
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
		secured { studyInstance, deviceInstance, studyDeviceFields ->
			def nextAction = studyDeviceFields[0].studyDevice.id == null ? 'create' : 'update'
            render(view:'edit', model:[studyInstance:studyInstance, deviceInstance:deviceInstance, studyDeviceFields: studyDeviceFields, nextAction:nextAction])
		}
    }
	
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
    def update = {
		
		secured { studyInstance, deviceInstance, studyDeviceFields ->
			def fieldsSize = params["fieldsSize"] as Integer
			if (studyDeviceFields.size() != fieldsSize) {
				flash.message = 'Number of fields in request does not match database. Editing cancelled'
				redirect (mapping:'studyDeviceFieldDetails', params:[studyId:studyInstance.id, deviceId:deviceInstance.id, action:'edit'])
				return				
			}
			def ok = true
			for (i in 0..fieldsSize-1) {
				def sdf = studyDeviceFields[i]
				def sdfParams = params["studyDeviceFields["+i+"]"]
				if (sdf.id != null) {
					if (sdf.version > sdfParams.version) {
						sdf.errors.reject('updated','Someone updated this in the dabase')
						ok = false
						continue
					}
				}
				sdf.properties = sdfParams
				ok = sdf.validate() && ok  // note: we want to validate always, so Ok should be last
			}
			if (!ok) {
				def nextAction = studyDeviceFields[0].studyDevice.id == null ? 'create' : 'update'
				render(view:'edit', model:[studyInstance:studyInstance, deviceInstance:deviceInstance, studyDeviceFields: studyDeviceFields, nextAction:nextAction])
				return
			}
			def studyDevice = studyDeviceFields[0].studyDevice
			if (studyDevice.id == null) {
				studyDevice.save(flush:true)
			}
			def allOk = true
			studyDeviceFields.each { allOk = it.save(flush:true) && allOk }
			if (!allOk) {
				// rollback
				flash.message = "There were unrecoverable errors saving the data"	
			} else {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'studyDevice.label', default: 'Device'), deviceInstance.name])
			}
            redirect(action: "list", controller:"studyDevice", mapping: "studyDeviceDetails", params:["studyId":studyInstance.id, "device.id": deviceInstance.id, "study.id": studyInstance.id] )
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
