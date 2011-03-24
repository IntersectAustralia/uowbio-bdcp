package au.org.intersect.bdcp

import au.org.intersect.bdcp.ldap.LdapUser

class AdminController {

    def index = { redirect(action:create,params:params) }
	static transactional = true
    
    def allowedMethods = []

	def create = {
		
	}
	
    def accountAdmin = {
        
    }
	
	def search = {
		def matches = []
		
		render(view: "search", model:[matches:matches])
	}
	
	def searchUsers = {
		if (params.firstName != null && !params.firstName.isEmpty())
		{
			session.firstName = params.firstName
		}
		else
		{
			session.firstName = ""
		}
		if (params.surname != null && !params.surname.isEmpty())
		{
				session.surname = params.surname
		}
		else
		{
			session.surname=""
		}
		if (params.userid != null && !params.userid.isEmpty())
		{
				session.userid = params.userid
		}
		else
		{
			session.userid = ""
		}
		
		List<LdapUser> matches = LdapUser.findAll() {
			and {
					if (!session.userid?.isEmpty())
					{
						like "uid", session.userid
					}
					else
					{
						like "uid", "*"
					}
				}
			and
			{
				if (!session.surname?.isEmpty())
					{
						like "sn", session.surname
					}
					else
					{
						like "sn", "*"
					}
			}
			and
			{
				if (!session.firstName?.isEmpty())
					{
						like "givenName", session.firstName
					}
					else
					{
						like "givenName", "*"
					}
			}
		}
		
		
		render (view: "search", model: [firstName: params.firstName, surname:params.surname, userid:params.userid, matches: matches])
	}
}
