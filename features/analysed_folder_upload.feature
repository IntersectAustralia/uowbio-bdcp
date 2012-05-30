Feature: Analysed folder upload
  In order to store my results data
  As a researcher
  I want to describe it and upload it
 
 Background:
    Given I have created a project with "100", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "labman"
    Given I have created a study with "2000", "100", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
    Given the file service folder "files/analysed" is empty
    Given I have logged in as "labman"
    
 Scenario: Upload files to analysed data tab
    Given the file service folder "uowbio/files/analysed/2000" exists
    Given the file service folder "uowbio/files/analysed/2000" is empty
    Given I am on the home page
    And I press "all-projects"
    Then I follow "My Biomechanics Project"
    And I follow "My Biomechanics Study"
    And I enable javascript
    Then I follow "Analysed data"
    Then I wait for ajax
    Then I open context menu for "Analysed Data"
    Then I follow "Upload Files"
    Then I should see "Upload analysed data"
    Then I use uploader to upload files to study 2000 into analysed data path ""
    | token       | path                        |
    | folder_root | test-files                  |
    | file_1      | test-files/form-upload1.txt |
    | file_2      | test-files/form-upload2.txt |
    | file_3      | test-files/form-upload3.txt |
    Then assert files "features/test-files/form-upload1.txt" and "web-app/uowbio/files/analysed/2000/test-files/form-upload1.txt" are identical
    Then assert files "features/test-files/form-upload2.txt" and "web-app/uowbio/files/analysed/2000/test-files/form-upload2.txt" are identical
    Then assert files "features/test-files/form-upload3.txt" and "web-app/uowbio/files/analysed/2000/test-files/form-upload3.txt" are identical
    Then I press "all-projects"
    And I follow "My Biomechanics Project"
    And I follow "My Biomechanics Study"
    Then I follow "Analysed Data"
    Then I wait for ajax
    Then I open folder "Analysed Data"
    Then I open folder "test-files"
    Then I should see "form-upload1.txt"
    Then I should see "form-upload2.txt"
    Then I should see "form-upload3.txt"

 Scenario: Upload files to analysed data folder 
    Given the file service folder "uowbio/files/analysed/2000" exists
    Given the file service folder "uowbio/files/analysed/2000" is empty
    Given the file service folder "uowbio/files/analysed/2000/folder" exists
    Given I am on the home page
    And I press "all-projects"
    Then I follow "My Biomechanics Project"
    And I follow "My Biomechanics Study"
    And I enable javascript
    Then I follow "Analysed data"
    Then I wait for ajax
    Then I open folder "Analysed Data"
    Then I print the page
    Then I open context menu for "folder"
    Then I follow "Upload Files"
    Then I should see "Upload analysed data"
    Then I use uploader to upload files to study 2000 into analysed data path "folder"
    | token       | path                        |
    | folder_root | test-files                  |
    | file_1      | test-files/form-upload1.txt |
    | file_3      | test-files/form-upload3.txt |
    Then assert files "features/test-files/form-upload1.txt" and "web-app/uowbio/files/analysed/2000/folder/test-files/form-upload1.txt" are identical
    Then assert files "features/test-files/form-upload3.txt" and "web-app/uowbio/files/analysed/2000/folder/test-files/form-upload3.txt" are identical
    Then I press "all-projects"
    And I follow "My Biomechanics Project"
    And I follow "My Biomechanics Study"
    Then I follow "Analysed Data"
    Then I wait for ajax
    Then I open folder "Analysed Data"
    Then I open folder "folder"
    Then I open folder "test-files"
    Then I should see "form-upload1.txt"
    Then I should see "form-upload3.txt"

 Scenario: Overwrite files to analysed data folder 
    Given the file service folder "uowbio/files/analysed/2000" exists
    Given the file service folder "uowbio/files/analysed/2000" is empty
    Given the file service folder "uowbio/files/analysed/2000/folder" exists
    Given I am on the home page
    And I press "all-projects"
    Then I follow "My Biomechanics Project"
    And I follow "My Biomechanics Study"
    And I enable javascript
    Then I follow "Analysed data"
    Then I wait for ajax
    Then I open folder "Analysed Data"
    Then I print the page
    Then I open context menu for "folder"
    Then I follow "Upload Files"
    Then I should see "Upload analysed data"
    Then I use uploader to upload files to study 2000 into analysed data path "folder"
    | token       | path                        |
    | folder_root | test-files                  |
    | file_1      | test-files/form-upload1.txt |
    | file_3      | test-files/form-upload3.txt |
    Then assert files "features/test-files/form-upload1.txt" and "web-app/uowbio/files/analysed/2000/folder/test-files/form-upload1.txt" are identical
    Then assert files "features/test-files/form-upload3.txt" and "web-app/uowbio/files/analysed/2000/folder/test-files/form-upload3.txt" are identical
    Then I press "all-projects"
    And I follow "My Biomechanics Project"
    And I follow "My Biomechanics Study"
    Then I follow "Analysed Data"
    Then I wait for ajax
    Then I open folder "Analysed Data"
    Then I open context menu for "folder"
    Then I follow "Upload Files"
    Then I should see "Upload analysed data"
    Then I use uploader to upload files to study 2000 into analysed data path "folder"
    | token       | path                        | content                        |
    | folder_root | test-files                  |                                |
    | file_3      | test-files/form-upload3.txt | test-files/form-upload2.txt    |
    Then assert files "features/test-files/form-upload2.txt" and "web-app/uowbio/files/analysed/2000/folder/test-files/form-upload3.txt" are identical



    
