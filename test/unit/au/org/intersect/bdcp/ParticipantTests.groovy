package au.org.intersect.bdcp

import grails.test.*

class ParticipantTests extends GrailsUnitTestCase
{
	def participant
	def project
	def study
	
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
		
		participant = new Participant(participantIdentifier: '11245',
			consentForm: 'Consent Form',
			formA: 'formA',
			formB: 'formB',
			study: study)
		
		mockForConstraintsTests Participant, [participant]
	}

	protected void tearDown()
	{
		super.tearDown()
	}

	/**
	 * Test the domain class {@link Participant} to make sure that blank fields
	 * are correctly validated
	 */
	void testBlank()
	{
		participant = new Participant(participantIdentifier: '',
									consentForm: '',
									formA: '',
									formB: '',
									study:study)

		assertFalse 'No validation for blank field(s)' ,participant.validate()

		assertEquals 'Participant Identifier is blank.','blank', participant.errors['participantIdentifier']
		assertEquals 'Consent Form is blank.','blank', participant.errors['consentForm']
		assertEquals 'Form A is blank.','blank', participant.errors['formA']
		assertEquals 'Form B is blank.','blank', participant.errors['formB']
		
		participant = new Participant(participantIdentifier: '11246',
			consentForm: 'Consent Form',
			formA: 'formA',
			formB: 'formB',
			study: study)
		
		
		assertTrue "A valid participant did not validate!", participant.validate()
	}
	
	void testUnique()
	{
		def test = new Participant(participantIdentifier: '11245',
			consentForm: 'Consent Form',
			formA: 'formA',
			formB: 'formB',
			study: study)

	mockForConstraintsTests(Participant, [test])

	assertFalse "No validation for unique field(s)",participant.validate()
	assertEquals 'Participant Identifier is not unique.','unique', participant.errors['participantIdentifier']
	
	participant = new Participant(participantIdentifier: '11246',
			consentForm: 'Consent Form',
			formA: 'formA',
			formB: 'formB',
			study: study)
	
	assertTrue "A valid study did not validate!", participant.validate()
	}
}
