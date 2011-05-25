package au.org.intersect.bdcp

import au.org.intersect.bdcp.enums.FieldType

class DeviceField 
{

    String fieldLabel
    FieldType fieldType
    String fieldOptions
    // automatically updated by GORM
    Date dateCreated
    
    // automatically updated by GORM
    Date lastUpdated
    
    static belongsTo = [device: Device]
    
    static constraints = 
    {
        fieldLabel(blank:false, size:1..1000)
        fieldType(nullable:false)
        
        fieldOptions(validator: { val, obj ->
            def fieldType = obj.properties["fieldType"]
            if ((fieldType == FieldType.DROP_DOWN || fieldType == FieldType.RADIO_BUTTONS)) 
            {
                return validFieldOptions(val)
            }
            else 
            {
              return true
            }
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
        
        }
        else
        {
            return ['nullable']
        }
        
        return true
    }
}
