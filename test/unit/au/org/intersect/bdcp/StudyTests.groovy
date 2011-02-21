package au.org.intersect.bdcp

import grails.test.*

/**
 * Unit tests for the domain class {@link Study}
 */
class StudyTests extends GrailsUnitTestCase
{
	def study

	/**
	 * Setup operations before each test
	 */
	protected void setUp()
	{
		super.setUp()

		// Set up default Study so we can easily test single properties.
		study = new Study(studyTitle: 'TestProject',
				ethicsNumber: '110678' ,
				description: 'Test Description',
				industryPartners: 'Partner1',
				collaborators: 'some collaborator',
				dataStart: new Date(),
				dataEnd: new Date())

		mockForConstraintsTests Study, [study]
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
		study = new Study(studyTitle: '',
				ethicsNumber: '' ,
				description: '',
				industryPartners: '',
				collaborators: '',
				dataStart: '',
				dataEnd: '')

		assertFalse 'No validation for blank field(s)' ,study.validate()

		assertEquals 'Study Title is blank.','blank', study.errors['studyTitle']
		assertEquals 'Ethics Number is blank.','blank', study.errors['ethicsNumber']
		assertEquals 'Description is blank.','blank', study.errors['description']
		assertEquals 'Industry Partners is blank.','blank', study.errors['industryPartners']
		assertEquals 'Collaborators is blank.','blank', study.errors['collaborators']

		study = new Study(studyTitle: 'TestProject',
				ethicsNumber: '110678' ,
				description: 'Test Description',
				industryPartners: 'Partner1',
				collaborators: 'some collaborator',
				dataStart: new Date(),
				dataEnd: new Date())

		assertTrue study.validate()
	}
}
