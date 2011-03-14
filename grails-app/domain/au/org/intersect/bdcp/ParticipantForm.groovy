package au.org.intersect.bdcp

import java.io.Serializable

import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

class ParticipantForm implements Serializable
{

	String formName
	String form
	String fileName
	
	String toString()
	{
		return "${formName}"
	}
	
	static belongsTo = [participant:Participant]
	static constraints =
	{
		formName(blank:false, size:1..1000)
		form(nullable:true)
		fileName(nullable:false, blank:false, size:1..1000)
	}
}
