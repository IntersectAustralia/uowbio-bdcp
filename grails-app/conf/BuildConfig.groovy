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

        mavenLocal()
        mavenCentral()
		
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
		//compile 'org.springframework.ldap:spring-ldap:1.3.0.RELEASE'
		//compile 'org.codehaus:gldapo:0.8.5'
	}
    
    plugins{
        //runtime ":greenmail:1.3.4"
        compile ":ajax-uploader:1.1"
        compile ":bubbling:2.1.4"
        compile ":cache-headers:1.1.5"
        compile ":ckeditor:3.5.4.1"
        //compile ":code-coverage:1.2.4"
        compile ":console:1.2"
        compile ":constraints:0.8.0"
        //compile ":eclipse-scripts:1.0.5"
        compile ":hibernate:2.1.1"
        compile ":joda-time:1.4"
        compile ":jquery:1.6.1.1"
        compile ":jquery-ui:1.8.11"
        compile ":js-tree:0.2"
        compile ":ldap-server:0.1.8"
        compile ":mail:1.0.1"
        compile ":richui:0.8"
        compile ":spring-security-core:1.2.7.3"
        compile ":spring-security-ldap:1.0.6"
        build ":tomcat:2.1.1"
        compile ":yui:2.8.2.1"
    }
	
	
	

}


