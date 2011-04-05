package au.org.intersect.bdcp

class UserStore{

    String username
    static constraints = {
    	username(blank:false, unique:true, size:1..1000)
	}
	
}
