Feature: Analysed folder, create
  In order manage my results data
  As a researcher
  I want to create a folder for my data
 
 Background:
    Given I have created a project with "100", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "labman"
    Given I have created a study with "200", "100", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
    Given the file service folder "files/analysed" is empty
    Given I have logged in as "labman"
    
Scenario: Create new folder
    Given I enable javascript
    Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Analysed data"
    Then I press "createFolder"
    Then I should see words "Create folder"
    Then I fill in "folder" with "NewFolder123"
    Then I press "save"
    Then I wait for ajax
    Then I should have file service folder "files/analysed/200/NewFolder123"
    Then I should see "NewFolder123"

Scenario: Create existing folder
    Given the file service folder "files/analysed/200/Folder456" exists
    Given I enable javascript
    Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Analysed data"
    Then I press "createFolder"
    Then I should see words "Create folder"
    Then I fill in "folder" with "Folder456"
    Then I press "save"
    Then I should see "Folder456"
    And I should see words "already exists"
    
Scenario: Test valid folder names
    Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Analysed data"
    Then I press "createFolder"
    Then I should see words "Create folder"
    Then I fill in "folder" with ""
    Then I press "save"
    Then There must be a div with class "errors"
