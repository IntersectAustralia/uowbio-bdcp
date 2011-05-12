Feature: Create Device
  In order manage my data
  As an Administrator
  I want to create a Device in a Device Grouping
 
 Background:
 Given I have logged in
 Given I have created a device grouping with "Force Platforms"
 Given I have created a device with "Device1"

 Scenario: Create Device
 Given I am on the home page
 And I press "system-administration"
 And I should see "System Administration"
 Then I press "device-administration"
 Then I should see "Device Administration"
 Then I press "Force Platforms"
 Then I should see "Force Platforms"
 
 
   