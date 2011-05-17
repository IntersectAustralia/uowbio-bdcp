package au.org.intersect.bdcp

import grails.test.*

class DeviceTests extends GrailsUnitTestCase {
    
    def deviceGroup
    
    def device
    
   /**
    * Setup operations before each test
    */
    protected void setUp() 
    {
        super.setUp()
        deviceGroup = new DeviceGroup(groupingName:"TestGrouping")
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
        
        mockForConstraintsTests Device, [device]
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

        device = new Device(name: "",
            description: "",
            manufacturer: "",
            locationOfManufacturer: "",
            modelName: "",
            serialNumber: "",
            dateOfPurchase: new Date(),
            dateOfDelivery: new Date(),
            purchasePrice: "",
            vendor: "",
            fundingBody: "",
            deviceGroup: deviceGroup)
        
        assertFalse 'No validation for blank field(s)' ,device.validate()
        assertEquals 'Name is blank.','blank', device.errors['name']
        assertEquals 'Description is blank.','blank', device.errors['description']
        assertEquals 'manufacturer is blank.','blank', device.errors['manufacturer']
        assertEquals 'ModelName is blank.','blank', device.errors['modelName']
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
        assertTrue "A valid device grouping did not validate!", device.validate()
    }
    
    /**
    * Test the range validation of fields in the domain class {@link DeviceGroup} are
    * correctly validated
    */
   void testRange()
   {
       device = new Device(name: "012345678910" * 100,
            description: "012345678910" * 100,
            manufacturer: "012345678910" * 100,
            locationOfManufacturer: "012345678910" * 100,
            modelName: "012345678910" * 100,
            serialNumber: "012345678910" * 100,
            dateOfPurchase: new Date(),
            dateOfDelivery: new Date(),
            purchasePrice: "012345678910" * 100,
            vendor: "012345678910" * 100,
            fundingBody: "012345678910" * 100,
            deviceGroup: deviceGroup)
       assertFalse "No validation for size of fields", device.validate()
       
       assertEquals 'Name does not validate size.','size', device.errors['name']
       assertEquals 'Description does not validate size.','size', device.errors['description']
       assertEquals 'manufacturer does not validate size.','size', device.errors['manufacturer']
       assertEquals 'locationOfManufacturer does not validate size.','size', device.errors['locationOfManufacturer']
       assertEquals 'model does not validate size.','size', device.errors['modelName']
       assertEquals 'serialNumber does not validate size.','size', device.errors['serialNumber']
       assertEquals 'purchasePrice does not validate size.','size', device.errors['purchasePrice']
       assertEquals 'vendor does not validate size.','size', device.errors['vendor']
       assertEquals 'fundingBody does not validate size.','size', device.errors['fundingBody']
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
       assertTrue device.validate()
   }
}
