package au.org.intersect.bdcp.enums


public enum FieldType
{
    TEXT('textField.label'),
    TEXTAREA('textArea.label'),
    NUMERIC('numeric.label'),
    DATE('date.label'),
    TIME('time.label'),
    DROP_DOWN("dropDown.label"),
    RADIO_BUTTONS("radioButtons.label")
    
    String name
    
    FieldType(String name)
    {
        this.name = name
    }
    
    String getName()
    {
        return this.name
    }
    
    static list()
    {
        def items = []
        this.values().each { items.add(it.getName())}
        return items
    }
    
}
