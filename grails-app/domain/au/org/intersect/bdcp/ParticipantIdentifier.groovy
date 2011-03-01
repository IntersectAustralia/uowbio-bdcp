package au.org.intersect.bdcp

class ParticipantIdentifier
{

	String identifier
	static hasMany = [forms:Form]
	
	String toString()
	{
		return "${identifier}"
	}
	
	static constraints =
	{ 
		identifier(blank:false) 
	}
}
