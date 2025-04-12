package org.pf.coop.portal.repository;

import java.util.List;

import org.pf.coop.portal.model.Sponsorship;
import org.pf.coop.portal.model.SponsorshipApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorshipApplicationRepo extends JpaRepository<SponsorshipApplication, Long> {

	List<SponsorshipApplication> findBySponsorshipAndEmail(Sponsorship sponsorship, String email);
	List<SponsorshipApplication> findBySponsorshipAndMobile(Sponsorship sponsorship, String mobile);
	
	Page<SponsorshipApplication> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	List<SponsorshipApplication> findBySearchString(String searchString);
}
