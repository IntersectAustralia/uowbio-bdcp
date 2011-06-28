package au.org.intersect.bdcp

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
        
        text(nullable: true, size:0..255, validator: {val, obj ->
                return obj.deviceField?.fieldType != FieldType.TEXT || TextUtils.isNotEmpty(val) ? true: ['nullable', obj.deviceField.fieldLabel]
            })
        textArea(nullable: true, size:0..1000, validator: {val, obj ->
                return obj.deviceField?.fieldType != FieldType.TEXTAREA || TextUtils.isNotEmpty(val) ? true: ['nullable', obj.deviceField.fieldLabel]
            })
        numeric(nullable:true, validator: {val, obj ->
            return obj.deviceField?.fieldType != FieldType.NUMERIC || val != null ? true : ['nullable', obj.deviceField.fieldLabel]
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
}
