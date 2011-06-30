package au.org.intersect.bdcp

import org.joda.time.LocalDate

class JqueryDatePickerTagLib {

def jqDatePicker = {attrs, body ->  
    def out = out 
    def name = attrs.name 
    def id = attrs.id ?: name
    def value = attrs?.value
    
    if (value != null)
    {
        if (value instanceof LocalDate)
        {
            value = value.getLocalMillis()
        }
        else if (value instanceof Date)
        {
            value = value.getTime()
        }
    }
    
    def jqName = name.replace('.','\\\\.')
    jqName = jqName.replace('[','\\\\[')
    jqName = jqName.replace(']','\\\\]')
    
    //Create date text field and supporting hidden text fields need by grails
out.println "<input type=\"text\" name=\"${name}\" id=\"${id}\" />"
out.println "<input type=\"hidden\" name=\"${name}_day\" id=\"${id}_day\" />"
out.println "<input type=\"hidden\" name=\"${name}_month\" id=\"${id}_month\" />"
out.println "<input type=\"hidden\" name=\"${name}_year\" id=\"${id}_year\" />"

//Code to parse selected date into hidden fields required by grails
out.println "<script type=\"text/javascript\"> \$(document).ready(function(){"
out.println "\$(\"#${jqName}\").datepicker({"
out.println "changeMonth: true,"
out.println "changeYear: true,"
out.println "onClose: function(dateText, inst) {"
out.println "\$(\"#${jqName}_month\").attr(\"value\",new Date(dateText).getMonth() +1);"
out.println "\$(\"#${jqName}_day\").attr(\"value\",new Date(dateText).getDate());"
out.println "\$(\"#${jqName}_year\").attr(\"value\",new Date(dateText).getFullYear());"
out.println "}"
out.println "});"
if (value != null)
{
    out.println "\$(\"#${jqName}\").datepicker(\"setDate\", new Date(${value}) );"
}
out.println "})</script>"


    }
} 
