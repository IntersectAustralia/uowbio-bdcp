package au.org.intersect.bdcp

class AdminController {

    def index = { redirect(action:create,params:params) }
	static transactional = true
    
    def allowedMethods = []

    def create = {
        
    }
	
	def ldapSearchPage = {
		
	}
}
