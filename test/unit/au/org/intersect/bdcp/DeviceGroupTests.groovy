package au.org.intersect.bdcp

import grails.test.*

class DeviceGroupTests extends GrailsUnitTestCase 
{
    def deviceGroup
    
   /**
    * Setup operations before each test
    */
    protected void setUp() 
    {
        super.setUp()
        deviceGroup = new DeviceGroup(groupingName:"TestGrouping")
        mockForConstraintsTests DeviceGroup, [deviceGroup]
    }
    
    /**
     * Cleanup operations after each test
     */
    protected void tearDown() 
    {
        super.tearDown()
    }

   /**
    * Test that the blank fields in the domain class {@link DeviceGroup} are
    * correctly validated
    */
    void testBlank() {

        deviceGroup = new DeviceGroup(groupingName: '')
        
        assertFalse 'No validation for blank field(s)' ,deviceGroup.validate()
        assertEquals 'Grouping Name is blank.','blank', deviceGroup.errors['groupingName']
        deviceGroup = new DeviceGroup(groupingName: 'TestGrouping1')
        assertTrue "A valid device grouping did not validate!", deviceGroup.validate()
    }
    
    /**
    * Test that the blank fields in the domain class {@link DeviceGroup} are
    * correctly validated
    */
    void testUnique() {

        deviceGroup = new DeviceGroup(groupingName: 'TestGrouping')
        
        assertFalse 'No validation for unique field(s)' ,deviceGroup.validate()
        assertEquals 'Grouping Name is not unique.','unique', deviceGroup.errors['groupingName']
        deviceGroup = new DeviceGroup(groupingName: 'TestGrouping2')
        assertTrue "A valid device grouping did not validate!", deviceGroup.validate()
    }
    
}
