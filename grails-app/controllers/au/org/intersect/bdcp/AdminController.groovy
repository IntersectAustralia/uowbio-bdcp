package au.org.intersect.bdcp

import au.org.intersect.bdcp.ldap.LdapUser

class AdminController {

    def index = { redirect(action:create,params:params) }
	static transactional = true
    
    def allowedMethods = []

    def create = {
        
    }
	
	def search = {
		def matches = []
		
		render(view: "search", model:[matches:matches])
	}
	
	def searchUsers = {
		
		session.firstName = params.firstName
		session.surname = params.surname
		session.userid = params.userid
		
		List<LdapUser> matches = LdapUser.findAll() {
			and {
					if (!params.userid?.isEmpty())
					{
						like "userid", params.userid
					}
					else
					{
						like "userid", "*"
					}
				}
			and
			{
				if (!params.surname?.isEmpty())
					{
						like "sn", params.surname
					}
					else
					{
						like "sn", "*"
					}
			}
			and
			{
				if (!params.firstName?.isEmpty())
					{
						like "givenName", params.firstName
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
