package au.org.intersect.bdcp

class DeviceGroupController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def DeviceGroupList = DeviceGroup.list(params)
        def sortedDeviceGroup = DeviceGroupList.sort {x,y -> x.groupingName <=> y.groupingName}
        [deviceGroupInstanceList: sortedDeviceGroup, deviceGroupInstanceTotal: DeviceGroup.count()]
    }

    def create = {
        def deviceGroupInstance = new DeviceGroup()
        deviceGroupInstance.properties = params
        return [deviceGroupInstance: deviceGroupInstance]
    }

    def save = {
        def deviceGroupInstance = new DeviceGroup(params)
        if (deviceGroupInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'deviceGroup.label', default: 'DeviceGroup'), deviceGroupInstance.id])}"
            redirect(action: "list", id: deviceGroupInstance.id)
        }
        else {
            render(view: "create", model: [deviceGroupInstance: deviceGroupInstance])
        }
    }

    def show = {
        def deviceGroupInstance = DeviceGroup.get(params.id)
        if (!deviceGroupInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceGroup.label', default: 'DeviceGroup'), params.id])}"
            redirect(action: "list")
        }
        else {
            [deviceGroupInstance: deviceGroupInstance]
        }
    }

    def edit = {
        def deviceGroupInstance = DeviceGroup.get(params.id)
        if (!deviceGroupInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceGroup.label', default: 'DeviceGroup'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [deviceGroupInstance: deviceGroupInstance]
        }
    }

    def update = {
        def deviceGroupInstance = DeviceGroup.get(params.id)
        if (deviceGroupInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (deviceGroupInstance.version > version) {
                    
                    deviceGroupInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'deviceGroup.label', default: 'DeviceGroup')] as Object[], "Another user has updated this DeviceGroup while you were editing")
                    render(view: "edit", model: [deviceGroupInstance: deviceGroupInstance])
                    return
                }
            }
            deviceGroupInstance.properties = params
            if (!deviceGroupInstance.hasErrors() && deviceGroupInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'deviceGroup.label', default: 'DeviceGroup'), deviceGroupInstance.id])}"
                redirect(action: "list", id: deviceGroupInstance.id)
            }
            else {
                render(view: "edit", model: [deviceGroupInstance: deviceGroupInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceGroup.label', default: 'DeviceGroup'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def deviceGroupInstance = DeviceGroup.get(params.id)
        if (deviceGroupInstance) {
            try {
                deviceGroupInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'deviceGroup.label', default: 'DeviceGroup'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'deviceGroup.label', default: 'DeviceGroup'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceGroup.label', default: 'DeviceGroup'), params.id])}"
            redirect(action: "list")
        }
    }
}
