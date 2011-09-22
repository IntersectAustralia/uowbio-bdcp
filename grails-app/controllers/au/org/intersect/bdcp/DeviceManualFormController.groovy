package au.org.intersect.bdcp

import java.io.File

import eu.medsea.mimeutil.detector.MagicMimeMimeDetector
import grails.plugins.springsecurity.Secured




class DeviceManualFormController
{
	static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]

	private DeviceManualForm extractDeviceManualForm(i)
	{
        
		def dmfc = new DeviceManualFormCommand()
		bindData( dmfc, params )
		DeviceManualForm deviceManualFormInstance = dmfc.forms[i];

		return deviceManualFormInstance
	}

	private String normalizeValue(value)
	{
		value = value.replaceAll(/[^A-Za-z0-9-_\s]/, '')
		return value
	}
	
	private boolean validateDeviceManualForms(deviceManualForms, usedFormRowNumbers)
	{
		def allValid = true
		
		int j = 0
		for (rowNumber in usedFormRowNumbers)
		{			
			if (request.getFile("form.${rowNumber}")?.isEmpty() && (session["fileName[${rowNumber}]"] == null))
			{
				deviceManualForms[j].fileName = null
			}

			if (!deviceManualForms[j]?.validate())
			{
				allValid = false
			}
            else if(checkForReplication(deviceManualForms, j, "formName"))
            {
                deviceManualForms[j].errors.rejectValue('formName', 'deviceManualForm.formName.uniqueIgnoreCase.invalid')
                allValid = false
            }
			else if(checkForReplication(deviceManualForms, j, "fileName"))
			{
				deviceManualForms[j].errors.rejectValue('fileName', 'deviceManualForm.fileName.uniqueIgnoreCase.invalid')
				allValid = false
			}
			else if(deviceManualForms[j]?.fileName != null && checkNoConflictWithExistingFileName(deviceManualForms[j]?.fileName)) // check that file names provided dont match any existing files
			{
				deviceManualForms[j].errors.rejectValue('fileName', 'deviceManualForm.fileName.uniqueIgnoreCase.invalid')
				allValid = false
			}
			j++
		}
		
		return allValid
	}
	
	private boolean checkNoConflictWithExistingFileName(_filename)
	{
		boolean conflict = false
		
		def deviceInstance = Device.get(params.deviceId)
		def f = new File( getRealPath() + params.deviceId.toString() )
		if( f.exists() )
		{
			f.eachFile()
			{ file->
				if( !file.isDirectory() )
				{
					def deviceManualForm = DeviceManualForm.findWhere(storedFileName: file.name, device: deviceInstance)
					if(deviceManualForm && _filename && deviceManualForm?.storedFileName?.equalsIgnoreCase(_filename))
					{
						conflict = true
					}
				}
			}
		}
		
		return conflict
	}
	
	private boolean checkForReplication(_deviceManualForms, index, fieldname)
	{
		boolean replication = false
		
		if(fieldname?.equalsIgnoreCase("fileName"))
		{
			replication = ((_deviceManualForms.findAll { it?.fileName.equalsIgnoreCase(_deviceManualForms[index]?.fileName) }).size() > 1)
		}
		else if((fieldname?.equalsIgnoreCase("formName")))
		{
			replication = ((_deviceManualForms.findAll { it?.formName.equalsIgnoreCase(_deviceManualForms[index]?.formName) }).size() > 1)
		}
			
		return replication
	}

	private deviceManualFormsToLoad()
	{
		def deviceManualFormCommand = new DeviceManualFormCommand()
		bindData( deviceManualFormCommand, params )
		List<Integer> usedFields = new ArrayList<Integer>();
		deviceManualFormCommand.forms.eachWithIndex()
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
		for (i in deviceManualFormsToLoad())
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
		return (request.getSession().getServletContext().getRealPath("/") + grailsApplication.config.forms.deviceManuals.location.toString())
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
	
	private saveFile(file, deviceManualFormInstance)
	{
		if ((file!= null) && (!file.isEmpty()))
		{
			def fileExtension = getFileExtension(file?.getOriginalFilename())
			def fileName = file?.getOriginalFilename()

			file.transferTo( new File( getRealPath() + params.deviceId.toString() +File.separatorChar + fileName) )
			deviceManualFormInstance.form = deviceManualFormInstance.formName
			deviceManualFormInstance.contentType = file.contentType
			deviceManualFormInstance.fileExtension = fileExtension
			deviceManualFormInstance.storedFileName = fileName
            deviceManualFormInstance.save(flush: true)
            
		}
		else
		{
			deviceManualFormInstance.delete(flush: true)
		}
	}

    private renderUploadErrorMsg(deviceManualForms)
    {
        def deviceManualFormInstanceList = []
        def deviceInstance = Device.get(params.deviceId)
        def f = new File( getRealPath() + params.deviceId.toString() )
        if( f.exists() )
        {
            f.eachFile()
            { file->
                if( !file.isDirectory() )
                {
                    def deviceManualForm = DeviceManualForm.findWhere(storedFileName: file.name, device: deviceInstance)
                    if(deviceManualForm != null)
                    {
                        deviceManualFormInstanceList.add(deviceManualForm)
                    }
                }
            }
        }
        render(view: "list", model: [deviceManualForms: deviceManualForms,deviceManualFormInstanceList: deviceManualFormInstanceList, deviceManualFormInstanceTotal: deviceManualFormInstanceList.size(), deviceInstance: Device.get(params.deviceId), forms:deviceManualForms, fileName: params.fileName, deviceId: params.deviceId ])
    }
    
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def upload =
	{
		cache false
		def deviceManualForms = []
		def deviceManualFormInstanceList = []
		def allValid = true
		
		// find row number of device manual rows with entries
		List<Integer> usedFormRowNumbers = deviceManualFormsToLoad()
		def j = 0
        for (rowNumber in usedFormRowNumbers)
		{
			deviceManualForms[j++] = new DeviceManualForm(params["forms["+rowNumber+"]"])
        }

		// check if device manual entries valid
		if (!validateDeviceManualForms(deviceManualForms, usedFormRowNumbers))
		{
            populateSessionValues()
            renderUploadErrorMsg(deviceManualForms);
		}
		else
		{
			j=0
			for (rowNumber in usedFormRowNumbers)
			{
                if (deviceManualForms[j].save(flush: true))
				{
					new File( getRealPath() + params.deviceId.toString()).mkdirs()
					def file = request.getFile("form.${rowNumber}")
				
					if (file?.isEmpty() && !(session["fileName[${rowNumber}]"] == null))
					{
						file = session["fileName[${rowNumber}]"]
					}
                    populateSessionValues()
					saveFile(file, deviceManualForms[j])
				}
                else
                {
                    allValid = false    
                }
                j++
			}
			
			j=0
            if (allValid == false)
            {
                for (rowNumber in usedFormRowNumbers)
                {
                    deviceManualForms[j].delete(flush:true)
                }
                
                renderUploadErrorMsg(deviceManualForms);
                return
            }
            else
            {
                clearSessionValues()
            }
            
			switch (deviceManualFormsToLoad().size())
			{
				case 0: flash.error = "No manuals selected to upload"
				break

				case 1: flash.message = "${deviceManualFormsToLoad().size()} Device manual uploaded"
				break

				case 2..10: flash.message = "${deviceManualFormsToLoad().size()} Device manuals uploaded"
				break
				default:
				break
			}

			redirect url: createLink(controller: 'deviceManualForm', action:'list', mapping:'deviceManualFormDetails', params:[deviceId: params.deviceId, deviceGroupId: params.deviceGroupId])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def downloadFile =
	{
		cache false
		def deviceManualFormInstance = DeviceManualForm.get(params.id)
		def fileDoc = new File( getRealPath() + params.deviceId.toString() + File.separatorChar + deviceManualFormInstance.fileName)
		
		if(fileDoc.exists())
		{
			def fileName = deviceManualFormInstance.fileName

			if (!deviceManualFormInstance.contentType)
			{
				response.setContentType(getMimeType(fileDoc))
			}
			else
			{
				response.setContentType deviceManualFormInstance.contentType
			}

			response.setHeader "Content-disposition", "attachment; filename=\"${fileName}\"" ;
			response.outputStream << fileDoc.newInputStream();
			response.outputStream.flush();

			return false
		}
		else
		{
			flash.message = "Device Manual ${deviceManualFormInstance.formName} could not be found"
			redirect url: createLink(controller: 'deviceManualForm', action:'list',
				mapping:'deviceManualFormDetails', params:[deviceGroupId: params.deviceGroupId, deviceId: params.deviceId, id: params.id])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def index =
	{
		cache false
		redirect(action: "list", params: params)
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def list =
	{
		cache false
		def deviceInstance = Device.get(params.deviceId)
		def deviceManualFormInstanceList = []
		def deviceManualForms = []
		def f = new File( getRealPath() + params.deviceId.toString() )
		
		if( f.exists() )
		{
			f.eachFile()
			{ file->
				if( !file.isDirectory() )
				{
					def deviceManualForm = DeviceManualForm.findWhere(storedFileName: file.getName(), device: deviceInstance)
                    if(deviceManualForm != null)
					{
						deviceManualFormInstanceList.add(deviceManualForm)
					}
				}
			}
		}
		[deviceManualFormInstanceList: deviceManualFormInstanceList, deviceManualFormInstanceTotal: deviceManualFormInstanceList.size(), deviceInstance: deviceInstance, deviceManualForms: deviceManualForms, forms: deviceManualForms, deviceManualFormId: params.id]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def create =
	{
		cache false
		def deviceManualFormInstance = new DeviceManualForm()
		deviceManualFormInstance.properties = params
		def deviceManualForms = []
		return [deviceManualFormInstance: deviceManualFormInstance, deviceManualForms: deviceManualForms]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def save =
	{
		cache false
		def deviceManualFormInstance = new DeviceManualForm(params)
		if (deviceManualFormInstance.save(flush: true))
		{
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'deviceManualForm.label', default: 'DeviceManualForm'), deviceManualFormInstance.id])}"
			redirect(action: "show", id: deviceManualFormInstance.id)
		}
		else
		{
			render(view: "create", model: [deviceManualFormInstance: deviceManualFormInstance])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def show =
	{
		cache false
		def deviceManualFormInstance = DeviceManualForm.get(params.id)
		if (!deviceManualFormInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceManualForm.label', default: 'DeviceManualForm'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			[deviceManualFormInstance: deviceManualFormInstance]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def edit =
	{
		cache false
		def deviceManualFormInstance = DeviceManualForm.get(params.id)
		if (!deviceManualFormInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceManualForm.label', default: 'DeviceManualForm'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			return [deviceManualFormInstance: deviceManualFormInstance]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def update =
	{
		cache false
		def deviceManualFormInstance = DeviceManualForm.get(params.id)
		if (deviceManualFormInstance)
		{
			if (params.version)
			{
				def version = params.version.toLong()
				if (deviceManualFormInstance.version > version)
				{

					deviceManualFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'deviceManualForm.label', default: 'DeviceManualForm')]
					as Object[], "Another user has updated this DeviceManualForm while you were editing")
					render(view: "edit", model: [deviceManualFormInstance: deviceManualFormInstance])
					return
				}
			}
			deviceManualFormInstance.properties = params
			if (!deviceManualFormInstance.hasErrors() && deviceManualFormInstance.save(flush: true))
			{
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'deviceManualForm.label', default: 'DeviceManualForm'), deviceManualFormInstance.id])}"
				redirect(action: "show", id: deviceManualFormInstance.id)
			}
			else
			{
				render(view: "edit", model: [deviceManualFormInstance: deviceManualFormInstance])
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceManualForm.label', default: 'DeviceManualForm'), params.id])}"
			redirect(action: "list")
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def delete =
	{
		cache false
		def deviceManualFormInstance = DeviceManualForm.get(params.id)
		
		if (deviceManualFormInstance)
		{
			try
			{
				def filename = deviceManualFormInstance.fileName 
				deviceManualFormInstance.delete(flush: true)
				def file =  new File( getRealPath() + params.deviceId + File.separatorChar + filename)

				if (file.exists())
				{
					file.delete()
				}
				flash.message = "Device Manual ${deviceManualFormInstance.formName} deleted"
				redirect url: createLink(controller: 'deviceManualForm', action:'list',
					mapping:'deviceManualFormDetails', params:[deviceGroupId: params.deviceGroupId, deviceId: params.deviceId, id: params.id])
			}
			catch (org.springframework.dao.DataIntegrityViolationException e)
			{
				flash.message = "Device Manual ${deviceManualFormInstance.formName} could not be deleted"
				redirect url: createLink(controller: 'deviceManualForm', action:'list',
					mapping:'deviceManualFormDetails', params:[deviceGroupId: params.deviceGroupId, deviceId: params.deviceId, id: params.id])
			}
		}
		else
		{
			flash.message = "Device Manual ${deviceManualFormInstance.formName} could not be found"
			redirect url: createLink(controller: 'deviceManualForm', action:'list',
				mapping:'deviceManualFormDetails', params:[deviceGroupId: params.deviceGroupId, deviceId: params.deviceId, id: params.id])
		}
	}
}
class DeviceManualFormCommand
{
	DeviceManualForm[] forms = [
		new DeviceManualForm(),
		new DeviceManualForm(),
		new DeviceManualForm(),
		new DeviceManualForm(),
		new DeviceManualForm(),
		new DeviceManualForm(),
		new DeviceManualForm(),
		new DeviceManualForm(),
		new DeviceManualForm(),
		new DeviceManualForm()] as DeviceManualForm[]
}