package au.org.intersect.bdcp

class Study
{

	String studyTitle
	String ethicsNumber
	String description
	String industryPartners
	String collaborators
	Date startDate
	Date endDate
	String numberOfParticipants
	String inclusionExclusionCriteria

	static hasMany = [participants:Participant]
	
	static belongsTo = [project:Project]
	
	
	String toString()
	{
		return "${studyTitle}"
	}
	
	
	static mapping =
	{
		participants cascade: "all-delete-orphan"
	}
	
	
	
	static constraints =
	{
		studyTitle(blank:false, size:1..1000)
		ethicsNumber(size:1..1000)
		description(blank:false, size:1..1000)
		industryPartners(size:1..1000)
		collaborators(size:1..1000)
		startDate(nullable: false)
		endDate(nullable:false)
		numberOfParticipants(size:1..1000)
		inclusionExclusionCriteria(size:1..1000) 
	}
	
	def getParticipantsList() {
		return LazyList.decorate(
			  participants,
			  FactoryUtils.instantiateFactory(Participant.class))
	}
}
