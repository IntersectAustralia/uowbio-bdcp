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
		projectTitle(blank:false)
		researcherName(blank:false)
		degree(blank:false)
		yearFrom(nullable:false)
		yearTo(nullable:false)
		description(blank:false)
		supervisors(blank:false)
	}
}
