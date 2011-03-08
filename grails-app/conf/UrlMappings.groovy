class UrlMappings
{

	static mappings =
	{
		name participantDetails: "/study/$studyId/$controller/$action?/$id?" {
			controller = 'participant'
	     }
		"/study/$studyId/$controller/$action?/$id?" {
		   view = "create"
		}
		
		"/$controller/$action?/$id?"
		{ constraints {
				// apply constraints here
			} }
		/*"/"(view:"/index")
		 "500"(view:'/error')*/
		"/"(controller:"project", action:"list")
		"500"(view:'/error')
		
	}
}
