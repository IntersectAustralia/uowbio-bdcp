// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.views.javascript.library="jquery"
grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: [
		'text/html',
		'application/xhtml+xml'
	],
	xml: [
		'text/xml',
		'application/xml'
	],
	text: 'text/plain',
	js: 'text/javascript',
	rss: 'application/rss+xml',
	atom: 'application/atom+xml',
	css: 'text/css',
	csv: 'text/csv',
	all: '*/*',
	json: [
		'application/json',
		'text/json'
	],
	form: 'application/x-www-form-urlencoded',
	multipartForm: 'multipart/form-data'
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// set per-environment serverURL stem for creating absolute links
environments {
	production
	{ grails.serverURL = "http://www.changeme.com" }
	development
	{ grails.serverURL = "http://localhost:8080/${appName}" }
	test
	{ grails.serverURL = "http://localhost:8080/${appName}" }
	intersect_test
	{ grails.serverURL = "http://www.changeme.com" }
}

// log4j configuration
log4j = {
	// Example of changing the log pattern for the default console
	// appender:
	//
	//appenders {
	//    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
	//}

	//debug  'org.codehaus.groovy.grails.plugins.springsecurity'
	//debug  'au.org.intersect.bdcp.ldap'
	error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
			'org.codehaus.groovy.grails.web.pages', //  GSP
			'org.codehaus.groovy.grails.web.sitemesh', //  layouts
			'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
			'org.codehaus.groovy.grails.web.mapping', // URL mapping
			'org.codehaus.groovy.grails.commons', // core / classloading
			'org.codehaus.groovy.grails.plugins', // plugins
			'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
			'org.springframework',
			'org.hibernate',
			'net.sf.ehcache.hibernate'

	warn   'org.mortbay.log'
}
images.location = "web-app/images/"

ldapServers {
	d1 {
		base = "dc=biomechanics, dc=local"
		port = 10400
		indexed = ["objectClass", "uid", "mail"]
	}

}

environments {
	development {
		grails.mail.port = com.icegreen.greenmail.util.ServerSetupTest.SMTP.port
		grails.mail.host = "localhost"
	}
	
	test {
		grails.mail.port = com.icegreen.greenmail.util.ServerSetupTest.SMTP.port
		grails.mail.host = "localhost"
		
	}
	
	production {
		grails.mail.host = "smtp.uow.edu.au"
	}
	
	intersect_test {
		grails.mail.host = "localhost"
	}
}

// Spring security LDAP settings
environments {
	production {
		// Spring security LDAP settings
		grails.plugins.springsecurity.ldap.context.server = 'ldap://ldap.uow.edu.au:389'
		grails.plugins.springsecurity.ldap.context.managerDn = 'ou=People,o=University of Wollongong,c=au'
		grails.plugins.springsecurity.ldap.context.managerPassword = ''
		grails.plugins.springsecurity.ldap.authorities.groupSearchBase ='ou=People,o=University of Wollongong,c=au'
		grails.plugins.springsecurity.ldap.authorities.retrieveDatabaseRoles = true
		grails.plugins.springsecurity.ldap.search.base = 'ou=People,o=University of Wollongong,c=au'
		grails.plugins.springsecurity.ldap.search.filter = '(uid={0})'
		grails.plugins.springsecurity.providerNames = ['myLdapAuthenticationProvider']
	}
	
	development {
		
		// Spring security LDAP settings
		grails.plugins.springsecurity.ldap.context.server = 'ldap://localhost:10400'
		grails.plugins.springsecurity.ldap.context.managerDn = "uid=admin,ou=system"
		grails.plugins.springsecurity.ldap.context.managerPassword = "secret"
		grails.plugins.springsecurity.ldap.authorities.groupSearchBase ="ou=people,dc=biomechanics, dc=local"
		grails.plugins.springsecurity.ldap.authorities.retrieveDatabaseRoles = true
		grails.plugins.springsecurity.ldap.authorities.ignorePartialResultException= true
		grails.plugins.springsecurity.ldap.search.base = "ou=people,dc=biomechanics,dc=local"
		grails.plugins.springsecurity.ldap.search.filter = '(uid={0})'
		grails.plugins.springsecurity.providerNames = ['myLdapAuthenticationProvider']
	}
	
	test {
		// Spring security LDAP settings
		grails.plugins.springsecurity.ldap.context.server = 'ldap://localhost:10400'
		grails.plugins.springsecurity.ldap.context.managerDn = "uid=admin,ou=system"
		grails.plugins.springsecurity.ldap.context.managerPassword = "secret"
		grails.plugins.springsecurity.ldap.authorities.groupSearchBase ="ou=people,dc=biomechanics, dc=local"
		grails.plugins.springsecurity.ldap.authorities.retrieveDatabaseRoles = true
		grails.plugins.springsecurity.ldap.authorities.ignorePartialResultException= true
		grails.plugins.springsecurity.ldap.search.base = "ou=people,dc=biomechanics, dc=local"
		grails.plugins.springsecurity.ldap.search.filter = '(uid={0})'
		grails.plugins.springsecurity.ldap.context.anonymousReadOnly = true
		grails.plugins.springsecurity.providerNames = ['myLdapAuthenticationProvider']
	}
	
	intersect_test {
		
		// Spring security LDAP settings
		grails.plugins.springsecurity.ldap.context.server = "ldap://gsw1-int-ldaptest-vm.intersect.org.au:389"
		grails.plugins.springsecurity.ldap.context.managerDn = "uid=chrisk,ou=people,dc=biomechanics, dc=local"
		grails.plugins.springsecurity.ldap.context.managerPassword = "password"
		grails.plugins.springsecurity.ldap.authorities.groupSearchBase ="ou=people,dc=biomechanics, dc=local"
		grails.plugins.springsecurity.ldap.authorities.retrieveDatabaseRoles = true
		grails.plugins.springsecurity.ldap.authorities.ignorePartialResultException= true
		grails.plugins.springsecurity.ldap.search.base = "ou=people,dc=biomechanics, dc=local"
		grails.plugins.springsecurity.ldap.search.filter = '(uid={0})'
		grails.plugins.springsecurity.ldap.context.anonymousReadOnly = true
		grails.plugins.springsecurity.providerNames = ['myLdapAuthenticationProvider']
	}
}


