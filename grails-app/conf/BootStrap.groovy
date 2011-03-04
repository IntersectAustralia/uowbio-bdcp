import au.org.intersect.bdcp.Participant
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
		
		def participant = new Participant(identifier:"10",
			study: study)
		participant.save()
		
		
	}
	def destroy =
	{
	}
}
