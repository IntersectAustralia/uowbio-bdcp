package au.org.intersect.bdcp

import grails.test.*

class ProjectTests extends GrailsUnitTestCase {
    
	def project
	
	protected void setUp() {
        super.setUp()
		// Set up default Project so we can easily test single properties.
		project = new Project(projectTitle: 'TestProject', researcherName: 'TestStudent' ,
			degree: 'TestDegree', yearFrom: new Date(), yearTo: new Date(), description: 'Test Description', supervisors: 'test supervisor') 
			
    	mockForConstraintsTests Project, [ project ]
	}

    protected void tearDown() {
        super.tearDown()
    }

    void testUnique() {
		def test = new Project(projectTitle: 'TestProject', researcherName: 'TestStudent' ,
			degree: 'TestDegree', yearFrom: new Date(), yearTo: new Date(), description: 'Test Description', supervisors: 'test supervisor') 
		mockForConstraintsTests(Project, [test])
		
		assertFalse project.validate()
		assertEquals 'Project Title is not unique.','unique', project.errors['projectTitle']
		
		project = new Project(projectTitle: 'Testing Project', researcherName: 'Student' ,
			degree: 'TestDegree', yearFrom: new Date(), yearTo: new Date(), description: 'Test Description', supervisors: 'test supervisor')  
		assertTrue project.validate()
	}
	
	void testBlank()
	{
		project = new Project(projectTitle: '', researcherName: '' ,
			degree: '', yearFrom: '', yearTo: '', description: '', supervisors: '')
		assertFalse project.validate()
		assertEquals 'Project Title is blank.','blank', project.errors['projectTitle']
		assertEquals 'Researcher Name is blank.','blank', project.errors['researcherName']
		assertEquals 'Degree is blank.','blank', project.errors['degree']
		assertEquals 'Description is blank.','blank', project.errors['description']
		assertEquals 'Supervisors is blank.','blank', project.errors['supervisors']
		
		project = new Project(projectTitle: 'Testing Project', researcherName: 'TestStudent' ,
			degree: 'TestDegree', yearFrom: new Date(), yearTo: new Date(), description: 'Test Description', supervisors: 'test supervisor')
		assertTrue project.validate()
	}
	
	void testNullable()
	{
		project = new Project(projectTitle: 'Testing Project', researcherName: 'TestStudent' ,
			degree: 'TestDegree', yearFrom: null, yearTo: null, description: 'Test Description', supervisors: 'test supervisor')
	
		assertFalse project.validate()
		assertEquals 'Year From is nullable.','nullable', project.errors['yearFrom']
		assertEquals 'Year To is nullable.','nullable', project.errors['yearTo']
		
		project = new Project(projectTitle: 'Testing Project', researcherName: 'TestStudent' ,
			degree: 'TestDegree', yearFrom: new Date(), yearTo: new Date(), description: 'Test Description', supervisors: 'test supervisor')
		assertTrue project.validate()
	}
	
}
