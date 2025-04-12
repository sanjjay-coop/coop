package org.pf.coop.portal.repository;

import java.util.List;

import org.pf.coop.portal.model.Funding;
import org.pf.coop.portal.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingRepo extends JpaRepository<Funding, Long>{
	
	Page<Funding> findByApplicant(Member member, Pageable pageable);
	
	List<Funding> findByApplicant(Member member, Sort sort);
	
	Page<Funding> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	List<Funding> findBySearchString(String searchString);
}

