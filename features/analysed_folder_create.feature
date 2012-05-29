Feature: Analysed folder, create
  In order manage my results data
  As a researcher
  I want to create folders for my data
 
 Background:
    Given I have logged in as "labman"
    Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "labman"
    Given I have created a study with "2000", "-1000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"

 Scenario: create folder at top level
    Given the file service folder "uowbio/files/analysed/2000" is empty
    Given the file service folder "uowbio/files/analysed/2000" exists
    Given I am on the home page
    And I press "all-projects"
    Then I follow "My Biomechanics Project"
    And I follow "My Biomechanics Study"
    And I enable javascript
    Then I follow "Analysed data"
    Then I wait for ajax
    Then I open context menu for "Analysed Data"
    And I follow "Create Folder"
    Then I should see "Create top folder in analysed data"
    Then I fill in "folderName" with "Brand new folder"
    And I press "save"
    Then the file service folder "uowbio/files/analysed/2000/Brand new folder" exists
    Then I should see "Folder created"
    Then I wait for ajax
    Then I open folder "Analysed Data"
    Then I should see "Brand new folder"


 Scenario: validates folder name
    Given the file service folder "uowbio/files/analysed/2000" is empty
    Given the file service folder "uowbio/files/analysed/2000" exists
    Given I am on the home page
    And I press "all-projects"
    Then I follow "My Biomechanics Project"
    And I follow "My Biomechanics Study"
    And I enable javascript
    Then I follow "Analysed data"
    Then I wait for ajax
    Then I open context menu for "Analysed Data"
    And I follow "Create Folder"
    Then I should see "Create top folder in analysed data"
    Then I fill in "folderName" with "Brand/new/folder"
    And I press "save"
    Then I should see "invalid"


 Scenario: create folder at sub-level
    Given the file service folder "uowbio/files/analysed/2000/Brand/folder" is empty
    Given the file service folder "uowbio/files/analysed/2000/Brand/folder" exists
    Given I am on the home page
    And I press "all-projects"
    Then I follow "My Biomechanics Project"
    And I follow "My Biomechanics Study"
    And I enable javascript
    Then I follow "Analysed data"
    Then I wait for ajax
    Then I open folder "Analysed Data"
    Then I open folder "Brand"
    Then I open context menu for "folder"
    And I follow "Create Folder"
    Then I should see "Create folder inside"
    Then I should see "Brand/folder"
    Then I fill in "folderName" with "New too"
    And I press "save"
    Then the file service folder "uowbio/files/analysed/2000/Brand/folder/New too" exists
    Then I should see "Folder created"
    Then I wait for ajax
    Then I open folder "Analysed Data"
    Then I open folder "Brand"
    Then I open folder "folder"
    Then I should see "New too"

