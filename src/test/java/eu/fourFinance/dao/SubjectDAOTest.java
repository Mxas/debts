package eu.fourFinance.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import eu.fourFinance.model.Subject;

@ContextConfiguration(locations = { "classpath:main-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SubjectDAOTest {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private SubjectDAO subjectDAO;

	@Before
	public void setUp() {
		Assert.notNull(subjectDAO);
		Assert.notNull(em);
	}

	@Test
	public void testCreateSubject() {
		String code = "aaaa";
		Subject s = subjectDAO.createSubject(code);
		Assert.notNull(s);
		org.junit.Assert.assertEquals(code, s.getCode());
	}

}
