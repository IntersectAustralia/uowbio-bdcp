package au.org.intersect.bdcp

class EmailNotifierService {

    static transactional = false

	def mailService
	
    def contactUser(template, username, password, email, role) {
		mailService.sendMail {
			to email
			from "biomechanics@uow.edu.au"
			subject "New Biomechanics Data Capture System Account"
			body (view: "/admin/send/${template}", model:[username:username, password:password, role:role]) 
		}
    }
}
