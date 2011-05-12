package au.org.intersect.bdcp

class DeviceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def deviceGroupInstance = DeviceGroup.findById(params.deviceGroupId)
        def sortedDeviceInstanceList = Device.findAllByDeviceGroup(deviceGroupInstance).sort {x,y -> x.name <=> y.name }
        [deviceInstanceList: sortedDeviceInstanceList, deviceInstanceTotal: Device.count(), "deviceGroupInstance": deviceGroupInstance]
    }

    def create = {
        def deviceInstance = new Device()
        deviceInstance.properties = params
        return [deviceInstance: deviceInstance]
    }

    def save = {
        def deviceInstance = new Device(params)
        if (deviceInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'device.label', default: 'Device'), deviceInstance.name])}"
            redirect( controller: "device", action: "list", mapping: "deviceDetails", params: [deviceGroupId: params.deviceGroupId])
        }
        else {
            render(view: "create", model: [deviceInstance: deviceInstance])
        }
    }

    def show = {
        def deviceInstance = Device.get(params.id)
        if (!deviceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'device.label', default: 'Device'), params.id])}"
            redirect(action: "list")
        }
        else {
            [deviceInstance: deviceInstance]
        }
    }

    def edit = {
        def deviceInstance = Device.get(params.id)
        if (!deviceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'device.label', default: 'Device'), params.name])}"
            redirect(action: "list")
        }
        else {
            return [deviceInstance: deviceInstance]
        }
    }

    def update = {
        def deviceInstance = Device.get(params.id)
        if (deviceInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (deviceInstance.version > version) {
                    
                    deviceInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'device.label', default: 'Device')] as Object[], "Another user has updated this Device while you were editing")
                    render(view: "edit", model: [deviceInstance: deviceInstance])
                    return
                }
            }
            deviceInstance.properties = params
            if (!deviceInstance.hasErrors() && deviceInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'device.label', default: 'Device'), deviceInstance.name])}"
                redirect( controller: "device", action: "list", mapping: "deviceDetails", params: [deviceGroupId: params.deviceGroupId])
            }
            else {
                render(view: "edit", model: [deviceInstance: deviceInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'device.label', default: 'Device'), params.id])}"
            redirect( controller: "device", action: "list", mapping: "deviceDetails", params: [deviceGroupId: params.deviceGroupId])
        }
    }

    def delete = {
        def deviceInstance = Device.get(params.id)
        if (deviceInstance) {
            try {
                deviceInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'device.label', default: 'Device'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'device.label', default: 'Device'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'device.label', default: 'Device'), params.id])}"
            redirect(action: "list")
        }
    }
}
