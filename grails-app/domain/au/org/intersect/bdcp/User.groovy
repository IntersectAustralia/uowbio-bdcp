package au.org.intersect.bdcp

class User {

	String uid
    static constraints = {
    	uid(blank:false, unique:true)
	}
}
