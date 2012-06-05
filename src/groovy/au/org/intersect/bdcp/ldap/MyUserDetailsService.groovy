package au.org.intersect.bdcp.ldap;

import au.org.intersect.bdcp.UserStore

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.codehaus.groovy.grails.plugins.springsecurity.GormUserDetailsService

public class MyUserDetailsService extends GormUserDetailsService  {

    UserDetails loadUserByUsername(String username) {
        def user = UserStore.findByUsername(username)
        if (user == null) {
            throw new UsernameNotFoundException("${username} not found")
        }
        if (user.password == null || user.password == '') {
            // LDAP user, don't return it
            throw new UsernameNotFoundException("${username} not found")
        }
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        if (user.authority != null) {
             authorities.add(new GrantedAuthorityImpl(user.authority.toString()));
        }
        return new User(user.username, user.password, true, true, true, !user.deactivated, authorities)
    }

}
