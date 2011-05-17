package au.org.intersect.bdcp

import au.org.intersect.bdcp.enums.FieldType;

class DeviceField 
{

    String fieldLabel
    FieldType fieldType
    
    // automatically updated by GORM
    Date dateCreated
    
    // automatically updated by GORM
    Date lastUpdated
    
    static belongsTo = [device: Device]
    
    static constraints = 
    {
        fieldLabel(blank:false, size:1..1000)
        fieldType(nullable:false)
    }
}
