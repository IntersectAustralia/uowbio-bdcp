package au.org.intersect.bdcp

class DeviceGroup {
    
    String groupingName
    
    static constraints = 
    {
        groupingName(blank:false, size:1..1000, unique:true)
    }
}
