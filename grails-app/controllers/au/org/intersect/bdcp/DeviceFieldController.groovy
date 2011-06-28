package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured

class DeviceFieldController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER'])
    def index = {
        redirect(action: "list", params: params)
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def deviceInstance = Device.findById(params.deviceId)
        [deviceFieldInstanceList: deviceInstance?.deviceFields, deviceFieldInstanceTotal: DeviceField.count(), deviceInstance: deviceInstance]
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def create = {
        def deviceInstance = Device.findById(params.deviceId)
        def deviceFieldInstance = new DeviceField()
        deviceFieldInstance.properties = params
        return [deviceFieldInstance: deviceFieldInstance, deviceInstance: deviceInstance]
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def save = {
        def deviceInstance = Device.findById(params.deviceId)
        def deviceFieldInstance = new DeviceField(params)
        if (deviceFieldInstance.validate()) {
			deviceInstance.addToDeviceFields(deviceFieldInstance)
			deviceInstance.save(flush: true)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'deviceField.label', default: 'Field'), deviceFieldInstance.fieldLabel])}"
            redirect(action: "list", mapping: "deviceFieldDetails", params: [deviceGroupId: params.deviceGroupId, deviceId: params.deviceId])
        }
        else {
            render(view: "create", model: [deviceFieldInstance: deviceFieldInstance, deviceInstance: deviceInstance])
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def show = {
        def deviceFieldInstance = DeviceField.get(params.id)
        if (!deviceFieldInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceField.label', default: 'Field'), params.id])}"
            redirect(action: "list")
        }
        else {
            [deviceFieldInstance: deviceFieldInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def edit = {
        def deviceInstance = Device.findById(params.deviceId)
        def deviceFieldInstance = DeviceField.get(params.id)
        if (!deviceFieldInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceField.label', default: 'Field'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [deviceFieldInstance: deviceFieldInstance, deviceInstance: deviceInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def update = {
        def deviceFieldInstance = DeviceField.get(params.id)
        if (deviceFieldInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (deviceFieldInstance.version > version) {
                    
                    deviceFieldInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'deviceField.label', default: 'Field')] as Object[], "Another user has updated this Field while you were editing")
                    render(view: "edit", model: [deviceFieldInstance: deviceFieldInstance, deviceInstance: deviceFieldInstance.device])
                    return
                }
            }
            deviceFieldInstance.properties = [fieldLabel: params.fieldLabel, fieldType: params.fieldType, staticContent: params.staticContent] 
            if (deviceFieldInstance.validate() && deviceFieldInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'deviceField.label', default: 'Field'), deviceFieldInstance.fieldLabel])}"
                redirect(action: "list", mapping: "deviceFieldDetails", params: [deviceGroupId: params.deviceGroupId, deviceId: params.deviceId])
            }
            else {
                render(view: "edit", model: [deviceFieldInstance: deviceFieldInstance, deviceInstance: deviceFieldInstance.device])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceField.label', default: 'Field'), params.id])}"
            redirect(action: "list", mapping: "deviceFieldDetails", params: [deviceGroupId: params.deviceGroupId, deviceId: params.deviceId])
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def delete = {
        def deviceFieldInstance = DeviceField.get(params.id)
        if (deviceFieldInstance) {
            try {
                deviceFieldInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'deviceField.label', default: 'DeviceField'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'deviceField.label', default: 'DeviceField'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceField.label', default: 'DeviceField'), params.id])}"
            redirect(action: "list")
        }
    }
}
