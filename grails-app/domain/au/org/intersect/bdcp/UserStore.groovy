 package au.org.intersect.bdcp

import java.util.Date;
import java.util.Set;

import au.org.intersect.bdcp.enums.UserRole

class UserStore {

        String firstName
        String surname
        String title
        String email
        boolean deactivated
        UserRole authority
        String nlaIdentifier // RIF-CS
        Boolean published // RIF-CS
        Date dateCreated
        Date lastUpdated
        
        // SecUser
        String username
        String password
        boolean enabled
        boolean accountExpired
        boolean accountLocked
        boolean passwordExpired

        
        static hasMany = [studyCollaborators: StudyCollaborator]
        
        static constraints = {
        username(blank:false, unique:true, size:1..255)
                firstName(nullable:true, size:1..255)
                surname(nullable:true, size:1..255)
                title(nullable:false, blank:false, size:2..20)
                email(nullable:true, size:6..255) // min email a@a.au
                authority(nullable:false, blank:false)
                nlaIdentifier(nullable:true, blank:false, maxSize:255)
                published(nullable:true)
                password(nullable:true,minSize:1)
        }
        
        String toString() {
                return "${username}"
        }

        static mapping = {
                password column: '`password`'
        }

        Set<SecRole> getAuthorities() {
                SecUserSecRole.findAllBySecUser(this).collect { it.secRole } as Set
        }
        
}
