

// Place your Spring DSL code here

import grails.util.*
beans = {
	switch(GrailsUtil.environment)
	{
		case "development":

			contextSource(org.springframework.ldap.core.support.LdapContextSource)
			{
				url="ldap://localhost:10400"
				base="ou=people,dc=biomechanics, dc=local"
				userDn="uid=admin,ou=system"
				password="secret"
			}
			break
		case "production":
			contextSource(org.springframework.ldap.core.support.LdapContextSource)
			{
				url="ldap://localhost:10400"
				base="ou=people,dc=biomechanics, dc=local"
				userDn="uid=admin,ou=system"
				password="secret"
			}
			break
	}
	myLdapAuthenticator(au.org.intersect.bdcp.ldap.MyLdapAuthenticator, ref("contextSource"))
	{ userDnPatterns = ['uid={0}'] }

	// this overrides the default Authentication Provider with our authenticator and our user details service
	myLdapAuthenticationProvider(org.springframework.security.ldap.authentication.LdapAuthenticationProvider,
			ref("myLdapAuthenticator"))

}
