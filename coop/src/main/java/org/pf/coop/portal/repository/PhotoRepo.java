package org.pf.coop.portal.repository;

import org.pf.coop.portal.model.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepo extends JpaRepository<Photo, Long>{
	
	Page<Photo> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
}
