package au.org.intersect.bdcp

import au.org.intersect.bdcp.ldap.LdapUser

class AdminController {

    def index = { redirect(action:create,params:params) }
	static transactional = true
    
    def allowedMethods = []

    def create = {
        
    }
	
	def search = {
		
		render(view: "search")
	}
	
	def searchUsers = {
		if (params.firstName != null)
		{
			session.firstName = params.firstName
		}
		else
		{
			session.firstName = ""
		}
		if (params.surname != null)
		{
			session.surname = params.surname
		}
		else
		{
			session.surname=""
		}
		if (params.userid != null)
		{
			session.userid = params.userid
		}
		else
		{
			session.userid = ""
		}
		
		List<LdapUser> matches = LdapUser.findAll() {
			and {
					like "uid", "${session.userid}"
				}
			and
			{
				like "sn", "${session.surname}"
			}
			and
			{
				like "givenName", "${session.firstName}"
			}
		}
		
		println matches.size() + " matches:"
		println "---------------------------------"
		matches.each
		{
			println it.uid + " " + it.cn
		}
		println "---------------------------------"
		
		
		render (view: "search", model: [firstName: params.firstName, surname:params.surname, userid:params.userid])
//		redirect (controller: "admin", action: "search")
	}
}
