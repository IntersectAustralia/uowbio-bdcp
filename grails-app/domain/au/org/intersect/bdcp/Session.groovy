package au.org.intersect.bdcp

class Session {

	String name
	String description
	
	static belongsTo = [component:Component]
	
    static constraints = {
		name(blank:false)
		description(blank:false)
    }
}
