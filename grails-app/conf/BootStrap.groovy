import au.org.intersect.bdcp.Project



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
	}
	def destroy =
	{
	}
}
