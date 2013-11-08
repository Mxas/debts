package eu.fourFinance.services;

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
import eu.fourFinance.dao.DebtsDAO;
import eu.fourFinance.dao.EvaluationDAO;
import eu.fourFinance.dao.SubjectDAO;
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

        Evaluation evaluation = evaluationDAO.createEvaluation(subject, new Date(), 2000d, 50, 1d, "a", 0d);

        Assert.assertTrue(evaluationService.evaluateRisk(evaluation).isEmpty());

        assertEquals(EvaluationService.ACCEPTABLE_COEF, evaluation.getCalculatedCoef().intValue());
    }

    @Test
    public void testLowLoanEvaluation() {

        Evaluation evaluation = evaluationDAO.createEvaluation(subject, new Date(), 20d, 50, 1d, "a", 0d);

        List<String> messages = evaluationService.evaluateRisk(evaluation);
        Assert.assertTrue(!messages.isEmpty());
        assertEquals(1, messages.size());

        Assert.assertTrue(messages.contains(EvaluationService.MSG_TOO_SMALL_LOAN));

        assertEquals(0, evaluation.getCalculatedCoef().intValue());
    }

    @Test
    public void testTooBigLoanEvaluation() {

        Evaluation evaluation = evaluationDAO.createEvaluation(subject, new Date(), 200000d, 50, 1d, "a", 0d);

        List<String> messages = evaluationService.evaluateRisk(evaluation);
        Assert.assertTrue(!messages.isEmpty());
        assertEquals(1, messages.size());

        Assert.assertTrue(messages.contains(EvaluationService.MSG_TOO_BIG_LOAN));

        assertEquals(0, evaluation.getCalculatedCoef().intValue());
    }

    @Test
    public void testShortTermEvaluation() {

        Evaluation evaluation = evaluationDAO.createEvaluation(subject, new Date(), 2100d, EvaluationService.MIN_TERM - 1, 1d, "a", 0d);

        List<String> messages = evaluationService.evaluateRisk(evaluation);
        Assert.assertTrue(!messages.isEmpty());
        assertEquals(1, messages.size());

        Assert.assertTrue(messages.contains(EvaluationService.MSG_TOO_SHORT_TERM));

        assertEquals(0, evaluation.getCalculatedCoef().intValue());
    }

    @Test
    public void testTooLongLoanEvaluation() {

        Evaluation evaluation = evaluationDAO.createEvaluation(subject, new Date(), 2000d, EvaluationService.MAX_TERM + 1, 1d, "a", 0d);

        List<String> messages = evaluationService.evaluateRisk(evaluation);
        Assert.assertTrue(!messages.isEmpty());
        assertEquals(1, messages.size());

        Assert.assertTrue(messages.contains(EvaluationService.MSG_TOO_LONG_TERM));

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

            Evaluation evaluation = evaluationDAO.createEvaluation(subject, c.getTime(), EvaluationService.MAX_LOAN, 50, 1d, "a", 0d);

            List<String> messages = evaluationService.evaluateRisk(evaluation);
            Assert.assertTrue(!messages.isEmpty());
            assertEquals(1, messages.size());

            Assert.assertTrue(messages.contains(EvaluationService.MSG_MAX_LOAN_AND_TIME));

            assertEquals(0, evaluation.getCalculatedCoef().intValue());
        }
    }

    @Test
    public void testMaxApplicationPerDay() {

        String ip = "1.1.1.1";
        for (int i = 0; i <= EvaluationService.MAX_APPLICATIONS_PER_DAY; i++)
            debtsDAO.createDebt(evaluationDAO.createEvaluation(subject, new Date(), 1000d, 50, 1d, ip, 0d));

        Evaluation evaluation = evaluationDAO.createEvaluation(subject, new Date(), 2000d, 50, 1d, ip, 0d);

        List<String> messages = evaluationService.evaluateRisk(evaluation);
        Assert.assertTrue(!messages.isEmpty());
        assertEquals(1, messages.size());

        Assert.assertTrue(messages.contains(EvaluationService.MSG_MAX_APPLICATION_FROM_IP_PER_DAY));

        assertEquals(0, evaluation.getCalculatedCoef().intValue());
    }

    @Test
    public void testMulltitalFail() {
        String ip = "1.1.1.1";
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        for (int i = 0; i <= EvaluationService.MAX_APPLICATIONS_PER_DAY; i++)
            debtsDAO.createDebt(evaluationDAO.createEvaluation(subject, date, 1000d, 5, 1d, ip, 0d));

        for (int hour = EvaluationService.UNACCEPTABLE_HOURS_FROM; hour <= EvaluationService.UNACCEPTABLE_HOURS_FROM; hour++) {
            c.set(Calendar.HOUR_OF_DAY, hour);
            assertEquals(hour, c.get(Calendar.HOUR_OF_DAY));

            Evaluation evaluation = evaluationDAO.createEvaluation(subject, c.getTime(), EvaluationService.MAX_LOAN + 1, 5, 1d, ip, 0d);

            List<String> messages = evaluationService.evaluateRisk(evaluation);
            Assert.assertTrue(!messages.isEmpty());

            Assert.assertTrue(messages.contains(EvaluationService.MSG_MAX_APPLICATION_FROM_IP_PER_DAY));
            Assert.assertTrue(messages.contains(EvaluationService.MSG_TOO_BIG_LOAN));
            Assert.assertTrue(messages.contains(EvaluationService.MSG_MAX_LOAN_AND_TIME));
            Assert.assertTrue(messages.contains(EvaluationService.MSG_TOO_SHORT_TERM));

            assertEquals(4, messages.size());

            assertEquals(0, evaluation.getCalculatedCoef().intValue());

        }

    }

    @Test
    public void testCreate() {

        Date date = new Date();
        Double debt = 1000d;
        Double rate = new Double(EvaluationService.LOAN_RATE / 100);
        Integer term = 12;
        String requestIP = "1";
        Double calculatedCoef = 0d;
        Evaluation s = evaluationService.createEvaluation(subject, date, debt, term, requestIP);
        assertEquals(rate, s.getRate());
        assertEquals(debt, s.getDebt());
        assertEquals(term, s.getTerm());
        assertEquals(requestIP, s.getRequestIP());
        assertEquals(calculatedCoef, s.getCalculatedCoef());
        assertEquals(subject, s.getSubject());

    }

}
