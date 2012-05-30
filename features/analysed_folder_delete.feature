Feature: Analysed folder, create
  In order manage my results data
  As a researcher
  I want to create a folder for my data
 
 Background:
    Given I have created a project with "100", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "labman"
    Given I have created a study with "2000", "100", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
    Given the file service folder "files/analysed" is empty
    Given the file service folder "uowbio/files/analysed/2000" is empty
    Given the file service folder "uowbio/files/analysed/2000" exists
    Given the file service folder "uowbio/files/analysed/2000/sub/folder/created" exists
    Given the file service file "uowbio/files/analysed/2000/sub/folder/one.txt" exists
    Given the file service file "uowbio/files/analysed/2000/sub/two.txt" exists
    Given I have logged in as "labman"
    Given I enable javascript
    
 Scenario: delete folder
    Given the confirmation will be true
    Given I am on the home page
    And I press "all-projects"
    Then I follow "My Biomechanics Project"
    And I follow "My Biomechanics Study"
    Then I follow "Analysed data"
    And I wait for ajax
    And I open folder "Analysed Data"
    And I open folder "sub"
    And I open context menu for "folder"
    And I follow "Delete"
    And I confirm
    Then the file service folder "uowbio/files/analysed/2000/sub/folder" should not exist
    And the file service folder "uowbio/files/analysed/2000/sub" should exist
    And the file service file "uowbio/files/analysed/2000/sub/two.txt" should exist
    And I should see "deleted"
    And I should see "Analysed Data"
 
 Scenario: delete file
    Given the confirmation will be true
    Given I am on the home page
    And I press "all-projects"
    Then I follow "My Biomechanics Project"
    And I follow "My Biomechanics Study"
    Then I follow "Analysed data"
    And I wait for ajax
    And I open folder "Analysed Data"
    And I open folder "sub"
    And I open folder "folder"
    And I open context menu for "one.txt"
    And I follow "Delete"
    And I confirm
    Then the file service folder "uowbio/files/analysed/2000/sub/folder" should exist
    And the file service file "uowbio/files/analysed/2000/sub/folder/one.txt" should not exist
    And I should see "deleted"
    And I should see "Analysed Data"
 
 Scenario: cancel delete folder
    Given the confirmation will be false
    Given I am on the home page
    And I press "all-projects"
    Then I follow "My Biomechanics Project"
    And I follow "My Biomechanics Study"
    Then I follow "Analysed data"
    And I wait for ajax
    And I open folder "Analysed Data"
    And I open folder "sub"
    And I open context menu for "folder"
    And I follow "Delete"
    And I cancel
    Then the file service folder "uowbio/files/analysed/2000/sub/folder" should exist
    And the file service folder "uowbio/files/analysed/2000/sub" should exist
 

