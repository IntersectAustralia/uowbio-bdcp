import static org.junit.Assert.*
import static org.junit.matchers.JUnitMatchers.*
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import groovy.sql.Sql

import au.org.intersect.bdcp.UserStore;
import au.org.intersect.bdcp.Project;

this.metaClass.mixin(cuke4duke.GroovyDsl)

/**
 * This is a set of steps that do the usual things on web pages, such as filling in form fields, clicking buttons and links etc
 * This should not contain application-specific steps - they should go in their own step file.
 */

Given(~"I am on the home page") { ->
    browser.get("http://localhost:8080/BDCP")
}

Given(~"I am on the show study page with \"(.*)\", \"(.*)\"") { String study_id, String project_id ->
	browser.get("http://localhost:8080/BDCP/project/${project_id}/study/show/${study_id}")
}

Given(~"I have logged in") { ->
	browser.get("http://localhost:8080/BDCP/login/auth")
	fieldElement = browser.findElement(By.name("j_username"))
	fieldElement.sendKeys("labman")
	fieldElement = browser.findElement(By.name("j_password"))
	fieldElement.sendKeys("password")
	browser.findElementById("Login").click()
}

Given(~"I have logged in as \"(.*)\"") { String username ->
	browser.get("http://localhost:8080/BDCP/login/auth")
	fieldElement = browser.findElement(By.name("j_username"))
	fieldElement.sendKeys(username)
	fieldElement = browser.findElement(By.name("j_password"))
	fieldElement.sendKeys("password")
	browser.findElementById("Login").click()
}

Given(~"I am on the email page") { ->
	browser.get("http://localhost:8080/BDCP/greenmail/list")
}

Given(~"I have created a project with \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\"") { String project_id, String project_title, String researcher_name, String student_number, String degree, String start_date, String end_date, String description, String supervisors, String username ->
	def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
	def userStoreId = (int)(getUserStore("${username}").id)
	sql.execute("INSERT INTO project (id,version, project_title, researcher_name, student_number, degree, start_date, end_date, description, supervisors, owner_id) VALUES ('${project_id}','0',${project_title}, ${researcher_name}, ${student_number}, ${degree}, '${start_date}', '${end_date}', ${description}, ${supervisors}, ${userStoreId});")
}

def getUserStore(String username) {
	def userStore
	def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
	sql.eachRow("Select * from user_store WHERE username = ?", [username]) {
		userStore = it.toRowResult()
	}
	return userStore
}

Given(~"I have created a study with \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\"") { String study_id, String project_id, String study_title, String uow_ethics_number, String has_additional_ethics_requirements, String description, industry_partners, collaborators, String start_date, String end_date, String number_of_participants, String inclusion_exclusion_criteria ->
	def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
	sql.execute("INSERT INTO study (id,version, project_id, study_title, uow_ethics_number, has_additional_ethics_reqs, description, industry_partners, collaborators, start_date, end_date, number_of_participants, inclusion_exclusion_criteria, date_created, last_updated) VALUES ('${study_id}','0', '${project_id}', ${study_title}, ${uow_ethics_number}, ${has_additional_ethics_requirements}, ${description}, ${industry_partners}, ${collaborators}, '${start_date}', '${end_date}', ${number_of_participants}, ${inclusion_exclusion_criteria}, now(), now());")
}

Given(~"I have created a collaborator study with \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\"") { String project_id, String study_id, String study_collaborator_id, String study_title, String uow_ethics_number, String has_additional_ethics_requirements, String description, industry_partners, collaborators, String start_date, String end_date, String number_of_participants, String inclusion_exclusion_criteria, String collaborator_name ->
	def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
	def collaboratorId = (int)(getUserStore("${collaborator_name}").id)
	sql.execute("INSERT INTO study (id,version, project_id, study_title, uow_ethics_number, has_additional_ethics_reqs, description, industry_partners, collaborators, start_date, end_date, number_of_participants, inclusion_exclusion_criteria, date_created, last_updated) VALUES ('${study_id}','0', '${project_id}', ${study_title}, ${uow_ethics_number}, ${has_additional_ethics_requirements}, ${description}, ${industry_partners}, ${collaborators}, '${start_date}', '${end_date}', ${number_of_participants}, ${inclusion_exclusion_criteria}, now(), now());")
	sql.execute("INSERT INTO study_collaborator (id, version, collaborator_id, study_id) VALUES ('${study_collaborator_id}', '0', ${collaboratorId}, '${study_id}')")
}

