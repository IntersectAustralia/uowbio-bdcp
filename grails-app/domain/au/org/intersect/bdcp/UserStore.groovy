 package au.org.intersect.bdcp

class UserStore{

    String username
	boolean deactivated
    String authority

    // String nlaIndentifier // RIF-CS
	
    static constraints = {
    	username(blank:false, unique:true, size:1..1000)
		authority(nullable:false, blank:false)
	}
	
	String toString() {
		return "Userstore toString()-> username is: ${username}, deactivated is: ${deactivated}, authority is: ${authority}.}"
	}
	
}
