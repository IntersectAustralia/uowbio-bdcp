Feature: Search User Accounts
  In order manage my data
  As a System Administrator
  I want to search user accounts in LDAP

  Scenario: Search User Accounts
     Given I have logged in
     Given I am on the home page
     And I press "system-administration"
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "create"
     Then I should see "New User Account"
     Then I press "search"
     Then I should see a 4 column table "searchTable" with contents
     |abe       | John  | Abe              | Select  |
     |chrisk    | Chris | Kenward          | Select  |
     |davidk    | David | Kenward          | Select  |
     |dpollum   | David | Pollum           | Select  |
     |jdoesmith | John  | Doe-Smith        | Select  |
     |johnk     | John  | Kenward          | Select  |
     |johnma    | John  | Michael Anderson | Select  |
     
     
     Given I am on the home page
     And I press "system-administration"
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "create"
     Then I should see "New User Account"
     Then I fill in "surname" with "Kenward"
     Then I press "search"
     Then I should see a 4 column table "searchTable" with contents
     |chrisk | Chris | Kenward  | Select  |
     |davidk | David | Kenward  | Select  |
     |johnk  | John  | Kenward  | Select  |
     
     Then I press "select[0]"
     Then I should see "Confirm account creation for"
     Then I press "Cancel"
     Then I should see a 4 column table "searchTable" with contents
     |chrisk | Chris | Kenward  | Select  |
     |davidk | David | Kenward  | Select  |
     |johnk  | John  | Kenward  | Select  |
     
     
      
     
     
     
     