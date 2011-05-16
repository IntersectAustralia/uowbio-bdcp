package au.org.intersect.bdcp

class DeviceGroup {
    
    String groupingName

    static hasMany = [devices:Device]
        
    static constraints = 
    {
        groupingName(blank:false, size:1..1000, unique:true, trulyunique:true)
    }
    
    void setGroupingName(String groupingName)
    {
        this.groupingName = groupingName?.trim()
    }
}
