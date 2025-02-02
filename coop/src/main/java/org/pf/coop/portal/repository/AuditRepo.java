package org.pf.coop.portal.repository;

import java.util.Date;

import org.pf.coop.portal.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AuditRepo extends JpaRepository<Audit, Long>{

	@Modifying
	@Transactional
	@Query("delete from Audit o where o.transactionDate < ?1")
	void deleteOldRecords(Date date);
}
