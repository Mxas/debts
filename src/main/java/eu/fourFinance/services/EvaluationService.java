package eu.fourFinance.services;

import java.util.Date;
import java.util.List;

import eu.fourFinance.model.Debts;
import eu.fourFinance.model.Evaluation;
import eu.fourFinance.model.Subject;

public interface EvaluationService {

	public static final int LOAN_RATE = 2;

	public static final Double MAX_LOAN = 5000d;
	public static final Double MIN_LOAN = 200d;

	public static final int MAX_TERM = 100;
	public static final int MIN_TERM = 8;

	public static final int ACCEPTABLE_COEF = 1;
	public static final int MAX_APPLICATIONS_PER_DAY = 2;
	public static final int UNACCEPTABLE_HOURS_FROM = 1;
	public static final int UNACCEPTABLE_HOURS_TILL = 6;

	/**
	 * 
	 * o Loan application risk analysis is performed. Risk is considered too
	 * high if: the attempt to take loan is made after from 00:00 to 6:00 AM
	 * with max possible amount. reached max applications (e.g. 3) per day from
	 * a single IP.
	 * 
	 * if there are no risks associated returns empty list
	 * 
	 * @param evaluation
	 * @return - if elauation fails result will be errors codes
	 */
	public List<String> evaluateRisk(Evaluation evaluation);

	/**
	 * 
	 * @param subject
	 * @param date
	 * @param debt
	 * @param term
	 * @param requestIP
	 * @return
	 */
	public Evaluation createEvaluation(Subject subject, Date date, Double debt,
			Integer term, String requestIP);

	/**
	 * 
	 * @param evaluation
	 */
	public void calcLoanDetails(Evaluation evaluation);

	public Evaluation getEvaluation(Long evaluationId);

	public Debts applay(Evaluation evaluation);

	public List<Debts> getGivenDebts();
}