Given(~"I have created a component with \"(.*)\", \"(.*)\"") { String name, String description ->
	def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
	sql.execute("INSERT INTO component (id,version, study_id, name, description) VALUES ('-3000','0','-2000', ${name}, ${description});")
}

Given(~"I have created a session with \"(.*)\", \"(.*)\"") { String name, String description ->
	def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
	sql.execute("INSERT INTO study_session (id,version, component_id, name, description) VALUES ('-4000','0', '-3000', ${name}, ${description});")
}

Given(~"I have created a device grouping with \"(.*)\"") { String groupingName ->
    def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
    sql.execute("INSERT INTO device_group(id,version, grouping_name) VALUES ('-5000','0',${groupingName});")
}

Given(~"I have created a device with \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\"") { String name, String description, String manufacturer, String locationOfManufacturer, String model, String serialNumber, String uowAssetNumber, String dateOfPurchase, String dateOfDelivery, String purchasePrice, String vendor, String fundingSource, String maintServiceInfo ->
    def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver") 
    sql.execute("INSERT INTO device(id,version, device_group_id, name, description, manufacturer, location_of_manufacturer, model_name, serial_number, uow_asset_number, date_of_purchase, date_of_delivery, purchase_price, vendor, funding_source, maint_service_info) VALUES (nextval('hibernate_sequence'),'0','-5000', ${name}, ${description}, ${manufacturer}, ${locationOfManufacturer}, ${model}, ${serialNumber}, ${uowAssetNumber}, '${dateOfPurchase}', '${dateOfDelivery}', ${purchasePrice}, ${vendor}, ${fundingSource}, ${maintServiceInfo});")
}

Given(~"I have created a device field with \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\" for \"(.*)\"") { String fieldLabel, String fieldType, String staticContent, String mandatory, String deviceName ->
    def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver") 
	def row = sql.firstRow("SELECT id FROM device WHERE name=${deviceName}")
	def deviceId = row.id;
	row = sql.firstRow("SELECT count(id) as num FROM device_field WHERE device_id=${deviceId}")
        def fieldIndex = row.num
    sql.execute("INSERT INTO device_field(id,device_id,device_fields_idx,field_label,field_type,static_content,mandatory,date_created,last_updated,version) VALUES (nextval('hibernate_sequence'),${deviceId},${fieldIndex},${fieldLabel},${fieldType},${staticContent},'${mandatory}', '2011-03-01 00:00:00', '2011-03-01 00:00:00',0);")
}

Given(~"I have created a deviceField with \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\"") { String deviceName, String dateCreated, String lastUpdated, String mandatory, String fieldLabel, String fieldType, String fieldOptions ->
    def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
    def deviceId = (int)(getDevice("${deviceName}").id)
    def row = sql.firstRow("SELECT count(id) as num FROM device_field WHERE device_id=${deviceId}")
    def fieldIndex = row.num
    sql.execute("INSERT INTO device_field(id, version,device_id, device_fields_idx,date_created, last_updated, mandatory, field_label, field_type, field_options) VALUES (nextval('hibernate_sequence'), '0','${deviceId}', ${fieldIndex}, '${dateCreated}', '${lastUpdated}', '${mandatory}', ${fieldLabel}, ${fieldType}, ${fieldOptions});")
}

def getDevice(String name) {
    def device
    def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
    sql.eachRow("Select * from Device WHERE name = ?", [name]) {
        device = it.toRowResult()
    }
    return device
}

Given(~"I have deleted all emails") { ->
	browser.get("http://localhost:8080/BDCP/greenmail/clear")
}

Given(~"I follow \"(.*)\"") { String linkText->
	List<WebElement> links = browser.findElementsByTagName('a')
	WebElement element = links.find {
		it.text.toLowerCase().contains(linkText.toLowerCase())
	}
	element.click()
}

Then(~"I cannot follow \"(.*)\"") { String link ->
	try {
		WebElement element = browser.findElement(By.linkText(link))
		Assert.fail()
	} catch (org.openqa.selenium.NoSuchElementException nse) {
		assertNotNull(nse)
	} catch (Exception e) {
		Assert.fail()
	}
}

Given(~"I have cleared and filled in \"(.*)\" with \"(.*)\"") { String field, String text ->
	fieldElement = browser.findElement(By.name(field))
	fieldElement.clear()
	fieldElement.sendKeys(text)
}

When(~"I fill in \"(.*)\" with \"(.*)\"") { String field, String text ->
	fieldElement = browser.findElement(By.name(field))
    fieldElement.sendKeys(text)
}

When(~"I fill in \"(.*)\" with enter") { String field ->
    fieldElement = browser.findElement(By.name(field))
    fieldElement.sendKeys("\n")
}

