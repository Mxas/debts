package eu.fourFinance.services;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import eu.fourFinance.BaseTest;
import eu.fourFinance.dao.DebtsDAO;
import eu.fourFinance.dao.EvaluationDAO;
import eu.fourFinance.dao.SubjectDAO;
import eu.fourFinance.model.Evaluation;
import eu.fourFinance.model.Subject;

public class EvaluationServiceTest extends BaseTest {

	@Autowired
	private SubjectDAO subjectDAO;

	@Autowired
	private EvaluationDAO evaluationDAO;

	@Autowired
	private EvaluationService evaluationService;

	@Autowired
	private DebtsDAO debtsDAO;

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
				new Date(), 2000d, 5, 1d, "a", 0d);

		Assert.assertTrue(evaluationService.evaluateRisk(evaluation).isEmpty());

		assertEquals(EvaluationService.ACCEPTABLE_COEF, evaluation
				.getCalculatedCoef().intValue());
	}
}
