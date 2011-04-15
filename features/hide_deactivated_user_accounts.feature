Feature: Hide User Accounts
  In order manage my data
  As a System Administrator
  I want to list user accounts in DB

  Background: 
     Given I have logged in
     Given I am on the home page
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "list"
     Then I should see "List All Users"
     Then I should see a 3 column table "listTable" with contents
     |Kenward  |Chris |chrisk  |        
     |Pollum   |David |dpollum |
     Then I press "edit[1]"
     Then I should see table "userDetailsTable" with contents
     |Firstname               | David   |
     |Surname                 | Pollum  |
     |User ID                 | dpollum |
     |Deactivate User Account |         |
     Then I press "deactivated"
     Then I press "save"
     Then I should see "dpollum deactivated successfully"
     
     Scenario: Hide User Accounts
     Given I am on the home page
     Then I enable javascript
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "list"
     Then I should see "List All Users"
     Then I press "hideUsers"
     Then I should see a 3 column table "listTable" with contents
     |Kenward  |Chris |chrisk  |        
     Then I disable javascript
     
      
     
     
     
     