import org.openqa.selenium.By
import static org.junit.Assert.*
import static org.junit.matchers.JUnitMatchers.*

this.metaClass.mixin(cuke4duke.GroovyDsl)

Given(~"I am on the create project page") { ->
  browser.get("http://localhost:8080/BDCP/project/create")
}
