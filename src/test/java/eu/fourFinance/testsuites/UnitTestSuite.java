package eu.fourFinance.testsuites;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import eu.fourFinance.testsuites.categories.UnitTests;

@RunWith(Categories.class)
@IncludeCategory(UnitTests.class)
@SuiteClasses(AllTests.class)
public class UniTestSuite {

}
