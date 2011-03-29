package au.org.intersect.bdcp.ldap


import gldapo.schema.annotation.GldapoNamingAttribute
import gldapo.schema.annotation.GldapoSynonymFor

 
class LdapUser{
	@GldapoNamingAttribute
	String uid
	@GldapoSynonymFor("uid")
	Set<String> username
	String cn
	String sn
	String givenName
	String title
	String mail
	String displayName

	def String getUserId()
	{
		def userId = this.username?.toArray()[1]
		if (userId != null)
		{
			return userId
		}
		return ""
	}
	
}