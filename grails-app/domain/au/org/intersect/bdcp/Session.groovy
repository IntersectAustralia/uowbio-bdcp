package au.org.intersect.bdcp

class Session {

	String name
	String description
	
	static belongsTo = [component:Component]
	
	static hasMany = [SessionFiles:Session]
	
    static constraints = {
		name(blank:false, size:1..1000, unique:'component')
		description(blank:false, size:1..1000)
    }
}
