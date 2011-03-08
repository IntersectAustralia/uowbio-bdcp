package au.org.intersect.bdcp

import grails.test.*

/**
 * Unit tests for the domain class {@link Study}
 */
class ParticipantTests extends GrailsUnitTestCase
{
	def study
	
	def project
	
	def participant

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
				project: project,
				numberOfParticipants:"10",
				inclusionExclusionCriteria: "No Criteria")
		participant = new Participant(identifier:"101",
			    study:study)
		
		mockForConstraintsTests Participant, [participant]
	}

	/**
	 * Tear down operations after each test�
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
		participant = new Participant(identifier:"",
			    study:study)

		assertFalse 'No validation for blank field(s)' ,participant.validate()

		assertEquals 'Identifier is blank.','blank', participant.errors['identifier']
		
		participant = new Participant(identifier:"1",
			study:study)
		
		assertTrue "A valid participant did not validate!", participant.validate()
	}
	
	void testUnique()
	{
		participant = new Participant(identifier:"101",
			study:study)
		
		assertFalse 'No validation for unique field(s)', participant.validate()
		
		assertEquals 'Identifier is not unique.','unique', participant.errors['identifier']
		
		participant = new Participant(identifier:"102",
			study:study)
		
		assertTrue "A valid participant did not validate!", participant.validate()
	}
	
}