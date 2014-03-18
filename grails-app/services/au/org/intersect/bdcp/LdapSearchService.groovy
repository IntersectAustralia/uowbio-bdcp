package au.org.intersect.bdcp

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.beans.factory.InitializingBean
import org.springframework.ldap.filter.AndFilter
import org.springframework.ldap.filter.EqualsFilter

import au.org.intersect.bdcp.LdapUOWUserDetails
import org.springframework.ldap.core.support.LdapContextSource
import javax.naming.directory.SearchControls;

/**
* A class provides ldap search service on ldap uow and ldap-ids uow 
 * @author qun
*
*/
class LdapSearchService {
	
	def grailsApplication
	LdapContextSource ldapIdsUOW
	

	private void initial()
	{

		//ldap-ids server
		ldapIdsUOW =new LdapContextSource()
		
		def config= grailsApplication.config
		def ldapSetting=config.uow.ldapids
		
		log.error ldapSetting.server
		
		ldapIdsUOW.setUrl(ldapSetting.server);
		ldapIdsUOW.setBase(ldapSetting.groupSearchBase);
		ldapIdsUOW.setUserDn(ldapSetting.managerDn)
		ldapIdsUOW.setPassword(ldapSetting.managerPassword)

		ldapIdsUOW.afterPropertiesSet();
				
	}

	def searchLdapIdsUOW(username){

		initial()
		def ldapTemplate = new org.springframework.ldap.core.LdapTemplate(ldapIdsUOW)
		def attrMapper = new org.springframework.ldap.core.AttributesMapper() {
					public Object mapFromAttributes(javax.naming.directory.Attributes attrs) throws javax.naming.NamingException {
						return LdapUOWUserDetails.build(attrs)
					}
				}
		AndFilter filter=new AndFilter()
		filter.and(new EqualsFilter("objectclass","person")).and(new EqualsFilter("uid", username));

		def results = ldapTemplate.search("", filter.toString(),SearchControls.SUBTREE_SCOPE,attrMapper);
	}

    def findFirst(username) {
        def match = searchLdapIdsUOW(username)
        if (match.size() > 0)
            return match[0]
        else
            return null
    }

    def searchLdap(username,surname,firstname) {
        initial()
        def ldapTemplate = new org.springframework.ldap.core.LdapTemplate(ldapIdsUOW)
        def attrMapper = new org.springframework.ldap.core.AttributesMapper() {
            public Object mapFromAttributes(javax.naming.directory.Attributes attrs) throws javax.naming.NamingException {
                return LdapUOWUserDetails.build(attrs)
            }
        }
        AndFilter filter=new AndFilter()
        filter.and(new EqualsFilter("objectclass","person"))
        if (username) filter.and(new EqualsFilter("uid", username));
        if (surname) filter.and(new EqualsFilter("sn", surname));
        if (firstname) filter.and(new EqualsFilter("givenName", firstname));

        def results = ldapTemplate.search("", filter.toString(),SearchControls.SUBTREE_SCOPE,attrMapper);

    }
}

