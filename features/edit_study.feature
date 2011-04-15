Feature: Edit Study
  In order manage my data
  As a researcher
  I want to edit a study
 
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
    And I fill in "uowEthicsNumber" with "1073A"
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
      | UOW Ethics Number              | 1073A                   |
      | Additional Ethics Requirements | No                      |
      | Description                    | Test Description        |
      | Industry Partners              | Partner1                |
      | Collaborators                  | Collaborator1           |
      | Start Date                     | 03/2011                 |
      | End Date                       | 03/2011                 |
      | Number of Participants         | 10                      |
      | Inclusion Exclusion Criteria   | Test Criteria           |
   
  Scenario: Edit Study
  Given I am on the home page
  And I follow "My Biomechanics Study"
  And I should see table "studyTable" with contents
      | Study Title                    | My Biomechanics Study   |
      | UOW Ethics Number              | 1073A                   |
      | Additional Ethics Requirements | No                      |
      | Description                    | Test Description        |
      | Industry Partners              | Partner1                |
      | Collaborators                  | Collaborator1           |
      | Start Date                     | 03/2011                 |
      | End Date                       | 03/2011                 |
      | Number of Participants         | 10                      |
      | Inclusion Exclusion Criteria   | Test Criteria           |
   
   Given I am on the home page
   And I follow "My Biomechanics Study"
   Then I press "edit"
   Then I should see "Edit Study"
   When I press "cancel"
   Then I should see "My Biomechanics Study"
   And I should see table "studyTable" with contents
      | Study Title                    | My Biomechanics Study   |
      | UOW Ethics Number              | 1073A                   |
      | Additional Ethics Requirements | No                      |
      | Description                    | Test Description        |
      | Industry Partners              | Partner1                |
      | Collaborators                  | Collaborator1           |
      | Start Date                     | 03/2011                 |
      | End Date                       | 03/2011                 |
      | Number of Participants         | 10                      |
      | Inclusion Exclusion Criteria   | Test Criteria           |  
    
    Then I press "edit"
    Then I should see "Edit Study"
    When I fill in "studyTitle" with " 1"
    Then I press "save"
    Then I should see "My Biomechanics Study 1"
    And I should see table "studyTable" with contents
      | Study Title                    | My Biomechanics Study 1 |
      | UOW Ethics Number              | 1073A                   |
      | Additional Ethics Requirements | No                      |
      | Description                    | Test Description        |
      | Industry Partners              | Partner1                |
      | Collaborators                  | Collaborator1           |
      | Start Date                     | 03/2011                 |
      | End Date                       | 03/2011                 |
      | Number of Participants         | 10                      |
      | Inclusion Exclusion Criteria   | Test Criteria           |
      
    Then I press "edit"
    Then I should see "Edit Study"
    And I select "Yes" from "hasAdditionalEthicsRequirements"
    And I fill in "additionalEthicsRequirements" with "Some Additional Requirements"
    Then I press "save"
    Then I should see "My Biomechanics Study 1"
    And I should see table "studyTable" with contents
      | Study Title                    | My Biomechanics Study 1       |
      | UOW Ethics Number              | 1073A                         |
      | Additional Ethics Requirements | Yes                           |
      | Additional Ethics Details      | Some Additional Requirements  |
      | Description                    | Test Description              |
      | Industry Partners              | Partner1                      |
      | Collaborators                  | Collaborator1                 |
      | Start Date                     | 03/2011                       |
      | End Date                       | 03/2011                       |
      | Number of Participants         | 10                            |
      | Inclusion Exclusion Criteria   | Test Criteria                 |
     