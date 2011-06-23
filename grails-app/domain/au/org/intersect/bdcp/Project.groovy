package au.org.intersect.bdcp

class Project
{

	String projectTitle
	String researcherName
	String studentNumber
	String degree
	Date startDate
	Date endDate
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
		studentNumber(blank:true, size:0..1000)
		degree(blank:false, size:1..1000)
		startDate(nullable:false)
		endDate(nullable:false)
		description(blank:false, size:1..1000)
		supervisors(blank:false, size:1..1000)
	}
}
