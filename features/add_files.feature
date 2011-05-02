Feature: Add Files
  In order manage my data
  As a researcher
  I want to upload files for a particular session and particular study
 
 Background:
    Given I have logged in
    Given I have created a project with "My Biomechanics Project", "Fred Bloggs", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith"
    
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

	Scenario: Add Files
	Given I am on the home page
	And I follow "My Biomechanics Study"
	Then I follow "Files"
	Then I should see "TestComponent"
	Then I should see "TestSession"
	Then I press "upload[0-0]"
	Then I should see "Browse For Files"
	Then I press "OK"
	Then I should see "TestComponent"
	Then I should see "TestSession"
	Then I press "upload[0-0]"
	Then I should see "Browse For Files"
	Then I press "Cancel"
	Then I should see "TestComponent"
	Then I should see "TestSession"
	