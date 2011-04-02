

// Place your Spring DSL code here

import grails.util.*
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
beans = {
	def conf = SpringSecurityUtils.securityConfig
	
	contextSource(org.springframework.ldap.core.support.LdapContextSource) {
		url = conf.ldap.context.server
		userDn = conf.ldap.context.managerDn
		password = conf.ldap.context.managerPassword
		}
	
		ldapUserSearch(org.springframework.security.ldap.search.FilterBasedLdapUserSearch,
			conf.ldap.search.base,conf.ldap.search.filter,ref("contextSource"))
				
	myLdapAuthenticator(au.org.intersect.bdcp.ldap.MyLdapAuthenticator, ref("contextSource"))
	{ userSearch = ref("ldapUserSearch") }

//	// this overrides the default Authentication Provider with our authenticator and our user details service
	myLdapAuthenticationProvider(org.springframework.security.ldap.authentication.LdapAuthenticationProvider,
			ref("myLdapAuthenticator")) 

}
