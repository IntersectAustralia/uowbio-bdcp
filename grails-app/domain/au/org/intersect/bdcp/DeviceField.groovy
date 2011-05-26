package au.org.intersect.bdcp

import au.org.intersect.bdcp.enums.FieldType;
import au.org.intersect.bdcp.util.TextUtils;

class DeviceField 
{

    String fieldLabel
    FieldType fieldType
	String staticContent
    
    // automatically updated by GORM
    Date dateCreated
    
    // automatically updated by GORM
    Date lastUpdated
    
    static belongsTo = [device: Device]
    
    static constraints = 
    {
        fieldLabel(blank:false, size:1..1000)
        fieldType(nullable:false)
		staticContent(validator:{val, obj ->
			return obj.fieldType != FieldType.STATIC_TEXT || TextUtils.isNotEmpty(val) 
			})
    }
}
