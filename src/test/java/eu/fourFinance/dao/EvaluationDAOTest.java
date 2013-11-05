package eu.fourFinance.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

import eu.fourFinance.BaseTest;
import eu.fourFinance.model.Evaluation;
import eu.fourFinance.model.Subject;
import eu.fourFinance.testsuites.IntegrationTests;
import eu.fourFinance.utils.DateUtils;

@Category(IntegrationTests.class)
public class EvaluationDAOTest extends BaseTest {

	@Autowired
	private SubjectDAO subjectDAO;

	@Autowired
	private EvaluationDAO evaluationDAO;

	@Autowired
	private DebtsDAO debtsDAO;

	private static final String SUBJECT_CODE = "c";
	private Subject subject;

	@Before
	public void setUp() {
		Assert.assertNotNull(subjectDAO);
		Assert.assertNotNull(evaluationDAO);
		subject = subjectDAO.createSubject(SUBJECT_CODE);
		assertEquals(SUBJECT_CODE, subject.getCode());
	}

	@Test
	public void testCreate() {

		Date date = new Date();
		Double debt = 1000d;
		Double rate = 1d;
		Integer term = 12;
		String requestIP = "1";
		Double calculatedCoef = 0d;
		Evaluation s = evaluationDAO.createEvaluation(subject, date, debt,
				term, rate, requestIP, calculatedCoef);
		assertEquals(rate, s.getRate());
		assertEquals(debt, s.getDebt());
		assertEquals(term, s.getTerm());
		assertEquals(requestIP, s.getRequestIP());
		assertEquals(calculatedCoef, s.getCalculatedCoef());
		assertEquals(subject, s.getSubject());

	}

	@Test
	public void testGetSubjectEvaluation() {
		evaluationDAO.createEvaluation(subject, new Date(), 1000d, 5, 1d, "a",
				0d);
		evaluationDAO.createEvaluation(subject, new Date(), 2000d, 5, 1d, "a",
				0d);

		assertEquals(2, evaluationDAO.getSubjectEvaluation(subject).size());
	}

	@Test
	public void testCountGivenLoan() {
		String ip = "1.1.1.1";
		Date date = new Date();
		debtsDAO.createDebt(evaluationDAO.createEvaluation(subject, date ,
				1000d, 5, 1d, ip, 0d));
		debtsDAO.createDebt(evaluationDAO.createEvaluation(subject, date,
				2000d, 5, 1d, ip, 0d));

		Date from = DateUtils.getDateDayStart(date);
		Date till = DateUtils.getDateDayEnd(date);;
		assertEquals(2, evaluationDAO.countGivenLoan(ip, from, till)
				.longValue());
	}

}
