Feature: Add Device to Study with Metadata
  In order manage my data
  As a Researcher
  I want to add a device to a study with metadata
 
 Background:
 Given I have logged in
 Given I have created a project with "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith"
 Given I have created a study with "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
 Given I have created a device grouping with "Force Platforms"
 Given I have created a device with "Device1", "Some Description", "Some Manufacturer", "Some location", "Some model", "Some serial number", "Some UOW Asset number", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "$10.00", "Intersect", "Some funding source", "Maintenance Service Info"
 Given I have created a deviceField with "-101", "Device1", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "Location of Strap", "TEXT", ""
 Given I have created a deviceField with "-102", "Device1", "2011-03-01 00:01:00", "2011-03-01 00:01:00", "Location of Text Area", "TEXTAREA", ""
 Given I have created a deviceField with "-103", "Device1", "2011-03-01 00:02:00", "2011-03-01 00:02:00", "Select a Radio Button Option", "RADIO_BUTTONS", "Option1"
 Given I have created a deviceField with "-104", "Device1", "2011-03-01 00:03:00", "2011-03-01 00:03:00", "Select a Drop Down Option", "DROP_DOWN", "Option1"
 Given I have created a deviceField with "-105", "Device1", "2011-03-01 00:04:00", "2011-03-01 00:04:00", "Select a time", "TIME", ""
 Given I have created a deviceField with "-106", "Device1", "2011-03-01 00:05:00", "2011-03-01 00:05:00", "Select a date", "DATE", ""
 
 Scenario: Add Device to Study with Metadata
 Given I am on the home page
 And I follow "My Biomechanics Study"
 Then I follow "Devices"
 Then I press "Add Device"
 Then I should see "Force Platforms"
 Then I should see "Device1"
 Then I press "select_0"
 Then I fill in "studyDeviceFields[0].text" with "Some text"
 Then I fill in "studyDeviceFields[1].textArea" with "Some textarea"
 Then I select radiobutton "Option1" from "studyDeviceFields[2].radioButtonsOption"
 Then I select "Option1" from "studyDeviceFields[3].dropDownOption"
 Then I select "01" from "studyDeviceFields[4].time_hour"
 Then I select "01" from "studyDeviceFields[4].time_minute"
 Then I select "1" from "studyDeviceFields[5].date_day"
 Then I select "March" from "studyDeviceFields[5].date_month"
 Then I select "2011" from "studyDeviceFields[5].date_year"
 Then I press "save"
 Then I should see "Devices"
 Then I should see "Force Platforms"
 Then I should see "Device1"
 
 Given I am on the home page
 Then I follow "My Biomechanics Study"
 Then I follow "Devices"
 Then I should see "Add Device"
 Then I should see "Force Platforms"
 Then I should see "Device1"
 Then I press "Add Device"
 Then I should see "Force Platforms"
 Then I should see "Device1 ... already selected"
 