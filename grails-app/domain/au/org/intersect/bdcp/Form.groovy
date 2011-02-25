package au.org.intersect.bdcp

class Form
{
	String name
	String location
	Boolean stored

	static belongsTo = [participant:Participant]
	static constraints =
	{
	}
}
