package org.pf.coop.portal.repository;

import java.util.Date;
import java.util.List;

import org.pf.coop.portal.model.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepo extends JpaRepository<Holiday, Long>{

	Holiday findByDate(Date date);
	
	Page<Holiday> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	List<Holiday> findBySearchString(String searchString);
	
	List<Holiday> findByRecordAddDateIsNull();
	
}
