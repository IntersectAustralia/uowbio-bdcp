Feature: View Study
  In order manage my data
  As a researcher
  I want to view a study
 
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
   
  Scenario: View Study
  Given I am on the home page
  And I follow "My Biomechanics Study"
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
   Then I follow "Project List"
   Then I should see "My Biomechanics Project"
   
   Given I am on the home page
   And I follow "My Biomechanics Study"
   Then I press "edit"
   Then I should see "Edit Study"
   Then I should see "studyTitle" with value "My Biomechanics Study"
    Then I should see "ethicsNumber" with value "1073A"
    Then I should see "description" with value "Test Description"
    Then I should see "industryPartners" with value "Partner1"
    Then I should see "collaborators" with value "Collaborator1"
    Then I should see "startDate_month" selected with value "3"
    Then I should see "startDate_year" selected with value "2011"
    Then I should see "endDate_month" selected with value "3"
    Then I should see "endDate_year" selected with value "2011"
    Then I should see "numberOfParticipants" with value "10"
    Then I should see "inclusionExclusionCriteria" with value "Test Criteria"
    
    Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Participants"
    Then I should see "Add Participant" 
    
    Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Components"
    Then I should see "Add Component"
     
  