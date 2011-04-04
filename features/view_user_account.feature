Feature: View User Account
  In order manage my data
  As a System Administrator
  I want to view a user accounts in DB

  Scenario: View User Account
     Given I have logged in
     Given I am on the home page
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "list"
     Then I should see "List All Users"
     Then I should see a 4 column table "listTable" with contents
     |Kenward  |Chris |chrisk  |View |        
     |Pollum   |David |dpollum |View |
     
     
     
     
      
     
     
     
     