package eu.fourFinance.services.impl;

import static org.apache.commons.lang.time.DateUtils.addWeeks;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import eu.fourFinance.dao.DebtsDAO;
import eu.fourFinance.model.Debts;
import eu.fourFinance.services.DebtsService;

@Service
public class DebtsServiceImpl implements DebtsService {

	public static final double WEEKS_PER_WEEK = 52.177457d;

	@Autowired
	private DebtsDAO debtsDAO;

	@Override
	public Debts getDebt(Long debtId) {
		return debtsDAO.getDebt(debtId);
	}

	@Override
	public void extendDebt(Debts debt) {
		Integer term = debt.getTerm() + 1;
		Double extencionRate = round(debt.getRate() * 1.5);
		Date date = new Date(System.currentTimeMillis());

		CalculationsResult result = claculate(extencionRate, debt.getDebt(),
				term, date);

		debtsDAO.createDebtExtension(debt, result.getEndDate(), extencionRate,
				term, result.getTotalPay(), date, result.getPeriodicalPay());

	}

	public static class CalculationsResult {
		public Double periodicalPay;
		public Double totalPay;
		public Date endDate;

		public CalculationsResult(Double periodicalPay, Double totalPay,
				Date endDate) {
			this.periodicalPay = periodicalPay;
			this.totalPay = totalPay;
			this.endDate = endDate;
		}

		public Double getPeriodicalPay() {
			return periodicalPay;
		}

		public Double getTotalPay() {
			return totalPay;
		}

		public Date getEndDate() {
			return endDate;
		}

	}

	@Override
	public CalculationsResult claculate(Double rate, Double loan, Integer term,
			Date date) {
		Assert.notNull(rate);
		Assert.notNull(loan);
		Assert.notNull(term);
		Assert.notNull(date);

		Double periodicalPay = round(loan * rate / WEEKS_PER_WEEK
				+ (loan / term));
		Double totalPay = round(periodicalPay * term);
		Date endDate = addWeeks(date, term);

		return new CalculationsResult(periodicalPay, totalPay, endDate);
	}

	public static double round(double d) {
		return Math.round(d * 100) / 100d;

	}
}
