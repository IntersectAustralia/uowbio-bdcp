package au.org.intersect.bdcp.enums

import au.org.intersect.bdcp.util.TextUtils

public enum FieldType
{
    TEXT('textField.label','text', { fieldDef, domObj -> FieldType.validateText('text', fieldDef, domObj) }),
	
    TEXTAREA('textArea.label','textArea', { fieldDef, domObj -> FieldType.validateText('textArea', fieldDef, domObj) }),
	
    NUMERIC('numeric.label','numeric', { fieldDef, domObj -> 
		def val = domObj.numeric
		def minVal = -0.9E16
		def maxVal = 0.9E16
        if (val!= null)
        {
            if (!val.isNumber())
            {
				domObj.errors.reject('not.number', fieldDef.fieldLabel + ' not a number')
            }
            else
            {
                if (val.toBigDecimal() < minVal)
                {
                    def nf = NumberFormat.getInstance()
					domObj.errors.reject('range.toosmall', [nf.format(minVal), fieldDef.fieldLabel] as Object[], fieldDef.fieldLabel + ' less than minimum allowed value')
                }
                else if (val.toBigDecimal() > maxVal)
                {
                    def nf = NumberFormat.getInstance()
					domObj.errors.reject('range.toobig', [nf.format(maxVal), fieldDef.fieldLabel] as Object[], fieldDef.fieldLabel + ' more than maximum allowed value')
                }
            }
        }
        else if (fieldDef.mandatory)
        {
			domObj.errors.reject('nullable', [fieldDef.fieldLabel] as Object[], fieldDef.fieldLabel + ' is mandatory')
        }
	}),

    DATE('date.label','date', { fieldDef, domObj -> validateNotNull('date', fieldDef, domObj)}),

    TIME('time.label','time', { fieldDef, domObj -> validateNotNull('time', fieldDef, domObj)}),
	
    DROP_DOWN("dropDown.label",'dropDownOption', { fieldDef, domObj -> validateOptions('dropDownOption', fieldDef, domObj)}),
	
    RADIO_BUTTONS("radioButtons.label",'radioButtonsOption', { fieldDef, domObj -> validateOptions('radioButtonsOption', fieldDef, domObj)}),
	
	STATIC_TEXT('staticText.label', null, null)
    
    String name
	String valueColumn
	final Closure fieldValidate
    
    FieldType(String name, String valueColumn, Closure fieldValidate)
    {
        this.name = name
		this.valueColumn = valueColumn
		this.fieldValidate = fieldValidate
    }
    
    String getName()
    {
        return this.name
    }

	void normaliseAndValidate(fieldDescription, domainObject) {
		if (valueColumn == null)
			return
	    fieldValidate(fieldDescription, domainObject)
	}
    
    static list()
    {
        def items = []
        this.values().each { items.add(it.getName())}
        return items
    }
    
    static listValues()
    {
        def items = []
        this.values().each { items.add(it)}
        return items
    }
	
	static validateText(valueColumn, fieldDef, domObj) {
			def val = domObj."$valueColumn" as String
			def minValue = 1
			def maxValue = 255
			if (TextUtils.isNotEmpty(val))
			{
				def l = val.length()
				if (maxValue < l || l < minValue)
				{
					def messageProp = l < minValue ? 'size.toosmall' : 'size.toobig'
					def messageDef = l < minValue ? 'small' : 'big'
					domObj.errors.reject(messageProp, ([maxValue, fieldDef.fieldLabel] as Object[]), fieldDef.fieldLabel + ' field too ' + messageDef)
				}
			}
			else if (fieldDef.mandatory)
			{
				domObj.errors.reject('blank', [fieldDef.fieldLabel] as Object[], fieldDef.fieldLabel + ' is mandatory')
			}
	}
	
	static validateNotNull(valueColumn, fieldDef, domObj) {
		def val = domObj."$valueColumn"
		if (val == null && fieldDef.mandatory)
		{
			domObj.errors.reject('nullable', [fieldDef.fieldLabel] as Object[], fieldDef.fieldLabel + ' is mandatory')
		}
	}
	
	static validateOptions(valueColumn, fieldDef, domObj) {
		def val = domObj."$valueColumn"
		if (!TextUtils.isNotEmpty(val) && fieldDef.mandatory)
		{
			domObj.errors.reject('nullable', [fieldDef.fieldLabel] as Object[], fieldDef.fieldLabel + ' is mandatory')
			return
		}
		if (TextUtils.isNotEmpty(val)) {
			def options = Arrays.asList(fieldDef.fieldOptions.replaceAll("\r","").split("\n"))
			if (!options.contains(val)) {
				domObj.errors.reject('invalid', [fieldDef.fieldLabel] as Object[], fieldDef.fieldLabel + ' has a not listed value!')
			}
		}
	}
    
}
