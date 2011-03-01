package au.org.intersect.bdcp

import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.list.LazyList

class Form
{

	String name
	String link

	List participantForms = new ArrayList()
	static hasMany = [participantForms:ParticipantForm]
	
	
	def getParticipantFormsList()
	{
		return LazyList.decorate(
		participantForms,
		FactoryUtils.instantiateFactory(ParticipantForm.class))
	}

	static belongsTo = [participantIdentifier:ParticipantIdentifier]

	static constraints =
	{
		name(blank:false)
		link(blank:false)
	}
}
