package au.org.intersect.bdcp

class ResultsDetailsFieldController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [resultsDetailsFieldInstanceList: ResultsDetailsField.list(params), resultsDetailsFieldInstanceTotal: ResultsDetailsField.count()]
    }

    def create = {
        def resultsDetailsFieldInstance = new ResultsDetailsField()
        resultsDetailsFieldInstance.properties = params
        return [resultsDetailsFieldInstance: resultsDetailsFieldInstance]
    }

    def save = {
        def resultsDetailsFieldInstance = new ResultsDetailsField(params)
        if (resultsDetailsFieldInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'resultsDetailsField.label', default: 'Results Details Field'), resultsDetailsFieldInstance.fieldLabel])}"
            redirect(action: "list")
        }
        else {
            render(view: "create", model: [resultsDetailsFieldInstance: resultsDetailsFieldInstance])
        }
    }

    def show = {
        def resultsDetailsFieldInstance = ResultsDetailsField.get(params.id)
        if (!resultsDetailsFieldInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'resultsDetailsField.label', default: 'ResultsDetailsField'), params.id])}"
            redirect(action: "list")
        }
        else {
            [resultsDetailsFieldInstance: resultsDetailsFieldInstance]
        }
    }

    def edit = {
        def resultsDetailsFieldInstance = ResultsDetailsField.get(params.id)
        if (!resultsDetailsFieldInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'resultsDetailsField.label', default: 'ResultsDetailsField'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [resultsDetailsFieldInstance: resultsDetailsFieldInstance]
        }
    }

    def update = {
        def resultsDetailsFieldInstance = ResultsDetailsField.get(params.id)
        if (resultsDetailsFieldInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (resultsDetailsFieldInstance.version > version) {
                    
                    resultsDetailsFieldInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'resultsDetailsField.label', default: 'Results Details Field')] as Object[], "Another user has updated this Results Details Field while you were editing")
                    render(view: "edit", model: [resultsDetailsFieldInstance: resultsDetailsFieldInstance])
                    return
                }
            }
            resultsDetailsFieldInstance.properties = params
            if (!resultsDetailsFieldInstance.hasErrors() && resultsDetailsFieldInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'resultsDetailsField.label', default: 'Results Details Field'), resultsDetailsFieldInstance.fieldLabel])}"
                redirect(action: "list")
            }
            else {
                render(view: "edit", model: [resultsDetailsFieldInstance: resultsDetailsFieldInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'resultsDetailsField.label', default: 'Results Details Field'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def resultsDetailsFieldInstance = ResultsDetailsField.get(params.id)
        if (resultsDetailsFieldInstance) {
            try {
                resultsDetailsFieldInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'resultsDetailsField.label', default: 'ResultsDetailsField'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'resultsDetailsField.label', default: 'ResultsDetailsField'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'resultsDetailsField.label', default: 'ResultsDetailsField'), params.id])}"
            redirect(action: "list")
        }
    }
}
