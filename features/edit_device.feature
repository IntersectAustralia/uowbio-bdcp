Feature: Create Device
  In order manage my data
  As an Administrator
  I want to create a Device in a Device Grouping
 
 Background:
 Given I have logged in
 Given I have created a device grouping with "Force Platforms"
 Given I have created a device with "Device1", "Some Description", "Some Manufacturer", "Some location", "Some model", "Some serial number", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "$10.00", "Intersect", "Some funding body"
 
 Scenario: Create Device
 Given I am on the home page
 And I press "system-administration"
 And I should see "System Administration"
 Then I press "device-administration"
 Then I should see "Device Administration"
 Then I press "Force Platforms"
 Then I should see "Force Platforms"
 Then I should see "Add new device"
 Then I press "Add new device"
 Then I should see "Add New Device"
 Then I fill in "name" with "Device1"
 Then I fill in "description" with "Some Description"
 Then I fill in "manufacturer" with "Some manufacturer"
 Then I fill in "locationOfManufacturer" with "Some location"
 Then I fill in "model" with "Some model"
 Then I fill in "serialNumber" with "Some serial number"
 Then I fill in "dateOfPurchase" with ""
 Then I fill in "dateOfDelivery" with ""
 Then I fill in "purchasePrice" with "$10.00"
 Then I fill in "vendor" with "Intersect"
 Then I fill in "fundingBody" with "Some funding body"
 Then I press "save"
 Then I should see "Device Device1 saved"
 Then I should see "Force Platforms"
 
   