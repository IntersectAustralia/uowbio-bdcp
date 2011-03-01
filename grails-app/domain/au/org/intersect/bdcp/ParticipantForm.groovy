package au.org.intersect.bdcp

class ParticipantForm
{

	String name
	String link
	
	
	static belongsTo = [form:Form]
	static constraints =
	{
		name(blank:false, size:1..1000)
		link(blank:false, size:1..1000)
	}
}
