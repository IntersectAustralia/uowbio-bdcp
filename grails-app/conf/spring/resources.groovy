

// Place your Spring DSL code here
beans = {
	
	contextSource(org.springframework.ldap.core.support.LdapContextSource){
			url="ldap://localhost:10400"
			base="ou=people,dc=biomechanics, dc=local"
			userDn="uid=admin,ou=system"
			password="secret"
		}
	
		myLdapAuthenticator(au.org.intersect.bdcp.ldap.MyLdapAuthenticator, ref("contextSource")) {
			userDnPatterns = ['uid={0}']
		}

	// this overrides the default Authentication Provider with our authenticator and our user details service
	myLdapAuthenticationProvider(org.springframework.security.ldap.authentication.LdapAuthenticationProvider,
	   ref("myLdapAuthenticator"))
	
}
