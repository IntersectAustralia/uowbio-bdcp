package au.org.intersect.bdcp

class Study
{

	String studyTitle
	String ethicsNumber
	String description
	String industryPartners
	String collaborators
	Date dateStart
	Date dateEnd
	
	static belongsTo = [project:Project]
	
	static hasMany = [participants:Participant]
	
	String toString()
	{
		return "${studyTitle}"
	}
	
	static constraints =
	{
		studyTitle(blank:false, unique:true)
		description(blank:false)
		industryPartners()
		collaborators()
		dateStart(nullable: false)
		dateEnd(nullable:false)
	}
}
