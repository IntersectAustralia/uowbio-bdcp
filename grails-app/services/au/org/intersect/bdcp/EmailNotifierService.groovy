package au.org.intersect.bdcp

class EmailNotifierService {

    static transactional = false

	def mailService
	
    def contactUser(username, role, email) {
		mailService.sendMail {
			to email
			from "biomechanics@uow.edu.au"
			subject "New Biomechanics Data Capture System Account for ${username} with role of ${role}."
			body (view: "/admin/send/mail") 
		}
    }
}
