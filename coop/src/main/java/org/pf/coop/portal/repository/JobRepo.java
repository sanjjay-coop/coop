package org.pf.coop.portal.repository;

import java.util.Date;
import java.util.List;

import org.pf.coop.portal.model.Job;
import org.pf.coop.portal.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface JobRepo extends JpaRepository<Job, Long>{

	Page<Job> findByEnabled(Boolean enabled, Pageable pageable);
	
	Page<Job> findByOwner(Member member, Pageable pageable);
	
	@Query("select count(*) from Job o where o.addDate > ?1")
	long countAddAfterDate(Date cal);
	
	@Modifying
	@Transactional
	@Query("delete from Job o where o.lastDate < ?1")
	void deleteOldRecords(Date date);
	
	@Query("select o "
			+ "from Job o "
			+ "where o.enabled = true "
			+ "order by o.id desc "
			+ "limit 5")
	public List<Job> listJobRecent();
	
	@Query("select o "
			+ "from Job o "
			+ "where o.enabled = true "
			+ "and o.recordAddDate >=:date "
			+ "order by o.id desc ")
	public List<Job> listJobForBulletin(Date date);
	
	Page<Job> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	List<Job> findBySearchString(String searchString);
	
	List<Job> findByRecordAddDateIsNull();
	
}