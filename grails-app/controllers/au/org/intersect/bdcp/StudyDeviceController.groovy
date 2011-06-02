package au.org.intersect.bdcp

class StudyDeviceController {

    static allowedMethods = [update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        def studyInstance = Study.get(params.studyId)
        def deviceGroupsMapping = DeviceGroup.list().collect {deviceGroup ->
            def devices = StudyDevice.list().findAll { it.device.deviceGroup.id == deviceGroup.id }
            devices = devices.collect { it.device }
            [deviceGroup:deviceGroup, devices:devices]
        }
        deviceGroupsMapping = deviceGroupsMapping.findAll { it.devices.size() > 0 }
        [deviceGroupsMapping: deviceGroupsMapping, studyInstance: studyInstance]
    }

    def create = {
        def studyDeviceInstance = new StudyDevice()
        def studyInstance = Study.findById(params.studyId)
        studyDeviceInstance.properties = params
        return [studyDeviceInstance: studyDeviceInstance, studyInstance: studyInstance]
    }

    def save = {
        def studyDeviceInstance = new StudyDevice(params)
        if (studyDeviceInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'studyDevice.label', default: 'Device'), studyDeviceInstance.device])}"
            redirect(mapping:"studyDeviceDetails",controller: "studyDevice", action: "list", id: studyDeviceInstance.id, params:[studyId: params.studyId])
        }
        else {
            render(view: "create", model: [studyDeviceInstance: studyDeviceInstance])
        }
    }

    def show = {
        def studyDeviceInstance = StudyDevice.get(params.id)
        if (!studyDeviceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyDevice.label', default: 'StudyDevice'), params.id])}"
            redirect(action: "list")
        }
        else {
            [studyDeviceInstance: studyDeviceInstance]
        }
    }

    def edit = {
        def studyDeviceInstance = StudyDevice.get(params.id)
        if (!studyDeviceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyDevice.label', default: 'StudyDevice'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [studyDeviceInstance: studyDeviceInstance]
        }
    }

    def update = {
        def studyDeviceInstance = StudyDevice.get(params.id)
        if (studyDeviceInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (studyDeviceInstance.version > version) {
                    
                    studyDeviceInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'studyDevice.label', default: 'StudyDevice')] as Object[], "Another user has updated this StudyDevice while you were editing")
                    render(view: "edit", model: [studyDeviceInstance: studyDeviceInstance])
                    return
                }
            }
            studyDeviceInstance.properties = params
            if (!studyDeviceInstance.hasErrors() && studyDeviceInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'studyDevice.label', default: 'StudyDevice'), studyDeviceInstance.id])}"
                redirect(action: "show", id: studyDeviceInstance.id)
            }
            else {
                render(view: "edit", model: [studyDeviceInstance: studyDeviceInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyDevice.label', default: 'StudyDevice'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def studyDeviceInstance = StudyDevice.get(params.id)
        if (studyDeviceInstance) {
            try {
                studyDeviceInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'studyDevice.label', default: 'StudyDevice'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'studyDevice.label', default: 'StudyDevice'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyDevice.label', default: 'StudyDevice'), params.id])}"
            redirect(action: "list")
        }
    }
}
