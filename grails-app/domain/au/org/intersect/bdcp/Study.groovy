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
	
	static belongsTo = [project:Project]
	
	static constraints =
	{
		studyTitle(blank:false, unique:true)
		ethicsNumber(blank:false, unique:true)
		description(blank:false)
		industryPartners(blank:false)
		collaborators(blank:false)
		dataStart(nullable: false)
		dataEnd(nullable:false)
	}
}
