package au.org.intersect.bdcp

class ParticipantForm
{

	String name
	String link
	
	String toString()
	{
		return "${name}"
	}
	
	static belongsTo = [participant:Participant]
	static constraints =
	{
		name(blank:false, size:1..1000)
		link(blank:false, size:1..1000)
	}
}
