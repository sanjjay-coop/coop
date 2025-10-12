package org.pf.coop.portal.repository;

import java.util.Date;
import java.util.List;

import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.Reminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ReminderRepo extends JpaRepository<Reminder, Long>{

	@Modifying
	@Transactional
	@Query("delete from Reminder o where o.remDate < ?1")
	void deleteOldRecords(Date date);
	
	public Page<Reminder> findByMember(Member member, Pageable pageable);
	
	@Query("select r from Reminder r where r.member =:member and "
			+ "r.remStartDate <:today and "
			+ "r.remDate >:today  "
			+ "order by r.remDate desc")
	public List<Reminder> listReminder(Member member, Date today);
	
	List<Reminder> findByRecordAddDateIsNull();
}
