//import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import groovy.sql.Sql

this.metaClass.mixin(cuke4duke.GroovyDsl)

private final String LOREM_IPSUM_TEXT = "lorem ipsum dolor sit amet";
private final String FILE_HTML = "<div>" + LOREM_IPSUM_TEXT + "</div>";

Before() {
  //browser = new FirefoxDriver()
  browser = new HtmlUnitDriver()
  testFile = createTmpFile(FILE_HTML);
  
}

After() {
  browser.close()
  browser.quit()
  
  def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
  sql.execute("delete from participant_form")
  sql.execute("delete from participant")
  sql.execute("delete from study")
  sql.execute("delete from project")
}

private File createTmpFile(String content) throws IOException {
	File f = File.createTempFile("webdriver", "tmp");
	f.deleteOnExit();

	OutputStream out = new FileOutputStream(f);
	PrintWriter pw = new PrintWriter(out);
	pw.write(content);
	pw.flush();
	pw.close();
	out.close();

	return f;
  }
