package eu.fourFinance.services;

import java.util.Date;

import eu.fourFinance.model.Debts;
import eu.fourFinance.services.impl.DebtsServiceImpl.CalculationsResult;

/**
 * 
 * For: for loans(debts) 
 *
 * @author MindaugasK
 * @since 2013.11.11
 *
 */
public interface DebtsService {

    /**
     * return by id
     * 
     * @param debtId
     * @return
     */
    public Debts getDebt(Long debtId);

    /**
     * extends debts(loan)
     * 
     * @param debt
     * @return
     */
    public Debts extendDebt(Debts debt);

    /**
     * 
     * calculates loan information
     * 
     * @param rate
     * @param loan
     * @param term
     * @param date
     * @return
     */
    public CalculationsResult claculate(Double rate, Double loan, Integer term, Date date);

}
