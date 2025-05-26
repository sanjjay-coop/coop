package org.pf.coop.portal.repository;

import java.util.List;

import org.pf.coop.portal.model.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepo extends JpaRepository<Organization, Long>{

	Page<Organization> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	List<Organization> findBySearchString(String searchString);
	
}
