package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured

class StudyDeviceFieldController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
    def index = {
        redirect(action: "list", params: params)
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def studyDeviceFields = []
        [studyDeviceFieldInstanceList: StudyDeviceField.list(params), studyDeviceFieldInstanceTotal: StudyDeviceField.count(), studyDeviceFields: studyDeviceFields]
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
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
    
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
    def validStudyDeviceFields(studyDeviceFields)
    {
        def studyDeviceInstance = StudyDevice.link(Study.findById(params.study.id),Device.findById(params.device.id))
        def deviceFields = DeviceField.findAllByDevice(Device.findById(params.device.id))
        def allValid = true
        for (int i=0; i < studyDeviceFields.size(); i++)
        {
            if (studyDeviceFields[i]?.id == null)
            {
                studyDeviceFields[i] = new StudyDeviceField(params["studyDeviceFields["+i+"]"])
            }
            else
            {
                studyDeviceFields[i].properties =params["studyDeviceFields["+i+"]"]
            }
            studyDeviceInstance?.addToStudyDeviceFields(studyDeviceFields[i])
            deviceFields[i]?.addToStudyDeviceFields(studyDeviceFields[i])
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
        for (int i=0; i < studyDeviceFields.size(); i++)
        {
            if (params["studyDeviceFields["+i+"]"].version) {
                def version = params["studyDeviceFields["+i+"]"].version.toLong()
                if (studyDeviceFields[i].version > version)
                {
                    isLaterVersion = true
                }
            }
        }
        return isLaterVersion
    }
    
    
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
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
                //flash.message = "${message(code: 'default.created.message', args: [message(code: 'studyDeviceField.label', default: 'StudyDeviceField'), studyDeviceFieldInstance.id])}"
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

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
    def show = {
       def studyDeviceFields = []
        def studyDeviceFieldInstance = []
//        def studyDeviceFieldInstance = StudyDeviceField.get(params.id)
//        if (!studyDeviceFieldInstance) {
         if (!studyDeviceFields)
         {  
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyDeviceField.label', default: 'StudyDeviceField'), params.id])}"
            redirect(action: "list")
        }
        else {
            [studyDeviceFields: studyDeviceFields, studyDeviceFieldInstance: studyDeviceFieldInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
    def edit = {
        def studyDeviceFields = []
        def studyFields = []
        studyDeviceFields.addAll(StudyDevice.findByStudyAndDevice(Study.findById(params.studyId), Device.findById(params.device.id))?.studyDeviceFields)
        studyDeviceFields.sort {x,y -> x.deviceField.dateCreated <=> y.deviceField.dateCreated}
        def deviceFields = DeviceField.findAllByDevice(Device.findById(params.device.id))
        def studyDeviceFieldInstance = []
        
        //def studyDeviceFieldInstance = StudyDeviceField.get(params.id)
        if (studyDeviceFields == null) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyDeviceField.label', default: 'StudyDeviceField'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [studyDeviceFieldInstance: studyDeviceFieldInstance, studyDeviceFields: studyDeviceFields, deviceFields: deviceFields]
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
    def update = {
        
        def studyDeviceInstance = StudyDevice.findByStudyAndDevice(Study.findById(params.study.id),Device.findById(params.device.id))
        def deviceFields = DeviceField.findAllByDevice(Device.findById(params.device.id))
        def studyDeviceFields = StudyDeviceField.findAllByStudyDevice(studyDeviceInstance)
        def allValid = true
        def studyDeviceFieldInstance = []
        studyDeviceFieldInstance.addAll(studyDeviceFields)
        studyDeviceFields = studyDeviceFieldInstance
        
        if (validStudyDeviceFields(studyDeviceFields)) {
            if (laterVersion(studyDeviceFields))
            {
                        for (int i=0; i < deviceFields.size(); i++)
                        {
                            if (studyDeviceFields[i]?.id == null)
                            {
                                studyDeviceInstance?.removeFromStudyDeviceFields(studyDeviceFields[i])
                                deviceFields[i]?.removeFromStudyDeviceFields(studyDeviceFields[i])
                            }
                            
                            if (params["studyDeviceFields["+i+"]"].version) {
                                def version = params["studyDeviceFields["+i+"]"].version.toLong()
                                if (studyDeviceFields[i].version > version)
                                {
                                    studyDeviceFields[i].errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'studyDeviceField.label', default: 'StudyDeviceField')] as Object[], "Another user has updated this StudyDeviceField while you were editing")
                                    studyDeviceFields[i].hasErrors()    
                                }
                            }
                        }
                        
                        render(view: "edit", model: [studyDeviceFields: studyDeviceFields])
                        return
             }
            
            if (saveAllStudyDeviceFields(studyDeviceFieldInstance)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'studyDevice.label', default: 'Device'), studyDeviceInstance.device])}"
                //flash.message = "${message(code: 'default.updated.message', args: [message(code: 'studyDeviceField.label', default: 'StudyDeviceField'), studyDeviceFieldInstance.id])}"
                 redirect(action: "list", controller:"studyDevice", mapping: "studyDeviceDetails", params:["studyId":params.studyId, "device.id": params.device.id, "study.id": params.study.id] )
             }
            else {
                for (int i=0; i < deviceFields.size(); i++)
                {
                    if (studyDeviceFieldInstance[i]?.id == null)
                    {
                        studyDeviceInstance?.removeFromStudyDeviceFields(studyDeviceFieldInstance[i])
                        deviceFields[i]?.removeFromStudyDeviceFields(studyDeviceFieldInstance[i])
                    }
                }
                studyDeviceFields = studyDeviceFieldInstance
                render(view: "edit", model: [studyDeviceFields: studyDeviceFields,studyDeviceFieldInstance: studyDeviceFieldInstance])
            }
        }
        else {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyDeviceField.label', default: 'StudyDeviceField'), params.id])}"
            for (int i=0; i < deviceFields.size(); i++)
                {
                    if (studyDeviceFieldInstance[i]?.id == null)
                    {
                        studyDeviceInstance?.removeFromStudyDeviceFields(studyDeviceFieldInstance[i])
                        deviceFields[i]?.removeFromStudyDeviceFields(studyDeviceFieldInstance[i])
                    }
                }
                studyDeviceFields = studyDeviceFieldInstance
                render(view: "edit", model: [studyDeviceFields: studyDeviceFields,studyDeviceFieldInstance: studyDeviceFieldInstance])
        }
    }
    
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
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
