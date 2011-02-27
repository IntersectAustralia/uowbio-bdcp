package au.org.intersect.bdcp

import grails.test.*

/**
 * Unit tests for the domain class {@link Study}
 */
class StudyTests extends GrailsUnitTestCase
{
	def study
	
	def project

	/**
	 * Setup operations before each test
	 */
	protected void setUp()
	{
		super.setUp()

		project = new Project(projectTitle: 'TestProject',
			researcherName: 'TestStudent' ,
			degree: 'TestDegree',
			yearFrom: new Date(),
			yearTo: new Date(),
			description: 'Test Description',
			supervisors: 'test supervisor')
		
		
		// Set up default Study so we can easily test single properties.
		study = new Study(studyTitle: 'TestStudy',
				ethicsNumber: '110678' ,
				description: 'Test Description',
				industryPartners: 'Partner1',
				collaborators: 'some collaborator',
				dateStart: new Date(),
				dateEnd: new Date(),
				project: project)
		
		mockForConstraintsTests Study, [study]
	}

	/**
	 * Tear down operations after each testì
	 */
	protected void tearDown()
	{
		super.tearDown()
	}

	/**
	 * Test the domain class {@link Study} to make sure that blank fields
	 * are correctly validated
	 */
	void testBlank()
	{
		study = new Study(studyTitle: '',
				ethicsNumber: '' ,
				description: '',
				industryPartners: '',
				collaborators: '',
				dateStart: '',
				dateEnd: '')

		assertFalse 'No validation for blank field(s)' ,study.validate()

		assertEquals 'Study Title is blank.','blank', study.errors['studyTitle']
		assertEquals 'Description is blank.','blank', study.errors['description']
		
		project = new Project(projectTitle: 'TestProject',
			researcherName: 'TestStudent' ,
			degree: 'TestDegree',
			yearFrom: new Date(),
			yearTo: new Date(),
			description: 'Test Description',
			supervisors: 'test supervisor')
		
		study = new Study(studyTitle: 'Testing Study',
				ethicsNumber: '110680' ,
				description: 'Test Description',
				industryPartners: 'Partner1',
				collaborators: 'some collaborator',
				dateStart: new Date(),
				dateEnd: new Date(),
				project: project)
		
		assertTrue "A valid study did not validate!", study.validate()
	}
	
	void testUnique()
	{
		def test = new Study(studyTitle: 'TestStudy',
				ethicsNumber: '110678' ,
				description: 'Test Description',
				industryPartners: 'Partner1',
				collaborators: 'some collaborator',
				dateStart: new Date(),
				dateEnd: new Date())

	mockForConstraintsTests(Study, [test])

	assertFalse "No validation for unique field(s)",study.validate()
	assertEquals 'Study Title is not unique.','unique', study.errors['studyTitle']
	
	study = new Study(studyTitle: 'Testing Study',
				ethicsNumber: '110679' ,
				description: 'Test Description',
				industryPartners: 'Partner1',
				collaborators: 'some collaborator',
				dateStart: new Date(),
				dateEnd: new Date(),
				project: project)
	
	assertTrue "A valid study did not validate!", study.validate()
	}
}
