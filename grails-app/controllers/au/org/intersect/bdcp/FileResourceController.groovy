package au.org.intersect.bdcp

class FileResourceController {
    
    def index = { redirect(action:list2,params:params) }
	static transactional = true
    
    def allowedMethods = []

    def list = {
		def fileResourceInstanceList = []
		def f = new File( grailsApplication.config.forms.location.toString() )
		if( f.exists() ){
			f.eachFile(){ file->
			if( !file.isDirectory() )
				fileResourceInstanceList.add( file.name )
			}
		}
        [ fileResourceInstanceList: fileResourceInstanceList ]
    }

	def list2 = {
		def fileResourceInstanceList = []
		def f = new File( grailsApplication.config.forms.location.toString() )
		if( f.exists() ){
			f.eachFile(){ file->
			if( !file.isDirectory() )
				fileResourceInstanceList.add( file.name )
			}
		}
		[ fileResourceInstanceList: fileResourceInstanceList ]
	}
	
    def delete = {
		def filename = params.id.replace('###', '.')
		def file = new File( grailsApplication.config.forms.location.toString() + File.separatorChar +   filename )
		file.delete()
		flash.message = "file ${filename} removed" 
		redirect( action:list )
    }

	def upload = {
		def f = request.getFile('fileUpload')
	    if(!f.empty) {
	      flash.message = 'Your file has been uploaded'
		  new File( grailsApplication.config.forms.location.toString() ).mkdirs()
		  f.transferTo( new File( grailsApplication.config.forms.location.toString() + File.separatorChar + f.getOriginalFilename() ) )								             			     	
		}    
	    else {
	       flash.message = 'file cannot be empty'
	    }
		redirect( action:list2)
	}
}
