package au.org.intersect.bdcp

class SessionFile {

	String file
	String fileName
	String contentType
	String fileExtension
	static belongsTo = [session:Session]
    static constraints = {
		file(nullable:true)
		contentType(nullable:true)
		fileExtension(nullable:true)
		fileName(nullable:false, blank:false, size:1..1000)
    }
}
