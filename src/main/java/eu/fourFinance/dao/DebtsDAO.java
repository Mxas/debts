package eu.fourFinance.dao;

import java.util.Date;

import eu.fourFinance.model.Debts;
import eu.fourFinance.model.Evaluation;

public interface DebtsDAO {

	public Debts createDebt(Evaluation evaluation);

	public Debts createDebtExtension(Debts old, Date extencionDate,
			Double extencionRate);
}