environments
{
	production
	{
		ldap
		{
			directories
			{
				user
				{
					defaultDirectory = true
					url = "ldap://ldap.uow.edu.au"
					port = 389
					base = "ou=People,o=University of Wollongong,c=au"
					searchControls
					{
						searchScope = "subtree"
					}
				}
			}

			schemas = [
				au.org.intersect.bdcp.ldap.LdapUser
			]
			
		}
	}
	intersect_test
	{
		ldap
		{

			directories
			{
				user
				{
					defaultDirectory = true
					url = "ldap://gsw1-int-ldaptest-vm.intersect.org.au:389"
					base = "ou=people,dc=biomechanics, dc=local"
					userDn = "uid=chrisk,ou=people,dc=biomechanics, dc=local"
					password = "password"
					searchControls
					{
						searchScope = "subtree"
					}
				}
			}

			schemas = [
				au.org.intersect.bdcp.ldap.LdapUser
			]
		}
	}
	
	development
	{
		ldap
		{

			directories
			{
				d1
				{
					defaultDirectory = true
					url = "ldap://localhost:10400"
					base = "ou=people,dc=biomechanics, dc=local"
					userDn = "uid=admin,ou=system"
					password = "secret"
				}
			}

			schemas = [
				au.org.intersect.bdcp.ldap.LdapUser
			]
		}
	}
	
	test
	{
		ldap
		{

			directories
			{
				d1
				{
					defaultDirectory = true
					url = "ldap://localhost:10400"
					base = "ou=people,dc=biomechanics, dc=local"
					userDn = "uid=admin,ou=system"
					password = "secret"
				}
			}

			schemas = [
				au.org.intersect.bdcp.ldap.LdapUser
			]
		}
	}
}


// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'au.org.intersect.bdcp.SecUser'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'au.org.intersect.bdcp.SecUserSecRole'
grails.plugins.springsecurity.authority.className = 'au.org.intersect.bdcp.SecRole'

//Custom grails messages for spring security
/** error messages */
grails.plugins.springsecurity.errors.login.disabled = "Sorry, your account is disabled."
grails.plugins.springsecurity.errors.login.expired = "Sorry, your account has expired."
grails.plugins.springsecurity.errors.login.passwordExpired = "Sorry, your password has expired."
grails.plugins.springsecurity.errors.login.locked = "Sorry, your account is locked."
grails.plugins.springsecurity.errors.login.fail = "Incorrect password or userid. Please enter your UOW userid and password again or call ITS for assistance."


