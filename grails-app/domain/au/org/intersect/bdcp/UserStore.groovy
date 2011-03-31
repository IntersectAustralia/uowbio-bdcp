package au.org.intersect.bdcp

class UserStore{

    String uid
    static constraints = {
    	uid(blank:false, unique:true, size:1..1000)
	}
	
}
