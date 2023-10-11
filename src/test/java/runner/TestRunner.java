package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        plugin = "json:target/jsonReports/cucumber-report.json",// Pointing to directory with feature files
        glue = "stepDefinitions" // Package with step definition classes
)
public class TestRunner {

}