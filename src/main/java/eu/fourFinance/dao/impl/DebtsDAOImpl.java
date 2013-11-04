package eu.fourFinance.dao.impl;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import eu.fourFinance.dao.DebtsDAO;
import eu.fourFinance.model.Debts;
import eu.fourFinance.model.Evaluation;

@Repository
@Transactional
public class DebtsDAOImpl implements DebtsDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Debts createDebt(Evaluation evaluation) {
		Assert.notNull(evaluation);
		Debts d = new Debts();
		d.setEvaluation(evaluation);
		em.persist(d);
		return d;
	}

	@Override
	public Debts createDebtExtension(Debts old, Date extencionDate,
			Double extencionRate) {
		Debts d = new Debts();
		d.setEvaluation(old.getEvaluation());
		d.setExtencionDate(extencionDate);
		d.setExtencionOf(old);
		d.setExtencionRate(extencionRate);
		em.persist(d);
		return d;
	}

}
