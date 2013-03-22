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

		def username = !params.isExternal ? params.username : params.email
		
		def user = new UserStore(username: username, firstName: params.firstName, surname: params.surname, authority: params.authority, nlaIdentifier: params.nlaIdentifier, 
                                         title: params.title, email: params.email, password: params.password);

		if (user.validate())
		{
			render (view: "create", model:[userid:user.username, isExternal:params.isExternal, firstName: user.firstName, surname: user.surname, authority:user.authority,
                                nlaIdentifier:user.nlaIdentifier, title:user.title, email: user.email, password: params.password, username:username])
		}
		else
		{
			render (view: "addRole", model:[userInstance:user, userid:user.username, firstName: user.firstName, surname: user.surname, authority:user.authority, password:params.password,
                                isExternal: params.isExternal, email:user.email, nlaIdentifier:user.nlaIdentifier, title:user.title, username:user.username, formErrors:user.errors])
		}
       
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def save =
	{
		cache false
		def accountStatus = "Failed"
		def user
		def email
                def ok = true
                def msg = null
		
		if (!params.isExternal)
		{
			LdapUser match = LdapUser.find(
					filter: "(uid=${params.userid})")
			if (match !=  null)
			{
				email = match.mail
				user= new UserStore(username: params.userid, authority: params.authority, nlaIdentifier: params.nlaIdentifier, title: params.title, password: springSecurityService.encodePassword(params.password), enabled: true, deactivated: false)
			        if (!user.save(flush:true, failOnError:false))
                                {
                                    ok = false
                                    msg = "Error saving LDAP user data"
                                }
			}
                        else
                        {
                                
                                ok = false
                                msg = "User ${params.userid} not found in LDAP!"
                        }
		}
		else // external user
		{
			user = new UserStore(username: params.userid, authority: params.authority, nlaIdentifier: params.nlaIdentifier, title: params.title, email: params.email, surname: params.surname, firstName: params.firstName, password: springSecurityService.encodePassword(params.password), enabled: true, deactivated: false)
			if (!user.save(flush:true, failOnError:false))
                        {
                            ok = false;
                            msg = "Error saving external user data"
                        }
			email = params.email
                }
                if (ok)
                {
		        def secRole = SecRole.findByAuthority( params.authority )
		        def secUserSecRole = new SecUserSecRole(secUser: user, secRole: secRole)
		        if (!secUserSecRole.save(flush:true, failOnError:false))
                        {
                            ok = false;
                            msg = "Error saving role information ${secRole}"
                        }
                }
		 
		if (ok)
		{
			accountStatus = "Successful"
			emailNotifierService.contactUser(params.email ? "mailExternal" : "mail", user.username, params.password, email, user.authority.getName())
			render (view: "createStatus", model:[accountStatus: accountStatus, user: user ,userid:params.userid, msg: msg, role: params.authority])
		}
		else
		{
			accountStatus = "Failed"
			render (view: "createStatus", model:[accountStatus: accountStatus, user: user ,userid:params.userid, msg: msg, role: params.authority])
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
		render(view: "search", model: [matches: matches, searching:false])
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

		render (view: "search", model: [searching:true,firstName: params.firstName, surname:params.surname, userid:params.userid, matches: sortedMatches, flagDisplayCreateExternalUser: flagDisplayCreateExternalUser])
	}
	
	@Secured(['IS_AUTHENTICATED_FULLY', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def displayCreateExternalUser =
	{
		cache false
		render (view: "displayCreateExternalUser", model: [firstName: params.firstName, surname:params.surname, email:params.email, formErrors:noFormErrors()])
	}
	
	@Secured(['IS_AUTHENTICATED_FULLY', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def createExternalUser =
	{
		cache false
		def user = new UserStore(username: params.email, title: 'NO', email: params.email, surname: params.surname, firstName: params.firstName, authority:UserRole.ROLE_RESEARCHER)
	        user.validate()
		if (params.password != params.password_2)
                {
                    // The following helps with field highlighting in your view
                    user.errors.rejectValue( 'password', 'userStore.password.doesnotmatch')
                }
		if (params.password == null || params.password.trim().length() == 0)
                {
                    // The following helps with field highlighting in your view
                    user.errors.rejectValue( 'password', 'userStore.password.blank')
                } else
		if (!goodPassword(params.password))
                {
                    // The following helps with field highlighting in your view
                    user.errors.rejectValue( 'password', 'userStore.password.weak')
                }
		if (params.firstName == null || params.firstName.trim().length() == 0)
                {
                    // The following helps with field highlighting in your view
                    user.errors.rejectValue( 'firstName', 'userStore.firstName.blank')
                }
		if (params.surname == null || params.surname.trim().length() == 0)
                {
                    // The following helps with field highlighting in your view
                    user.errors.rejectValue( 'surname', 'userStore.surname.blank')
                }
		if (params.email == null || !(params.email.toLowerCase() ==~ /^[_.a-z0-9%-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)+$/))
                {
                    // The following helps with field highlighting in your view
                    user.errors.rejectValue( 'email', 'userStore.email.invalid')
                }
		
		if (!user.hasErrors())
		{
			session.firstName = params.firstName
			session.surname = params.surname
			session.email = params.email
                        session.password = params.password


			render (view: "addRole", model: [isExternal:params.isExternal, username: params.email, firstName: params.firstName, surname: params.surname, 
                                email: params.email, password: params.password, formErrors:noFormErrors()])
		}
		else
		{
			render (view: "displayCreateExternalUser", model:[firstName:params.firstName, surname:params.surname, email:params.email, formErrors:user.errors])
		}
	}
	

	@Secured(['IS_AUTHENTICATED_FULLY', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def addRole =
	{
		cache false

		def userid = params.userid != null ? params.userid : params.email
		return [username: userid, firstName: params.firstName, surname: params.surname, email: params.email, password: params.password, role: params.authority, 
                        nlaIdentifier: params.nlaIdentifier, isExternal: params.isExternal, formErrors:noFormErrors()]
	}

        def noFormErrors =
        {
	        def noErrors = [hasErrors: { -> false}, hasFieldErrors: { String p -> false }]	
                return noErrors
        }

        def goodPassword =
        { password ->
                // at least two letters and two digits
                return (password =~ /[a-zA-Z].*[a-zA-Z]/) && (password =~ /[0-9].*[0-9]/)
        }

}
