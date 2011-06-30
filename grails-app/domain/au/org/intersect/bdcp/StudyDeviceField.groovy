package au.org.intersect.bdcp

import java.text.NumberFormat
import java.util.Date

import org.joda.time.*
import org.joda.time.contrib.hibernate.*

import au.org.intersect.bdcp.enums.FieldType
import au.org.intersect.bdcp.util.TextUtils

class StudyDeviceField {

    String text
    String textArea
    BigDecimal numeric
    LocalDate date
    LocalTime time
    String radioButtonsOption
    String dropDownOption
    
    // automatically updated by GORM
    Date dateCreated
    
    // automatically updated by GORM
    Date lastUpdated
    
    static belongsTo = [studyDevice: StudyDevice, deviceField: DeviceField]
    
    static mapping = {
        date type: PersistentLocalDate
        time type: PersistentLocalTimeAsTime
        
        table 'study_device_field'
        columns
        {
            date column: "date_field"
        }
    }
    
    static constraints = {
        
        text(nullable: true, validator: {val, obj ->
                return checkSizeAndIfNull(val, obj, 0, 255, FieldType.TEXT) })
        
        textArea(nullable: true, validator: {val, obj ->
                return checkSizeAndIfNull(val, obj, 0, 1000, FieldType.TEXTAREA)
            })
        numeric(nullable:true, validator: {val, obj ->
            return checkRangeOfNumber(val, obj, -0.9E16, 0.9E16, FieldType.NUMERIC)
            })
        date(nullable:true, validator: {val, obj ->
            return obj.deviceField?.fieldType != FieldType.DATE || val != null ? true : ['nullable', obj.deviceField.fieldLabel]
            })
        time(nullable:true, validator: {val, obj ->
            return obj.deviceField?.fieldType != FieldType.TIME || val != null ? true : ['nullable', obj.deviceField.fieldLabel]
            })
        radioButtonsOption(nullable: true, size:0..1000, validator: {val, obj ->
                return obj.deviceField?.fieldType != FieldType.RADIO_BUTTONS || TextUtils.isNotEmpty(val) ? true: ['nullable', obj.deviceField.fieldLabel]
            })
        dropDownOption(nullable: true, size:0..1000, validator: {val, obj ->
            return obj.deviceField?.fieldType != FieldType.DROP_DOWN || TextUtils.isNotEmpty(val) ? true: ['nullable', obj.deviceField.fieldLabel]
        })
    }
    
    static checkRangeOfNumber(val, obj, BigDecimal minVal, BigDecimal maxVal, fieldType)
    {
        if (obj.deviceField?.fieldType == fieldType)
        {
             if (val!= null)
             {
                 if (val < minVal)
                 {
                     def nf = NumberFormat.getInstance()
                     return ['range.toosmall', nf.format(minVal), obj.deviceField.fieldLabel]
                 }
                 else if (val > maxVal)
                 {
                     def nf = NumberFormat.getInstance()
                     return ['range.toobig', nf.format(maxVal), obj.deviceField.fieldLabel]
                 }
             }
             else
             {
                 return ['nullable', obj.deviceField.fieldLabel]
             }  
        }
        return true
    }
    
    static checkSizeAndIfNull(val, obj, minVal, maxVal, fieldType)
    {
        if (obj.deviceField?.fieldType == fieldType)
        {
            if (TextUtils.isNotEmpty(val))
            {
                if (val.size() < minVal)
                {
                    return ['size.toosmall', minVal, obj.deviceField.fieldLabel]
                }
                else if (val.size() > maxVal)
                {
                    return ['size.toobig', maxVal, obj.deviceField.fieldLabel]
                }
                
            }
            else
            {
                return ['nullable', obj.deviceField.fieldLabel]
            }
            
        }
        return true
    }
}
