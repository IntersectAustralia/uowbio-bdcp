package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured
import au.org.intersect.bdcp.enums.UserRole
import au.org.intersect.bdcp.ldap.LdapUser
import au.org.intersect.bdcp.SecRole
import au.org.intersect.bdcp.SecUserSecRole


class AdminController
{

	def emailNotifierService
	def sessionRegistry
	def springSecurityService
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def index =
	{
		cache false
		redirect(action: "accountAdmin", params: params)
	}

	static transactional = true

	def allowedMethods = []

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def create =
	{
		cache false
        def accountStatus = "Failed"

		def userid = params.userid?.length() > 0 ? params.userid : params.email
		def firstName = params.firstName != null ? params.firstName : ""
		def surname = params.surname != null ? params.surname : ""
		def role = params.authority != null ? params.authority : ""
		def email = params.email != null ? params.email : ""
		def password = params.password != null ? params.password : ""
		def nlaIdentifier = params.nlaIdentifier != null ? params.nlaIdentifier : ""
		def title = params.title != null ? params.title : null
		
		println "create::userid is: " + userid
		println "create::firstName is: " + firstName
		println "create::surname is: " + surname
		println "create::password is: " + password
	
		def user;
		user = new UserStore(username: userid, firstName: firstName, surname: surname, authority: role, nlaIdentifier: nlaIdentifier, title: title, email: email, password: password);

		if (user?.validate())
		{
			accountStatus = "Successful"
			def roleString = (UserRole)role.toString()
			def rolename = roleString.getName();
			render (view: "create", model:[userid:userid, firstName: firstName, surname: surname, role:role, rolename:rolename, nlaIdentifier:nlaIdentifier, title:title, email: email, password: password])
		}
		else
		{
			accountStatus = "Failed"
			render (view: "addRole", model:[accountStatus: accountStatus, user:user, userid:userid, firstName: firstName, surname: surname, authority:role, nlaIdentifier:nlaIdentifier, title:title])
        }
       
        return [userid: userid, firstName: firstName, surname: surname, role: role, password: password]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def save =
	{
		cache false
		def accountStatus = "Failed"
		def user
		def email
		
println "save::firstName is: " + params.firstName
println "save::surname is: " + params.surname
println "save::role is: " + params.role
println "save::password is: " + params.password

		if (params.userid != null && !params.email)
		{
			LdapUser match = LdapUser.find(
					filter: "(uid=${params.userid})")
			if (match !=  null)
			{
				email = match.mail
				user= new UserStore(username: params.userid, authority: params.role, nlaIdentifier: params.nlaIdentifier, title: params.title, password: springSecurityService.encodePassword(params.password), enabled: true, deactivated: false)
			}
		}
		else if( params.email) // external user
		{
			user = new UserStore(username: params.userid, authority: params.role, nlaIdentifier: params.nlaIdentifier, title: params.title, email: params.email, surname: params.surname, firstName: params.firstName, password: springSecurityService.encodePassword(params.password), enabled: true, deactivated: false)
			user.save(flush:true, failOnError:true)
			email = params.email
			def _secRole = SecRole.findByAuthority( params.role )
println "secRole1 is: " + _secRole
			if(!_secRole)
			{
				_secRole = new SecRole( authority: params.role )
				_secRole.save(flush:true, failOnError:true)
			}
println "secRole2 is: " + _secRole
			def secUserSecRole = new SecUserSecRole(secUser: user, secRole: _secRole)
			secUserSecRole.save(flush:true, failOnError:true)
		}
		if (user!= null && user.save(flush:true))
		{
			accountStatus = "Successful"
			emailNotifierService.contactUser(params.email ? "mailExternal" : "mail", user.username, params.password, email, user.authority.getName())
			render (view: "createStatus", model:[accountStatus: accountStatus, user: user ,userid:params.userid, role: params.role])
			session.firstName =  ""
			session.surname = ""
			session.userid = ""
			session.password = ""
			session.email = ""
		}
		else
		{
			accountStatus = "Failed"
			render (view: "createStatus", model:[accountStatus: accountStatus, user: user ,userid:params.userid])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def createStatus =
	{ cache false }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def systemAdmin =
    { cache false }
    
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def resultsAdmin =
    { cache false }
    
    
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def accountAdmin =
	{ cache false }
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def search =
	{
		cache false
		def matches = []
		def flagDisplayCreateExternalUser = true

		render(view: "search", model: [matches: matches, flagDisplayCreateExternalUser: flagDisplayCreateExternalUser])
	}

	private String normalizeValue(value)
	{
		value = value.replaceAll(/[^A-Za-z0-9-]/, '')
		return value
	}


	@Secured(['IS_AUTHENTICATED_FULLY', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def listUsers =
	{
		cache false
		def hideDeactivatedUsers = (params.hideUsers == null || params.hideUsers == "false") ? false : true
		def matches = []
		def activatedMatches = []
		def match
		UserStore.list().each
		{
			match = LdapUser.find(filter: "(uid=${it?.username})")
			if(match)
			{
				matches << new UserStore(username: match.username, firstName: match.givenName, surname: match.sn)
				
				if (!it?.deactivated)
				{
					activatedMatches << new UserStore(username: match.username, firstName: match.givenName, surname: match.sn)
				}
			}
			if(it.email)
			{
				matches << it
				if (!it?.deactivated)
				{
					activatedMatches << it
				}
				
			}
		}
		def sortedMatches = matches.sort
		{x,y -> x.surname <=> y.surname}
		def sortedActivatedMatches = activatedMatches.sort
		{x,y -> x.surname <=> y.surname}
		if (hideDeactivatedUsers)
		{
			render (view: "listUsers", model: [ matches: sortedActivatedMatches, hideUsers: hideDeactivatedUsers])
		}
		else
		{
			render (view: "listUsers", model: [ matches: sortedMatches, hideUsers: hideDeactivatedUsers])
		}
	}

	@Secured(['IS_AUTHENTICATED_FULLY', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def edit =
	{
		cache false
		def match = LdapUser.find(filter: "(uid=${params.username})")
		def userStore = UserStore.findByUsername(params.username)
		if(match)
		{
			match = new UserStore( username: match.username, firstName: match.givenName, surname: match.sn)
		}
		else
		{
			match = userStore
		}
		
		render (view:"edit", model :[matchInstance: match, userInstance: userStore, hideUsers: params.hideUsers])
	}

	@Secured(['IS_AUTHENTICATED_FULLY', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def update =
	{
		cache false
		def match = LdapUser.find(filter: "(uid=${params.username})")
		def userInstance = UserStore.get(params.id)
		params.title = params.title == null ? null : params.title
		if (userInstance)
		{
			if (params.version)
			{
				def version = params.version.toLong()
				if (userInstance.version > version)
				{

					userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'userStore.label', default: 'UserStore')]
					as Object[], "Another user has updated this user while you were editing")
					render(view: "edit", model: [matchInstance:match, userInstance: userInstance])
					return
				}
			}
			def active = userInstance.deactivated
			userInstance.properties = params
			if (userInstance.deactivated && springSecurityService.principal.getUsername() == params.userid)
			{
				flash.error="${userInstance.username} could not be deactivated because you are the current user"
				userInstance.deactivated = false
				render(view: "edit", model: [matchInstance:match, userInstance: userInstance])
				return
			}
			if (!userInstance.hasErrors() && userInstance.save(flush: true))
			{
				def message = "${userInstance.username} updated"
				if (userInstance?.deactivated != active)
				{
					message += " and ${userInstance.deactivated?'deactivated':'activated'} successfully"
				}
				else
				{
					message += " successfully"
				}
				flash.message = message
				if (userInstance?.deactivated)
				{
					
					sessionRegistry.getAllPrincipals().each {
						if (it.getUsername() == params.username)
						{
							sessionRegistry.getAllSessions(it, false).each {
								it.expireNow()
							}
						}
					}
				}
				redirect(action: "listUsers", params:["hideUsers":params.hideUsers])
			}
			else
			{
				println userInstance.errors
				render(view: "edit", model: [matchInstance:match, userInstance: userInstance])
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userStore.label', default: 'UserStore'), params.id])}"
			redirect(action: "listUsers", params:["hideUsers":params.hideUsers])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def searchUsers =
	{
		cache false
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

		matches = LdapUser.findAll()
		{
			and
			{
				if (!session.userid?.isEmpty())
				{
					like "uid", "*" + normalizeValue(session.userid) + "*"
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
					like "sn", "*" + normalizeValue(session.surname) + "*"
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
					like "givenName", "*" + normalizeValue(session.firstName) +"*"
				}
				else
				{
					like "givenName", "*"
				}
			}
		}

		def sortedMatches = matches.sort
		{x,y -> x.getUserId() <=> y.getUserId()?: x.sn <=> y.sn ?: x.givenName <=> y.givenName}
		
		def flagDisplayCreateExternalUser = false

		render (view: "search", model: [firstName: params.firstName, surname:params.surname, userid:params.userid, matches: sortedMatches, flagDisplayCreateExternalUser: flagDisplayCreateExternalUser])
	}
	
	@Secured(['IS_AUTHENTICATED_FULLY', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def displayCreateExternalUser =
	{
		cache false
		
		render (view: "displayCreateExternalUser", model: [firstName: params.firstName, surname:params.surname, email:params.email, formErrors:[]])
	}
	
	@Secured(['IS_AUTHENTICATED_FULLY', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def createExternalUser =
	{
		cache false
                def formErrors = [:]
		
		if (params.firstName != null && params.firstName.length() > 0)
		{
			session.firstName = params.firstName
		}
		else
		{
			session.firstName = ""
			formErrors['firstName'] = 'Please provide first name'
		}
		if (params.surname != null && params.surname.length() > 0)
		{
			session.surname = params.surname
		}
		else
		{
			session.surname=""
			formErrors['surname'] = 'Please provide first name'
		}
		if (params.email != null && params.email.length() > 0)
		{
			session.email = params.email
		}
		else
		{
			session.email=""
			formErrors['email'] = 'Please provide email'
		}
		if (params.password != null && params.password.length() > 0)
		{
			if (params.password.equals(params.password_2))
			{
				session.password = params.password
			}
			else
			{
				formErrors['password_2'] = "Passwords do not match"
			}
		}
		else
		{
			session.password=""
                        formErrors['password'] = 'Please provide a password';
		}

		if (formErrors.size() == 0)
		{

			render (view: "addRole", model: [userid: params.email, firstName: params.firstName, surname: params.surname, email: params.email, password: params.password])
		}
		else
		{
			render (view: "displayCreateExternalUser", model:[firstName:session.firstName, surname:session.surname, email:session.email, formErrors:formErrors])
		}
	}
	
	@Secured(['IS_AUTHENTICATED_FULLY', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def addRole =
	{
		cache false

		def userid = params.userid != null ? params.userid : params.email
		def firstName = params.firstName != null ? params.firstName : ""
		def surname = params.surname != null ? params.surname : ""
		def email = params.email != null ? params.email : ""
		def password = params.password != null ? params.password : ""
		def role = params.authority != null ? params.authority: ""
		def nlaIdentifier = params.nlaIdentifier != null ? params.nlaIdentifier : null
		
		println "addRole::userid is: " + userid
		println "addRole::firstName is: " + firstName
		println "addRole::surname is: " + surname
		println "addRole::password is: " + password
		
		return [userid: userid, firstName: firstName, surname: surname, email: email, password: password, role: role, nlaIdentifier: nlaIdentifier]
	}
	
}
