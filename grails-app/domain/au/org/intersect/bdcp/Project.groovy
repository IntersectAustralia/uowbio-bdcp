package au.org.intersect.bdcp

class Project {

	String projectTitle
	String researcherName
	String degree
	Date yearFrom
	Date yearTo
	String description
	String supervisors
	
    static constraints = {
		projectTitle(blank:false, unique:true)
		researcherName(blank:false)
		degree(blank:false)
		yearFrom(nullable:false)
		yearTo(nullable:false)
		description(blank:false)
		supervisors(blank:false)
		
		
    }
}
