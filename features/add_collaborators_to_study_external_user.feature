Feature: Add Collaborators to Study who are external users
  In order to collaborate
  As a Researcher
  I want collaborators added to my study by a Lab Manager
 
 Background:
 Given I have logged in as "labman"
 Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "chrisk"
 Given I have created a collaborator study with "-1000", "-2000", "-6000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keywords", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria", "researcher"
 
 Scenario: Add Collaborator to Study who is an external user
 
 Given I am on the home page
 And I press "system-administration"
 And I press "account-administration"
 Then I should see "Account Administration"
 Then I press "create"
 Then I should see "New UoW User Account"
 Then I press "createExternal"
 Then I should see "Create External User Account"
 Given I have cleared and filled in "firstName" with "Karls"
 Given I have cleared and filled in "surname" with "Herrmanns"
 Given I have cleared and filled in "email" with "karl@hotmail.com"
 Given I have cleared and filled in "password" with "password"
 Then I press "create"
 Then I should see "nlaIdentifier" with value ""
 Then I should see "authority" selected with value ""
 Then I select "Lab Manager" from "authority"
 Then I fill in "title" with "Mr"
 Then I press "select"
 Then I should see "Confirm account creation"
 Then I press "Confirm"
 Then I press "Logout"
 
 Given I have logged in as "labman" 
 Given I am on the home page
 And I press "all-projects"
 Then I follow "My Biomechanics Project"
 Then I follow "My Biomechanics Study"
 Then I follow "Collaborators"
 Then I press "Add Collaborator"
 Then I should see "Add Collaborator to study My Biomechanics Study"
 Then I fill in "firstName" with ""
 Then I fill in "surname" with ""
 Then I fill in "userid" with "karl@hotmail.com"
 Then I press "search"
 Then I should see a 4 column table "searchTable" with contents
 | karl@hotmail.com | Karls | Herrmanns | Select |
 Then I press "select[0]"
 Then I should see "Collaborator karl@hotmail.com added to study My Biomechanics Study"
 Then I follow "Collaborators"
 Then I should see "Add Collaborator"
 Then I should see "karl@hotmail.com"
 Then I press "Logout"
 
 Given I have logged in as "karl@hotmail.com"
 Given I am on the home page
