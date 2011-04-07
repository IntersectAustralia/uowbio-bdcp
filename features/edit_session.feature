Feature: Edit Session
  In order manage my data
  As a researcher
  I want to edit a session for a study
 
 Background:
    Given I have logged in
 	Given I am on the create project page
    When I fill in "projectTitle" with "My Biomechanics Project"
    And I fill in "researcherName" with "Fred Bloggs"
    And I fill in "degree" with "Masters of Biomechanics"
    And I select "March" from "startDate_month"
    And I select "2011" from "startDate_year"
    And I select "March" from "endDate_month"
    And I select "2011" from "endDate_year"
    And I fill in "description" with "Studying some stuff"
    And I fill in "supervisors" with "Alice Smith"
    And I press "create"
    Then I should see "saved"
    
    Given I am on the home page
    And I follow "Add Study"
    When I fill in "studyTitle" with "My Biomechanics Study"
    And I fill in "ethicsNumber" with "1073A"
    And I fill in "description" with "Test Description"
    And I fill in "industryPartners" with "Partner1"
    And I fill in "collaborators" with "Collaborator1"
    And I select "March" from "startDate_month"
    And I select "2011" from "startDate_year"
    And I select "March" from "endDate_month"
    And I select "2011" from "endDate_year"
    
    And I fill in "numberOfParticipants" with "10"
    And I fill in "inclusionExclusionCriteria" with "Test Criteria"
    And I press "create"
    Then I should see "saved"
    Then I should see "My Biomechanics Study"
    And I should see table "studyTable" with contents
      | Study Title                    | My Biomechanics Study   |
      | Ethics Number                  | 1073A                   |
      | Description                    | Test Description        |
      | Industry Partners              | Partner1                |
      | Collaborators                  | Collaborator1           |
      | Start Date                     | 03/2011                 |
      | End Date                       | 03/2011                 |
      | Number of Participants         | 10                      |
      | Inclusion Exclusion Criteria   | Test Criteria           |
   
    Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Components"
    Then I should see "Add Component"
    Then I follow "Add Component"
    Then I should see "Add New Component"
    And I fill in "name" with "TestComponent"
    And I fill in "description" with "Some Description"
    And I press "save"
    Then I should see "saved"
    Then I should see "TestComponent"

    Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Components"
    Then I should see "Add Session"
    Then I follow "Add Session"
    Then I should see "Add New Session"
    Then I fill in "name" with "TestSession"
    Then I fill in "description" with "Some Description"
    And I press "save"
    Then I should see "TestSession"  

	Scenario: Edit Session
	Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Components"
    Then I should see "Add Session"
    Then I should see "TestSession"
    Then I press "edit-session[0]"
    Then I should see "Edit Session"
    Then I press "cancel"
    Then I should see "Add Session"
    Then I should see "TestSession"
    
    Then I press "edit-session[0]"
    Then I should see "Edit Session"
    And I fill in "name" with "1"
    And I press "save"
    Then I should see "updated"
    Then I should see "Add Session"
    Then I should see "TestSession1"
    
    Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Components"
    Then I should see "Add Session"
    Then I should see "TestSession"
    Then I press "edit-session[0]"
    Then I should see "Edit Session"
    Then I clear "name"
    Then I clear "description"
    Then I press "save"
    Then I should see "Please provide a description for the session"
    Then I should see "Please provide a name for the session"
    Then I press "cancel"
    Then I should see "Add Session"     
  