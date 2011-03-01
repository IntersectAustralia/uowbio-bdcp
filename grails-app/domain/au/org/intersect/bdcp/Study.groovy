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
		studyTitle(blank:false, size:1..1000)
		description(blank:false, size:1..1000)
		industryPartners(size:1..1000)
		collaborators(size:1..1000)
		dateStart(nullable: false)
		dateEnd(nullable:false)
		participant(nullable:true)
	}
}
