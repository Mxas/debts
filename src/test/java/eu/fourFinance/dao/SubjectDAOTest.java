package eu.fourFinance.dao;

import static org.junit.Assert.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Assert;

import eu.fourFinance.BaseTest;
import eu.fourFinance.model.Subject;

public class SubjectDAOTest extends BaseTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private SubjectDAO subjectDAO;

    @Before
    public void setUp() {
        Assert.assertNotNull(subjectDAO);
        Assert.assertNotNull(em);
    }

    @Test
    public void testCreateSubject() {
        String code = "aaaa";
        Subject s = subjectDAO.createSubject(code);
        assertNotNull(s);
        assertEquals(code, s.getCode());
    }

    @Test
    public void testGetSubject() {
        String code = "bb";
        Subject s = subjectDAO.getSubject(code);
        assertEquals(code, s.getCode());
        s = subjectDAO.getSubject(code);
        assertEquals(code, s.getCode());
    }

}
