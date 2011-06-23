import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import au.org.intersect.bdcp.Component
import au.org.intersect.bdcp.Device
import au.org.intersect.bdcp.DeviceField
import au.org.intersect.bdcp.DeviceGroup
import au.org.intersect.bdcp.Participant
import au.org.intersect.bdcp.ParticipantForm
import au.org.intersect.bdcp.Project
import au.org.intersect.bdcp.Session
import au.org.intersect.bdcp.Study
import au.org.intersect.bdcp.StudyDevice
import au.org.intersect.bdcp.UserStore
import au.org.intersect.bdcp.enums.FieldType



class BootStrap
{
	def springSecurityService
	def concurrentSessionController
	def securityContextPersistenceFilter
	def fileService
	
	def init =
	{ servletContext ->

		securityContextPersistenceFilter.forceEagerSessionCreation = true
		SpringSecurityUtils.clientRegisterFilter('concurrentSessionFilter',
		SecurityFilterPosition.CONCURRENT_SESSION_FILTER)
		
		environments
		{
			production
			{
				def user = new UserStore(username:"dpollum", deactivated: false)
				user.save(flush:true)
				
				user = new UserStore(username:"egully", deactivated: false)
				user.save(flush:true)
			}
			
			development
			{ createTestData() }
			
			test
			{
				def user = new UserStore(username:"dpollum", deactivated: false)
				user.save(flush:true)
				user = new UserStore(username:"chrisk", deactivated: false)
				user.save(flush:true)
			}
			
			cucumber
			{
				def user = new UserStore(username:"dpollum", deactivated: false)
				user.save(flush:true)
				user = new UserStore(username:"chrisk", deactivated: false)
				user.save(flush:true)
			}
			
			intersect_test
			{
				def user = new UserStore(username:"dpollum", deactivated: false, authority: "ROLE_LAB_MANAGER")
				user.save(flush:true)
                user = new UserStore(username:"chrisk", deactivated: false, authority: "ROLE_LAB_MANAGER")
                user.save(flush:true)
			}
		}

		List.metaClass.partition = {size ->
			if (!delegate)
				return []
		
			def rslt = delegate.inject([[]]) {ret, elem ->
				(ret.last() << elem).size() >= size ? (ret << []) : ret
			}
			!rslt.last() ? rslt[0..-2] : rslt
		}
		
	}

	def createTestData =
	{
		println "creating test data"

		def project = new Project(projectTitle: 'TestProject',
				researcherName: 'TestStudent' ,
				studentNumber: 'StudentNumber' ,
				degree: 'TestDegree',
				startDate: new Date(),
				endDate: new Date(),
				description: 'Test Description',
				supervisors: 'test supervisor')
		project.save(flush: true)

		def study = new Study(studyTitle: 'TestStudy',
				uowEthicsNumber: '110678' ,
				additionalEthicRequirements:"Some requirements",
				description: 'Test Description',
				industryPartners: 'Partner1',
				collaborators: 'some collaborator',
				startDate: new Date(),
				endDate: new Date(),
				project: project,
				numberOfParticipants:"10",
				inclusionExclusionCriteria:"test Criteria")
		study.save(flush: true)

		def participant = new Participant(identifier:"10",
				study: study)
		participant.save(flush: true)
		
		def participantForm = new ParticipantForm(formName:"bash profile",
			form: ".bashrc",
			participant: participant)
		participantForm.save(flush: true)
		
        def component = new Component(name:"test",
            description: "test", study: study)
        component.save(flush: true)
        
        def sessionInstance = new Session(name:"test",
            description: "test", component: component)
        sessionInstance.save(flush:true)
        
        def deviceGroup = new DeviceGroup(groupingName: "Force Platforms")
        deviceGroup.save()
        
		def user = new UserStore(username:"dpollum", deactivated: false, authority: "ROLE_LAB_MANAGER")
		user.save(flush:true)
        
		user = new UserStore(username:"chrisk", deactivated: false, authority: "ROLE_LAB_MANAGER")
		user.save(flush:true)
        
        def device = new Device(name: "Device1",
            description: "Some device",
            manufacturer: "Some manufacturer",
            locationOfManufacturer: "Some location",
            modelName: "Some model",
            serialNumber: "11231ABC",
			uowAssetNumber: "11231ABC",
            dateOfPurchase: new Date(),
            dateOfDelivery: new Date(),
            purchasePrice: "\$10.00",
            vendor: "Intersect",
            fundingSource: "Some funding Body",
			maintServiceInfo: "Maintenance/Service information",
            deviceGroup: deviceGroup)
        device.save(flush: true)

        def device2 = new Device(name: "Device2",
            description: "Some device",
            manufacturer: "Some manufacturer",
            locationOfManufacturer: "Some location",
            modelName: "Some model",
            serialNumber: "11231ABC",
            uowAssetNumber: "11231ABC",
            dateOfPurchase: new Date(),
            dateOfDelivery: new Date(),
            purchasePrice: "\$10.00",
            vendor: "Intersect",
            fundingSource: "Some funding Body",
            maintServiceInfo: "Maintenance/Service information",
            deviceGroup: deviceGroup)
        device2.save(flush: true)
        
        def deviceField = new DeviceField(fieldLabel: "Is the device currently being used?",
            fieldType: FieldType.TEXT)
        device.addToDeviceFields(deviceField)
        deviceField.save(flush: true)
        
        def deviceField2 = new DeviceField(fieldLabel: "Radio buttons?",
            fieldType: FieldType.RADIO_BUTTONS,
            fieldOptions: "test1\ntest2\n")
        device.addToDeviceFields(deviceField2)
        deviceField2.save(flush:true)
        
        def deviceField3 = new DeviceField(fieldLabel: "Device 2 questions?",
            fieldType: FieldType.TEXT)
        device2.addToDeviceFields(deviceField3)
        deviceField3.save(flush: true)
        
        def deviceField4 = new DeviceField(fieldLabel: "Device 2 questions 2?",
            fieldType: FieldType.TEXT)
        device2.addToDeviceFields(deviceField4)
        deviceField4.save(flush: true)
        def studyDevice = StudyDevice.link(study, device);
        studyDevice.save(flush: true)

        
        
	}

	def destroy =
	{
        environments
        {
            development 
            {
                def d1LdapServer
                d1LdapServer.stop()
            }
            
            test
            {
                def d1LdapServer
                d1LdapServer.stop()
            }

			cucumber
            {
                def d1LdapServer
                d1LdapServer.stop()
            }
        }
        
    }
}
