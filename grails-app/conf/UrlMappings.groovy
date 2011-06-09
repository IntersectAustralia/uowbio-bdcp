class UrlMappings
{

	static mappings =
	{
		name participantDetails: "/study/$studyId/participant/$action?/$id?" {
			controller = 'participant'
	     }
		
        name studyDeviceDetails: "/study/$studyId/studyDevice/$action?/$id?" {
            controller = 'studyDevice'
        }
        
        name studyDeviceFieldDetails: "/study/$studyId/studyDeviceField/$action?/$id?" {
            controller = 'studyDeviceField'
        }
        
        name deviceDetails: "/deviceGroup/$deviceGroupId/device/$action?/$id?" {
            controller = 'device'
        }
        
        name deviceFieldDetails: "/deviceGroup/$deviceGroupId/device/$deviceId/deviceField/$action?/$id?" {
            controller = 'deviceField'
        }
        
		name componentDetails: "/study/$studyId/component/$action?/$id?" {
			controller = 'component'
		 }
		
		name sessionDetails: "/study/$studyId/component/$componentId/session/$action?/$id?" {
			controller = 'session'
		 }
		
		name sessionFileDetails: "/study/$studyId/session/$sessionId/sessionFile/$action?/$id?" {
			controller = 'sessionFile'
		 }
		
		name sessionFileList: "/study/$studyId/sessionFile/$action?/$id?" {
			controller = 'sessionFile'
		 }
		
		name studyDetails: "/project/$projectId/study/$action?/$id?" {
			controller = 'study'
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
