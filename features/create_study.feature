Feature: Create project
  In order manage my data
  As a researcher
  I want to create a study

  Scenario: Create Study
    Given I am on the create study page
    When I fill in "studyTitle" with "My Biomechanics Study"
    And I fill in "ethicsNumber" with "1073A"
    And I fill in "description" with "Test Description"
    And I fill in "industryPartners" with "Partner1"
    And I fill in "collaborators" with "Collaborator1"
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
   
