 Feature: Add Files
  In order manage my data
  As a researcher
  I want to upload files for a particular session and particular study
 
 Background:
    Given I have logged in as "labman"
    Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "labman"
    Given I have created a study with "2000", "-1000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
    Given I have created a component 3000 for study 2000 with "TestComponent", "Some Description"
    Given I have created a session 4000 for component 3000 with "TestSession", "Some Description"

	Scenario: Add Files to session
        Given the file service folder "uowbio/files/sessions/2000/3000/4000" is empty
        Given the file service folder "uowbio/files/sessions/2000/3000/4000" exists
	Given I am on the home page
	And I press "all-projects"
	Then I follow "My Biomechanics Project"
	And I follow "My Biomechanics Study"
        And I enable javascript
	Then I follow "Raw data files"
        Then I wait for ajax
        Then I open folder "TestComponent"
        Then I should see "TestSession"
        Then I open context menu for "TestSession"
        Then I follow "Upload Files"
        Then I should see "TestComponent/TestSession"
        Then I use uploader to upload files to study 2000 into session 4000 and path ""
        | token       | path                        |
        | folder_root | test-files                  |
        | file_1      | test-files/form-upload1.txt |
	| file_2      | test-files/form-upload2.txt |
        | file_3      | test-files/form-upload3.txt |
        Then assert files "features/test-files/form-upload1.txt" and "web-app/uowbio/files/sessions/2000/3000/4000/test-files/form-upload1.txt" are identical
        Then assert files "features/test-files/form-upload2.txt" and "web-app/uowbio/files/sessions/2000/3000/4000/test-files/form-upload2.txt" are identical
        Then assert files "features/test-files/form-upload3.txt" and "web-app/uowbio/files/sessions/2000/3000/4000/test-files/form-upload3.txt" are identical
	Then I press "all-projects"
	And I follow "My Biomechanics Project"
	And I follow "My Biomechanics Study"
	Then I follow "Raw data files"
        Then I wait for ajax
        Then I open folder "TestComponent"
        Then I open folder "TestSession"
        Then I open folder "test-files"
        Then I should see "form-upload1.txt"
        Then I should see "form-upload2.txt"
        Then I should see "form-upload3.txt"

	Scenario: Add files to folder
        Given the file service folder "uowbio/files/sessions/2000/3000/4000" is empty
        Given the file service folder "uowbio/files/sessions/2000/3000/4000/test-folder" exists
	Given I am on the home page
	And I press "all-projects"
	Then I follow "My Biomechanics Project"
	And I follow "My Biomechanics Study"
        And I enable javascript
	Then I follow "Raw data files"
        Then I wait for ajax
        Then I open folder "TestComponent"
        Then I open folder "TestSession"
        Then I should see "test-folder"
        Then I open context menu for "test-folder"
        Then I follow "Upload Files"
        Then I should see "TestComponent/TestSession/test-folder"
        Then I use uploader to upload files to study 2000 into session 4000 and path "test-folder"
        | token       | path                        |
        | folder_root | test-files                  |
        | file_1      | test-files/form-upload1.txt |
        | file_3      | test-files/form-upload3.txt |
        Then assert files "features/test-files/form-upload1.txt" and "web-app/uowbio/files/sessions/2000/3000/4000/test-folder/test-files/form-upload1.txt" are identical
        Then assert files "features/test-files/form-upload3.txt" and "web-app/uowbio/files/sessions/2000/3000/4000/test-folder/test-files/form-upload3.txt" are identical
	Then I press "all-projects"
	And I follow "My Biomechanics Project"
	And I follow "My Biomechanics Study"
	Then I follow "Raw data files"
        Then I wait for ajax
        Then I open folder "TestComponent"
        Then I open folder "TestSession"
        Then I open folder "test-folder"
        Then I open folder "test-files"
        Then I should see "form-upload1.txt"
        Then I should see "form-upload3.txt"


	Scenario: Overwrite file in folder
        Given the file service folder "uowbio/files/sessions/2000/3000/4000" is empty
        Given the file service folder "uowbio/files/sessions/2000/3000/4000/test-folder" exists
	Given I am on the home page
	And I press "all-projects"
	Then I follow "My Biomechanics Project"
	And I follow "My Biomechanics Study"
        And I enable javascript
	Then I follow "Raw data files"
        Then I wait for ajax
        Then I open folder "TestComponent"
        Then I open folder "TestSession"
        Then I should see "test-folder"
        Then I open context menu for "test-folder"
        Then I follow "Upload Files"
        Then I should see "TestComponent/TestSession/test-folder"
        Then I use uploader to upload files to study 2000 into session 4000 and path "test-folder"
        | token       | path                        |
        | folder_root | test-files                  |
        | file_1      | test-files/form-upload1.txt |
        | file_3      | test-files/form-upload3.txt |
        Then assert files "features/test-files/form-upload1.txt" and "web-app/uowbio/files/sessions/2000/3000/4000/test-folder/test-files/form-upload1.txt" are identical
        Then assert files "features/test-files/form-upload3.txt" and "web-app/uowbio/files/sessions/2000/3000/4000/test-folder/test-files/form-upload3.txt" are identical
	Then I press "all-projects"
	And I follow "My Biomechanics Project"
	And I follow "My Biomechanics Study"
	Then I follow "Raw data files"
        Then I wait for ajax
        Then I open folder "TestComponent"
        Then I open folder "TestSession"
        Then I open folder "test-folder"
        Then I open folder "test-files"
        Then I should see "form-upload1.txt"
        Then I should see "form-upload3.txt"
        Then I open context menu for "test-folder"
        Then I follow "Upload Files"
        Then I should see "TestComponent/TestSession/test-folder"
        Then I use uploader to upload files to study 2000 into session 4000 and path "test-folder"
        | token       | path                        | content                     |
        | folder_root | test-files                  |                             |
        | file_1      | test-files/form-upload1.txt | test-files/form-upload2.txt |
        Then assert files "features/test-files/form-upload2.txt" and "web-app/uowbio/files/sessions/2000/3000/4000/test-folder/test-files/form-upload1.txt" are identical
	Then I press "all-projects"
	And I follow "My Biomechanics Project"
	And I follow "My Biomechanics Study"
	Then I follow "Raw data files"
        Then I wait for ajax
        Then I open folder "TestComponent"
        Then I open folder "TestSession"
        Then I open folder "test-folder"
        Then I open folder "test-files"
        Then I should see "form-upload1.txt"
        Then I should see "form-upload3.txt"
