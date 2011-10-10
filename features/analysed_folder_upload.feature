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
    
Scenario: Upload form, some errors, fix and then applet
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
    # submit empty form, we should have errors
    Then I press "update"
    Then There must be 3 div with class "errors"
    # fix the errors
    Then I fill in "studyAnalysedDataFields[1].text" with "ABC"
    Then I fill in "studyAnalysedDataFields[2].textArea" with "My summary"
    Then I select "06" from "studyAnalysedDataFields[4].time_hour"
    Then I select "00" from "studyAnalysedDataFields[4].time_minute"
    Then I select radiobutton "Linux" from "studyAnalysedDataFields[6].radioButtonsOption"
    Then I press "update"
    Then I should see words "Upload analysed data NewFolder123"
    Then I use uploader to upload files to study 200 in folder "NewFolder123"
    Then I should have file service folder "files/analysed/200/NewFolder123/test-files"
    Then I should have file service file "files/analysed/200/NewFolder123/test-files/form-upload1.txt"    
    Then I should have file service file "files/analysed/200/NewFolder123/test-files/form-upload2.txt"    
    Then I should have file service file "files/analysed/200/NewFolder123/test-files/form-upload3.txt"
    # this is what the applet would do
    Then I navigate to "http://localhost:8080/BDCP/studyAnalysedData/200/upload?done=true"
    Then I should see "Finishing uploading files"
    # this test that ajax navigation works and has proper data
    Then I wait for ajax
    Then I click tree node "NewFolder123"
    Then I wait for ajax 
    Then I should see "test-files"
    Then I click tree node "test-files"
    Then I wait for ajax
    Then I print the page
    Then I should see "form-upload1.txt"
    