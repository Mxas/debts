package eu.fourFinance.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import eu.fourFinance.dao.EvaluationDAO;
import eu.fourFinance.model.Evaluation;
import eu.fourFinance.services.EvaluationService;
import eu.fourFinance.utils.DateUtils;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationDAO evaluationDAO;

    @Override
    public List<String> evaluateRisk(Evaluation evaluation) {

        Assert.notNull(evaluation);
        Double loan = evaluation.getDebt();
        Date date = evaluation.getDate();
        String requestIp = evaluation.getRequestIP();
        Assert.notNull(loan);
        Assert.notNull(date);
        Assert.notNull(requestIp);

        List<String> messages = performEvaluation(loan, date, requestIp);

        if (messages.isEmpty()) {
            evaluation.setCalculatedCoef((double) EvaluationService.ACCEPTABLE_COEF);
        } else {
            evaluation.setCalculatedCoef(0d);
        }

        return messages;
    }

    private List<String> performEvaluation(Double loan, Date date, String requestIp) {

        List<String> result = new ArrayList<String>();

        if (MAX_LOAN < loan) {
            result.add(MSG_TOO_BIG_LOAN);
        }
        if (loan < MIN_LOAN) {
            result.add(MSG_TOO_SMALL_LOAN);
        }

        // evaluate time
        if (loan >= MAX_LOAN) {
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour < 6) {
                result.add(MSG_MAX_LOAN_AND_TIME);
            }
        }

        // reached max applications (e.g. 3) per day from a single IP.
        if (MAX_APPLICATIONS_PER_DAY <= evaluationDAO.countGivenLoan(requestIp, DateUtils.getDateDayStart(date), DateUtils.getDateDayEnd(date)).longValue()) {
            result.add(MSG_MAX_APPLICATION_FROM_IP_PER_DAY);
        }
        return result;
    }

}
