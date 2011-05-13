import static org.junit.Assert.*
import static org.junit.matchers.JUnitMatchers.*
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import groovy.sql.Sql

import au.org.intersect.bdcp.Project;

this.metaClass.mixin(cuke4duke.GroovyDsl)



/**
 * This is a set of steps that do the usual things on web pages, such as filling in form fields, clicking buttons and links etc
 * This should not contain application-specific steps - they should go in their own step file.
 */

Given(~"I am on the home page") { ->
    browser.get("http://localhost:8080/BDCP")
}

Given(~"I have logged in") { ->
	browser.get("http://localhost:8080/BDCP/login/auth")
	fieldElement = browser.findElement(By.name("j_username"))
	fieldElement.sendKeys("chrisk")
	fieldElement = browser.findElement(By.name("j_password"))
	fieldElement.sendKeys("password")
	browser.findElementById("Login").click()
}


Given(~"I am on the email page") { ->
	browser.get("http://localhost:8080/BDCP/greenmail/list")
}

Given(~"I have created a project with \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\"") { String project_title, String researcher_name, String degree, String start_date, String end_date, String description, String supervisors ->
	def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
	sql.execute("INSERT INTO project (id,version, project_title, researcher_name, degree, start_date, end_date, description, supervisors) VALUES ('-1','0',${project_title}, ${researcher_name}, ${degree}, '${start_date}', '${end_date}', ${description}, ${supervisors});")
}

Given(~"I have created a study with \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\"") { String study_title, String uow_ethics_number, String has_additional_ethics_requirements, String description, industry_partners, collaborators, String start_date, String end_date, String number_of_participants, String inclusion_exclusion_criteria ->
	def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
	sql.execute("INSERT INTO study (id,version, project_id, study_title, uow_ethics_number, has_additional_ethics_requirements, description, industry_partners, collaborators, start_date, end_date, number_of_participants, inclusion_exclusion_criteria) VALUES ('-2','0', '-1', ${study_title}, ${uow_ethics_number}, ${has_additional_ethics_requirements}, ${description}, ${industry_partners}, ${collaborators}, '${start_date}', '${end_date}', ${number_of_participants}, ${inclusion_exclusion_criteria});")
}

Given(~"I have created a component with \"(.*)\", \"(.*)\"") { String name, String description ->
	def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
	sql.execute("INSERT INTO component (id,version, study_id, name, description) VALUES ('-3','0','-2', ${name}, ${description});")
}

Given(~"I have created a session with \"(.*)\", \"(.*)\"") { String name, String description ->
	def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
	sql.execute("INSERT INTO session (id,version, component_id, name, description) VALUES ('-4','0', '-3', ${name}, ${description});")
}

Given(~"I have created a device grouping with \"(.*)\"") { String groupingName ->
    def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
    sql.execute("INSERT INTO device_group(id,version, grouping_name) VALUES ('-5','0',${groupingName});")
}

Given(~"I have created a device with \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\"") { String name, String description, String manufacturer, String locationOfManufacturer, String model, String serialNumber, String dateOfPurchase, String dateOfDelivery, String purchasePrice, String vendor, String fundingBody ->
    def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver") 
    sql.execute("INSERT INTO device(id,version, device_group_id, name, description, manufacturer, location_of_manufacturer, model, serial_number, date_of_purchase, date_of_delivery, purchase_price, vendor, funding_body) VALUES ('-6','0','-5', ${name}, ${description}, ${manufacturer}, ${locationOfManufacturer}, ${model}, ${serialNumber}, '${dateOfPurchase}', '${dateOfDelivery}', '${purchasePrice}', ${vendor}, ${fundingBody});")
}

Given(~"I have deleted all emails") { ->
	browser.get("http://localhost:8080/BDCP/greenmail/clear")
}

Given(~"I follow \"(.*)\"") { String link->
	WebElement element = browser.findElement(By.linkText(link))
	element.click()
}

When(~"I fill in \"(.*)\" with \"(.*)\"") { String field, String text ->
	fieldElement = browser.findElement(By.name(field))
    fieldElement.sendKeys(text)
}

When(~"I select \"(.*)\" from \"(.*)\"") { String value, String field ->
    fieldElement = browser.findElement(By.name(field))
	Select select = new Select(fieldElement)
	select.selectByVisibleText(value);
}

When(~"I press \"(.*)\"") { String button ->
    browser.findElementById(button).click()
}

When(~"I press browser back button"){
	browser.navigate().back()
}

Then(~"I should see \"(.*)\"") { String text ->
    assertThat(browser.findElementByTagName('body').text, containsString(text))
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
	assertThat(fieldElement.getValue(), containsString(text))
}

Then(~"I should see \"(.*)\" selected with value \"(.*)\"") { String field, String text ->
	fieldElement = browser.findElement(By.name(field))
	Select select = new Select(fieldElement)
	assertThat(select.getFirstSelectedOption().getValue(), containsString(text))
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
