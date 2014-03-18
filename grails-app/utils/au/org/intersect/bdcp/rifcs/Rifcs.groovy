package au.org.intersect.bdcp.rifcs

import au.org.intersect.bdcp.Study

class Rifcs {

    def ldapSearchService
	
	def fromStudy = 
	{ Study study, Map others ->
		def basics = common().plus(others)
		def email = findEmail(study.project.owner.username)
		return basics.plus([
			'key' : 'auto-generated',
			'collection.name' : study.studyTitle,
			'collection.description' : study.description,
			'collection.email.address' : email
			])
	}
	
	private String findEmail(String username)
	{
		def ldapUsers = ldapSearchService.findFirst(username)
		return (ldapUsers != null && ldapUsers.size() > 0) ? ldapUsers.get(0).email : username + "?@uow.edu.au"; 
	}

}
