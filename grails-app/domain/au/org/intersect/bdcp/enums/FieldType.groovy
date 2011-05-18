package au.org.intersect.bdcp.enums


public enum FieldType
{
    TEXT('Text Field'),
    TEXTAREA('Text Area'),
    NUMERIC('Numeric'),
    DATE('Date'),
    TIME('Time'),
    DROP_DOWN("Drop Down"),
    RADIO_BUTTONS("Radio Buttons")
    
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
