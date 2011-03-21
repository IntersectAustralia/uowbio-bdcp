Feature: Create User Account
  In order manage my data
  As a System Administrator
  I want to create a user account

  Scenario: Create User Account
    Given I am on the home page
    And I press "account-administration"
    And I should see "Account Administration"
    And I press "Back"
    Then I should see "Welcome Researcher"
    
    Given I am on the home page
    And I press "account-administration"
    Then I should see "Account Administration"
    Then I press "create"
    Then I should see "New User Account"
