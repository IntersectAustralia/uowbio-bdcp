package au.org.intersect.bdcp

class Session implements Comparable {

	String name
	String description

        int compareTo(obj) {
           return name.compareTo(obj.name)
        }
	
	static belongsTo = [component:Component]
	
	static hasMany = [SessionFiles:Session]
	
    static constraints = {
		name(blank:false, size:1..1000, unique:'component',validFilename:true)
		description(blank:false, size:1..1000)
    }
    
    static mapping = {
        table 'study_session'
    }
}
