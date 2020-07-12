import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;


@RunWith(Cucumber.class)
@CucumberOptions(
strict = true,
features = {"/Users/aliciahuang/repos/myProjects/hubdoc/src/test/resources/features/hubdoc.feature:20"},
plugin = {"json:/Users/aliciahuang/repos/myProjects/hubdoc/target/cucumber-parallel/2.json", "hubdoc.hubdocTestRunner:/Users/aliciahuang/repos/myProjects/hubdoc/target/cucumber-parallel/json/2"},
monochrome = false,
glue = {"hubdoc"})
public class Parallel02IT
{

@BeforeClass
public static void setupCATestSuite()
{

}
@AfterClass
public static void tearCaTestSuite()
{

}

}
