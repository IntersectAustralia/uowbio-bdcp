import grails.util.Environment

import org.apache.log4j.net.SMTPAppender

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

//grails.urlcreator.cache.maxsize=0

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
	{ 	grails.config.locations = [ "file:/adminpkgs/tomcat/settings/${appName}/config.properties",
									"file:/adminpkgs/tomcat/settings/${appName}/${appVersion}/config.properties" ]

		grails.serverURL = grailsApplication.config.server.url 
	}
	development
	{ grails.serverURL = "http://localhost:8080/${appName}" }
	test
	{ grails.serverURL = "http://localhost:8080/${appName}" }
	intersect_test
	{ grails.serverURL = "http://www.changeme.com" }
	intersect_demo
	{ grails.serverURL = "http://www.changeme.com" }
	cucumber
	{ grails.serverURL = "http://localhost:8080/${appName}" }
}

bdcp.log.dir=""

switch(Environment.current) {
	case Environment.DEVELOPMENT:
		bdcp.log.dir="/tmp/biomech.log"
		break
	case Environment.TEST:
		bdcp.log.dir="/adminpkgs/tomcat/tomcat/logs/biomechanics/biomech.log"
		break
	case Environment.PRODUCTION:
		bdcp.log.dir="/adminpkgs/tomcat/tomcat/logs/biomechanics/biomech.log"
		break
}

