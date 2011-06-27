package au.org.intersect.bdcp

import au.org.intersect.bdcp.enums.FieldType
import au.org.intersect.bdcp.util.TextUtils

class DeviceField 
{

    String fieldLabel
    FieldType fieldType
    String fieldOptions
	String staticContent
    
    // automatically updated by GORM
    Date dateCreated
    
    // automatically updated by GORM
    Date lastUpdated
    
    static belongsTo = [device: Device]
    
    static hasMany = [studyDeviceFields: StudyDeviceField]
    
    static constraints = 
    {
        staticContent(maxSize: 10485760) // Maximum size in Postgres, please keep it
        fieldLabel(blank:false, size:1..1000)
        fieldType(nullable:false)
		staticContent(nullable:true, validator:{val, obj ->
			return obj.fieldType != FieldType.STATIC_TEXT || TextUtils.isNotEmpty(val) ? true : ['nullable'] 
			})
        
        fieldOptions(nullable:true, validator: { val, obj ->
			return [FieldType.DROP_DOWN, FieldType.RADIO_BUTTONS].contains(obj.fieldType) ? validFieldOptions(val) : true;
          })
    }
    
    static validFieldOptions(String val)
    {
        if (val != null && !val.isEmpty())
        {
            val = val.replaceAll("\r", "")
            
            List<String> fieldOptions = Arrays.asList(val.split("\n"))
            if (fieldOptions.size() < 2)
            {
                return ['size.toosmall']
            }
            for (int i = 0; i < fieldOptions.size();i++) 
            {
                if (fieldOptions.subList(0,i).contains(fieldOptions.get(i)))
                {
                    return ['unique']
                }
            }
			return true
        }
        else
        {
            return ['nullable']
        }
        
    }
    
    String toString()
    {
        fieldLabel
    }
    
    def getFieldOptionsList()
    {
        def options = fieldOptions?.tokenize("\n")
        def trimmedOptions = []
        options.each {
            trimmedOptions.add(it.trim())
        }
        return trimmedOptions
    }
}
