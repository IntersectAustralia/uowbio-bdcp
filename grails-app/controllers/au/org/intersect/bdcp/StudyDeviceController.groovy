package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured

class StudyDeviceController {

    static allowedMethods = [update: "POST", delete: "POST"]

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
    def index = {
        redirect(action: "list", params: params)
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
    def list = {
        def studyInstance = Study.get(params.studyId)
        def deviceGroupList = DeviceGroup.list()
        def sortedDeviceGroupList = deviceGroupList.sort {x,y -> y.groupingName <=> x.groupingName}
        def deviceGroupsMapping = sortedDeviceGroupList.collect {deviceGroup ->
            def devices = studyInstance.studyDevices.findAll { it.device.deviceGroup.id == deviceGroup.id }
            devices = devices.collect { it.device }
            [deviceGroup:deviceGroup, devices:devices.sort {x,y -> x.name <=> y.name}]
        }
        deviceGroupsMapping = deviceGroupsMapping.findAll { it.devices.size() > 0 }
        [deviceGroupsMapping: deviceGroupsMapping, studyInstance: studyInstance]
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
    def create = {
        def studyDeviceInstance = new StudyDevice()
        def studyInstance = Study.findById(params.studyId)
        studyDeviceInstance.properties = params
        def deviceGroupList = DeviceGroup.list()
        def sortedDeviceGroupList = deviceGroupList.sort {x,y -> y.groupingName <=> x.groupingName}
        return [studyDeviceInstance: studyDeviceInstance, studyInstance: studyInstance, deviceGroupList: sortedDeviceGroupList]
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
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

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
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

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
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

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
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

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
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
