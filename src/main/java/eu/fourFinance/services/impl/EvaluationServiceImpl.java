package eu.fourFinance.services.impl;

import static eu.fourFinance.services.EvaluationService.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import eu.fourFinance.model.Evaluation;
import eu.fourFinance.services.EvaluationService;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Override
    public List<String> evaluateRisk(Evaluation evaluation) {

        Assert.notNull(evaluation);
        Double loan = evaluation.getDebt();
        Date date = evaluation.getDate();
        String requestIp = evaluation.getRequestIP();
        Assert.notNull(loan);
        Assert.notNull(date);
        Assert.notNull(requestIp);

       return  performEvaluation(loan, date,requestIp);
        
        
    }

    protected List<String> performEvaluation(Double loan, Date date, String requestIp) {
        List<String> result = new ArrayList<String>();

        if (MAX_LOAN < loan) {
            result.add(MSG_TOO_BIG_LOAN);
        }
        if (loan < MIN_LOAN) {
            result.add(MSG_TOO_SMALL_LOAN);
        }

        //elauate time
        if (MAX_LOAN == loan) {
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour < 6) {
                result.add(EvaluationService.MSG_MAX_LOAN_AND_TIME);
            }
        }
        return result;
    }

}
