package au.org.intersect.bdcp

class Form
{
	String formName
	String formLink

	static belongsTo = [participant:Participant]
	static constraints =
	{
		formName(blank:false)
	}
}
