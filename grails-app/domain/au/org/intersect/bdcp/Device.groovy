package au.org.intersect.bdcp

class Device {

    String name
    String description
    String manufacturer
    String locationOfManufacturer
    String model
    String serialNumber
    Date dateOfPurchase
    Date dateOfDelivery
    String purchasePrice
    String vendor
    String fundingBody
    
    static belongsTo = [deviceGroup: DeviceGroup]
    
    static constraints = 
    {
        name(blank:false, size:1..1000, unique:true)
        description(blank:false, size:1..1000)
        manufacturer(blank:false, size:1..1000)
        locationOfManufacturer(size:1..1000)
        model(blank:false, size:1..1000)
        serialNumber(size:1..1000)
        dateOfPurchase(nullable:false)
        dateOfDelivery(nullable:false)
        purchasePrice(size:1..1000)
        vendor(size:1..1000)
        fundingBody(size:1..1000)
    }
}
