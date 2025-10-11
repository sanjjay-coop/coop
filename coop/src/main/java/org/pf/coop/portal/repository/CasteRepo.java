package org.pf.coop.portal.repository;

import org.pf.coop.portal.model.Caste;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CasteRepo extends JpaRepository<Caste, Long>{

	public Caste findByName(String name);
	
	Page<Caste> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
}
