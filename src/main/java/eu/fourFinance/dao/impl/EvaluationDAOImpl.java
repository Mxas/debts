package eu.fourFinance.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import eu.fourFinance.dao.EvaluationDAO;
import eu.fourFinance.model.Evaluation;
import eu.fourFinance.model.Subject;

@Repository
@Transactional
public class EvaluationDAOImpl implements EvaluationDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Evaluation createEvaluation(Subject subject, Date date, Double debt,
			Integer term, Double rate, String requestIP, Double calculatedCoef) {
		Assert.notNull(subject);

		Evaluation e = new Evaluation();
		e.setSubject(subject);
		e.setDate(date);
		e.setDebt(debt);
		e.setTerm(term);
		e.setRate(rate);
		e.setRequestIP(requestIP);
		e.setCalculatedCoef(calculatedCoef);
		em.persist(e);
		return e;
	}

	@Override
	public List<Evaluation> getSubjectEvaluation(Subject subject) {

		Assert.notNull(subject);

		TypedQuery<Evaluation> q = em.createNamedQuery(
				Evaluation.SUBJECT_EVALUATIONS, Evaluation.class).setParameter(
				"subject", subject);
		return q.getResultList();
	}

	@Override
	public Long countGivenLoan(String requestIp, Date from, Date till) {

		return em
				.createQuery(
						"select count(e) from Evaluation e "
								+ "where exists(select d from Debts d where d.evaluation = e)"
								+ " and e.requestIP = :requestIP and e.date >= :from and e.date <= :till",
						Long.class).setParameter("requestIP", requestIp)
				.setParameter("from", from).setParameter("till", till)
				.getSingleResult();
	}

}
