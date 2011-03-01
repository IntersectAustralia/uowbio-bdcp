package au.org.intersect.bdcp

class ParticipantForm
{

	String name
	String link
	
	
	static belongsTo = [form:Form]
	static constraints =
	{
		name(blank:false)
		link(blank:false)
	}
}
