package au.org.intersect.bdcp

class Participant
{

	String participantIdentifier
	String consentForm
	String formA
	String formB
	Boolean hasConsentForm
	Boolean hasFormA
	Boolean hasFormB
	
	String toString()
	{
		return "${participantIdentifier}"
	}
	
	static hasMany = [forms:Form]
	
	static belongsTo = [study:Study]
	
	static constraints =
	{
		participantIdentifier(blank:false, unique:true)
		consentForm(blank:false)
		formA(blank:false)
		formB(blank:false)
	}
}
