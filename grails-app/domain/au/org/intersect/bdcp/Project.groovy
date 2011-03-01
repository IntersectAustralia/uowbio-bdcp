package au.org.intersect.bdcp

class Project
{

	String projectTitle
	String researcherName
	String degree
	Date yearFrom
	Date yearTo
	String description
	String supervisors
	
	static hasMany = [studies: Study]
	
	String toString()
	{
		return "${projectTitle}"
	}
	
	static mapping = 
	{
		studies cascade: "all-delete-orphan"
	}
	
	static constraints =
	{
		projectTitle(blank:false, size:1..1000)
		researcherName(blank:false, size:1..1000)
		degree(blank:false, size:1..1000)
		yearFrom(nullable:false)
		yearTo(nullable:false)
		description(blank:false, size:1..1000)
		supervisors(blank:false, size:1..1000)
	}
}
