package eu.fourFinance.services;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

import eu.fourFinance.BaseTest;
import eu.fourFinance.model.Subject;
import eu.fourFinance.testsuites.categories.IntegrationTests;

@Category(IntegrationTests.class)
public class SubjectServiceTest extends BaseTest {

    @Autowired
    private SubjectService SubjectService;

    @Before
    public void setUp() {
        Assert.assertNotNull(SubjectService);
    }

    @Test
    public void testGetSubject() {
        String code = "bb";
        Subject s = SubjectService.getSubject(code);
        assertEquals(code, s.getCode());
        //if exists
        s = SubjectService.getSubject(code);
        assertEquals(code, s.getCode());
    }
}
