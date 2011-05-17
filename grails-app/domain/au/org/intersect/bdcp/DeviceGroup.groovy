package au.org.intersect.bdcp

import au.org.intersect.bdcp.constraints.UniqueIgnoreCaseConstraint

class DeviceGroup {
    
    String groupingName

    static hasMany = [devices:Device]
        
    static constraints = 
    {
        groupingName(blank:false, size:1..1000, uniqueIgnoreCase:true)
    }
    
    void setGroupingName(String groupingName)
    {
        this.groupingName = groupingName?.trim()
    }
}
