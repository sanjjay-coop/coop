package org.pf.coop.portal.repository;

import java.util.Date;
import java.util.List;

import org.pf.coop.portal.model.Matrimonial;
import org.pf.coop.portal.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MatrimonialRepo extends JpaRepository<Matrimonial, Long>{
	
	Page<Matrimonial> findByEnabled(Boolean enabled, Pageable pageable);
	
	Page<Matrimonial> findByOwner(Member member, Pageable pageable);
	
	@Query("select count(*) from Matrimonial o where o.addDate > ?1")
	long countAddAfterDate(Date cal);
	
	@Modifying
	@Transactional
	@Query("delete from Matrimonial o where o.addDate < ?1")
	void deleteOldRecords(Date cal);
	
	@Query("select o "
			+ "from Matrimonial o "
			+ "where o.enabled = true "
			+ "order by o.id desc "
			+ "limit 5")
	public List<Matrimonial> listMatrimonialRecent();
}
