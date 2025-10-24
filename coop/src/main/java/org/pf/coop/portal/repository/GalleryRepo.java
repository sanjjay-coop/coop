package org.pf.coop.portal.repository;

import org.pf.coop.portal.model.Gallery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepo extends JpaRepository<Gallery, Long>{
	
	Page<Gallery> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
}