// log4j configuration
log4j = {
	// Example of changing the log pattern for the default console
	// appender:
	//
	// Example of changing the log pattern for the default console
	// appender:
	//
	
		
	appenders {
		console name:'stdout',
		layout:pattern(conversionPattern: '%c{2} %m%n')
		appender new org.apache.log4j.DailyRollingFileAppender(name: 'rollingFileAppender', datePattern: "'.'yyyy-MM-dd'.log'", layout: pattern(conversionPattern: '%-5p %d{dd/MMM/yyyy:HH:mm:ss,SSS} [%t] %c - %m%n'), file: bdcp.log.dir)
	}
	
	root {
		info 'stdout', 'rollingFileAppender'
		additivity = true
	}
		
	info        'org.hibernate',
				'net.sf.ehcache.hibernate'
				'org.codehaus.groovy.grails.orm.hibernate' // hibernate integration


	//debug  'org.codehaus.groovy.grails.plugins.springsecurity'
	debug  'au.org.intersect.bdcp'
	
	warn  'org.codehaus.groovy.grails.web.servlet',  //  controllers
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

bdcp.files.root=""

switch(Environment.current) {
	case Environment.DEVELOPMENT:
		images.location = "web-app/images/"
		bdcp.dev.root = "/tmp/"
		bdcp.files.root = bdcp.dev.root
		forms.deviceManuals.location = bdcp.dev.root + "files/forms/deviceManuals/"
		forms.location = bdcp.dev.root + "files/forms/"
		files.session.location = bdcp.dev.root + "files/sessions/"
		files.rifcs.location = bdcp.dev.root + "files/rifcs/"
		files.analysed.location = bdcp.dev.root + "files/analysed/"
		tmp.location = System.getProperty("java.io.tmpdir")
		break
	case Environment.TEST:
		images.location = "web-app/images/"
		bdcp.test.root = "/adminpkgs/tomcat/tomcat/biomech_files/"
		bdcp.files.root = bdcp.test.root
		forms.deviceManuals.location = bdcp.test.root + "files/forms/deviceManuals/"
		forms.location = bdcp.test.root + "files/forms/"
		files.session.location = bdcp.test.root + "files/sessions/"
		files.rifcs.location = bdcp.test.root + "files/rifcs/"
		files.analysed.location = bdcp.test.root + "files/analysed/"
		tmp.location = System.getProperty("java.io.tmpdir")
		break
	case Environment.PRODUCTION:
		images.location = "web-app/images/"
		bdcp.prod.root = "/adminpkgs/biomech_data/"
		bdcp.files.root = bdcp.prod.root
		forms.deviceManuals.location = bdcp.prod.root + "files/forms/deviceManuals/"
		forms.location = bdcp.prod.root + "files/forms/"
		files.session.location = bdcp.prod.root + "files/sessions/"
		files.rifcs.location = bdcp.prod.root + "files/rifcs/"
		files.analysed.location = bdcp.prod.root + "files/analysed/"
		tmp.location = System.getProperty("java.io.tmpdir")
		break
}


environments {
	development {
		grails.mail.port = com.icegreen.greenmail.util.ServerSetupTest.SMTP.port
		grails.mail.host = "localhost"
		
		ldapServers {
			d1 {
				base = "dc=biomechanics, dc=local"
				port = 10400
				indexed = ["objectClass", "uid", "mail"]
                password = "secret"
			}
		}
		
	}
	
	test {
		grails.mail.host = "smtp.uow.edu.au"
		
		ldapServers {
			d1 {
				base = "dc=biomechanics, dc=local"
				port = 10400
				indexed = ["objectClass", "uid", "mail"]
			}
		}
		
	}
	
	cucumber {
		grails.mail.port = com.icegreen.greenmail.util.ServerSetupTest.SMTP.port
		grails.mail.host = "localhost"
		
	}
	
	production {
		grails.mail.host = "smtp.uow.edu.au"
	}
	
	intersect_test {
		grails.mail.host = "localhost"
	}

	intersect_demo {
		grails.mail.host = "localhost"
	}

}

// Spring security LDAP settings
environments {
	production {
		// Spring security LDAP settings
		grails.plugins.springsecurity.ldap.context.server = 'ldap://ldap-ids.uow.edu.au:389'
		grails.plugins.springsecurity.ldap.context.managerDn = 'uid=biomechuserldap,ou=Special Users,dc=uow,dc=edu,dc=au'
		grails.plugins.springsecurity.ldap.context.managerPassword = '*********'
		grails.plugins.springsecurity.ldap.authorities.groupSearchBase ='ou=People,dc=uow,dc=edu,dc=au'
		grails.plugins.springsecurity.ldap.authorities.retrieveDatabaseRoles = true
		grails.plugins.springsecurity.ldap.authorities.ignorePartialResultException= true
		grails.plugins.springsecurity.ldap.search.base = 'ou=People,dc=uow,dc=edu,dc=au'
		grails.plugins.springsecurity.ldap.search.filter = '(uid={0})'
		grails.plugins.springsecurity.ldap.search.subtree = true
		grails.plugins.springsecurity.providerNames = ['myLdapAuthenticationProvider', 'daoAuthenticationProvider']
		
		uow.ldapids.server = 'ldap://ldap-ids.uow.edu.au:389'
		uow.ldapids.groupSearchBase = "ou=People,dc=uow,dc=edu,dc=au"
		uow.ldapids.managerDn = "uid=biomechuserldap,ou=Special Users,dc=uow,dc=edu,dc=au"
		uow.ldapids.managerPassword = "*********"
		
	}
	
	development {
		ldapServers {
			d1 {
				base = "dc=biomechanics, dc=local"
				port = 10400
				indexed = ["objectClass", "uid", "mail"]
			}
		}
		// Spring security LDAP settings
		grails.plugins.springsecurity.ldap.context.server = 'ldap://localhost:10400'
		grails.plugins.springsecurity.ldap.context.managerDn = "uid=admin,ou=system"
		grails.plugins.springsecurity.ldap.context.managerPassword = "secret"
		grails.plugins.springsecurity.ldap.authorities.groupSearchBase ="ou=people,dc=biomechanics, dc=local"
		grails.plugins.springsecurity.ldap.authorities.retrieveDatabaseRoles = true
		grails.plugins.springsecurity.ldap.authorities.ignorePartialResultException= true
		grails.plugins.springsecurity.ldap.search.base = "ou=people,dc=biomechanics,dc=local"
		grails.plugins.springsecurity.ldap.search.filter = '(uid={0})'
		grails.plugins.springsecurity.ldap.search.subtree = true
		grails.plugins.springsecurity.providerNames = ['myLdapAuthenticationProvider', 'daoAuthenticationProvider']
		
		uow.ldapids.server = 'ldap://localhost:10400'
		uow.ldapids.groupSearchBase = "ou=people,dc=biomechanics, dc=local"
		uow.ldapids.managerDn = "uid=admin,ou=system"
		uow.ldapids.managerPassword = "secret"
		
	}
	
	test {
		// Spring security LDAP settings
		grails.plugins.springsecurity.ldap.context.server = 'ldap://ldap-ids.uow.edu.au:389'
		grails.plugins.springsecurity.ldap.context.managerDn = 'uid=biomechuserldap,ou=Special Users,dc=uow,dc=edu,dc=au'
		grails.plugins.springsecurity.ldap.context.managerPassword = '*********'
		grails.plugins.springsecurity.ldap.authorities.groupSearchBase ='ou=People,dc=uow,dc=edu,dc=au'
		grails.plugins.springsecurity.ldap.authorities.retrieveDatabaseRoles = true
		grails.plugins.springsecurity.ldap.authorities.ignorePartialResultException= true
		grails.plugins.springsecurity.ldap.search.base = 'ou=People,dc=uow,dc=edu,dc=au'
		grails.plugins.springsecurity.ldap.search.filter = '(uid={0})'
		grails.plugins.springsecurity.ldap.search.subtree = true
		grails.plugins.springsecurity.providerNames = ['myLdapAuthenticationProvider', 'daoAuthenticationProvider']
		
		uow.ldapids.server = 'ldap://ldap-ids.uow.edu.au:389'
		uow.ldapids.groupSearchBase = "ou=People,dc=uow,dc=edu,dc=au"
		uow.ldapids.managerDn = "uid=biomechuserldap,ou=Special Users,dc=uow,dc=edu,dc=au"
		uow.ldapids.managerPassword = "*********"
	
	
	cucumber {
		ldapServers {
			d1 {
				base = "dc=biomechanics, dc=local"
				port = 10400
				indexed = ["objectClass", "uid", "mail"]
			}
		}
		// Spring security LDAP settings
		grails.plugins.springsecurity.ldap.context.server = 'ldap://localhost:10400'
		grails.plugins.springsecurity.ldap.context.managerDn = "uid=admin,ou=system"
		grails.plugins.springsecurity.ldap.context.managerPassword = "secret"
		grails.plugins.springsecurity.ldap.authorities.groupSearchBase ="ou=people,dc=biomechanics, dc=local"
		grails.plugins.springsecurity.ldap.authorities.retrieveDatabaseRoles = true
		grails.plugins.springsecurity.ldap.authorities.ignorePartialResultException= true
		grails.plugins.springsecurity.ldap.search.base = "ou=people,dc=biomechanics, dc=local"
		grails.plugins.springsecurity.ldap.search.filter = '(uid={0})'
		grails.plugins.springsecurity.ldap.search.subtree = true
		grails.plugins.springsecurity.providerNames = ['myLdapAuthenticationProvider', 'daoAuthenticationProvider']
		grails.plugins.springsecurity.ldap.context.anonymousReadOnly = true
		
		uow.ldapids.server = 'ldap://localhost:10400'
		uow.ldapids.groupSearchBase = "ou=people,dc=biomechanics, dc=local"
		uow.ldapids.managerDn = "uid=admin,ou=system"
		uow.ldapids.managerPassword = "*********"
		
	}
	
	intersect_test {
		
		// Spring security LDAP settings
		grails.plugins.springsecurity.ldap.context.server = "ldap://gsw1-int-ldaptest-vm.intersect.org.au:389"
		grails.plugins.springsecurity.ldap.context.managerDn = "uid=chrisk,ou=people,dc=biomechanics, dc=local"
		grails.plugins.springsecurity.ldap.context.managerPassword = "********"
		grails.plugins.springsecurity.ldap.authorities.groupSearchBase ="ou=people,dc=biomechanics, dc=local"
		grails.plugins.springsecurity.ldap.authorities.retrieveDatabaseRoles = true
		grails.plugins.springsecurity.ldap.authorities.ignorePartialResultException= true
		grails.plugins.springsecurity.ldap.search.base = "ou=people,dc=biomechanics, dc=local"
		grails.plugins.springsecurity.ldap.search.filter = '(uid={0})'
		grails.plugins.springsecurity.ldap.context.anonymousReadOnly = true
		grails.plugins.springsecurity.providerNames = ['myLdapAuthenticationProvider']
	}

	intersect_demo {
		
		// Spring security LDAP settings
		grails.plugins.springsecurity.ldap.context.server = "ldap://gsw1-int-ldaptest-vm.intersect.org.au:389"
		grails.plugins.springsecurity.ldap.context.managerDn = "uid=chrisk,ou=people,dc=biomechanics, dc=local"
		grails.plugins.springsecurity.ldap.context.managerPassword = "********"
		grails.plugins.springsecurity.ldap.authorities.groupSearchBase ="ou=people,dc=bfiles/sessions/iomechanics, dc=local"
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
					password = "********"
					searchControls
					{
						searchScope = "subtree"
					}
				}
			}

		}
	}
	
	intersect_demo
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
					password = "********"
					searchControls
					{
						searchScope = "subtree"
					}
				}
			}

		}
	}
}


