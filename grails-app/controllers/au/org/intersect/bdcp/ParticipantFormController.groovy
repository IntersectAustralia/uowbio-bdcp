package au.org.intersect.bdcp

import java.io.File

import eu.medsea.mimeutil.detector.MagicMimeMimeDetector
import grails.plugins.springsecurity.Secured




class ParticipantFormController
{
	static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]
	
	def roleCheckService

	private ParticipantForm extractParticipantForm(i)
	{
        
		def pfc = new ParticipantFormCommand()
		bindData( pfc, params )
		ParticipantForm participantFormInstance = pfc.forms[i];

		return participantFormInstance
	}

	private String normalizeValue(value)
	{
		value = value.replaceAll(/[^A-Za-z0-9-_\s]/, '')
		return value
	}
	
	private boolean validateParticipantForms(participantForms)
	{
		def allValid = true
		for (i in participantFormsToLoad())
		{
			if (request.getFile("form.${i}")?.isEmpty() && (session["fileName[${i}]"] == null))
			{
				participantForms[i].fileName = null
			}
            
			if (!participantForms[i]?.validate() && !participantForms[i].hasErrors())
			{
				allValid = false
			}
//            else if(participantForms[i]?.validate() && participantForms.findAll { it.formName.equalsIgnoreCase(participantForms[i].formName) }.size() > 1 )
//            {
//                participantForms[i].errors.rejectValue('formName', 'participantForm.formName.uniqueIgnoreCase.invalid')
//                allValid = false   
//            }
            
		}

		return allValid
	}

	private participantFormsToLoad()
	{
		def participantFormCommand = new ParticipantFormCommand()
		bindData( participantFormCommand, params )
		List<Integer> usedFields = new ArrayList<Integer>();
		participantFormCommand.forms.eachWithIndex()
		{ obj, i ->

			if ((obj?.formName.size() > 0) || obj?.fileName.size() >0)
			{
				usedFields.add (i)
			}
		}
        
		return usedFields
	}

	private populateSessionValues()
	{
		for (i in participantFormsToLoad())
		{
			if (!request.getFile("form.${i}")?.isEmpty())
			{
				session["fileName[${i}]"] = request.getFile("form.${i}")
			}
		}
	}

    private clearSessionValues()
    {
        session.fileName = null
    }
    
	private String getMimeType(File file)
	{
		// use mime magic
		MagicMimeMimeDetector detector = new MagicMimeMimeDetector();
		Collection mimeTypes = detector.getMimeTypesFile(file);

		if (mimeTypes) return mimeTypes.iterator().getAt(0).toString()

		return "application/octet-stream"
	}

	private String getRealPath()
	{
		return (request.getSession().getServletContext().getRealPath("/") + grailsApplication.config.forms.location.toString())
	}

	private String getFileExtension(fileName)
	{
		def fileExtension
		if (fileName != null)
		{
			int mid= fileName.lastIndexOf(".");

			if (!(mid < 0))
			{
				fileExtension=fileName.substring(mid+1,fileName.length());
			}
		}
		return fileExtension
	}

	private saveFile(file, participantFormInstance)
	{
		if (!file?.isEmpty() && !(file == null))
		{
			def fileExtension = getFileExtension(file?.getOriginalFilename())
			def fileName = file?.getOriginalFilename()
			file.transferTo( new File( getRealPath() + params.participantId.toString() +File.separatorChar + fileName) )
			participantFormInstance.form = participantFormInstance.formName
			participantFormInstance.contentType = file.contentType
			participantFormInstance.fileExtension = fileExtension
			participantFormInstance.storedFileName = fileName
            participantFormInstance.save(flush: true)
            
		}
		else
		{
			participantFormInstance.delete(flush: true)
		}
	}

    private renderUploadErrorMsg(participantForms)
    {
        def participantFormInstanceList = []
        def participantInstance = Participant.get(params.participantId)
        def f = new File( getRealPath() + params.participantId.toString() )
        if( f.exists() )
        {
            f.eachFile()
            { file->
                if( !file.isDirectory() )
                {
                    def participantForm = ParticipantForm.findWhere(storedFileName: file.name, participant: participantInstance)
                    if(participantForm != null)
                    {
                        participantFormInstanceList.add(participantForm)
                    }
                }
            }
        }
        render(view: "list", model: [participantForms: participantForms,participantFormInstanceList: participantFormInstanceList, participantFormInstanceTotal: participantFormInstanceList.size(), participantInstance: Participant.get(params.participantId), forms:participantForms, fileName: params.fileName, participantId: params.participantId ])
    }
    
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def upload =
	{
		cache false
		def participantForms = []
		def participantFormInstanceList = []
		def allValid = true
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
        
        for (i in participantFormsToLoad())
		{
			participantForms[i] = new ParticipantForm(params["forms["+i+"]"])		
        }

		if (!validateParticipantForms(participantForms))
		{
            populateSessionValues()
            renderUploadErrorMsg(participantForms);
		}
		else
		{
			for (i in participantFormsToLoad())
			{
                if (participantForms[i].save(flush: true))
				{
					new File( getRealPath() + params.participantId.toString()).mkdirs()
					def file = request.getFile("form.${i}")
					if (file?.isEmpty() && !(session["fileName[${i}]"] == null))
					{
						file = session["fileName[${i}]"]
					}
                    populateSessionValues()
					saveFile(file, participantForms[i])
				}
                else
                {
                    allValid = false    
                }
                
			}
            
            if (allValid == false)
            {
                for (i in participantFormsToLoad())
                {
                    participantForms[i].delete(flush:true)
                }
                
                renderUploadErrorMsg(participantForms);
                return
            }
            else
            {
                clearSessionValues()
            }
            
			switch (participantFormsToLoad().size())
			{
				case 0: flash.error = "No forms selected to upload"
				break

				case 1: flash.message = "${participantFormsToLoad().size()} Participant Form uploaded"
				break

				case 2..10: flash.message = "${participantFormsToLoad().size()} Participant Forms uploaded"
				break
				default:
				break
			}

			redirect url: createLink(controller: 'participantForm', action:'list',
			mapping:'participantFormDetails', params:[studyId: params.studyId, participantId: params.participantId])
		}
	}
	
	/**
	* Display Study only to research owner or research collaborator
	* @param _studyInstance
	*/
   private void redirectNonAuthorizedResearcherAccessStudy(Study _studyInstance)
   {
	   def userStore = UserStore.findByUsername(principal.username)
	   def studyCollaborator = StudyCollaborator.findByStudyAndCollaborator(_studyInstance,userStore)

	   if(!_studyInstance.project.owner.username.equals(principal.username) && !studyCollaborator){
		   redirect controller:'login', action: 'denied'
	   }
   }

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def downloadFile =
	{
		cache false
		def participantFormInstance = ParticipantForm.get(params.id)
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}

		def fileDoc = new File( getRealPath() + params.participantId.toString() +File.separatorChar + participantFormInstance.fileName)

		if(fileDoc.exists())
		{
			def fileName = participantFormInstance.fileName

			if (participantFormInstance.contentType == null)
			{
				response.setContentType(getMimeType(fileDoc))
			}
			else
			{
				response.setContentType participantFormInstance.contentType
			}
			response.setHeader "Content-disposition", "attachment; filename=\"${fileName}\"" ;
			response.outputStream << fileDoc.newInputStream();
			response.outputStream.flush();

			return false
		}
		return null
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def index =
	{
		cache false
		redirect(action: "list", params: params)
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def list =
	{
		cache false
		def participantInstance = Participant.get(params.participantId)
		def participantFormInstanceList = []
		def participantForms = []
		
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		
		def f = new File( getRealPath() + params.participantId.toString() )
		
		if( f.exists() )
		{
			f.eachFile()
			{ file->
				if( !file.isDirectory() )
				{
					def participantForm = ParticipantForm.findWhere(storedFileName: file.name, participant: participantInstance)
                    if(participantForm != null)
					{
						participantFormInstanceList.add(participantForm)
					}
				}
			}
		}
		[participantFormInstanceList: participantFormInstanceList, participantFormInstanceTotal: participantFormInstanceList.size(), participantInstance: participantInstance,participantForms: participantForms, forms:participantForms, participantId: params.participantId]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def create =
	{
		cache false
		def participantFormInstance = new ParticipantForm()
		participantFormInstance.properties = params
		def participantForms = []
		return [participantFormInstance: participantFormInstance, participantForms: participantForms]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def save =
	{
		cache false
		def participantFormInstance = new ParticipantForm(params)
		if (participantFormInstance.save(flush: true))
		{
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), participantFormInstance.id])}"
			redirect(action: "show", id: participantFormInstance.id)
		}
		else
		{
			render(view: "create", model: [participantFormInstance: participantFormInstance])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def show =
	{
		cache false
		def participantFormInstance = ParticipantForm.get(params.id)
		if (!participantFormInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			[participantFormInstance: participantFormInstance]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def edit =
	{
		cache false
		def participantFormInstance = ParticipantForm.get(params.id)
		if (!participantFormInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			return [participantFormInstance: participantFormInstance]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def update =
	{
		cache false
		def participantFormInstance = ParticipantForm.get(params.id)
		if (participantFormInstance)
		{
			if (params.version)
			{
				def version = params.version.toLong()
				if (participantFormInstance.version > version)
				{

					participantFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'participantForm.label', default: 'ParticipantForm')]
					as Object[], "Another user has updated this ParticipantForm while you were editing")
					render(view: "edit", model: [participantFormInstance: participantFormInstance])
					return
				}
			}
			participantFormInstance.properties = params
			if (!participantFormInstance.hasErrors() && participantFormInstance.save(flush: true))
			{
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), participantFormInstance.id])}"
				redirect(action: "show", id: participantFormInstance.id)
			}
			else
			{
				render(view: "edit", model: [participantFormInstance: participantFormInstance])
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), params.id])}"
			redirect(action: "list")
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def delete =
	{
		cache false
		def participantFormInstance = ParticipantForm.get(params.id)
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		
		if (participantFormInstance)
		{
			try
			{
				participantFormInstance.delete(flush: true)
				def filename = participantFormInstance.fileName
				def file =  new File( getRealPath() + params.participantId.toString() +File.separatorChar + participantFormInstance.fileName)
				if (file.exists())
				{
					file.delete()
				}
				flash.message = "Participant Form ${participantFormInstance.formName} deleted"
				redirect url: createLink(controller: 'participantForm', action:'list',
					mapping:'participantFormDetails', params:[studyId: params.studyId, participantId: params.participantId])
			}
			catch (org.springframework.dao.DataIntegrityViolationException e)
			{
				flash.message = "Participant Form ${participantFormInstance.formName} could not be deleted"
				redirect url: createLink(controller: 'participantForm', action:'list',
					mapping:'participantFormDetails', params:[studyId: params.studyId, participantId: params.participantId])
			}
		}
		else
		{
			flash.message = "ParticipantForm ${participantFormInstance.formName} could not be found"
			redirect url: createLink(controller: 'participantForm', action:'list',
				mapping:'participantFormDetails', params:[studyId: params.studyId, participantId: params.participantId])
		}
	}
}
class ParticipantFormCommand
{
	ParticipantForm[] forms = [
		new ParticipantForm(),
		new ParticipantForm(),
		new ParticipantForm(),
		new ParticipantForm(),
		new ParticipantForm(),
		new ParticipantForm(),
		new ParticipantForm(),
		new ParticipantForm(),
		new ParticipantForm(),
		new ParticipantForm()] as ParticipantForm[]
}