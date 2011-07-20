Feature: Add Collaborators to Study
  In order to collaborate
  As a Researcher
  I want collaborators added to my study by a Lab Manager
 
 Background:
 Given I have logged in as "labman"
 Given I have created a project with "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "chrisk"
 Given I have created a collaborator study with "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria", "researcher"
 
 Scenario: Add Collaborator to Study
 Given I am on the home page
 Then I follow "My Biomechanics Study"
 Then I follow "Collaborators"
 Then I press "Add Collaborator"
 Then I should see "Add Collaborator to study My Biomechanics Study"
 Then I fill in "firstName" with ""
 Then I fill in "surname" with ""
 Then I fill in "userid" with ""
 Then I press "search"
 Then I should see a 4 column table "searchTable" with contents
 | sysadm  | System 		| Administrator | Select |
 | labman  | Lab Manager 	| Manager 		| Select |
 | dpollum | David  		| Pollum 		| Select |
 Then I press "select[0]"
 Then I should see "Collaborator sysadm added to study My Biomechanics Study"
 Then I follow "Collaborators"
 Then I should see "Add Collaborator"
 Then I should see "sysadm"
 Then I should see "researcher"
 Then I press "Logout"
   	 
 Given I have logged in as "chrisk"
 Given I am on the home page
 Then I follow "My Biomechanics Study"
 Then I follow "Collaborators"
 Then I press "Add Collaborator"
 Then I should see "Sorry, you're not authorized to view this page."
