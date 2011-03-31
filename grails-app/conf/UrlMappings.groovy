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
		"/"(controller:"project", action:"list")
		"500"(view:'/error')
		
	}
}
