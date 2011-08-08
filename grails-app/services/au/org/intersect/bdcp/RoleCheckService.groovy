package au.org.intersect.bdcp

class RoleCheckService {

    static transactional = false

	def springSecurityService
	
	/**
	* Check the role of the logged in user.
	*/
   def boolean checkUserRole(roleType)
   {
	   def auth = springSecurityService.authentication;
	   def role = auth.getPrincipal().getAuthorities()[0];
	   return role.equals(roleType)
   }
   
   /**
   * Check the role of the logged in user.
   */
  def boolean checkSameUser(username)
  {
	  def auth = springSecurityService.authentication;
	  def ppal = auth.getPrincipal()
	  return ppal.getUsername().equals(username)
  }

}
