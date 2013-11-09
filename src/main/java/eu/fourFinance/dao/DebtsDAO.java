package eu.fourFinance.dao;

import java.util.Date;
import java.util.List;

import eu.fourFinance.model.Debts;
import eu.fourFinance.model.Evaluation;

public interface DebtsDAO {

	public Debts createDebt(Evaluation evaluation);

	public Debts createDebtExtension(Debts old, Date lastPayDate,
			Double extencionRate, Integer term, Double totalPay, Date date,
			Double periodicalPay);

	public List<Debts> getGivenDebts();

	public Debts getDebt(Long debtId);
}
