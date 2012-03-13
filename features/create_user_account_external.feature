Feature: Create User Account
  In order manage my data
  As a System Administrator
  I want to create a user account

  Scenario: Create User Account For External User
    Given I have logged in as "sysadm"
    Given I am on the home page
    And I press "system-administration"
    And I press "account-administration"
    And I should see "Account Administration"
    And I press "Back"
    Then I should see "System Administration"
    
    Given I am on the home page
    And I press "system-administration"
    And I press "account-administration"
    Then I should see "Account Administration"
    Then I press "create"
    Then I should see "New UoW User Account"
    And I press "Back"
    Then I should see "Account Administration"
    Then I press "create"
    Then I should see "New UoW User Account"
    Then I press "cancel"
    Then I should see "Account Administration"
          
    Given I am on the home page
    And I press "system-administration"
    And I press "account-administration"
    Then I should see "Account Administration"
    Then I press "create"
    Then I should see "New UoW User Account"
    Then I press "createExternal"
    Then I should see "Create External User Account"
    Given I have cleared and filled in "firstName" with "Karl"
    Given I have cleared and filled in "surname" with "Herrmann"
    Given I have cleared and filled in "email" with "karl@hotmail.com"
    Given I have cleared and filled in "password" with "myPassword"
    Given I have cleared and filled in "password_2" with "myPassword"
    Then I press "create"
	Then I should see "nlaIdentifier" with value ""
    Then I should see "authority" selected with value ""
    Then I select "Lab Manager" from "authority"
    Then I fill in "title" with "Mr"
    Then I press "select"
    Then I should see "Confirm account creation"
    Then I press "Confirm"
