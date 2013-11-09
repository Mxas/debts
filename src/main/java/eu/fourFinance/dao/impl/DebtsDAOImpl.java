package eu.fourFinance.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import eu.fourFinance.dao.DebtsDAO;
import eu.fourFinance.model.Debts;
import eu.fourFinance.model.Evaluation;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class DebtsDAOImpl implements DebtsDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Debts createDebt(Evaluation evaluation) {
		Assert.notNull(evaluation);
		Assert.notNull(evaluation.getSubject());
		Assert.notNull(evaluation.getLastPayDate());
		Assert.notNull(evaluation.getTotalPay());
		Assert.notNull(evaluation.getPeriodicalPay());

		Debts d = new Debts(evaluation);
		em.persist(d);
		return d;
	}

	@Override
	public Debts createDebtExtension(Debts old, Date lastPayDate,
			Double extencionRate, Integer term, Double totalPay, Date date,
			Double periodicalPay) {
		Debts d = new Debts(old, lastPayDate, extencionRate, term, totalPay,
				date, periodicalPay);
		old.setActive(Boolean.FALSE);
		em.persist(d);
		return d;
	}

	@Override
	public List<Debts> getGivenDebts() {
		return em.createQuery(
				"select d from Debts as d " + "inner join d.evaluation as e "
						+ "inner join e.subject as s "
						+ "order by s.code, e.date, d.date", Debts.class)
				.getResultList();
	}

	@Override
	public Debts getDebt(Long debtId) {
		Assert.notNull(debtId);
		return em.find(Debts.class, debtId);
	}
}