When(~"I select \"(.*)\" from \"(.*)\"") { String value, String field ->
    fieldElement = browser.findElement(By.name(field))
	elements = fieldElement.findElements(By.tagName('option')).findAll { it -> it.getText().equals(value) }
	elements[0].click()
}

When(~"I select radiobutton \"(.*)\" from \"(.*)\"") { String value, String field ->
    List<WebElement> radioButtons = browser.findElements(By.name(field))
    for (WebElement radio: radioButtons)
    {
       if (radio.getAttribute("value").equals(value))
       {
           radio.click();
       }
    }
}

When(~"I press \"(.*)\"") { String button ->
    browser.findElementById(button).click()
}

When(~"I press browser back button"){
	browser.navigate().back()
}

Then(~"I navigate to \"(.*)\"") { String text ->
    browser.get(text)
}

Then(~"I should see \"(.*)\"") { String text ->
    assertThat(browser.findElementByTagName('body').text, containsString(text))
}

Then(~"I should see in \"(.*)\" phrase \"(.*)\"") { String elementId, String phrase ->
	element = browser.findElementById(elementId)
	assertNotNull(element)
	assertTrue(element.isDisplayed())
    assertThat(element.text, containsString(phrase))
}

Then(~"I should see words \"(.*)\"") { String text ->
	bodyText = browser.findElementByTagName('body').text
	text.split().each { word ->
		assertThat(bodyText, containsString(word))
	}
}

Then(~"I should have \"(.*)\" in text field named \"(.*)\"") { String text, String fieldname ->
	fieldElement = browser.findElement(By.name(fieldname))
    assertThat(fieldElement.getAttribute('value'), containsString(text))
}

Then(~"There must be an ID \"(.*)\" which is (visible|hidden)") { String elementId, String shown ->
	element = browser.findElementById(elementId)
    assertNotNull(element)
	assertEquals(shown,(element.isDisplayed() ? 'visible' : 'hidden'))
}

Then(~"There must be an ID \"(.*)\"") { String elementId ->
    assertNotNull(browser.findElementById(elementId))
}

Then(~"I clear \"(.*)\"") { String field ->
	fieldElement = browser.findElement(By.name(field))
	fieldElement.clear()
}

Then(~"I enable javascript")
{ 
	browser.setJavascriptEnabled(true)
}

Then(~"I disable javascript")
{
	browser.setJavascriptEnabled(false)
}

Then(~"I should see \"(.*)\" with value \"(.*)\"") { String field, String text ->
	fieldElement = browser.findElement(By.name(field))
	assertThat(fieldElement.getAttribute('value'), containsString(text))
}

Then(~"I should see \"(.*)\" selected with value \"(.*)\"") { String field, String text ->
	fieldElement = browser.findElement(By.name(field))
	selectedOptions = fieldElement.findElements(By.tagName('option')).findAll{ it -> it.getAttribute('selected') != null && it.getAttribute('selected') }
	if (selectedOptions.size() == 0) {
		selectedOptions = [fieldElement.findElement(By.tagName('option'))]
	}
	assertEquals(text,selectedOptions[0].getAttribute('value'))
}

Then(~"I select file \"(.*)\" from \"(.*)\"") { String filePath, String field ->
	fieldElement = browser.findElement(By.name(field))
	fieldElement.sendKeys(testFile.getAbsolutePath())
}

Then(~"I should see table \"(.*)\" with contents") { String tableId, cuke4duke.Table table ->
	webTable = browser.findElementsByCssSelector("table#${tableId} tbody tr").collect {
        def cols = it.findElementsByTagName('td')
        [cols[0].text, cols[1].text]
	}
	table.diffLists(webTable)
}


Then(~"I should see a 3 column table \"(.*)\" with contents") { String tableId, cuke4duke.Table table ->
	webTable = browser.findElementsByCssSelector("table#${tableId} tbody tr").collect {
		def cols = it.findElementsByTagName('td')
		[cols[0].text, cols[1].text, cols[2].text]
	}
	table.diffLists(webTable)
}

Then(~"I should see a 4 column table \"(.*)\" with contents") { String tableId, cuke4duke.Table table ->
	webTable = browser.findElementsByCssSelector("table#${tableId} tbody tr").collect {
		def cols = it.findElementsByTagName('td')
		[cols[0].text, cols[1].text, cols[2].text, cols[3].text]
	}
	table.diffLists(webTable)
}

Then(~"I print the page") {
	page = response.body
	println page
}

Then(~"I wait for ajax") {
	Thread.sleep(5000)
}

