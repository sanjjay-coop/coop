package org.pf.coop.portal.repository;

import java.util.Date;
import java.util.List;

import org.pf.coop.portal.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepo extends JpaRepository<Event, Long>{

	@Query("select eve "
			+ "from Event eve "
			+ "where "
			+ "eve.endDate >=:today "
			+ "and "
			+ "eve.publish = TRUE "
			+ "order by eve.startDate asc")
	public List<Event> listEventForPublication(Date today);
	
	@Query("select count(*) "
			+ "from Event eve "
			+ "where "
			+ "eve.endDate >=:today "
			+ "and "
			+ "eve.publish = TRUE ")
	public long countEventForPublication(Date today);
	
	@Query("select eve "
			+ "from Event eve "
			+ "where "
			+ "eve.endDate >=:today "
			+ "and "
			+ "eve.publish = TRUE "
			+ "order by eve.startDate asc "
			+ "limit 5")
	public List<Event> listEventRecent(Date today);
	
	public Page<Event> findByPublishAndEndDateGreaterThanEqual(Boolean publish, Date endDate, Pageable pageable);
	
	List<Event> findByRecordAddDateIsNull();
}
