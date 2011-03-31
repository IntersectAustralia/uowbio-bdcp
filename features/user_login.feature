Feature: User Login
  In order manage my data
  As a researcher
  I want to login to the system

  Background:
    
 
   Scenario: User Login
   Given I am on the home page
   Then I see "Please enter your userid and password to login"
   Then I fill in "username" with "dpollum"
   Then I fill in "password" with "password"
   Then I press "Login"
   Then I should see "Welcome Researcher"
   Then I press "Logout"
   Then I should see "Please enter your userid and password to login"
   
   Given I am on the home page
   Then I see "Please enter your userid and password to login"
   Then I fill in "username" with "dpollum"
   Then I fill in "password" with "password"
   Then I press "Clear"
   Then I should see "username" with value ""
   Then I should see "password" with value ""
   