package au.org.intersect.bdcp

class Device {

    String name
    String description
    String manufacturer
    String locationOfManufacturer
    String modelName
    String serialNumber
    Date dateOfPurchase
    Date dateOfDelivery
    String purchasePrice
    String vendor
    String fundingBody
    
    static belongsTo = [deviceGroup: DeviceGroup]
    
    static constraints = 
    {
        name(blank:false, size:1..1000, uniqueIgnoreCase:true)
        description(blank:false, size:1..1000)
        manufacturer(blank:false, size:1..1000)
        locationOfManufacturer(size:1..1000)
        modelName(blank:false, size:1..1000)
        serialNumber(size:1..1000)
        dateOfPurchase(nullable:false)
        dateOfDelivery(nullable:false)
        purchasePrice(size:1..1000)
        vendor(size:1..1000)
        fundingBody(size:1..1000)
    }
    
    String getName()
    {
        name?.trim()
    }
    
    void setName(String name)
    {
        this.name = name?.trim()
    }
}
