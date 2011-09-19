Feature: View Devices in Study
  In order manage my data
  As a Researcher
  I want to view a device in a study
  I want to view manuals associated with a device in a study
 
 Background:
 Given I have logged in as "labman"
 Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "labman"
 Given I have created a study with "-2000", "-1000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
 Given I have created a device grouping with "Force Platforms"
 Given I have created a device with "Device1", "Some Description", "Some Manufacturer", "Some location", "Some model", "Some serial number", "Some UOW Asset number", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "$10.00", "Intersect", "Some funding source", "Maintenance Service Info"
 Given I have created a device manual form with "deviceManual1", "deviceManual1.txt", "Device1"
 
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
 Then I press "forms[0]"
 Then I should see "Device manuals for Device1"
 Then I press "return"
 Then I should see "Force Platforms"
 Then I press "forms[0]"
 Then I should see "Device manuals for Device1"
 