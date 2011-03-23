package au.org.intersect.bdcp

class AdminController {

    def index = { redirect(action:create,params:params) }
	static transactional = true
    
    def allowedMethods = []

    def create = {
        
    }
	
	def search = {
		
		render(view: "search")
	}
	
	def searchUsers = {
		if (params.firstName != null)
		{
			session.firstName = params.firstName
		}
		if (params.surname != null)
		{
			session.surname = params.surname
		}
		if (params.userid != null)
		{
			session.userid = params.userid
		}
		println "Completed!"
		render (view: "search", model: [firstName: params.firstName, surname:params.surname, userid:params.userid])
//		redirect (controller: "admin", action: "search")
	}
}
