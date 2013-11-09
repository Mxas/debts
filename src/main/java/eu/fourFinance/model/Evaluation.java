package eu.fourFinance.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name = Evaluation.SUBJECT_EVALUATIONS, 
			query = "select e from Evaluation as e where e.subject = :subject" )
	})
public class Evaluation {

	public static final String SUBJECT_EVALUATIONS = "SUBJECT_EVALUATIONS";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Subject subject;

	@Column(nullable = false)
	private Date date;

	@Column(nullable = false)
	private Double debt;

	@Column(nullable = false)
	private Double rate;

	@Column(nullable = false)
	private Integer term;

	@Column(nullable = false)
	private String requestIP;

	@Column(nullable = false)
	private Double calculatedCoef;

	private Double periodicalPay;

	private Double totalPay;

	private Date lastPayDate;
	
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getRequestIP() {
		return requestIP;
	}

	public void setRequestIP(String requestIP) {
		this.requestIP = requestIP;
	}

	public Double getCalculatedCoef() {
		return calculatedCoef;
	}

	public void setCalculatedCoef(Double calculatedCoef) {
		this.calculatedCoef = calculatedCoef;
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
}
