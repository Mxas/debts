package eu.fourFinance.web.beans;

import java.text.SimpleDateFormat;

import org.springframework.util.Assert;

import eu.fourFinance.model.Debts;
import eu.fourFinance.model.Evaluation;

public class SuccessfullEvaluation {

	private long id;
	private String code;
	private double loan;
	private double rate;
	private int term;
	private Double periodicalPay;
	private Double totalPay;
	private String lastPayDate;
	private boolean active;

	public SuccessfullEvaluation() {
	}

	public SuccessfullEvaluation(Evaluation e) {
		Assert.notNull(e);
		Assert.notNull(e.getSubject());

		this.id = e.getId();
		this.code = e.getSubject().getCode();
		this.loan = e.getDebt();
		this.rate = Math.round(e.getRate() * 100);
		this.term = e.getTerm();
		this.periodicalPay = e.getPeriodicalPay();
		this.totalPay = e.getTotalPay();
		this.lastPayDate = new SimpleDateFormat("yyyy-MM-dd").format(e
				.getLastPayDate());

	}

	public SuccessfullEvaluation(Debts d) {

		this.code = d.getEvaluation().getSubject().getCode();
		this.loan = d.getDebt();
		this.rate = Math.round(d.getRate() * 100);
		this.term = d.getTerm();
		this.periodicalPay = d.getPeriodicalPay();
		this.totalPay = d.getTotalPay();
		this.lastPayDate = new SimpleDateFormat("yyyy-MM-dd").format(d
				.getLastPayDate());

		this.active = d.getActive();
		this.id = d.getId();

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

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public Double getPeriodicalPay() {
		return periodicalPay;
	}

	public void setPeriodicalPay(Double periodicalPay) {
		this.periodicalPay = periodicalPay;
	}

	public Double getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(Double totalPay) {
		this.totalPay = totalPay;
	}

	public String getLastPayDate() {
		return lastPayDate;
	}

	public void setLastPayDate(String lastPayDate) {
		this.lastPayDate = lastPayDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
