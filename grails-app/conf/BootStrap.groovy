import au.org.intersect.bdcp.Participant
import au.org.intersect.bdcp.ParticipantForm
import au.org.intersect.bdcp.Project
import au.org.intersect.bdcp.SecRole
import au.org.intersect.bdcp.SecUser
import au.org.intersect.bdcp.Study
import au.org.intersect.bdcp.UserStore



class BootStrap
{
	def springSecurityService

	def init =
	{ servletContext ->

		environments
		{
			production
			{
			}
			development
			{ createTestData() }
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
				ethicsNumber: '110678' ,
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
		
		def user = new UserStore(uid:"dpollum")
		user.save(flush:true)
		
		user = new UserStore(uid:"chrisk")
		user.save(flush:true)
	}



	def destroy =
	{
	}
}
