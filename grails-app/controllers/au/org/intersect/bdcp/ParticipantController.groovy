package au.org.intersect.bdcp

class ParticipantController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
		def studyInstance = Study.get(params.studyId)
		def participantsInStudy = Participant.executeQuery('select count(p) from Participant p where p.study = :study',[study:studyInstance])
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [participantInstanceList: Participant.list(params), participantInstanceTotal: Participant.count(), studyInstance:studyInstance, participantsInStudy: participantsInStudy]
    }

    def create = {
        def participantInstance = new Participant()
        participantInstance.properties = params
        return [participantInstance: participantInstance]
    }

    def save = {
		
		def participantInstance = new Participant(params)
		participantInstance.identifier = participantInstance.identifier?.trim()
		if (participantInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'participant.label', default: 'Participant'), participantInstance.identifier])}"
			redirect url:createLink(controller: 'participant', action:'list',
				mapping:'participantDetails', params:[studyId: params.studyId, id: participantInstance.id])
			//redirect(action: "show", id: participantInstance.id)
        }
        else {
            render(view: "create", model: [participantInstance: participantInstance])
		}
    }

    def show = {
        def participantInstance = Participant.get(params.id)
        if (!participantInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participant.label', default: 'Participant'), params.id])}"
            redirect(action: "list")
        }
        else {
            [participantInstance: participantInstance]
        }
    }

    def edit = {
        def participantInstance = Participant.get(params.id)
        if (!participantInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participant.label', default: 'Participant'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [participantInstance: participantInstance, studyId: params.studyId]
        }
    }

    def update = {
        def participantInstance = Participant.get(params.id)
        if (participantInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (participantInstance.version > version) {
                    
                    participantInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'participant.label', default: 'Participant')] as Object[], "Another user has updated this Participant while you were editing")
                    render(view: "edit", model: [participantInstance: participantInstance])
                    return
                }
            }
            participantInstance.properties = params
			participantInstance.identifier = participantInstance.identifier?.trim()
			if (!participantInstance.hasErrors() && participantInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'participant.label', default: 'Participant'), participantInstance.identifier])}"
                redirect url:createLink(controller: 'participant', action:'list',
				mapping:'participantDetails', params:[studyId: params.studyId, id: participantInstance.id])
            }
            else {
                render(view: "edit", model: [participantInstance: participantInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participant.label', default: 'Participant'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def participantInstance = Participant.get(params.id)
        if (participantInstance) {
            try {
                participantInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'participant.label', default: 'Participant'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'participant.label', default: 'Participant'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participant.label', default: 'Participant'), params.id])}"
            redirect(action: "list")
        }
    }
}
