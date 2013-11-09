package eu.fourFinance.services;

import java.util.Date;

import eu.fourFinance.model.Debts;
import eu.fourFinance.services.impl.DebtsServiceImpl.CalculationsResult;

public interface DebtsService {

	public Debts getDebt(Long debtId);

	public void extendDebt(Debts debt);

	public CalculationsResult claculate(Double rate, Double loan, Integer term, Date date);

}
