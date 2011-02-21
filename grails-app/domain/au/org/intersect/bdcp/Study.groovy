package au.org.intersect.bdcp

class Study
{

	String studyTitle
	String ethicsNumber
	String description
	String industryPartners
	String collaborators
	Date dataStart
	Date dataEnd
	
	static constraints =
	{
		studyTitle(blank:false)
		ethicsNumber(blank:false)
		description(blank:false)
		industryPartners(blank:false)
		collaborators(blank:false)
		dataStart(nullable: false)
		dataEnd(nullable:false)
	}
}
