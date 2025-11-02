package org.pf.coop.portal.repository;

import java.util.List;

import org.pf.coop.portal.model.OrderCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCategoryRepo extends JpaRepository<OrderCategory, Long>{

	public OrderCategory findByName(String name);
	
	Page<OrderCategory> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	List<OrderCategory> findByRecordAddDateIsNull();
}
