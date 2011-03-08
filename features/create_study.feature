Feature: Create project
  In order manage my data
  As a researcher
  I want to create a study
 
 Background:
 	Given I have a project with title "TestProject"
 	And I am on the project page


  Scenario: Create Study
    Given I follow "Add Study"
    When I fill in "studyTitle" with "My Biomechanics Study"
    And I fill in "ethicsNumber" with "1073A"
    And I fill in "description" with "Test Description"
    And I fill in "industryPartners" with "Partner1"
    And I fill in "collaborators" with "Collaborator1"
    And I select "March" from "dateStart_month"
    And I select "2011" from "dateStart_year"
    And I select "March" from "dateEnd_month"
    And I select "2011" from "dateEnd_year"
    
    And I fill in "numberOfParticipants" with "10"
    And I fill in "inclusionExclusionCriteria" with "Test Criteria"
    And I press "create"
    Then I should see "saved"
    And I should see table "studyTable" with contents
      | Study Title                    | My Biomechanics Study   |
      | Ethics Number                  | 1073A                   |
      | Description                    | Test Description        |
      | Industry Partners              | Parnter1                |
      | Collaborators                  | Collaborator1           |
      | Date Start                     | 03/2011                 |
      | Date End                       | 03/2011                 |
      | Number of Participants         | 10                      |
      | Inclusion Exclusion Criteria   | Test Criteria           |
   
