package au.org.intersect.bdcp

class ParticipantForm
{

	String formName
	String form
	
	String toString()
	{
		return "${formName}"
	}
	
	static belongsTo = [participant:Participant]
	static constraints =
	{
		formName(blank:false, size:1..1000)
		form(blank:false, size:1..1000)
	}
}
