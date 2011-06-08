package au.org.intersect.bdcp

class StudyDeviceFieldController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def studyDeviceFields = []
        [studyDeviceFieldInstanceList: StudyDeviceField.list(params), studyDeviceFieldInstanceTotal: StudyDeviceField.count(), studyDeviceFields: studyDeviceFields]
    }

    def create = {
        def studyDeviceFieldInstance = new StudyDeviceField()
        studyDeviceFieldInstance.properties = params
        def studyDeviceFields = []
        return [studyDeviceFieldInstance: studyDeviceFieldInstance, studyDeviceFields: studyDeviceFields]
    }

    def save = {
        def studyDeviceFieldInstance = new StudyDeviceField(params)
        if (studyDeviceFieldInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'studyDeviceField.label', default: 'StudyDeviceField'), studyDeviceFieldInstance.id])}"
            redirect(action: "show", id: studyDeviceFieldInstance.id)
        }
        else {
            render(view: "create", model: [studyDeviceFieldInstance: studyDeviceFieldInstance])
        }
    }

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
