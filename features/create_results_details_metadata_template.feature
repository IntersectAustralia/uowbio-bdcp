Feature: Create Results Details Metadata Template
  In order manage my data
  As an Administrator
  I want to create Results Details fields for the Results Details Template
 
 Background:
 Given I have logged in
 
 Scenario: Create Results Details Metadata Template
 Given I am on the home page
 And I press "system-administration"
 And I should see "System Administration"
 Then I press "results-administration"
 Then I should see "Results Administration"
 Then I should see "Results Details Template"
 Then I press "results-details-template"
 Then I press "Add Field"
 Then I should see "Add New Results Details Template Field"
 Then I fill in "fieldLabel" with "Location of Strap"
 Then I select radiobutton "TEXTAREA" from "fieldType"
 Then I press "mandatory"
 Then I press "save"
 Then I should see "Location of Strap saved"
 
 Then I should see "Results Details Template"
 Then I press "Add Field"
 Then I should see "Add New Results Details Template Field"
 Then I fill in "fieldLabel" with "Select a Radio Button Option"
 Then I select radiobutton "RADIO_BUTTONS" from "fieldType"
 Then I fill in "fieldOptions" with "Option1"
 Then I fill in "fieldOptions" with enter
 Then I fill in "fieldOptions" with "Option2"
 Then I fill in "fieldOptions" with enter
 Then I press "save"
 Then I should see "Select a Radio Button Option saved"
 
 Then I should see "Results Details Template"
 Then I press "Add Field"
 Then I should see "Add New Results Details Template Field"
 Then I fill in "fieldLabel" with "Select a Drop Down Option"
 Then I select radiobutton "DROP_DOWN" from "fieldType"
 Then I fill in "fieldOptions" with "Option1"
 Then I fill in "fieldOptions" with enter
 Then I fill in "fieldOptions" with "Option2"
 Then I fill in "fieldOptions" with enter
 Then I press "save"
 Then I should see "Select a Drop Down Option saved"
 
 Then I should see a 3 column table "listTable" with contents 
 | Location of Strap 		    | Text Area          | true  |
 | Select a Radio Button Option | Radio Buttons Show | false |
 | Select a Drop Down Option    | Drop Down Show     | false |
   