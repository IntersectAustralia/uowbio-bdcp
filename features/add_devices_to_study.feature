Feature: Add Device to Study
  In order manage my data
  As an Administrator
  I want to create a Device in a Device Grouping
 
 Background:
 Given I have logged in
 Given I have created a project with "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith"
 Given I have created a study with "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
 Given I have created a device grouping with "Force Platforms"
 Given I have created a device with "Device1", "Some Description", "Some Manufacturer", "Some location", "Some model", "Some serial number", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "$10.00", "Intersect", "Some funding source"
 
 Scenario: Add Device to Study
 Given I am on the home page
 Then I press "My Biomechanics Study"
 Then I press "Device"
 Then I press "Add Device"
 Then I should see "Force Platforms"
 Then I should see "Device1"
 Then I press "select_0"
 Then I should see "Device"
 Then I should see "Force Platforms"
 Then I should see "Device1"
 
 Given I am on the home page
 Then I press "My Biomechanics Study"
 Then I press "Device"
 Then I should see "Add Device"
 Then I should see "Force Platforms"
 Then I should see "Device1"
 Then I press "Add Device"
 Then I should see "Force Platforms"
 Then I should see "Device1 already selected"
 