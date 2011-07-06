 package au.org.intersect.bdcp

class UserStore{

    String username
	boolean deactivated
    String authority

    String nlaIdentifier // RIF-CS
	
    static constraints = {
    	username(blank:false, unique:true, size:1..1000)
		authority(nullable:false, blank:false)
		nlaIdentifier(nullable:true, blank:false)
	}
	
	String toString() {
		return "${username}"
	}
	
}
