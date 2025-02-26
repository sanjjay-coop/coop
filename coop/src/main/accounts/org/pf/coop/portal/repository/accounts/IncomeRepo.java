package org.pf.coop.portal.repository.accounts;

import java.util.Date;
import java.util.List;

import org.pf.coop.portal.model.accounts.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IncomeRepo extends JpaRepository<Income, Long>{

	@Query("select o from Income o order by o.transactionDate desc limit 50")
	public List<Income> listIncomeRecent();
	
	@Query("select o from Income o "
			+ "WHERE "
			+ "o.transactionDate >=:startDate "
			+ "AND "
			+ "o.transactionDate <=:endDate "
			+ "order by o.transactionDate asc")
	public List<Income> listIncome(Date startDate, Date endDate);
}
