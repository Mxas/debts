package eu.fourFinance.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Debts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Evaluation evaluation;

	@OneToOne
	private Debts extencionOf;

	private Double periodicalPay;

	private Double totalPay;

	private Date lastPayDate;

	@Column(nullable = false)
	private Date date;

	@Column(nullable = false)
	private Double debt;

	@Column(nullable = false)
	private Double rate;

	@Column(nullable = false)
	private Integer term;

	private Boolean active = Boolean.TRUE;

	public Debts() {
	}

	public Debts(Evaluation e) {
		this.evaluation = e;
		this.date = e.getDate();
		this.debt = e.getDebt();

		this.lastPayDate = e.getLastPayDate();
		this.rate = e.getRate();
		this.term = e.getTerm();
		this.totalPay = e.getTotalPay();
		this.periodicalPay = e.getPeriodicalPay();
	}

	public Debts(Debts old, Date lastPayDate, Double rate, Integer term,
			Double totalPay, Date date, Double periodicalPay) {
		Evaluation e = old.getEvaluation();
		this.evaluation = e;
		this.date = date;
		this.debt = e.getDebt();
		this.extencionOf = old;

		this.lastPayDate = lastPayDate;
		this.rate = rate;
		this.term = term;
		this.totalPay = totalPay;
		old.setActive(Boolean.FALSE);
		
		this.periodicalPay = periodicalPay;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public Debts getExtencionOf() {
		return extencionOf;
	}

	public void setExtencionOf(Debts extencionOf) {
		this.extencionOf = extencionOf;
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

	public Date getLastPayDate() {
		return lastPayDate;
	}

	public void setLastPayDate(Date lastPayDate) {
		this.lastPayDate = lastPayDate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getDebt() {
		return debt;
	}

	public void setDebt(Double debt) {
		this.debt = debt;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
