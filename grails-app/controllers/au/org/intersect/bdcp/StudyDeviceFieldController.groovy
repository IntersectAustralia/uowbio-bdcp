package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured

class StudyDeviceFieldController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def index = {
        redirect(action: "list", params: params)
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def studyDeviceFields = []
        [studyDeviceFieldInstanceList: StudyDeviceField.list(params), studyDeviceFieldInstanceTotal: StudyDeviceField.count(), studyDeviceFields: studyDeviceFields]
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def create = {
        def studyDeviceFieldInstance = new StudyDeviceField()
        studyDeviceFieldInstance.properties = params
        def studyDeviceFields = []
        return [studyDeviceFieldInstance: studyDeviceFieldInstance, studyDeviceFields: studyDeviceFields]
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
    
    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
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
                render(view: "create", model: [studyDeviceFieldInstance: studyDeviceFieldInstance, studyDeviceFields: studyDeviceFields])
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
            render(view: "create", model: [studyDeviceFieldInstance: studyDeviceFieldInstance, studyDeviceFields: studyDeviceFields, 'study.id': params.study.id])
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def show = {
        def studyDeviceFieldInstance = StudyDeviceField.get(params.id)
        if (!studyDeviceFieldInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyDeviceField.label', default: 'StudyDeviceField'), params.id])}"
            redirect(action: "list")
        }
        else {
            [studyDeviceFieldInstance: studyDeviceFieldInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def edit = {
        def studyDeviceFieldInstance = StudyDeviceField.get(params.id)
        if (!studyDeviceFieldInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyDeviceField.label', default: 'StudyDeviceField'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [studyDeviceFieldInstance: studyDeviceFieldInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def update = {
        def studyDeviceFieldInstance = StudyDeviceField.get(params.id)
        if (studyDeviceFieldInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (studyDeviceFieldInstance.version > version) {
                    
                    studyDeviceFieldInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'studyDeviceField.label', default: 'StudyDeviceField')] as Object[], "Another user has updated this StudyDeviceField while you were editing")
                    render(view: "edit", model: [studyDeviceFieldInstance: studyDeviceFieldInstance])
                    return
                }
            }
            studyDeviceFieldInstance.properties = params
            if (!studyDeviceFieldInstance.hasErrors() && studyDeviceFieldInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'studyDeviceField.label', default: 'StudyDeviceField'), studyDeviceFieldInstance.id])}"
                redirect(action: "show", id: studyDeviceFieldInstance.id)
            }
            else {
                render(view: "edit", model: [studyDeviceFieldInstance: studyDeviceFieldInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyDeviceField.label', default: 'StudyDeviceField'), params.id])}"
            redirect(action: "list")
        }
    }
    
    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
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
