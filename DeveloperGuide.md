# Developer setup #

## Prerequisites ##

  * Java (on Ubuntu install the sun-java6-jdk package)
  * Groovy (for Ubuntu there is a deb [here](http://groovy.codehaus.org/Download))
  * Grails (get a zip from [here](http://grails.org/Download) or download the version of STS w/ the latest grails)
  * STS (get from [here](http://www.springsource.com/downloads/sts) then follow the IDE integration steps [here](http://grails.org/STS+Integration))
  * git (for Ubuntu install the git package)

## Check-out & build ##

  * Check-out the code:
```
git clone https://intersect.engineering.team@code.google.com/p/uowbio-bdcp/
```
  * Upgrade the app:
```
pc$ cd uowbio-bdcp
pc$ grails upgrade
```
(ignore the warning about versions.)
  * Install plugins required, make sure you use the latest in lib/ folder in the project
```
ps$ grails install-plugin lib/grails-greenmail-<LATEST>.zip
ps$ grails install-plugin lib/grails-ldap-server-<LATEST>.zip
```
> Replace _LATEST_ with the version provided in source code.
  * Import project into STS
  * Run (using grails run-app or from within STS)

## Troubleshooting ##

  * If _grails run-app_ complains about not finding the native2ascii.Main class, make sure JAVA\_HOME is pointing to a JDK
  * If _grails run-app_ complains about not being able to open the servlet context, you need to run _grails upgrade_

# Cucumber setup and running #

The software uses Cucumber as _integration test tool_. The following describes how to get those test working.

## Once-only Setup ##

  1. Make sure you have Maven installed
  1. Make sure you have Postgres installed
  1. Create a bdcp user and test database (when running a blank postgres installation, it usually creates a 'postgres' user which is the one used to access postgresql commands, no password initially)

```
$ sudo -u postgres createuser -P grails
(provide 'grails' as password)
$ sudo -u postgres createdb bdcp-test
```

## To Run The Features ##

  1. Run the app in **cucumber** mode:
```
grails -Dgrails.env=cucumber run-app
```
  1. In separate terminal window, run the features (the parameter \-Dcucumber.installGems=true is only required first time)
```
mvn -Dcucumber.installGems=true integration-test
```
  1. If you want to run a single feature
```
mvn cuke4duke:cucumber -Dcucumber.features=features/edit_device_metadata_template_static.feature
```

## Step Definitions ##

web\_steps.groovy contains a few generic methods for doing things on web pages. So far it includes filling in fields, clicking buttons and checking table contents. This is equivalent to the web\_steps.rb file we get from Capybara in Rails. Unfortunately there's no such pre-baked steps for us in Grails so we have to construct our own. We can look at the Capybara Rails version for some inspiration on good naming conventions.  Application-specific steps should be placed in their own file.