// Added by the Spring Security Core plugin:
//grails.plugins.springsecurity.userLookup.userDomainClassName = 'au.org.intersect.bdcp.SecUser'
//grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'au.org.intersect.bdcp.SecUserSecRole'
grails.plugins.springsecurity.authority.className = 'au.org.intersect.bdcp.SecRole'
//grails.plugins.springsecurity.controllerAnnotations.staticRules = [
//	'/js/**':      ['IS_AUTHENTICATED_ANONYMOUSLY'],
//	'/css/**':     ['IS_AUTHENTICATED_ANONYMOUSLY'],
//	'/images/**':  ['IS_AUTHENTICATED_ANONYMOUSLY'],
//	'/**':         ['IS_AUTHENTICATED_REMEMBERED']
// ]

//Custom grails messages for spring security
/** error messages */
grails.plugins.springsecurity.errors.login.disabled = "Sorry, your account is disabled."
grails.plugins.springsecurity.errors.login.expired = "Sorry, your account has expired."
grails.plugins.springsecurity.errors.login.passwordExpired = "Sorry, your password has expired."
grails.plugins.springsecurity.errors.login.locked = "Sorry, your account is locked."
grails.plugins.springsecurity.errors.login.fail = "Incorrect password or userid. Please enter your UOW userid and password again or call ITS for assistance."



// Added by the Joda-Time plugin:
grails.gorm.default.mapping = {
	"user-type" type: org.joda.time.contrib.hibernate.PersistentDateTime, class: org.joda.time.DateTime
	"user-type" type: org.joda.time.contrib.hibernate.PersistentDuration, class: org.joda.time.Duration
	"user-type" type: org.joda.time.contrib.hibernate.PersistentInstant, class: org.joda.time.Instant
	"user-type" type: org.joda.time.contrib.hibernate.PersistentInterval, class: org.joda.time.Interval
	"user-type" type: org.joda.time.contrib.hibernate.PersistentLocalDate, class: org.joda.time.LocalDate
	"user-type" type: org.joda.time.contrib.hibernate.PersistentLocalTimeAsString, class: org.joda.time.LocalTime
	"user-type" type: org.joda.time.contrib.hibernate.PersistentLocalDateTime, class: org.joda.time.LocalDateTime
	"user-type" type: org.joda.time.contrib.hibernate.PersistentPeriod, class: org.joda.time.Period
}



// Uncomment and edit the following lines to start using Grails encoding & escaping improvements

/* remove this line 
// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside null
                scriptlet = 'none' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        filteringCodecForContentType {
            //'text/html' = 'html'
        }
    }
}
remove this line */
}