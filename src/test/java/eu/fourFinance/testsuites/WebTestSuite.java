package eu.fourFinance.testsuites;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import eu.fourFinance.testsuites.categories.WebTests;

@RunWith(Categories.class)
@IncludeCategory(WebTests.class)
@SuiteClasses(AllTests.class)
public class WebTestSuite {

}
