package au.org.intersect.bdcp.constraints

import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler
import org.hibernate.Criteria
import org.hibernate.FlushMode
import org.hibernate.Session
import org.hibernate.criterion.Restrictions
import org.springframework.orm.hibernate3.HibernateCallback

class TrulyUniqueConstraint
{
    static name = "trulyunique"
    static defaultMessageCode = "default.not.trulyunique.message"

    def supports = { type ->
        return type!= null && String.class.isAssignableFrom(type);
    }

    static persistent = true

    def dbCall = { propertyValue, Session session -> 
        session.setFlushMode(FlushMode.MANUAL);
        
        try {
            boolean shouldValidate = true;
            if(propertyValue != null && DomainClassArtefactHandler.isDomainClass(propertyValue.getClass())) {
                shouldValidate = session.contains(propertyValue)
            }
            if(shouldValidate) {
                Criteria criteria = session.createCriteria( constraintOwningClass )
                        .add(Restrictions.eq( constraintPropertyName, propertyValue ).ignoreCase())
                return criteria.list()
            } else {
                return null
            }
        } finally {
            session.setFlushMode(FlushMode.AUTO)
        }
    }

    def validate = { propertyValue -> 
        dbCall.delegate = delegate
        def _v = dbCall.curry(propertyValue) as HibernateCallback
        def result = hibernateTemplate.executeFind(_v)

        return result ? false : true    // If we find a result, then non-unique
    }



}
