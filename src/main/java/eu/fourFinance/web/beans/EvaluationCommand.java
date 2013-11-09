package eu.fourFinance.web.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.fourFinance.model.Debts;
import eu.fourFinance.model.Evaluation;

public class EvaluationCommand {

	private String code;
	private double loan;
	private int term;
	private boolean success = false;
	private List<String> messages;
	private List<SuccessfullEvaluation> successfullEvaluation;

	public EvaluationCommand() {

	}

	public EvaluationCommand(String... string) {
		messages = new ArrayList<String>(Arrays.asList(string));
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getLoan() {
		return loan;
	}

	public void setLoan(double loan) {
		this.loan = loan;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public void putMessage(String string) {
		if (messages == null)
			messages = new ArrayList<String>();
		messages.add(string);
	}

	public void addSuccessfullEvaluation(Evaluation debtCalculated) {
		if (successfullEvaluation == null)
			successfullEvaluation = new ArrayList<SuccessfullEvaluation>();
		successfullEvaluation.add(new SuccessfullEvaluation(debtCalculated));

	}

	public List<SuccessfullEvaluation> getSuccessfullEvaluation() {
		return successfullEvaluation;
	}

	public void setSuccessfullEvaluation(
			List<SuccessfullEvaluation> successfullEvaluation) {
		this.successfullEvaluation = successfullEvaluation;
	}

	public void addLoan(Debts loan2) {
		if (successfullEvaluation == null)
			successfullEvaluation = new ArrayList<SuccessfullEvaluation>();
		successfullEvaluation.add(new SuccessfullEvaluation(loan2));

	}

}
