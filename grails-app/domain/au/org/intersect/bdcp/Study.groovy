package au.org.intersect.bdcp

import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.list.LazyList

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

	static hasMany = [participants:Participant, components:Component, studyDevices:StudyDevice, studyCollaborators:StudyCollaborator]
	
	static belongsTo = [project:Project]
	
	
	String toString()
	{
		return "${studyTitle}"
	}
	
	
	static mapping =
	{
		participants cascade: "all-delete-orphan"
        table 'study'
            columns 
            {
                hasAdditionalEthicsRequirements column: "has_additional_ethics_reqs"
            }
        
	}
	
	
	
	static constraints =
	{
		studyTitle(blank:false, size:1..1000, validFilename:true)
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
	
	void setUowEthicsNumber(String setUowEthicsNumber)
	{
		uowEthicsNumber = setUowEthicsNumber.trim()
	}

	String getHasAdditionalEthicsRequirements()
	{
		if (hasAdditionalEthicsRequirements == null || hasAdditionalEthicsRequirements == "No")
		{
			return "No"
		}
		else
		{
				return "Yes"
		}
	}
		
	def getParticipantsList() {
		return LazyList.decorate(
			  participants,
			  FactoryUtils.instantiateFactory(Participant.class))
	}
}
