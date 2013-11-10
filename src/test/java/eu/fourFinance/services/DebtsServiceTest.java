package eu.fourFinance.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

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
import eu.fourFinance.services.impl.DebtsServiceImpl.CalculationsResult;
import eu.fourFinance.testsuites.categories.IntegrationTests;
import eu.fourFinance.testsuites.categories.UnitTests;

@Category(IntegrationTests.class)
public class DebtsServiceTest extends BaseTest {

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
		Assert.assertNotNull(debtsService);
		subject = subjectDAO.createSubject(SUBJECT_CODE);
		assertEquals(SUBJECT_CODE, subject.getCode());
	}

	@Test
	public void testGetDebt() {

		Debts de = createDebt();
		createDebt();
		assertEquals(de, debtsService.getDebt(de.getId()));
		Debts ext = debtsService.extendDebt(de);
		assertEquals(ext, debtsService.getDebt(ext.getId()));
	}

	@Test
	@Category(UnitTests.class)
	public void testCalculate() {
		Double rate = 2d;
		Double loan = 1000d;
		Integer term = 10;
		Date date = new Date();
		CalculationsResult r = debtsService.claculate(rate, loan, term, date);

		assertEquals(DateUtils.addWeeks(date, term), r.getEndDate());
		assertTrue(loan < r.getTotalPay());
		assertTrue(loan / term < r.getPeriodicalPay());

	}



	private Debts createDebt() {
		Date date = new Date();
		Double debt = 1000d;
		Integer term = 12;
		String requestIP = "1";
		Evaluation s = evaluationService.createEvaluation(subject, date, debt,
				term, requestIP);
		evaluationService.evaluateRisk(s);

		evaluationService.calcLoanDetails(s);

		Debts de = evaluationService.applay(s);
		return de;
	}

}
