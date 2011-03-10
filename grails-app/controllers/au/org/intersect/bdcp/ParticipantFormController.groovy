package au.org.intersect.bdcp

import java.io.File;

class ParticipantFormController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def upload = {
		
		def participantFormInstance = new ParticipantForm(params)
		def f = request.getFile('fileUpload')
		if (!f.empty)
		{
			participantFormInstance.form = f
			if (participantFormInstance.save(flush: true)) {
				new File( grailsApplication.config.forms.location.toString() + File.separatorChar + params.participantId.toString()).mkdirs()
				f.transferTo( new File( grailsApplication.config.forms.location.toString() + File.separatorChar + params.participantId.toString() + File.separatorChar + participantFormInstance.id ) )
				participantFormInstance.form = participantFormInstance.id
			
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), participantFormInstance.formName])}"
            redirect(action: "show", id: participantFormInstance.id)
			}
			else 
			{
            render(view: "create", model: [participantFormInstance: participantFormInstance])
			}
		}
		else
		{
			if (!participantFormInstance.save(flash:true))
			{
				render(view: "create", model: [participantFormInstance: participantFormInstance])
			}
			
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
        [participantFormInstanceList: ParticipantForm.list(params), participantFormInstanceTotal: ParticipantForm.count(), fileResourceInstanceList: fileResourceInstanceList, participantInstance: participantInstance]
    }

    def create = {
        def participantFormInstance = new ParticipantForm()
        participantFormInstance.properties = params
        return [participantFormInstance: participantFormInstance]
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
