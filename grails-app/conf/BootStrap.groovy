import grails.util.Environment

import groovy.sql.Sql
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.commons.ApplicationHolder

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
import au.org.intersect.bdcp.StaticMetadataObject
import au.org.intersect.bdcp.Study
import au.org.intersect.bdcp.StudyCollaborator
import au.org.intersect.bdcp.StudyDevice
import au.org.intersect.bdcp.UserStore
import au.org.intersect.bdcp.enums.FieldType
import au.org.intersect.bdcp.enums.UserRole


class BootStrap
{
	def springSecurityService
	def concurrentSessionController
	def securityContextPersistenceFilter
	def fileService
	
	def init =
	{ servletContext ->

		println "*** STARTING ENVIRONMENT : ${Environment.current} ***"
		securityContextPersistenceFilter.forceEagerSessionCreation = true
		SpringSecurityUtils.clientRegisterFilter('concurrentSessionFilter',
		SecurityFilterPosition.CONCURRENT_SESSION_FILTER)
		
		environments
		{
			production
			{
				def user = new UserStore(username:"dpollum", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER)
                user.save(flush:true)
				
				user = new UserStore(username:"egully", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER)
				user.save(flush:true)
                
                user = new UserStore(username:"kherrman", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER)
                user.save(flush:true)
			}
			
			development
			{ 
				createTestData()
				createStaticData() 
			}
			
			test
			{
				def user = new UserStore(username:"dpollum", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER)
                user.save(flush:true)
                user =new UserStore(username:"chrisk", deactivated: false, authority: UserRole.ROLE_RESEARCHER)
                user.save(flush:true)
                user = new UserStore(username:"labman", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER)
                user.save(flush:true)
                user = new UserStore(username:"sysadm", deactivated: false, authority: UserRole.ROLE_SYS_ADMIN)
                user.save(flush:true)
                user = new UserStore(username:"researcher", deactivated: false, authority: UserRole.ROLE_RESEARCHER)
                user.save(flush:true)
			}
			
			cucumber
			{
				// SEE features/support/env.groovy for initialization values as used in cuke4duke
				def user = new UserStore(username:"dpollum", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, nlaIdentifier:"http://ands.org.au/1234")
                user.save(flush:true)
				user =new UserStore(username:"chrisk", deactivated: false, authority: UserRole.ROLE_RESEARCHER, nlaIdentifier:null)
				user.save(flush:true)
				user = new UserStore(username:"labman", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, nlaIdentifier:"http://ands.org.au/5678")
				user.save(flush:true)
				user = new UserStore(username:"sysadm", deactivated: false, authority: UserRole.ROLE_SYS_ADMIN, nlaIdentifier:null)
				user.save(flush:true)
				user = new UserStore(username:"researcher", deactivated: false, authority: UserRole.ROLE_RESEARCHER, nlaIdentifier:null)
				user.save(flush:true)
			}
			
			intersect_test
			{
				def user = new UserStore(username:"dpollum", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER)
				user.save(flush:true)				
                user =new UserStore(username:"chrisk", deactivated: false, authority: UserRole.ROLE_RESEARCHER)
                user.save(flush:true)
				user = new UserStore(username:"labman", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER)
				user.save(flush:true)
				user = new UserStore(username:"sysadm", deactivated: false, authority: UserRole.ROLE_SYS_ADMIN)
				user.save(flush:true)
				user = new UserStore(username:"researcher", deactivated: false, authority: UserRole.ROLE_RESEARCHER)
				user.save(flush:true)
			}

			intersect_demo
			{
				def user = new UserStore(username:"dpollum", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER)
				user.save(flush:true)				
                user =new UserStore(username:"chrisk", deactivated: false, authority: UserRole.ROLE_RESEARCHER)
                user.save(flush:true)
				user = new UserStore(username:"labman", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER)
				user.save(flush:true)
				user = new UserStore(username:"sysadm", deactivated: false, authority: UserRole.ROLE_SYS_ADMIN)
				user.save(flush:true)
				user = new UserStore(username:"researcher", deactivated: false, authority: UserRole.ROLE_RESEARCHER)
				user.save(flush:true)
				createStaticData() 
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
		
        String.metaClass.capitalise = { delegate[0].toUpperCase()+delegate[1..-1] }
		
	}
	
	def createStaticData =
	{
		def files = [['short':'uow','fname':'static~1.xml'],['short':'bml','fname':'static~2.xml']]
		files.each { props ->
				def shortDescription = props['short']
				def fname = props['fname']
				def content = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("WEB-INF/$fname")).text
				def staticObj = new StaticMetadataObject(shortDescription:shortDescription, description:shortDescription, xmlContent:content)
				staticObj.save(flush:true)
			}
	}

	def createTestData =
	{
        def user1 = new UserStore(username:"dpollum", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, nlaIdentifier:"http://nla.ands.org.au/1234")
        user1.save(flush:true)
        
        def user2 = new UserStore(username:"chrisk", deactivated: false, authority: UserRole.ROLE_RESEARCHER, nlaIdentifier:"http://nla.ands.org.au/2345")
        user2.save(flush:true)
		
		def user = new UserStore(username:"researcher", deactivated: false, authority: UserRole.ROLE_RESEARCHER)
		user.save(flush:true)
        
		def project = new Project(projectTitle: 'TestProject',
				researcherName: 'researcher' ,
				studentNumber: 'StudentNumber' ,
				degree: 'TestDegree',
				startDate: new Date(),
				endDate: new Date(),
				description: 'Test Description',
				supervisors: 'test supervisor',
                owner: user2)
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
		
		def studyCollaborator = new StudyCollaborator(study, user)
		studyCollaborator.save(flush: true)

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
		
		user = new UserStore(username:"labman", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER)
		user.save(flush:true)
		
		user = new UserStore(username:"sysadm", deactivated: false, authority: UserRole.ROLE_SYS_ADMIN)
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
            fieldType: FieldType.TEXT,
            mandatory: true)
        device.addToDeviceFields(deviceField)
        deviceField.save(flush: true)
        
        def deviceField2 = new DeviceField(fieldLabel: "Radio buttons?",
            fieldType: FieldType.RADIO_BUTTONS,
            fieldOptions: "test1\ntest2\n",
            mandatory: true)
        device.addToDeviceFields(deviceField2)
        deviceField2.save(flush:true)
        
        def deviceField3 = new DeviceField(fieldLabel: "Device 2 questions?",
            fieldType: FieldType.TEXT,
            mandatory: true)
        device2.addToDeviceFields(deviceField3)
        deviceField3.save(flush: true)
        
        def deviceField4 = new DeviceField(fieldLabel: "Device 2 questions 2?",
            fieldType: FieldType.TEXT,
            mandatory: true)
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
