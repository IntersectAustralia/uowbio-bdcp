package au.org.intersect.bdcp

class DeviceFieldController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def deviceInstance = Device.findById(params.deviceId)
        [deviceFieldInstanceList: DeviceField.findAllByDevice(deviceInstance), deviceFieldInstanceTotal: DeviceField.count(), deviceInstance: deviceInstance]
    }

    def create = {
        def deviceInstance = Device.findById(params.deviceId)
        def deviceFieldInstance = new DeviceField()
        deviceFieldInstance.properties = params
        return [deviceFieldInstance: deviceFieldInstance, deviceInstance: deviceInstance]
    }

    def save = {
        def deviceFieldInstance = new DeviceField(params)
        if (deviceFieldInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'deviceField.label', default: 'DeviceField'), deviceFieldInstance.fieldLabel])}"
            redirect(action: "list", mapping: "deviceFieldDetails", params: [deviceGroupId: params.deviceGroupId, deviceId: params.deviceId])
        }
        else {
            render(view: "create", model: [deviceFieldInstance: deviceFieldInstance])
        }
    }

    def show = {
        def deviceFieldInstance = DeviceField.get(params.id)
        if (!deviceFieldInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceField.label', default: 'DeviceField'), params.id])}"
            redirect(action: "list")
        }
        else {
            [deviceFieldInstance: deviceFieldInstance]
        }
    }

    def edit = {
        def deviceFieldInstance = DeviceField.get(params.id)
        if (!deviceFieldInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceField.label', default: 'DeviceField'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [deviceFieldInstance: deviceFieldInstance]
        }
    }

    def update = {
        def deviceFieldInstance = DeviceField.get(params.id)
        if (deviceFieldInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (deviceFieldInstance.version > version) {
                    
                    deviceFieldInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'deviceField.label', default: 'DeviceField')] as Object[], "Another user has updated this DeviceField while you were editing")
                    render(view: "edit", model: [deviceFieldInstance: deviceFieldInstance])
                    return
                }
            }
            deviceFieldInstance.properties = params
            if (!deviceFieldInstance.hasErrors() && deviceFieldInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'deviceField.label', default: 'DeviceField'), deviceFieldInstance.fieldLabel])}"
                redirect(action: "list", mapping: "deviceFieldDetails", params: [deviceGroupId: params.deviceGroupId, deviceId: params.deviceId])
            }
            else {
                render(view: "edit", model: [deviceFieldInstance: deviceFieldInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceField.label', default: 'DeviceField'), params.id])}"
            redirect(action: "list", mapping: "deviceFieldDetails", params: [deviceGroupId: params.deviceGroupId, deviceId: params.deviceId])
        }
    }

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
