class UrlMappings
{

	static mappings =
	{
		name participantDetails: "/study/$studyId/$controller/$action?/$id?" {
			controller = 'participant'
	     }
		
		name participantFormDetails: "/study/$studyId/participant/$participantId/$controller/$action?/$id?" {
			controller = 'participantForm'
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
