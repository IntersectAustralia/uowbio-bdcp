package au.org.intersect.bdcp

class RoleCheckService {

    static transactional = false

	def springSecurityService
	
	/**
	* Check the role of the logged in user.
	*/
   def boolean checkUserRole(roleType)
   {
	   boolean check = false;
	   def auth = springSecurityService.authentication;
	   def role = auth.getPrincipal().getAuthorities()[0];
	   if(role.equals(roleType)) {
		   check = true;
	   }

	   return check;
   }
}
