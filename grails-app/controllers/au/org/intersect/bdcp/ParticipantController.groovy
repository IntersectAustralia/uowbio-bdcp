package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured

class ParticipantController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def index = {
		cache false
        redirect(action: "list", params: params)
    }

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def list = {
		cache false
		def studyInstance = Study.get(params.studyId)
		def participantsInStudy = Participant.executeQuery('select count(p) from Participant p where p.study = :study',[study:studyInstance])
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [participantInstanceList: Participant.list(params), participantInstanceTotal: Participant.count(), studyInstance:studyInstance, participantsInStudy: participantsInStudy]
    }

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def create = {
		cache false
        def participantInstance = new Participant()
        participantInstance.properties = params
        return [participantInstance: participantInstance]
    }

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def save = {
		cache false
		def participantInstance = new Participant(params)
		participantInstance.identifier = participantInstance.identifier?.trim()
		if (participantInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'participant.label', default: 'Participant'), participantInstance.identifier])}"
//			redirect url:createLink(controller: 'participant', action:'list',
//				mapping:'participantDetails', params:[studyId: params.studyId, id: participantInstance.id])
			redirect(controller: "study", action: "show", id: params.studyId, params:["participantsSelected":"true"])
        }
        else {
            render(view: "create", model: [participantInstance: participantInstance])
		}
    }

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def show = {
		cache false
        def participantInstance = Participant.get(params.id)
        if (!participantInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participant.label', default: 'Participant'), params.id])}"
            redirect(controller: "study", action: "show", id: params.studyId, params:["participantsSelected":"true"])
        }
        else {
            [participantInstance: participantInstance]
        }
    }

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def edit = {
		cache false
        def participantInstance = Participant.get(params.id)
        if (!participantInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participant.label', default: 'Participant'), params.id])}"
            redirect(controller: "study", action: "show", id: params.studyId, params:["participantsSelected":"true"])
        }
        else {
            return [participantInstance: participantInstance, studyId: params.studyId]
        }
    }

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def update = {
		cache false
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
                redirect(controller: "study", action: "show", id: params.studyId, params:["participantsSelected":"true"])
            }
            else {
                render(view: "edit", model: [participantInstance: participantInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participant.label', default: 'Participant'), params.id])}"
            redirect(controller: "study", action: "show", id: params.studyId, params:["participantsSelected":"true"])
        }
    }

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def delete = {
		cache false
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
