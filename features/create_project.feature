Feature: Create project
  In order manage my data
  As a researcher
  I want to create a project

  Scenario: Create project
    Given I am on the create project page
    When I fill in "projectTitle" with "My Biomechanics Project"
    And I fill in "researcherName" with "Fred Bloggs"
    And I fill in "degree" with "Masters of Biomechanics"
    And I fill in "description" with "Studying some stuff"
    And I fill in "supervisors" with "Alice Smith"
    And I press "create"
    Then I should see "created"
    And I should see table "projectTable" with contents
      | Project Title   | My Biomechanics Project |
      | Researcher Name | Fred Bloggs             |
      | Degree          | Masters of Biomechanics |
      | Year From       | todo                    |
      | Year To         | todo                    |
      | Description     | Studying some stuff     |
      | Supervisor(s)   | Alice Smith             |
   