package org.pf.coop.portal.repository;

import org.pf.coop.portal.model.Tribe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TribeRepo extends JpaRepository<Tribe, Long>{

	public Tribe findByName(String name);
	
	Page<Tribe> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
}
