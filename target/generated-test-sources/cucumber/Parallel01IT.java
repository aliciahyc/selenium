import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;


@RunWith(Cucumber.class)
@CucumberOptions(
strict = true,
features = {"/Users/aliciahuang/repos/myProjects/hubdoc/src/test/resources/features/hubdoc.feature:10"},
plugin = {"json:/Users/aliciahuang/repos/myProjects/hubdoc/target/cucumber-parallel/1.json", "hubdoc.hubdocTestRunner:/Users/aliciahuang/repos/myProjects/hubdoc/target/cucumber-parallel/json/1"},
monochrome = false,
glue = {"hubdoc"})
public class Parallel01IT
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
