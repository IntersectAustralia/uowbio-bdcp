grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.war.file = "target/${appName}.war"
grails.project.dependency.resolution = {
	// inherit Grails' default dependencies
	inherits("global") {
		// uncomment to disable ehcache
		// excludes 'ehcache'
	}
	log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
	repositories {
		grailsPlugins()
		grailsHome()
		grailsCentral()
		ebr()
		// uncomment the below to enable remote dependency resolution
		// from public Maven repositories
		//mavenLocal()
		mavenCentral()
		//mavenRepo "http://snapshots.repository.codehaus.org"
		mavenRepo "http://repository.codehaus.org"
		mavenRepo "http://download.java.net/maven/2/"
		mavenRepo "http://repository.jboss.com/maven2/"
		mavenRepo "http://repo.desirableobjects.co.uk/"
	}
	dependencies
	{
		// specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
		
		
		// runtime 'mysql:mysql-connector-java:5.1.13'
		compile("joda-time:joda-time-hibernate:1.3") {
			excludes "joda-time", "hibernate"
		}
		compile 'org.springframework.ldap:spring-ldap:1.3.0.RELEASE'
		compile 'org.codehaus:gldapo:0.8.5'
	}
    
    plugins{
        //runtime ":greenmail:1.3.4"
		compile ":ajax-uploader:1.1"
    }
	
	
	grails.war.resources = { stagingDir ->
		// we need to do this to fix the ldap / gldap issue with the plugin
		delete(file:"${stagingDir}/WEB-INF/lib/spring-ldap-1.2.1.jar")
		delete(file:"${stagingDir}/WEB-INF/lib/gldapo-0.8.2.jar")
		// and remove the tomcat 7 libs because we are using tomcat 6
		delete(file:"${stagingDir}/WEB-INF/lib/tomcat-catalina-7.0.30.jar")
		delete(file:"${stagingDir}/WEB-INF/lib/tomcat-catalina-ant-7.0.30.jar")
		delete(file:"${stagingDir}/WEB-INF/lib/tomcat-coyote-7.0.30.jar")
		delete(file:"${stagingDir}/WEB-INF/lib/tomcat-embed-core-7.0.30.jar")
		delete(file:"${stagingDir}/WEB-INF/lib/tomcat-embed-jasper-7.0.30.jar")
		delete(file:"${stagingDir}/WEB-INF/lib/tomcat-embed-logging-juli-7.0.30.jar")
		delete(file:"${stagingDir}/WEB-INF/lib/tomcat-embed-logging-log4j-7.0.30.jar")
		
		
	  }
	

}


