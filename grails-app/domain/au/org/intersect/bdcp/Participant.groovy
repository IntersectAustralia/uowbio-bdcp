package au.org.intersect.bdcp

import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.list.LazyList

class Participant
{
	
	String numberOfParticipants
	String inclusionExclusionCriteria
	Study study
	
	
	String toString()
	{
		return "${study}"
	}

	
	
	List participantIdentifiers = new ArrayList()
	static hasMany = [participantIdentifiers:ParticipantIdentifier]
	

	static constraints =
	{ 
		numberOfParticipants(blank:false)
		inclusionExclusionCriteria(blank:false) 
	}
	
	def getParticipantIdentifiersList() {
		return LazyList.decorate(
			  participantIdentifiers,
			  FactoryUtils.instantiateFactory(ParticipantIdentifier.class))
	}
	
}
