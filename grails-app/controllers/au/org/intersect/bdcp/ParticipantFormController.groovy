package au.org.intersect.bdcp

import java.io.File
import java.io.FileInputStream;
import java.io.FileOutputStream;


class ParticipantFormController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	private ParticipantForm extractParticipantForm(i)
	{
		
			def message =[]
			def pfc = new ParticipantFormCommand()
			bindData( pfc, params )
			ParticipantForm participantFormInstance = pfc.forms[i];
			def f = request.getFile("form.${i}")
			if (!f?.isEmpty())
			{
				participantFormInstance.form = f
			}
		   return participantFormInstance
	}
	
	private boolean validateParticipantForms(participantForms)
	{
		def allValid = true
		participantForms.each { 
			if (!it.validate())
			{
				allValid = false
			}
		}
		return allValid
	}
	
	def upload = {
		
		def participantForms = []
		def participantFormsError = []
		for (i in 0..9)
		{
			participantForms[i] = extractParticipantForm(i)
		}
		
		if (!validateParticipantForms(participantForms))
		{
			params.max = Math.min(params.max ? params.int('max') : 10, 100)
			render(view: "list", model: [participantForms: participantForms,participantFormInstance: participantForms[0],participantFormInstanceList: ParticipantForm.list(params), participantFormInstanceTotal: ParticipantForm.count(), participantInstance: Participant.get(params.participantId) ])
		}
		else
		{
			def message = []
			for (i in 0..9)
			{
				def participantFormInstance = participantForms[i]
				def f = request.getFile("form.${i}")
				if (participantFormInstance.save(flush: true)) {
								new File( grailsApplication.config.forms.location.toString() + File.separatorChar + params.participantId.toString()).mkdirs()
								f.transferTo( new File( grailsApplication.config.forms.location.toString() + File.separatorChar + params.participantId.toString() + File.separatorChar + participantFormInstance.id ) )
								participantFormInstance.form = participantFormInstance.id
				
							    message[i] ="Participant form ${participantFormInstance.formName} uploaded"
				}
			}
				
				if (message.size() < 2)
							{
								flash.message = "${message.size()} Participant Form uploaded"
							}
							else
							{
								flash.message = "${message.size()} Participant Forms uploaded"
							}
				
		redirect url: createLink(controller: 'participantForm', action:'list',
							mapping:'participantFormDetails', params:[studyId: params.studyId, participantId: params.participantId])
		}
	}
	
	def downloadFile =
	{
		def participantFormInstance = ParticipantForm.get(params.id)
		
		def fileDoc = new File(participantFormInstance.form);
		if(fileDoc.exists()){
			// force download
			def fileName = fileDoc.getName();
			response.setContentType("application/octet-stream")
			response.setHeader "Content-disposition", "attachment; filename=${fileName}" ;
			response.outputStream << fileDoc.newInputStream();
			response.outputStream.flush();
			
	   }
		return null;	
	}
	
	def index = {
        redirect(action: "list", params: params)
    }

    def list = {
		def participantInstance = Participant.get(params.participantId)		
		def fileResourceInstanceList = []
		def participantForms = []
		def f = new File( grailsApplication.config.forms.location.toString() + File.separatorChar + params.participantId.toString() )
		if( f.exists() ){
			f.eachFile(){ file->
			if( !file.isDirectory() )
			{
				if(ParticipantForm.findWhere(form:file.name) != null)
				{
					fileResourceInstanceList.add( file.name )
				}
			}
			}
		}
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [participantFormInstanceList: ParticipantForm.list(params), participantFormInstanceTotal: ParticipantForm.count(), fileResourceInstanceList: fileResourceInstanceList, participantInstance: participantInstance,participantForms: participantForms]
    }
		
    def create = {
        def participantFormInstance = new ParticipantForm()
        participantFormInstance.properties = params
		def participantForms = []
        return [participantFormInstance: participantFormInstance, participantForms: participantForms]
    }

    def save = {
        def participantFormInstance = new ParticipantForm(params)
        if (participantFormInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), participantFormInstance.id])}"
            redirect(action: "show", id: participantFormInstance.id)
        }
        else {
            render(view: "create", model: [participantFormInstance: participantFormInstance])
        }
    }

    def show = {
        def participantFormInstance = ParticipantForm.get(params.id)
        if (!participantFormInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), params.id])}"
            redirect(action: "list")
        }
        else {
            [participantFormInstance: participantFormInstance]
        }
    }

    def edit = {
        def participantFormInstance = ParticipantForm.get(params.id)
        if (!participantFormInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [participantFormInstance: participantFormInstance]
        }
    }

    def update = {
        def participantFormInstance = ParticipantForm.get(params.id)
        if (participantFormInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (participantFormInstance.version > version) {
                    
                    participantFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'participantForm.label', default: 'ParticipantForm')] as Object[], "Another user has updated this ParticipantForm while you were editing")
                    render(view: "edit", model: [participantFormInstance: participantFormInstance])
                    return
                }
            }
            participantFormInstance.properties = params
            if (!participantFormInstance.hasErrors() && participantFormInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), participantFormInstance.id])}"
                redirect(action: "show", id: participantFormInstance.id)
            }
            else {
                render(view: "edit", model: [participantFormInstance: participantFormInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def participantFormInstance = ParticipantForm.get(params.id)
        if (participantFormInstance) {
            try {
                participantFormInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), params.id])}"
            redirect(action: "list")
        }
    }
}
class ParticipantFormCommand {
	ParticipantForm[] forms = [ new ParticipantForm(), new ParticipantForm(), new ParticipantForm(),
		new ParticipantForm(), new ParticipantForm(), new ParticipantForm(),
		new ParticipantForm(), new ParticipantForm(), new ParticipantForm(),
		new ParticipantForm()] as ParticipantForm[]
}