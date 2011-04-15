Feature: List User Accounts
  In order manage my data
  As a System Administrator
  I want to list user accounts in DB

  Scenario: List User Accounts
     Given I have logged in
     Given I am on the home page
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "list"
     Then I should see "List All Users"
     Then I should see a 3 column table "listTable" with contents
     |Kenward  |Chris |chrisk  |        
     |Pollum   |David |dpollum |
     
     Then I press "Back"
     Then I should see "Account Administration"
     Then I press "list"
     Then I should see "List All Users"
     Then I press "home"
     Then I should see "Welcome Researcher"
     
     Given I am on the home page
     Then I enable javascript
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "list"
     Then I should see "List All Users"
     Then I press "hideUsers"
     Then I should see a 3 column table "listTable" with contents
     |Kenward  |Chris |chrisk  |        
     |Pollum   |David |dpollum |
     Then I disable javascript
     
     
      
     
     
     
     