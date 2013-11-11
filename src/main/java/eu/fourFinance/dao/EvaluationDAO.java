package eu.fourFinance.dao;

import java.util.Date;
import java.util.List;

import eu.fourFinance.model.Evaluation;
import eu.fourFinance.model.Subject;

/**
 * 
 * For: Evaluations... 
 *
 * @author MindaugasK
 * @since 2013.11.11
 *
 */
public interface EvaluationDAO {

    /**
     * creates evaluation by given params
     * 
     * @param subject
     * @param date
     * @param debt
     * @param term
     * @param rate
     * @param requestIP
     * @param calculatedCoef
     * @return
     */
    public Evaluation createEvaluation(Subject subject, Date date, Double debt, Integer term, Double rate, String requestIP, Double calculatedCoef);

    /**
     * count subject Evaluations
     * 
     * @param subject
     * @return
     */
    public List<Evaluation> getSubjectEvaluation(Subject subject);

    /**
     * counts given loans by IP and date period
     * 
     * @param requestIp
     * @param from
     * @param till
     * @return
     */
    public Long countGivenLoan(String requestIp, Date from, Date till);

    /**
     * 
     * finds by id
     * 
     * @param evaluationId
     * @return
     */
    public Evaluation getEvaluation(Long evaluationId);
}
