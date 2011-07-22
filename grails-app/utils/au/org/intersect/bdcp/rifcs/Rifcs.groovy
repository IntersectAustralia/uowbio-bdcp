package au.org.intersect.bdcp.rifcs

import au.org.intersect.bdcp.Study

class Rifcs {
	
	def fromStudy = 
	{ Study study, Map others ->
		def basics = common().plus(others)
		return basics.plus([
			'key' : 'auto-generated',
			'collection.name' : study.studyTitle,
			'collection.description' : study.description,
			])
	}
	
	private Map common() {
		return [
			'originatingSource' : 'http://www.uow.edu.au',
			'@group' : 'University of Wollongong',
			'collection.email' : 'email-TBA@uow.edu.au',
			'callection.accessRights' : 'Access rights to be announced'
			]
	}

}
