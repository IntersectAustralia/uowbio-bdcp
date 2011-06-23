package au.org.intersect.bdcp

import grails.test.*


/**
* Unit tests for the domain class {@link UserStore}
*/
class UserTests extends GrailsUnitTestCase {
    
	def userStore
	
	/**
	* Setup operations before each test
	*/
	protected void setUp() {
        super.setUp()
		
		userStore = new UserStore(username:"dpollum", authority:"ROLE_USER")
		
		mockForConstraintsTests UserStore, [ userStore ]
    }

	/**
	* Cleanup operations after each test
	*/
    protected void tearDown() {
        super.tearDown()
    }

	/**
	* Test that the blank fields in the domain class {@link UserStore} are
	* correctly validated
	*/
    void testBlank() {

		userStore = new UserStore(username: '', authority:"ROLE_USER")
		assertFalse 'No validation for blank field(s)' ,userStore.validate()
		assertEquals 'Username is blank.','blank', userStore.errors['username']
		userStore = new UserStore(username: "test", authority:"ROLE_USER")
		assertTrue "A valid user did not validate!", userStore.validate()
    }
	
	/**
	* Test that the unique fields in the domain class {@link UserStore} are
	* correctly validated
	*/
	void testUnique() {
		
				userStore = new UserStore(username: "dpollum", authority:"ROLE_USER")
				assertFalse 'No validation for unique field(s)' ,userStore.validate()
				assertEquals 'Username is not unique.','unique', userStore.errors['username']
				userStore = new UserStore(username: "test", authority:"ROLE_USER")
				assertTrue "A valid user did not validate!", userStore.validate()
			}
	/**
	* Test the range validation of fields in the domain class {@link UserStore} are
	* correctly validated
	*/
   void testRange()
   {
		userStore = new UserStore(username: "012345678910" * 100, authority:"ROLE_USER")
 
	   assertFalse "No validation for size of fields", userStore.validate()
 
	   assertEquals 'Username does not validate size.','size', userStore.errors['username']
	   
	  userStore = new UserStore(username: "test", authority:"ROLE_USER")
 
	   assertTrue userStore.validate()
   }
}
