class UrlMappings
{

	static mappings =
	{
		name participantDetails: "/study/$studyId/participant/$action?/$id?" {
			controller = 'participant'
	     }
		
		name componentDetails: "/study/$studyId/component/$action?/$id?" {
			controller = 'component'
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
