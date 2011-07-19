 package au.org.intersect.bdcp

import au.org.intersect.bdcp.enums.UserRole

class StudyCollaborator{
	
	Study study
	UserStore collaborator
	
	StudyCollaborator() {
		// define default constructor
	}
	
	StudyCollaborator(Study _study, UserStore _collaborator) {
		study = _study
		collaborator = _collaborator
	}
	
    static StudyCollaborator link(Study study, UserStore collaborator) {
        StudyCollaborator studyCollaborator = StudyCollaborator.findByStudyAndCollaborator(study,collaborator)
        if (!studyCollaborator) {
            studyCollaborator = new StudyCollaborator()
            study?.addToStudyCollaborators(studyCollaborator)
            collaborator?.addToStudyCollaborators(studyCollaborator)
            studyCollaborator.save()

        }
        return studyCollaborator
    }
    static void unlink(Study study, UserStore collaborator) {
        StudyCollaborator studyCollaborator = StudyCollaborator.findByStudyAndCollaborator(study,collaborator)
        if (studyCollaborator) {
            study?.removeFromStudyCollaborators(studyCollaborator)
            collaborator?.removeFromStudyCollaborators(studyCollaborator)
            studyCollaborator.delete()
        }
    }

    static mapping = {
        studyCollaboratorFields cascade:'all-delete-orphan'
    }

    
    static constraints = {
        study(nullable: true)
        collaborator(nullable: true)
        
    }
	
}
