package eu.fourFinance.web.beans;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.springframework.util.Assert;

import eu.fourFinance.model.Debts;
import eu.fourFinance.model.Evaluation;

public class SuccessfullEvaluation {

	private long id;
	private String code;
	private String loan;
	private String rate;
	private int term;
	private String periodicalPay;
	private String totalPay;
	private String lastPayDate;
	private boolean active;

	public SuccessfullEvaluation() {
	}

	public SuccessfullEvaluation(Evaluation e) {
		Assert.notNull(e);
		Assert.notNull(e.getSubject());

		this.id = e.getId();
		this.code = e.getSubject().getCode();
		this.loan = format(e.getDebt());
		this.rate = formatP(e.getRate() * 100);
		this.term = e.getTerm();
		this.periodicalPay = format(e.getPeriodicalPay());
		this.totalPay = format(e.getTotalPay());
		this.lastPayDate = new SimpleDateFormat("yyyy-MM-dd").format(e
				.getLastPayDate());

	}

	public SuccessfullEvaluation(Debts d) {

		this.code = d.getEvaluation().getSubject().getCode();
		this.loan = format(d.getDebt());
		this.rate = formatP(d.getRate() * 100);
		this.term = d.getTerm();
		this.periodicalPay = format(d.getPeriodicalPay());
		this.totalPay = format(d.getTotalPay());
		this.lastPayDate = new SimpleDateFormat("yyyy-MM-dd").format(d
				.getLastPayDate());

		this.active = d.getActive();
		this.id = d.getId();

	}

	private String format(double money) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String moneyString = formatter.format(money);
		return moneyString;
	}

	private String formatP(double money) {
		return String.format("%-2.2f%n", money) + " %";
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
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

	public String getLoan() {
		return loan;
	}

	public void setLoan(String loan) {
		this.loan = loan;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getPeriodicalPay() {
		return periodicalPay;
	}

	public void setPeriodicalPay(String periodicalPay) {
		this.periodicalPay = periodicalPay;
	}

	public String getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(String totalPay) {
		this.totalPay = totalPay;
	}

}
