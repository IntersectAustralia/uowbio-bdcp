Feature: Create project
  In order manage my data
  As a researcher
  I want to create a project

  Scenario: Create project
    Given I am on the create project page
    When I fill in "projectTitle" with "My Biomechanics Project"
    And I fill in "researcherName" with "Fred Bloggs"
    And I fill in "degree" with "Masters of Biomechanics"
    And I select "March" from "yearFrom_month"
    And I select "2011" from "yearFrom_year"
    And I select "March" from "yearTo_month"
    And I select "2011" from "yearTo_year"
    And I fill in "description" with "Studying some stuff"
    And I fill in "supervisors" with "Alice Smith"
    And I press "create"
    Then I should see "saved"
    And I should see table "projectTable" with contents
      | Project Title   | My Biomechanics Project |
      | Researcher Name | Fred Bloggs             |
      | Degree          | Masters of Biomechanics |
      | Year From       | 03/2011                 |
      | Year To         | 03/2011                 |
      | Description     | Studying some stuff     |
      | Supervisor(s)   | Alice Smith             |
   
