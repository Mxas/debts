package eu.fourFinance.web.controllers;

import static eu.fourFinance.services.LangService.MSG_IP_EMPTY;
import static eu.fourFinance.services.LangService.MSG_LOAN_EMPTY;
import static eu.fourFinance.services.LangService.MSG_TERM_EMPTY;
import static eu.fourFinance.services.LangService.MSG_WRONG;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.fourFinance.model.Debts;
import eu.fourFinance.model.Evaluation;
import eu.fourFinance.model.Subject;
import eu.fourFinance.services.DebtsService;
import eu.fourFinance.services.EvaluationService;
import eu.fourFinance.services.LangService;
import eu.fourFinance.services.SubjectService;
import eu.fourFinance.web.beans.EvaluationCommand;

@Controller
@Transactional
public class LoanController {

	@Autowired
	private SubjectService subjectService;
	@Autowired
	private LangService langService;

	@Autowired
	private EvaluationService evaluationService;

	@Autowired
	private DebtsService debtsService;

	@RequestMapping("/getLonaInterestRate.mvc")
	public @ResponseBody
	String getLonaInterestRateNew() {
		return new Integer(EvaluationService.LOAN_RATE).toString();
	}

	@RequestMapping(value = "/evaluate.mvc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	EvaluationCommand evaluate(@RequestBody EvaluationCommand command,
			@Value("#{request.remoteAddr}") StringBuffer ip,
			HttpServletResponse response) {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		if (command == null) {
			return new EvaluationCommand(langService.get(MSG_WRONG));
		} else {
			Double debt = command.getLoan();
			if (debt == null || debt.doubleValue() <= 0) {
				command.setSuccess(false);
				command.putMessage(langService.get(MSG_LOAN_EMPTY));
			}
			Integer term = command.getTerm();
			if (term == null) {
				command.setSuccess(false);
				command.putMessage(langService.get(MSG_TERM_EMPTY));
			}
			String requestIP = ip.toString();
			if (StringUtils.isBlank(requestIP)) {
				command.setSuccess(false);
				command.putMessage(langService.get(MSG_IP_EMPTY));
			}
			if (command.getMessages() != null
					&& !command.getMessages().isEmpty()) {
				return command;
			}
			Subject subject = subjectService.getSubject(command.getCode());

			Evaluation evaluation = evaluationService
					.createEvaluation(subject,
							new Date(System.currentTimeMillis()), debt, term,
							requestIP);

			List<String> mess = evaluationService.evaluateRisk(evaluation);
			if (mess.isEmpty()) {
				if (evaluation.getCalculatedCoef() != null
						&& evaluation.getCalculatedCoef().intValue() == EvaluationService.ACCEPTABLE_COEF) {
					evaluationService.calcLoanDetails(evaluation);
					command.addSuccessfullEvaluation(evaluation);
					command.setSuccess(true);
					command.putMessage(langService.get(
							LangService.MSG_SUCCESS_EVA, evaluation.getId()));
				} else {
					command.setSuccess(false);
					command.putMessage(langService.get(MSG_WRONG));
				}

			} else {
				command.setSuccess(false);
				for (String string : mess) {
					command.putMessage(langService.get(string));
				}

			}
		}
		return command;
	}

	@RequestMapping(value = "/applay.mvc/{evaluationId}")
	public @ResponseBody
	String applay(@PathVariable("evaluationId") String evaluationId) {
		try {
			if (StringUtils.isBlank(evaluationId))
				return langService.get(LangService.MSG_INCORRECT_EVALUATION);

			Evaluation evaluation = evaluationService.getEvaluation(Long
					.valueOf(evaluationId));
			if (evaluation == null
					|| evaluation.getCalculatedCoef() == null
					|| evaluation.getCalculatedCoef().doubleValue() != EvaluationService.ACCEPTABLE_COEF) {
				return langService.get(LangService.MSG_INCORRECT_EVALUATION);
			}
			List<String> mess = evaluationService.evaluateRisk(evaluation);
			if (!mess.isEmpty()) {
				return langService.get(mess.get(0));
			}

			evaluationService.applay(evaluation);
			return "OK";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@RequestMapping(value = "/extend.mvc/{debtId}")
	public @ResponseBody
	String extend(@PathVariable("debtId") String debtId) {
		try {
			if (StringUtils.isBlank(debtId))
				return langService.get(LangService.MSG_WRONG);

			Debts debt = debtsService.getDebt(Long.valueOf(debtId));
			if (debt == null || !debt.getActive()) {
				return langService.get(LangService.MSG_WRONG);
			}
			debtsService.extendDebt(debt);
			return "OK";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@RequestMapping(value = "/getAll.mvc")
	public @ResponseBody
	EvaluationCommand getAll() {

		EvaluationCommand command = new EvaluationCommand();
		for (Debts loan : evaluationService.getGivenDebts()) {
			command.addLoan(loan);
		}
		return command;
	}
}
