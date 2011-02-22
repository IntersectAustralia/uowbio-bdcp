class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		/*"/"(view:"/index")
		"500"(view:'/error')*/
		"/"(controller:"project", action:"list") 
		"500"(view:'/error')
	}
}
