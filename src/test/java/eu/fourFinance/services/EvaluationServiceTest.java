package eu.fourFinance.services;

import static eu.fourFinance.services.LangService.*;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

import eu.fourFinance.BaseTest;
import eu.fourFinance.dao.DebtsDAO;
import eu.fourFinance.dao.EvaluationDAO;
import eu.fourFinance.dao.SubjectDAO;
import eu.fourFinance.model.Debts;
import eu.fourFinance.model.Evaluation;
import eu.fourFinance.model.Subject;
import eu.fourFinance.testsuites.categories.IntegrationTests;

@Category(IntegrationTests.class)
public class EvaluationServiceTest extends BaseTest {

	@Autowired
	private SubjectDAO subjectDAO;

	@Autowired
	private EvaluationDAO evaluationDAO;

	@Autowired
	private EvaluationService evaluationService;

	@Autowired
	private DebtsDAO debtsDAO;

	@Autowired
	private DebtsService debtsService;

	private static final String SUBJECT_CODE = "c";
	private Subject subject;

	@Before
	public void setUp() {
		Assert.assertNotNull(subjectDAO);
		Assert.assertNotNull(evaluationDAO);
		Assert.assertNotNull(debtsDAO);
		Assert.assertNotNull(evaluationService);
		subject = subjectDAO.createSubject(SUBJECT_CODE);
		assertEquals(SUBJECT_CODE, subject.getCode());
	}

	@Test
	public void testSimpleEvaluation() {

		Evaluation evaluation = evaluationDAO.createEvaluation(subject,
				new Date(), 2000d, 50, 1d, "a", 0d);

		Assert.assertTrue(evaluationService.evaluateRisk(evaluation).isEmpty());

		assertEquals(EvaluationService.ACCEPTABLE_COEF, evaluation
				.getCalculatedCoef().intValue());
	}

	@Test
	public void testLowLoanEvaluation() {

		Evaluation evaluation = evaluationDAO.createEvaluation(subject,
				new Date(), 20d, 50, 1d, "a", 0d);

		List<String> messages = evaluationService.evaluateRisk(evaluation);
		Assert.assertTrue(!messages.isEmpty());
		assertEquals(1, messages.size());

		Assert.assertTrue(messages.contains(MSG_TOO_SMALL_LOAN));

		assertEquals(0, evaluation.getCalculatedCoef().intValue());
	}

	@Test
	public void testTooBigLoanEvaluation() {

		Evaluation evaluation = evaluationDAO.createEvaluation(subject,
				DateUtils.setHours(new Date(), 10), 200000d, 50, 1d, "a", 0d);

		List<String> messages = evaluationService.evaluateRisk(evaluation);
		Assert.assertTrue(!messages.isEmpty());
		assertEquals(1, messages.size());

		Assert.assertTrue(messages.contains(MSG_TOO_BIG_LOAN));

		assertEquals(0, evaluation.getCalculatedCoef().intValue());
	}

	@Test
	public void testShortTermEvaluation() {

		Evaluation evaluation = evaluationDAO.createEvaluation(subject,
				new Date(), 2100d, EvaluationService.MIN_TERM - 1, 1d, "a", 0d);

		List<String> messages = evaluationService.evaluateRisk(evaluation);
		Assert.assertTrue(!messages.isEmpty());
		assertEquals(1, messages.size());

		Assert.assertTrue(messages.contains(MSG_TOO_SHORT_TERM));

		assertEquals(0, evaluation.getCalculatedCoef().intValue());
	}

	@Test
	public void testTooLongLoanEvaluation() {

		Evaluation evaluation = evaluationDAO.createEvaluation(subject,
				new Date(), 2000d, EvaluationService.MAX_TERM + 1, 1d, "a", 0d);

		List<String> messages = evaluationService.evaluateRisk(evaluation);
		Assert.assertTrue(!messages.isEmpty());
		assertEquals(1, messages.size());

		Assert.assertTrue(messages.contains(MSG_TOO_LONG_TERM));

		assertEquals(0, evaluation.getCalculatedCoef().intValue());
	}

	@Test
	public void testNightWitMaxLoanEvaluation() {

		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		for (int hour = EvaluationService.UNACCEPTABLE_HOURS_FROM; hour <= EvaluationService.UNACCEPTABLE_HOURS_FROM; hour++) {
			c.set(Calendar.HOUR_OF_DAY, hour);
			assertEquals(hour, c.get(Calendar.HOUR_OF_DAY));

			Evaluation evaluation = evaluationDAO.createEvaluation(subject,
					c.getTime(), EvaluationService.MAX_LOAN, 50, 1d, "a", 0d);

			List<String> messages = evaluationService.evaluateRisk(evaluation);
			Assert.assertTrue(!messages.isEmpty());
			assertEquals(1, messages.size());

			Assert.assertTrue(messages.contains(MSG_MAX_LOAN_AND_TIME));

			assertEquals(0, evaluation.getCalculatedCoef().intValue());
		}
	}

