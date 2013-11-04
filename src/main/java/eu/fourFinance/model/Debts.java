package eu.fourFinance.model;

import java.util.Date;

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

	private Date extencionDate;

	private Double extencionRate;

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

	public Date getExtencionDate() {
		return extencionDate;
	}

	public void setExtencionDate(Date extencionDate) {
		this.extencionDate = extencionDate;
	}

	public Double getExtencionRate() {
		return extencionRate;
	}

	public void setExtencionRate(Double extencionRate) {
		this.extencionRate = extencionRate;
	}

}
