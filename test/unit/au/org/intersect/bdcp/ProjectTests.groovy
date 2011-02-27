package au.org.intersect.bdcp


import grails.test.GrailsUnitTestCase
import au.org.intersect.*


/**
 * Unit tests for the domain class {@link Project} 
 */
class ProjectTests extends GrailsUnitTestCase
{

	def project

	def study
	
	/**
	 * Setup operations before each test
	 */
	protected void setUp()
	{
		super.setUp()
		
		study = new Study(studyTitle: 'TestStudy',
			ethicsNumber: '110678' ,
			description: 'Test Description',
			industryPartners: 'Partner1',
			collaborators: 'some collaborator',
			dateStart: new Date(),
			dateEnd: new Date())
		
		// Set up default Project so we can easily test single properties.
		project = new Project(projectTitle: 'TestProject',
				researcherName: 'TestStudent' ,
				degree: 'TestDegree',
				yearFrom: new Date(),
				yearTo: new Date(),
				description: 'Test Description',
				supervisors: 'test supervisor')
	
		project.studies = [study]
		
		mockForConstraintsTests Project, [project]
	}

	/**
	 * Perform these operations after each test
	 */
	protected void tearDown()
	{
		super.tearDown()
	}

	void testToString()
	{
		assertEquals "The toString() method returned incorrectly.", "TestProject", project.toString()
	}
	
	/**
	 * Test that the blank fields in the domain class {@link Project} are
	 * correctly validated
	 */
	void testBlank()
	{
		project = new Project(projectTitle: '',
				researcherName: '' ,
				degree: '',
				yearFrom: '',
				yearTo: '',
				description: '',
				supervisors: '')

		assertFalse "No validation exists for blank field(s)",project.validate()

		assertEquals 'Project Title is blank.','blank', project.errors['projectTitle']
		assertEquals 'Researcher Name is blank.','blank', project.errors['researcherName']
		assertEquals 'Degree is blank.','blank', project.errors['degree']
		assertEquals 'Description is blank.','blank', project.errors['description']
		assertEquals 'Supervisors is blank.','blank', project.errors['supervisors']

		project = new Project(projectTitle: 'Testing Project',
				researcherName: 'TestStudent' ,
				degree: 'TestDegree',
				yearFrom: new Date(),
				yearTo: new Date(),
				description: 'Test Description',
				supervisors: 'test supervisor')

		assertTrue project.validate()
	}

	/**
	 * Test the nullable fields in the domain class {@link Project} are
	 * correctly validated
	 */
	void testNullable()
	{
		project = new Project(projectTitle: 'Testing Project',
				researcherName: 'TestStudent' ,
				degree: 'TestDegree',
				yearFrom: null,
				yearTo: null,
				description: 'Test Description',
				supervisors: 'test supervisor')

		assertFalse "No validation for nullable fields", project.validate()

		assertEquals 'Year From is nullable.','nullable', project.errors['yearFrom']
		assertEquals 'Year To is nullable.','nullable', project.errors['yearTo']

		project = new Project(projectTitle: 'Testing Project',
				researcherName: 'TestStudent' ,
				degree: 'TestDegree',
				yearFrom: new Date(),
				yearTo: new Date(),
				description: 'Test Description',
				supervisors: 'test supervisor')

		assertTrue project.validate()
	}
	
	/**
	 * Test that the one to many relationship between project and study
	 * works correctly.
	 */
	void testProjectAndStudyRelationship()
	{
		assertTrue "Not enough studies have been returned.", project.studies?.size() > 0
	}
}
