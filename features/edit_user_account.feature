  Feature: Edit User Account
  In order manage my data
  As a System Administrator
  I want to view a user accounts in DB

  Scenario: Edit User Account
     Given I have logged in
     Given I am on the home page
     And I press "system-administration"
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "list"
     Then I should see "List All Users"
     Then I should see a 4 column table "listTable" with contents
     |Administrator	|System 		|sysadm		|Edit |
     |Kenward		|Chris			|chrisk 	|Edit |
     |Manager		|Lab Manager	|labman		|Edit |        
     |Pollum		|David			|dpollum	|Edit |
     |Researcher	|The	 		|researcher	|Edit |
     Then I press "edit[3]"
     Then I should see table "userDetailsTable" with contents
     |Firstname               | David   									|
     |Surname                 | Pollum 										|
     |User ID                 | dpollum										|
     |Role                    | Lab Manager System Administrator Researcher	|
     |Deactivate User Account |         									|
     Then I press "deactivated"
     Then I press "save"
     Then I should see "dpollum deactivated successfully"
     Then I press "Logout"
   	 Then I should see "Please enter your userid and password to login"
     Then I fill in "j_username" with "dpollum"
   	 Then I fill in "j_password" with "password"
   	 Then I press "Login"
   	 Then I should see "Please enter your userid and password to login"
   	 
   	 Given I have logged in
   	 Given I am on the home page
   	 And I press "system-administration" 
   	 And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "list"
     Then I should see "List All Users"
     Then I should see a 4 column table "listTable" with contents
     |Administrator	|System 		|sysadm		|Edit |
     |Kenward		|Chris			|chrisk 	|Edit |
     |Manager		|Lab Manager	|labman		|Edit |        
     |Pollum		|David			|dpollum	|Edit |
     |Researcher	|The	 		|researcher	|Edit |
     Then I press "edit[3]"
     Then I press "deactivated"
     Then I press "save"
     Then I should see "dpollum activated successfully"
     Then I press "Logout"
     Then I fill in "j_username" with "dpollum"
   	 Then I fill in "j_password" with "password"
     Then I press "Login"
     Then I should see "Welcome Researcher"
     