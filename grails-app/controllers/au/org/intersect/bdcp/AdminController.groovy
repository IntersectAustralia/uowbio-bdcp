package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured
import au.org.intersect.bdcp.ldap.LdapUser



class AdminController {

    def emailNotifierService
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def index = { redirect(action: "accountAdmin", params: params) }
	static transactional = true
    
    def allowedMethods = []

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def create = {
		def username
		if (params.username == null)
		{
			username = ""
		}
		else
		{
			username = params.username
		}
		return [username: username]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def save = {
		def accountStatus = "Failed"
		def user
		def email
		if (params.username != null)
		{
			LdapUser match = LdapUser.find(
				filter: "(uid=${params.username})")
			if (match !=  null)
			{
				email = match.mail
				user= new UserStore(uid: params.username)
			}
			
		   
		}
		if (user!= null && user.save(flush:true))
		{
				accountStatus = "Successful"
				emailNotifierService.contactUser(user.uid, email)
				render (view: "createStatus", model:[accountStatus: accountStatus, user: user ,username:params.username])
				session.firstName =  ""
				session.surname = ""
				session.userid = ""
		}
		else
		{
			accountStatus = "Failed"
			render (view: "createStatus", model:[accountStatus: accountStatus, user: user ,username:params.username])
		}
		
		
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def createStatus = {
		
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def accountAdmin = {
        
    }
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def search = {
		def matches = []
		
		render(view: "search", model:[matches:matches])
	}
	
	private String normalizeValue(value)
	{
		value = value.replaceAll(/[^A-Za-z0-9-]/, '')
		return value
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def listUsers = {
		 def matches = []
		 UserStore.list().each 
		 { 
			 matches <<LdapUser.find(filter: "(uid=${it?.username})")
		 }
		 def sortedMatches = matches.sort {x,y -> x.sn <=> y.sn}
		 render (view: "listUsers", model: [ matches: sortedMatches])
     }
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def show = {
		def match = LdapUser.find(filter: "(uid=${params.username})")
		render (view:"show", model :[matchInstance: match])
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def searchUsers = {
		def matches = []
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
		
		matches = LdapUser.findAll() {
			and {
					if (!session.userid?.isEmpty())
					{
						like "uid", "*" + normalizeValue(session.userid) + "*"
					}
					else
					{
						like "uid", "*"
					}
				}
			and{
				if (!session.surname?.isEmpty())
					{
						like "sn", "*" + normalizeValue(session.surname) + "*" 
					}
					else
					{
						like "sn", "*"
					}
			}
			and {
				if (!session.firstName?.isEmpty())
					{
						like "givenName", "*" + normalizeValue(session.firstName) +"*"
					}
					else
					{
						like "givenName", "*"
					}
			}
		}
		
		def sortedMatches = matches.sort{x,y -> x.getUserId() <=> y.getUserId()?: x.sn <=> y.sn ?: x.givenName <=> y.givenName}
		
		render (view: "search", model: [firstName: params.firstName, surname:params.surname, userid:params.userid, matches: sortedMatches])
	}
}
