package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured

class StudyDeviceFieldController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def index = {
        redirect(action: "list", params: params)
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def list = {
        def studyDeviceFields = []
        [studyDeviceFieldInstanceList: StudyDeviceField.list(), studyDeviceFieldInstanceTotal: StudyDeviceField.count(), studyDeviceFields: studyDeviceFields]
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def create = {
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
    
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
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
    
    
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def save = {
        
        def studyDeviceInstance = StudyDevice.link(Study.findById(params.study.id),Device.findById(params.device.id))
        def deviceFields = DeviceField.findAllByDevice(Device.findById(params.device.id))
        def allValid = true
        def studyDeviceFieldInstance = []
        for (int i=0; i < deviceFields.size(); i++)
        {
            studyDeviceFieldInstance[i] = new StudyDeviceField(params["studyDeviceFields["+i+"]"])
            studyDeviceInstance?.addToStudyDeviceFields(studyDeviceFieldInstance[i])
            deviceFields[i]?.addToStudyDeviceFields(studyDeviceFieldInstance[i])
            if (!studyDeviceFieldInstance[i].validate())
            {
                allValid = false
            }
    
        }
                
        if (allValid)
        {
            if (saveAllStudyDeviceFields(studyDeviceFieldInstance)) 
            {
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'studyDevice.label', default: 'Device'), studyDeviceInstance.device])}"
                redirect(action: "list", controller:"studyDevice", mapping: "studyDeviceDetails", params:["studyId":params.studyId, "device.id": params.device.id, "study.id": params.study.id] )
            }
            else
            {
                
                for (int i=0; i < deviceFields.size(); i++)
                {
                    studyDeviceInstance?.removeFromStudyDeviceFields(studyDeviceFieldInstance[i])
                    deviceFields[i]?.removeFromStudyDeviceFields(studyDeviceFieldInstance[i])
                }
                StudyDevice?.unlink(Study.findById(params.study.id),Device.findById(params.device.id))
                def studyDeviceFields = studyDeviceFieldInstance
                render(view: "create", model: [studyDeviceFields: studyDeviceFields,studyDeviceFieldInstance: studyDeviceFieldInstance])
             }
        }
        else {
            for (int i=0; i < deviceFields.size(); i++)
            {
                studyDeviceInstance?.removeFromStudyDeviceFields(studyDeviceFieldInstance[i])
                deviceFields[i]?.removeFromStudyDeviceFields(studyDeviceFieldInstance[i])
            }
            StudyDevice?.unlink(Study.findById(params.study.id),Device.findById(params.device.id))
            def studyDeviceFields = studyDeviceFieldInstance
            render(view: "create", model: [studyDeviceFields: studyDeviceFields, studyDeviceFieldInstance: studyDeviceFieldInstance, 'study.id': params.study.id])
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

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def edit = {
        def studyDeviceFields = []
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

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def update = {
        
        def studyDeviceInstance = StudyDevice.findByStudyAndDevice(Study.findById(params.study.id),Device.findById(params.device.id))
        def deviceFields = DeviceField.findAllByDevice(Device.findById(params.device.id))
        def allValid = true
        def studyDeviceFields = []
        
        
        for (int i=0; i < deviceFields.size(); i++)
        {
            
            if (params["studyDeviceFields["+i+"]"] != null)
            {
                studyDeviceFields[i] = StudyDeviceField.get(params["studyDeviceFields["+i+"]"]?.id)
                if (studyDeviceFields[i])
                {
                    studyDeviceFields[i].properties = params["studyDeviceFields["+i+"]"]
                }
                else
                {
                    studyDeviceFields[i] = new StudyDeviceField(params["studyDeviceFields["+i+"]"])
                    studyDeviceInstance?.addToStudyDeviceFields(studyDeviceFields[i])
                    deviceFields[i]?.addToStudyDeviceFields(studyDeviceFields[i])
                }
                
            }
        }
        
         if (validStudyDeviceFields(studyDeviceFields)) {
            if (laterVersion(studyDeviceFields))
            {
                        studyDeviceFields.sort {x,y -> x.deviceField.dateCreated <=> y.deviceField.dateCreated}
                        render(view: "edit", model: [studyDeviceFields: studyDeviceFields, deviceFields: deviceFields])
                        return
             }
            
            if (saveAllStudyDeviceFields(studyDeviceFields)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'studyDevice.label', default: 'Device'), studyDeviceInstance.device])}"
                 redirect(action: "list", controller:"studyDevice", mapping: "studyDeviceDetails", params:["studyId":params.studyId, "device.id": params.device.id, "study.id": params.study.id] )
             }
            else {
                studyDeviceFields.sort {x,y -> x.deviceField.dateCreated <=> y.deviceField.dateCreated}
                render(view: "edit", model: [studyDeviceFields: studyDeviceFields, deviceFields: deviceFields])
            }
        }
        else {
                studyDeviceFields.sort {x,y -> x?.deviceField?.dateCreated <=> y?.deviceField?.dateCreated}
                render(view: "edit", model: [studyDeviceFields: studyDeviceFields,deviceFields: deviceFields])
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
