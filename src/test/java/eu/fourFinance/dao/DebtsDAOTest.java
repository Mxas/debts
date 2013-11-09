package eu.fourFinance.dao;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
		evaluation.setLastPayDate(new Date());
		evaluation.setTotalPay(100d);
		evaluation.setPeriodicalPay(10d);
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
		Integer term = 55;
		Double totalPay = 1000d;
		Date date = date(5);
		Double periodicPay = 555d;
		;
		Debts debtExt = debtsDAO.createDebtExtension(debt, extencionDate,
				extencionRate, term, totalPay, date, periodicPay);

		assertEquals(extencionDate, debtExt.getLastPayDate());
		assertEquals(extencionRate, debtExt.getRate());
		assertEquals(term, debtExt.getTerm());
		assertEquals(totalPay, debtExt.getTotalPay());
		assertEquals(debt, debtExt.getExtencionOf());
		assertEquals(evaluation, debtExt.getEvaluation());
		assertEquals(periodicPay, debtExt.getPeriodicalPay());

	}

	@Test
	public void testGetAll() {
		Debts debt = debtsDAO.createDebt(evaluation);

		List<Debts> debts = debtsDAO.getGivenDebts();
		assertEquals(1, debts.size());
		assertEquals(debt, debts.get(0));

		debtsDAO.createDebt(evaluation);

		assertEquals(2, debtsDAO.getGivenDebts().size());
		Date extencionDate = date(1);
		Double extencionRate = 1.5d;
		Integer term = 55;
		Double totalPay = 1000d;
		Date date = date(5);
		Double periodicPay = 55d;

		debtsDAO.createDebtExtension(debt, extencionDate, extencionRate, term,
				totalPay, date, periodicPay);

		debtsDAO.createDebtExtension(debt, extencionDate, extencionRate, term,
				totalPay, date, periodicPay);

		assertEquals(4, debtsDAO.getGivenDebts().size());

	}

	private Date date(int i) {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_YEAR, i);
		return c.getTime();

	}

}
