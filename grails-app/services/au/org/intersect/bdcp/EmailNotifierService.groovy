package au.org.intersect.bdcp

class EmailNotifierService {

    static transactional = false

	def mailService
	
    def contactUser(username, email) {
		mailService.sendMail {
			to email
			from "admin@uow.edu.au"
			subject "New Biomechanics Data Capture System Account for ${username}"
			body (view: "/admin/send/mail") 
		}
    }
}
