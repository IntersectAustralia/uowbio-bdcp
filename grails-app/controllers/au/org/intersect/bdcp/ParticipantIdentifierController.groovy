package au.org.intersect.bdcp

class ParticipantIdentifierController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [participantIdentifierInstanceList: ParticipantIdentifier.list(params), participantIdentifierInstanceTotal: ParticipantIdentifier.count()]
    }

    def create = {
        def participantIdentifierInstance = new ParticipantIdentifier()
        participantIdentifierInstance.properties = params
        return [participantIdentifierInstance: participantIdentifierInstance]
    }

    def save = {
        def participantIdentifierInstance = new ParticipantIdentifier(params)
        if (participantIdentifierInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'participantIdentifier.label', default: 'ParticipantIdentifier'), participantIdentifierInstance.id])}"
            redirect(action: "show", id: participantIdentifierInstance.id)
        }
        else {
            render(view: "create", model: [participantIdentifierInstance: participantIdentifierInstance])
        }
    }

    def show = {
        def participantIdentifierInstance = ParticipantIdentifier.get(params.id)
        if (!participantIdentifierInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participantIdentifier.label', default: 'ParticipantIdentifier'), params.id])}"
            redirect(action: "list")
        }
        else {
            [participantIdentifierInstance: participantIdentifierInstance]
        }
    }

    def edit = {
        def participantIdentifierInstance = ParticipantIdentifier.get(params.id)
        if (!participantIdentifierInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participantIdentifier.label', default: 'ParticipantIdentifier'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [participantIdentifierInstance: participantIdentifierInstance]
        }
    }

    def update = {
        def participantIdentifierInstance = ParticipantIdentifier.get(params.id)
        if (participantIdentifierInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (participantIdentifierInstance.version > version) {
                    
                    participantIdentifierInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'participantIdentifier.label', default: 'ParticipantIdentifier')] as Object[], "Another user has updated this ParticipantIdentifier while you were editing")
                    render(view: "edit", model: [participantIdentifierInstance: participantIdentifierInstance])
                    return
                }
            }
            participantIdentifierInstance.properties = params
            if (!participantIdentifierInstance.hasErrors() && participantIdentifierInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'participantIdentifier.label', default: 'ParticipantIdentifier'), participantIdentifierInstance.id])}"
                redirect(action: "show", id: participantIdentifierInstance.id)
            }
            else {
                render(view: "edit", model: [participantIdentifierInstance: participantIdentifierInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participantIdentifier.label', default: 'ParticipantIdentifier'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def participantIdentifierInstance = ParticipantIdentifier.get(params.id)
        if (participantIdentifierInstance) {
            try {
                participantIdentifierInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'participantIdentifier.label', default: 'ParticipantIdentifier'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'participantIdentifier.label', default: 'ParticipantIdentifier'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participantIdentifier.label', default: 'ParticipantIdentifier'), params.id])}"
            redirect(action: "list")
        }
    }
}
