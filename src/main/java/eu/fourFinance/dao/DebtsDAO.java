package eu.fourFinance.dao;

import java.util.Date;
import java.util.List;

import eu.fourFinance.model.Debts;
import eu.fourFinance.model.Evaluation;

public interface DebtsDAO {

    /**
     * confirms loan taking
     * 
     * @param evaluation
     * @return
     */
    public Debts createDebt(Evaluation evaluation);

    /**
     * extend given loan wits calculated parameters
     * 
     * @param old
     * @param lastPayDate
     * @param extencionRate
     * @param term
     * @param totalPay
     * @param date
     * @param periodicalPay
     * @return
     */
    public Debts createDebtExtension(Debts old, Date lastPayDate, Double extencionRate, Integer term, Double totalPay, Date date, Double periodicalPay);

    /**
     * return given loans
     * 
     * @return
     */
    public List<Debts> getGivenDebts();

    /**
     * fins by id
     * 
     * @param debtId
     * @return
     */
    public Debts getDebt(Long debtId);
}
