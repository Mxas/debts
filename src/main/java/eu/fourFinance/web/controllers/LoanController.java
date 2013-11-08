package eu.fourFinance.web.controllers;

import static eu.fourFinance.services.LangService.*;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.fourFinance.model.Evaluation;
import eu.fourFinance.model.Subject;
import eu.fourFinance.services.EvaluationService;
import eu.fourFinance.services.LangService;
import eu.fourFinance.services.SubjectService;
import eu.fourFinance.web.beans.EvaluationCommand;

@Controller
public class LoanController {

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private LangService langService;

    @Autowired
    private EvaluationService evaluationService;

    @RequestMapping("/getLonaInterestRate.mvc")
    public @ResponseBody
    String getLonaInterestRateNew() {
        return new Integer(EvaluationService.LOAN_RATE).toString();
    }

    @RequestMapping(value = "/evaluate.mvc", method = RequestMethod.POST)
    public @ResponseBody
    EvaluationCommand evaluate(@RequestBody EvaluationCommand command, @Value("#{request.remoteAddr}") StringBuffer ip) {

        if (command == null) {
            return new EvaluationCommand(langService.get(MSG_WRONG));
        } else {
            Double debt = command.getLoan();
            if (debt == null) {
                command.setSuccess(false);
                command.putMessage(langService.get(MSG_LOAN_EMPTY));
            }
            Integer term = command.getTerm();
            if (debt == null) {
                command.setSuccess(false);
                command.putMessage(langService.get(MSG_IP_EMPTY));
            }
            String requestIP = ip.toString();
            if (StringUtils.isBlank(requestIP)) {
                command.setSuccess(false);
                command.putMessage(langService.get(MSG_TERM_EMPTY));
            }
            Subject subject = subjectService.getSubject(command.getCode());

            Evaluation evaluation = evaluationService.createEvaluation(subject, new Date(System.currentTimeMillis()), debt, term, requestIP);

            List<String> mess = evaluationService.evaluateRisk(evaluation);
            if (mess.isEmpty()) {
                if (evaluation.getCalculatedCoef() != null && evaluation.getCalculatedCoef().intValue() == EvaluationService.ACCEPTABLE_COEF) {
                    evaluationService.calcLoanDetails(evaluation);
                } else {
                    command.setSuccess(false);
                    command.putMessage(MSG_WRONG);
                }

            } else {
                command.setSuccess(false);
                command.setMessages(mess);
            }
        }
        return command;
    }
}
