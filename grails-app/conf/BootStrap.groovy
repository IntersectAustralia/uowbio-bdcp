import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import au.org.intersect.bdcp.Component
import au.org.intersect.bdcp.DeviceGroup
import au.org.intersect.bdcp.Participant
import au.org.intersect.bdcp.ParticipantForm
import au.org.intersect.bdcp.Project
import au.org.intersect.bdcp.Session
import au.org.intersect.bdcp.Study
import au.org.intersect.bdcp.UserStore



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
			
			intersect_test
			{
				def user = new UserStore(username:"dpollum", deactivated: false)
				user.save(flush:true)
			}
		}
	}

	def createTestData =
	{
		println "creating test data"

		def project = new Project(projectTitle: 'TestProject',
				researcherName: 'TestStudent' ,
				degree: 'TestDegree',
				startDate: new Date(),
				endDate: new Date(),
				description: 'Test Description',
				supervisors: 'test supervisor')
		project.save()

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
		study.save()

		def participant = new Participant(identifier:"10",
				study: study)
		participant.save()
		
		def participantForm = new ParticipantForm(formName:"bash profile",
			form: ".bashrc",
			participant: participant)
		participantForm.save()
		
        def component = new Component(name:"test",
            description: "test", study: study)
        component.save()
        
        def session = new Session(name:"test",
            description: "test", component: component)
        session.save()
        
        def deviceGroup = new DeviceGroup(groupingName: "Force Platforms")
        deviceGroup.save()
        
		def user = new UserStore(username:"dpollum", deactivated: false)
		user.save(flush:true)
        
		user = new UserStore(username:"chrisk", deactivated: false)
		user.save(flush:true)
        
       
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
        }
        
    }
}
