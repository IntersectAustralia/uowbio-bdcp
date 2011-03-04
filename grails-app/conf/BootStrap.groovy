import au.org.intersect.bdcp.Form
import au.org.intersect.bdcp.Participant
import au.org.intersect.bdcp.ParticipantIdentifier
import au.org.intersect.bdcp.Project
import au.org.intersect.bdcp.Study



class BootStrap
{

	def init =
	{ servletContext ->
		def project = new Project(projectTitle: 'TestProject',
				researcherName: 'TestStudent' ,
				degree: 'TestDegree',
				yearFrom: new Date(),
				yearTo: new Date(),
				description: 'Test Description',
				supervisors: 'test supervisor')
		project.save()
		
		def study = new Study(studyTitle: 'TestStudy',
				ethicsNumber: '110678' ,
				description: 'Test Description',
				industryPartners: 'Partner1',
				collaborators: 'some collaborator',
				dateStart: new Date(),
				dateEnd: new Date(),
				project: project,
				numberOfParticipants:"10",
				inclusionExclusionCriteria:"test Criteria",)
		study.save()
		
		def participant = new Participant(numberOfParticipants:"10",
			inclusionExclusionCriteria:"test Criteria",
			study: study)
		participant.save()
		
		def participantIdentifier = new ParticipantIdentifier(identifier: "testing")
		participantIdentifier.save()
		
		def form = new Form(name: "test Form name", link:"test link",
			participantIdentifier: participantIdentifier)
		form.save()
		
		
	}
	def destroy =
	{
	}
}
