package au.org.intersect.bdcp

import grails.test.*

class UserTests extends GrailsUnitTestCase {
    
	def userStore
	
	protected void setUp() {
        super.setUp()
		
		userStore = new UserStore(username:"dpollum")
		
		mockForConstraintsTests UserStore, [ userStore ]
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testBlank() {

		userStore = new UserStore(username: '')
		assertFalse 'No validation for blank field(s)' ,userStore.validate()
		assertEquals 'Username is blank.','blank', userStore.errors['username']
		userStore = new UserStore(username: "test")
		assertTrue "A valid user did not validate!", userStore.validate()
    }
	
}
