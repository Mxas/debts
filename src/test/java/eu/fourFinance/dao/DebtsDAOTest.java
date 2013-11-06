package eu.fourFinance.dao;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

import eu.fourFinance.BaseTest;
import eu.fourFinance.model.Debts;
import eu.fourFinance.model.Evaluation;
import eu.fourFinance.model.Subject;
import eu.fourFinance.testsuites.categories.IntegrationTests;

@Category(IntegrationTests.class)
public class DebtsDAOTest extends BaseTest {

	@Autowired
	private SubjectDAO subjectDAO;
	@Autowired
	private EvaluationDAO evaluationDAO;
	@Autowired
	private DebtsDAO debtsDAO;

	private Evaluation evaluation;

	@Before
	public void setUp() {
		Assert.assertNotNull(subjectDAO);
		Assert.assertNotNull(evaluationDAO);
		Assert.assertNotNull(debtsDAO);
		Subject subject = subjectDAO.createSubject("sss");

		evaluation = evaluationDAO.createEvaluation(subject, new Date(), 2000d,
				5, 1d, "a", 0d);
		assertEquals(subject.getId(), evaluation.getSubject().getId());
	}

	@Test
	public void testCreateDebt() {
		Debts debt = debtsDAO.createDebt(evaluation);

		assertEquals(evaluation.getId(), debt.getEvaluation().getId());
	}

	@Test
	public void testCreateExtension() {
		Debts debt = debtsDAO.createDebt(evaluation);
		Date extencionDate = date(1);
		Double extencionRate = 1.5d;
		Debts debtExt = debtsDAO.createDebtExtension(debt, extencionDate,
				extencionRate);
		assertEquals(extencionDate, debtExt.getExtencionDate());
		assertEquals(extencionRate, debtExt.getExtencionRate());
		assertEquals(debt, debtExt.getExtencionOf());

	}

	private Date date(int i) {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, i);
		return c.getTime();

	}

}
