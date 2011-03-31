package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured

class StudyController
{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def index =
	{
		redirect(action: "list", params: params)
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def list =
	{
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[studyInstanceList: Study.list(params), studyInstanceTotal: Study.count()]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def create =
	{
		def studyInstance = new Study()
		studyInstance.properties = params
		return [studyInstance: studyInstance]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def save =
	{
		def studyInstance = new Study(params)
		if (studyInstance.save(flush: true))
		{
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'study.label', default: 'Study'), studyInstance.studyTitle])}"
			redirect(action: "show", id: studyInstance.id)
		}
		else
		{
			render(view: "create", model: [studyInstance: studyInstance])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def show =
	{
		def studyInstance = Study.get(params.id)
		def participantsInStudy = Participant.executeQuery('select count(p) from Participant p where p.study = :study',[study:studyInstance])
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
//		[participantInstanceList: Participant.list(params), participantInstanceTotal: Participant.count(), studyInstance:studyInstance, participantsInStudy: participantsInStudy]
		if (!studyInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'study.label', default: 'Study'), params.id])}"
			redirect(controller: "project", action: "list")
		}
		else
		{
			String participantsSelected
			String detailsSelected
			
			if (params.participantsSelected == "true")
			{
				participantsSelected = params.participantsSelected
				detailsSelected = "false"
			}
			else
			{
				participantsSelected = "false"
				detailsSelected = "true"
			}
			[studyInstance: studyInstance, participantInstanceList: Participant.findAllByStudy(studyInstance), participantInstanceTotal: Participant.countByStudy(studyInstance), participantsInStudy: participantsInStudy,participantsSelected:participantsSelected,detailsSelected:detailsSelected]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def edit =
	{
		def studyInstance = Study.get(params.id)
		if (!studyInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'study.label', default: 'Study'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			return [studyInstance: studyInstance]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def update =
	{
		def studyInstance = Study.get(params.id)
		if (studyInstance)
		{
			if (params.version)
			{
				def version = params.version.toLong()
				if (studyInstance.version > version)
				{

					studyInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'study.label', default: 'Study')]
					as Object[], "Another user has updated this Study while you were editing")
					render(view: "edit", model: [studyInstance: studyInstance])
					return
				}
			}
			studyInstance.properties = params
			if (!studyInstance.hasErrors() && studyInstance.save(flush: true))
			{
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'study.label', default: 'Study'), studyInstance.studyTitle])}"
				redirect(action: "show", id: studyInstance.id)
				
			}
			else
			{
				render(view: "edit", model: [studyInstance: studyInstance])
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'study.label', default: 'Study'), params.id])}"
			redirect(action: "list")
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def delete =
	{
		def studyInstance = Study.get(params.id)
		if (studyInstance)
		{
			try
			{
				studyInstance.delete(flush: true)
//				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'study.label', default: 'Study'), params.id])}"
//				redirect(action: "list")
				redirect(controller:"project", action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e)
			{
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'study.label', default: 'Study'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'study.label', default: 'Study'), params.id])}"
			redirect(action: "list")
		}
	}
}
