package eu.fourFinance.testsuites;

import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.extensions.cpsuite.ClasspathSuite.ClassnameFilters ;
import org.junit.extensions.cpsuite.ClasspathSuite.IncludeJars ;
import org.junit.runner.RunWith;

/**
 *
 * klasė panaudojanti ClasspathSuite ir susirenkanti testus iš classpath
 */
@RunWith(ClasspathSuite.class)
@IncludeJars(true)
@ClassnameFilters("eu.fourFinance.*")
public class AllTests {

}
