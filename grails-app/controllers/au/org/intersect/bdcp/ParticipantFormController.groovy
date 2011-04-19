package au.org.intersect.bdcp

import java.io.File

import eu.medsea.mimeutil.detector.MagicMimeMimeDetector
import grails.plugins.springsecurity.Secured




class ParticipantFormController
{

	static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]

	private ParticipantForm extractParticipantForm(i)
	{

		def pfc = new ParticipantFormCommand()
		bindData( pfc, params )
		ParticipantForm participantFormInstance = pfc.forms[i];

		return participantFormInstance
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
			if (!participantForms[i]?.validate())
			{
				allValid = false
			}
		}

		return allValid
	}

	private List<Integer> participantFormsToLoad()
	{
		int size = 0
		int count = 0
		def participantFormCommand = new ParticipantFormCommand()
		bindData( participantFormCommand, params )
		List<Integer> usedFields = new ArrayList<Integer>();
		participantFormCommand.forms.each
		{

			if ((it?.formName.size() > 0) || it?.fileName.size() >0)
			{
				usedFields.add (count)
			}

			count++
		}

		return usedFields
	}

	private populateSessionValues(List<Integer> formsToLoad)
	{
		for (i in formsToLoad)
		{
			if (!request.getFile("form.${i}")?.isEmpty())
			{
				session["fileName[${i}]"] = request.getFile("form.${i}")
			}
		}
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
			def fileName = participantFormInstance.id
			if (fileExtension != null)
			{
				fileName = participantFormInstance.id + "." + fileExtension
			}

			file.transferTo( new File( getRealPath() +  File.separatorChar + params.participantId.toString() +File.separatorChar + fileName) )
			participantFormInstance.form = participantFormInstance.id
			participantFormInstance.contentType = file.contentType
			participantFormInstance.fileExtension = fileExtension
			participantFormInstance.fileName = fileName
			participantFormInstance.save(flush: true)
		}
		else
		{
			participantFormInstance.delete(flush: true)
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def upload =
	{
		cache false
		def participantForms = []
		def participantFormsError = []
		def participantFormInstanceList = []
		for (i in participantFormsToLoad())
		{
			participantForms[i] = extractParticipantForm(i)
		}

		if (!validateParticipantForms(participantForms))
		{

			populateSessionValues(participantFormsToLoad())
			def f = new File( getRealPath() + File.separatorChar + params.participantId.toString() )
			if( f.exists() )
			{
				f.eachFile()
				{ file->
					if( !file.isDirectory() )
					{
						def participantForm = ParticipantForm.findWhere(fileName: file.name)
						if(participantForm != null)
						{
							participantFormInstanceList.add(participantForm)
						}
					}
				}
			}
			render(view: "list", model: [participantForms: participantForms,participantFormInstance: participantForms[0],participantFormInstanceList: participantFormInstanceList, participantFormInstanceTotal: participantFormInstanceList.size(), participantInstance: Participant.get(params.participantId), forms:participantForms, fileName: params.fileName, participantId: params.participantId ])
		}
		else
		{
			def message = []
			for (i in participantFormsToLoad())
			{
				def participantFormInstance = participantForms[i]

				if (participantFormInstance.save(flush: true))
				{
					new File( getRealPath() + File.separatorChar + params.participantId.toString()).mkdirs()
					def file = request.getFile("form.${i}")
					if (file?.isEmpty() && !(session["fileName[${i}]"] == null))
					{
						file = session["fileName[${i}]"]
					}

					saveFile(file, participantFormInstance)
				}
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

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def downloadFile =
	{
		cache false
		def participantFormInstance = ParticipantForm.get(params.id)

		def fileDoc = new File( getRealPath() +  File.separatorChar + params.participantId.toString() +File.separatorChar + participantFormInstance.fileName)

		if(fileDoc.exists())
		{
			def fileExtension = getFileExtension(participantFormInstance.fileName)
			def fileName = participantFormInstance.formName
			if (fileExtension != null)
			{
				fileName = participantFormInstance.formName + "." + fileExtension
			}

			if (participantFormInstance.contentType == null)
			{
				response.setContentType(getMimeType(fileDoc))
			}
			else
			{
				response.setContentType participantFormInstance.contentType
			}
			response.setHeader "Content-disposition", "attachment; filename=${fileName}" ;
			response.outputStream << fileDoc.newInputStream();
			response.outputStream.flush();

			return false
		}
		return null
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def index =
	{
		cache false
		redirect(action: "list", params: params)
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def list =
	{
		cache false
		def participantInstance = Participant.get(params.participantId)
		def fileResourceInstanceList = []
		def participantFormInstanceList = []
		def participantForms = []
		def f = new File( getRealPath() + File.separatorChar + params.participantId.toString() )
		if( f.exists() )
		{
			f.eachFile()
			{ file->
				if( !file.isDirectory() )
				{
					def participantForm = ParticipantForm.findWhere(fileName: file.name)
					if(participantForm != null)
					{
						participantFormInstanceList.add(participantForm)
					}
				}
			}
		}
		[participantFormInstanceList: participantFormInstanceList, participantFormInstanceTotal: participantFormInstanceList.size(), participantInstance: participantInstance,participantForms: participantForms, forms:participantForms, participantId: params.participantId]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def create =
	{
		cache false
		def participantFormInstance = new ParticipantForm()
		participantFormInstance.properties = params
		def participantForms = []
		return [participantFormInstance: participantFormInstance, participantForms: participantForms]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
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

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
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

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
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

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
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

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def delete =
	{
		cache false
		def participantFormInstance = ParticipantForm.get(params.id)
		if (participantFormInstance)
		{
			try
			{
				participantFormInstance.delete(flush: true)
				def filename = params.id.replace('###', '.')
				def file =  new File( getRealPath() +  File.separatorChar + params.participantId.toString() +File.separatorChar + participantFormInstance.fileName)
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