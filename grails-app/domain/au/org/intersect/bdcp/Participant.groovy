package au.org.intersect.bdcp

class Participant
{

	String participantIdentifier
	
	String toString()
	{
		return "${participantIdentifier}"
	}
	
	static hasMany = [forms:Form]
	
	static belongsTo = [study:Study]
	
	static constraints =
	{
		participantIdentifier(blank:false)
	}
}
