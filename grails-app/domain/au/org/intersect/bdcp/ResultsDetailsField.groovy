package au.org.intersect.bdcp

import java.util.Date

import au.org.intersect.bdcp.enums.FieldType
import au.org.intersect.bdcp.util.TextUtils

class ResultsDetailsField {
    
    String fieldLabel
    FieldType fieldType
    String fieldOptions
    String staticContent
    boolean mandatory
    // automatically updated by GORM
    Date dateCreated
    
    // automatically updated by GORM
    Date lastUpdated
    
    static constraints = {
        
        fieldLabel(blank:false, size:1..1000)
        fieldType(nullable:false)
    // 10485760 : Maximum size in Postgres, please keep it
    staticContent(maxSize: 10485760,nullable:true, validator:{val, obj ->
        return obj.fieldType != FieldType.STATIC_TEXT || TextUtils.isNotEmpty(val) ? true : ['nullable']
        })
        
        fieldOptions(nullable:true, validator: { val, obj ->
            return [FieldType.DROP_DOWN, FieldType.RADIO_BUTTONS].contains(obj.fieldType) ? validFieldOptions(val) : true;
          })
        mandatory(nullable: false)
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
    
    String mandatoryStatus()
    {
        if (fieldType == FieldType.STATIC_TEXT)
        {
            return "N/A"
        }
        return mandatory.toString().capitalize()
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
