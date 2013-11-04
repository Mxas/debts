package eu.fourFinance;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Log4jConfigurer;

@ContextConfiguration(locations = "classpath:/conf/beans/**/*.xml")
@TransactionConfiguration(transactionManager = "transactionManager")
public abstract class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Log4jConfigurer.initLogging("classpath:conf/log4jConfig.xml");
    }

    @Override
    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
}
