//import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import groovy.sql.Sql

this.metaClass.mixin(cuke4duke.GroovyDsl)

Before() {
  //browser = new FirefoxDriver()
  browser = new HtmlUnitDriver()
}

After() {
  browser.close()
  browser.quit()
  
  def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
  sql.execute("delete from participant")
  sql.execute("delete from study")
  sql.execute("delete from project")
}

