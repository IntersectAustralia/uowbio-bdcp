package au.org.intersect.bdcp.ldap

import au.org.intersect.bdcp.UserStore

import java.util.Collection

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.springframework.ldap.core.DirContextOperations
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator

class MyLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator
{

    private static final Log logger = LogFactory.getLog(MyLdapAuthoritiesPopulator.class);
    
    @Override
    public Collection<GrantedAuthority> getGrantedAuthorities(DirContextOperations user, String username)
    {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        if (user == null) return authorities;

        def userStore = UserStore.findByUsername(username)

        if (userStore == null) return authorities;
        if (userStore.authority == null) return authorities;

        authorities.add(new GrantedAuthorityImpl(userStore.authority.toString()));
        return authorities;
    }

}
