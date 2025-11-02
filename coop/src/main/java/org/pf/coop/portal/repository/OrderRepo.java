package org.pf.coop.portal.repository;

import java.util.List;

import org.pf.coop.portal.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long>{

	Page<Order> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	List<Order> findBySearchString(String searchString);
	
	List<Order> findByRecordAddDateIsNull();
	
}