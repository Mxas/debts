package eu.fourFinance.dao;

import java.util.Date;
import java.util.List;

import eu.fourFinance.model.Evaluation;
import eu.fourFinance.model.Subject;

public interface EvaluationDAO {

	public Evaluation createEvaluation(Subject subject, Date date, Double debt,
			Integer term,Double rate, String requestIP, Double calculatedCoef);
	
	public List<Evaluation> getSubjectEvaluation(Subject subject);

	public Long countGivenLoan(String requestIp, Date from,
			Date till);
}
