Feature: Analysed folder upload
  In order to store my results data
  As a researcher
  I want to describe it and upload it
 
 Background:
    Given I have created a project with "100", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "labman"
    Given I have created a study with "200", "100", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
    Given the file service folder "files/analysed" is empty
    Given I have created result fields
    Given I have logged in as "labman"
    
Scenario: Normal workflow
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
    Then I press "edit-folder[0]"
    Then I should see "Results Template Form for NewFolder123"
    Then I press "update"
    Then I use uploader to upload files to study 200 in folder "NewFolder123"
    Then I should have file service folder "files/analysed/200/NewFolder123/test-files"
    Then I should have file service file "files/analysed/200/NewFolder123/test-files/form-upload1.txt"    
    Then I should have file service file "files/analysed/200/NewFolder123/test-files/form-upload2.txt"    
    Then I should have file service file "files/analysed/200/NewFolder123/test-files/form-upload3.txt"    
    