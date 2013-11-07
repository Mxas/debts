package eu.fourFinance.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.fourFinance.model.Evaluation;
import eu.fourFinance.services.EvaluationService;

@Controller
public class LoanController {

	private static final String TEST_SUBJECT_CODE = "Subject A";

	@Autowired
	private EvaluationService evaluationService;

	@RequestMapping("/getLonaInterestRate.mvc")
	public @ResponseBody
	String getLonaInterestRateNew() {
		return new Integer(EvaluationService.LOAN_RATE).toString();
	}
}
