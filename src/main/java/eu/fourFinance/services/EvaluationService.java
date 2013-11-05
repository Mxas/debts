package eu.fourFinance.services;

import java.util.List;

import eu.fourFinance.model.Evaluation;

public interface EvaluationService {

    public static final Double MAX_LOAN = 5000d;
    public static final Double MIN_LOAN = 200d;
    public static final int ACCEPTABLE_COEF = 1;
    public static final int UNACCEPTABLE_HOURS_FROM = 1;
    public static final int UNACCEPTABLE_HOURS_TILL = 6;
    public static final String MSG_TOO_BIG_LOAN = "MSG_TOO_BIG_LOAN";
    public static final String MSG_TOO_SMALL_LOAN = "MSG_TOO_SMALL_LOAN";
    public static final String MSG_MAX_LOAN_AND_TIME = "MSG_MAX_LOAN_AND_TIME";

    /**
     * 
     * o    Loan application risk analysis is performed. Risk is considered too high if:
     *  the attempt to take loan is made after from 00:00 to 6:00 AM with max possible amount.
     *  reached max applications (e.g. 3) per day from a single IP.
     * 
     * if there are no risks associated returns empty list
     * 
     * @param evaluation
     * @return - if elauation fails result will be errors codes
     */
    public List<String> evaluateRisk(Evaluation evaluation);
}
