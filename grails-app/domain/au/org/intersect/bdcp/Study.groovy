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

	static hasOne = [participant:Participant]
	
	static belongsTo = [project:Project]
	
	
	String toString()
	{
		return "${studyTitle}"
	}
	
	static constraints =
	{
		studyTitle(blank:false)
		description(blank:false)
		industryPartners()
		collaborators()
		dateStart(nullable: false)
		dateEnd(nullable:false)
		participant(nullable:true)
	}
}
