package au.org.intersect.bdcp.enums


public enum UserRoles
{
    ROLE_LAB_MANAGER('Lab Manager'),
    ROLE_SYS_ADMIN('System Administrator'),
    ROLE_RESEARCHER('Researcher'),
    
    String name
    
    UserRoles(String name)
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
    
    static listValues()
    {
        def items = []
        this.values().each { items.add(it)}
        return items
    }
    
}
