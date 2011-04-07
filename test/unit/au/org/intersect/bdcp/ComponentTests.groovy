package au.org.intersect.bdcp

import grails.test.*


/**
* Unit tests for the domain class {@link Component}
*/
class ComponentTests extends GrailsUnitTestCase {
    
	def component
	
	def study
	
	def project
	
	/**
	* Setup operations before each test
	*/
	protected void setUp() {
        super.setUp()
		
		project = new Project(projectTitle: 'TestProject',
			researcherName: 'TestStudent' ,
			degree: 'TestDegree',
			startDate: new Date(),
			endDate: new Date(),
			description: 'Test Description',
			supervisors: 'test supervisor')
		
		
		// Set up default Study so we can easily test single properties.
		study = new Study(studyTitle: 'TestStudy',
				ethicsNumber: '110678' ,
				description: 'Test Description',
				industryPartners: 'Partner1',
				collaborators: 'some collaborator',
				startDate: new Date(),
				endDate: new Date(),
				project: project,
				numberOfParticipants:"10",
				inclusionExclusionCriteria: "No Criteria")
		component = new Component(name:"TestComponent",
			description: "Some Description",	
			study:study)
		
		mockForConstraintsTests Component, [component]
    }

	/**
	* Cleanup operations after each test
	*/
    protected void tearDown() {
        super.tearDown()
    }

	/**
	* Test that the blank fields in the domain class {@link Component} are
	* correctly validated
	*/
    void testBlank() {

		component = new Component(name: '',
			description: '', study:study)
		
		assertFalse 'No validation for blank field(s)' ,component.validate()
		assertEquals 'Name is blank.','blank', component.errors['name']
		assertEquals 'Description is blank.','blank', component.errors['description']
		component = new Component(name:"TestComponent", 
			description: "Some Description", study:study)
		assertTrue "A valid component did not validate!", component.validate()
    }
}
