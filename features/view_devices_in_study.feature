Feature: View Devices in Study
  In order manage my data
  As a Researcher
  I want to view a device in a study
 
 Background:
 Given I have logged in
 Given I have created a project with "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith"
 Given I have created a study with "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
 Given I have created a device grouping with "Force Platforms"
 Given I have created a device with "Device1", "Some Description", "Some Manufacturer", "Some location", "Some model", "Some serial number", "Some UOW Asset number", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "$10.00", "Intersect", "Some funding source", "Maintenance Service Info"
 
 Given I am on the home page
 And I follow "My Biomechanics Study"
 Then I follow "Devices"
 Then I press "Add Device"
 Then I should see "Force Platforms"
 Then I should see "Device1"
 Then I press "select_0"
 Then I press "save"
 Then I should see "Devices"
 Then I should see "Force Platforms"
 Then I should see "Device1"
 
 Scenario: View Device in Study
 Given I am on the home page
 Then I follow "My Biomechanics Study"
 Then I follow "Devices"
 Then I should see "Add Device"
 Then I should see "Force Platforms"
 Then I should see "Device1"
 
 