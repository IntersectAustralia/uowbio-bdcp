package au.org.intersect.bdcp

import grails.test.*

/**
 * Unit tests for the domain class {@link Project} 
 */
class ProjectTests extends GrailsUnitTestCase
{

	def project

	/**
	 * Setup operations before each test
	 */
	protected void setUp()
	{
		super.setUp()
		// Set up default Project so we can easily test single properties.
		project = new Project(projectTitle: 'TestProject',
				researcherName: 'TestStudent' ,
				degree: 'TestDegree',
				yearFrom: new Date(),
				yearTo: new Date(),
				description: 'Test Description',
				supervisors: 'test supervisor')

		mockForConstraintsTests Project, [project]
	}

	/**
	 * Perform these operations after each test
	 */
	protected void tearDown()
	{
		super.tearDown()
	}

	/**
	 * Test that the unique fields in the domain class {@link Project}
	 * are correctly validated
	 */
	void testUnique()
	{
		def test = new Project(projectTitle: 'TestProject',
				researcherName: 'TestStudent' ,
				degree: 'TestDegree',
				yearFrom: new Date(),
				yearTo: new Date(),
				description: 'Test Description',
				supervisors: 'test supervisor')

		mockForConstraintsTests(Project, [test])

		assertFalse "No validation for unique field(s)",project.validate()
		assertEquals 'Project Title is not unique.','unique', project.errors['projectTitle']

		project = new Project(projectTitle: 'Testing Project',
				researcherName: 'Student' ,
				degree: 'TestDegree',
				yearFrom: new Date(),
				yearTo: new Date(),
				description: 'Test Description',
				supervisors: 'test supervisor')

		assertTrue project.validate()
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
}
