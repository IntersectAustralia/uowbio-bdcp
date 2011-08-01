Feature: Delete Collaborator of Study
  In order to cease a collaboration
  As a Researcher
  I want collaborators removed from my study by a Lab Manager
 
 Background:
 Given I have logged in as "labman"
 Given I have created a project with "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "chrisk"
 Given I have created a collaborator study with "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria", "researcher"
 
 Scenario: Delete Collaborator from Study
 Given I am on the home page
 Then I follow "My Biomechanics Study"
 Then I follow "Collaborators"
 Then I press "delete[0]"
 Then I should see "Collaborators"
 Then I press "Logout"
   	 
 Given I have logged in as "researcher"
 Given I am on the home page
 Then I press "Logout"
