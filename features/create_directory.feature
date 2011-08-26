Feature: Create Directory
  In order manage my data
  As a researcher
  I want to create a directory for a particular session and particular study
 
 Background:
    Given I have logged in as "labman"
    Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "labman"
    Given I have created a study with "-2000", "-1000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
    
    Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Components"
    Then I press "createComponent"
    Then I should see "Add New Component"
    Then I fill in "name" with "TestComponent"
    Then I fill in "description" with "Some Description"
    Then I press "save"
    Then I should see "TestComponent"
    Then I should see "Components"
    
    Then I press "createSession[0]"
    Then I should see "Add New Session"
    Then I fill in "name" with "TestSession"
    Then I fill in "description" with "Some Description"
    Then I press "save"
    Then I should see "TestSession"
    Then I should see "Components"
    
	Scenario: Create Directory
	Given I am on the home page
	And I follow "My Biomechanics Study"
	Then I follow "Files"
	Then I should see "TestComponent"
	Then I should see "TestSession"
	Then I press "createDirectory[0-0]"
	Then I should see "Add New Directory"
	Then I fill in "name" with "Test Directory"
	Then I press "save"
	Then I should see "Test Directory"
	Then I should see "Files"
	
	Then I press "createDirectory[0-0]"
	Then I should see "Add New Directory"
	Then I fill in "name" with "Test?"
	Then I press "save"
	Then I should see "Please enter a valid directory name for the directory to be created"
	Then I press "cancel"
	Then I should see "Files"
	
	Then I press "createDirectory[0-0]"
	Then I should see "Add New Directory"
	Then I fill in "name" with "1348703149873120948713h143hu143y47132h7r3eqwreiwporuqoepiruepqoirueqproiueqrpiouerpqoiueqrpoiuqrepowiuepqoriueqproiureqoipuqeropiueropiuqr1348703149873120948713h143hu143y47132h7r3eqwreiwporuqoepiruepqoirueqproiueqrpiouerpqoiueqrpoiuqrepowiuepqoriueqproiureqoipuqeropiueropiuqr"
	Then I press "save"
	Then I should see "Please provide a name for the new Directory that is smaller than 255 characters"
	Then I press "cancel"
	Then I should see "Files"
	
	Then I press "createDirectory[0-0]"
	Then I should see "Add New Directory"
	Then I fill in "name" with "Test Directory"
	Then I press "save"
	Then I should see "Please provide a unique name for this directory"
	Then I press "cancel"
	Then I should see "Files"