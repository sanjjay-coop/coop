package org.pf.coop.portal.repository;

import java.util.Date;
import java.util.List;

import org.pf.coop.portal.model.Business;
import org.pf.coop.portal.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BusinessRepo extends JpaRepository<Business, Long>{

	Page<Business> findByEnabled(Boolean enabled, Pageable pageable);
	
	Page<Business> findByOwner(Member member, Pageable pageable);
	
	@Query("select count(*) from Business o where o.addDate > ?1")
	long countAddAfterDate(Date cal);
	
	@Query("select o "
			+ "from Business o "
			+ "where o.enabled = true "
			+ "order by o.id desc "
			+ "limit 5")
	public List<Business> listBusinessRecent();
	
	@Query("select o "
			+ "from Business o "
			+ "where o.enabled = true "
			+ "and "
			+ "o.recordAddDate >=:date "
			+ "order by o.businessName asc ")
	public List<Business> listBusinessForBulletin(Date date);
	
	public Business findByIdAndOwner(Long id, Member owner);
	
	Page<Business> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	List<Business> findBySearchString(String searchString);
	
	Page<Business> findByEnabledAndSearchStringContainingIgnoreCase(Boolean enabled, String searchString, Pageable pageable);
	
	List<Business> findByEnabledAndSearchString(Boolean enabled, String searchString);
}
