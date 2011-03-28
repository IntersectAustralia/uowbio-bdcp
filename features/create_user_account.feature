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
    And I press "Back"
    Then I should see "Account Administration"
     Then I press "create"
     Then I should see "New User Account"
     Then I press "cancel"
     Then I should see "Account Administration"
     
     Given I am on the home page
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "create"
     Then I should see "New User Account"
     Then I fill in "userid" with "dpollum"
     Then I press "search"
     Then I should see a 4 column table "searchTable" with contents
     |dpollum   | David      | Pollum  | Select |
     Then I press "select[0]"
     Then I should see "Confirm Account Creation"
     Then I press "Confirm"
     Then I should see "Account Creation Successful"
     Then I press "accountAdmin"
     Then I should see "Account Administration"
     Given I am on the email page
     Then I should see "New Biomechanics Data Capture System Account for dpollum"
     
     Given I am on the home page
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "create"
     Then I should see "New User Account"
     Then I press "search"
     Then I should see a 4 column table "searchTable" with contents
     |dpollum  | David  | Pollum   | Select |
     |chrisk   | Chris  | Kenward  | Select |
     
     