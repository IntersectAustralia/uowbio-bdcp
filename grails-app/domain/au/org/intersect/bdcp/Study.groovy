package au.org.intersect.bdcp

class Study
{

	String studyTitle
	String uowEthicsNumber
	String hasAdditionalEthicsRequirements
	String additionalEthicsRequirements
	String description
	String industryPartners
	String collaborators
	Date startDate
	Date endDate
	String numberOfParticipants
	String inclusionExclusionCriteria

	static hasMany = [participants:Participant, components:Component]
	
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
		uowEthicsNumber(blank:false, unique:true,size:1..1000)
		hasAdditionalEthicsRequirements(nullable:true,inList:["No", "Yes"])
		additionalEthicsRequirements(nullable:true, size:1..1000)
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