	@Test
	public void testMaxApplicationPerDay() {

		String ip = "1.1.1.1";
		for (int i = 0; i <= EvaluationService.MAX_APPLICATIONS_PER_DAY; i++) {
			Evaluation evaluation = evaluationDAO.createEvaluation(subject,
					new Date(), 1000d, 50, 1d, ip, 0d);
			evaluation.setLastPayDate(new Date());
			evaluation.setTotalPay(100d);
			evaluation.setPeriodicalPay(10d);

			debtsDAO.createDebt(evaluation);
		}

		Evaluation evaluation = evaluationDAO.createEvaluation(subject,
				new Date(), 2000d, 50, 1d, ip, 0d);

		List<String> messages = evaluationService.evaluateRisk(evaluation);
		Assert.assertTrue(!messages.isEmpty());
		assertEquals(1, messages.size());

		Assert.assertTrue(messages
				.contains(MSG_MAX_APPLICATION_FROM_IP_PER_DAY));

		assertEquals(0, evaluation.getCalculatedCoef().intValue());
	}

	@Test
	public void testMulltitalFail() {
		String ip = "1.1.1.1";
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		for (int i = 0; i <= EvaluationService.MAX_APPLICATIONS_PER_DAY; i++) {
			Evaluation evaluation = evaluationDAO.createEvaluation(subject,
					date, 1000d, 5, 1d, ip, 0d);
			evaluation.setLastPayDate(new Date());
			evaluation.setTotalPay(100d);
			evaluation.setPeriodicalPay(10d);

			debtsDAO.createDebt(evaluation);
		}
		for (int hour = EvaluationService.UNACCEPTABLE_HOURS_FROM; hour <= EvaluationService.UNACCEPTABLE_HOURS_FROM; hour++) {
			c.set(Calendar.HOUR_OF_DAY, hour);
			assertEquals(hour, c.get(Calendar.HOUR_OF_DAY));

			Evaluation evaluation = evaluationDAO.createEvaluation(subject,
					c.getTime(), EvaluationService.MAX_LOAN + 1, 5, 1d, ip, 0d);

			List<String> messages = evaluationService.evaluateRisk(evaluation);
			Assert.assertTrue(!messages.isEmpty());

			Assert.assertTrue(messages
					.contains(MSG_MAX_APPLICATION_FROM_IP_PER_DAY));
			Assert.assertTrue(messages.contains(MSG_TOO_BIG_LOAN));
			Assert.assertTrue(messages.contains(MSG_MAX_LOAN_AND_TIME));
			Assert.assertTrue(messages.contains(MSG_TOO_SHORT_TERM));

			assertEquals(4, messages.size());

			assertEquals(0, evaluation.getCalculatedCoef().intValue());

		}

	}

	@Test
	public void testCreate() {

		Date date = new Date();
		Double debt = 1000d;
		Double rate = new Double(EvaluationService.LOAN_RATE / 100d);
		Integer term = 12;
		String requestIP = "1";
		Double calculatedCoef = 0d;
		Evaluation s = evaluationService.createEvaluation(subject, date, debt,
				term, requestIP);
		assertEquals(rate, s.getRate());
		assertEquals(debt, s.getDebt());
		assertEquals(term, s.getTerm());
		assertEquals(requestIP, s.getRequestIP());
		assertEquals(calculatedCoef, s.getCalculatedCoef());
		assertEquals(subject, s.getSubject());

	}

	@Test
	public void testCalcLoanDetails() {

		Date date = new Date();
		Double debt = 1000d;
		Double rate = new Double(EvaluationService.LOAN_RATE / 100d);
		Integer term = 12;
		String requestIP = "1";
		Double calculatedCoef = 1d;
		Evaluation s = evaluationService.createEvaluation(subject, date, debt,
				term, requestIP);
		evaluationService.evaluateRisk(s);

		evaluationService.calcLoanDetails(s);
		assertEquals(rate, s.getRate());
		assertEquals(debt, s.getDebt());
		assertEquals(term, s.getTerm());
		assertEquals(requestIP, s.getRequestIP());
		assertEquals(calculatedCoef, s.getCalculatedCoef());
		assertEquals(subject, s.getSubject());
		assertEquals(DateUtils.addWeeks(s.getDate(), term), s.getLastPayDate());

		Assert.assertTrue(s.getPeriodicalPay() > 0);
		Assert.assertTrue(s.getTotalPay() > 0);
	}

	@Test
	public void testApplay() {

		Date date = new Date();
		Double debt = 1000d;
		Double rate = new Double(EvaluationService.LOAN_RATE / 100d);
		Integer term = 12;
		String requestIP = "1";
		Evaluation s = evaluationService.createEvaluation(subject, date, debt,
				term, requestIP);
		evaluationService.evaluateRisk(s);

		evaluationService.calcLoanDetails(s);

		Debts de = evaluationService.applay(s);

		assertEquals(rate, de.getRate());
		assertEquals(debt, de.getDebt());
		assertEquals(term, de.getTerm());
		assertEquals(subject, de.getEvaluation().getSubject());
		assertEquals(DateUtils.addWeeks(de.getDate(), term), s.getLastPayDate());

		Assert.assertTrue(de.getPeriodicalPay() > 0);
		Assert.assertTrue(de.getTotalPay() > 0);
	}

	@Test
	public void testGetAll() {

		Debts de = createDebt();
		createDebt();
		createDebt();
		Debts ext = debtsService.extendDebt(de);
		debtsService.getDebt(ext.getId());

		List<Debts> list = evaluationService.getGivenDebts();
		assertEquals(4, list.size());
	}

	private Debts createDebt() {
		Evaluation s = evaluationService.createEvaluation(subject, new Date(),
				1000d, 12, "d");
		evaluationService.evaluateRisk(s);

		evaluationService.calcLoanDetails(s);

		Debts de = evaluationService.applay(s);
		return de;
	}
}
