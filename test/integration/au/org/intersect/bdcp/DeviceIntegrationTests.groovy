package au.org.intersect.bdcp

import grails.test.*
import groovy.util.GroovyTestCase

class DeviceIntegrationTests extends GroovyTestCase {
    
    def deviceGroup
    
    def device
    
   /**
    * Setup operations before each test
    */
    protected void setUp() 
    {
        super.setUp()
        deviceGroup = new DeviceGroup(groupingName:"TestGrouping")
        deviceGroup.save(flush:true)
        device = new Device(name: "Device1",
            description: "Some description",
            manufacturer: "Some manufacturer",
            locationOfManufacturer: "Some location",
            modelName: "Some model",
            serialNumber: "Some serialNumber",
            dateOfPurchase: new Date(),
            dateOfDelivery: new Date(),
            purchasePrice: "\$10.00",
            vendor: "Some vendor",
            fundingBody: "Some funding Body",
            deviceGroup: deviceGroup)
        device.save(flush:true)
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
    void testUnique() {

        device = new Device(name: "Device1",
            description: "Some description",
            manufacturer: "Some manufacturer",
            locationOfManufacturer: "Some location",
            modelName: "Some model",
            serialNumber: "Some serialNumber",
            dateOfPurchase: new Date(),
            dateOfDelivery: new Date(),
            purchasePrice: "\$10.00",
            vendor: "Some vendor",
            fundingBody: "Some funding Body",
            deviceGroup: deviceGroup)
        
        assertFalse 'No validation for unique field(s)' ,device.validate()
        assertNotNull 'Name is not unique.', device.errors.getFieldError('name')
        def code = device.errors.getFieldError('name')?.codes.find {it == 'device.name.uniqueIgnoreCase.invalid'}
        assertNotNull 'Name is not unique', code
        device = new Device(name: "Device2",
            description: "Some description",
            manufacturer: "Some manufacturer",
            locationOfManufacturer: "Some location",
            modelName: "Some model",
            serialNumber: "Some serialNumber",
            dateOfPurchase: new Date(),
            dateOfDelivery: new Date(),
            purchasePrice: "\$10.00",
            vendor: "Some vendor",
            fundingBody: "Some funding Body",
            deviceGroup: deviceGroup)
        assertTrue "A valid device did not validate!", device.validate()
    }
    
}
