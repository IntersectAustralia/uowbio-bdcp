Feature: View Project
  In order manage my data
  As a researcher
  I want to view a project

  Background:
    Given I have logged in as "dpollum"
    Given I am on the create project page
    When I fill in "projectTitle" with "My Biomechanics Project"
    And I fill in "researcherName" with "Fred Bloggs"
    And I fill in "studentNumber" with "123456"
    And I fill in "degree" with "Masters of Biomechanics"
    And I select "March" from "startDate_month"
    And I select "2011" from "startDate_year"
    And I select "March" from "endDate_month"
    And I select "2011" from "endDate_year"
    And I fill in "description" with "Studying some stuff"
    And I fill in "supervisors" with "Alice Smith"
    And I press "create"
    Then I should see "saved"
    Then I follow "My Biomechanics Project"
    Then I should see "My Biomechanics Project"
    And I should see table "projectTable" with contents
      | Project Title   | My Biomechanics Project |
      | Researcher Name | Fred Bloggs             |
      | Student Number  | 123456                  |
      | Degree          | Masters of Biomechanics |
      | Start Date      | 03/2011                 |
      | End Date        | 03/2011                 |
      | Description     | Studying some stuff     |
      | Supervisor(s)   | Alice Smith             |
   

   Scenario: View Project
   Given I am on the home page
   Then I follow "My Biomechanics Project"
   Then I should see "My Biomechanics Project"
    And I should see table "projectTable" with contents
      | Project Title   | My Biomechanics Project |
      | Researcher Name | Fred Bloggs             |
      | Student Number  | 123456                  |
      | Degree          | Masters of Biomechanics |
      | Start Date      | 03/2011                 |
      | End Date        | 03/2011                 |
      | Description     | Studying some stuff     |
      | Supervisor(s)   | Alice Smith             |
   