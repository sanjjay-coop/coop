package org.pf.coop.portal.repository;

import java.util.List;

import org.pf.coop.portal.model.Quotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuotationRepo extends JpaRepository<Quotation, Long>{

	@Query("select i from Quotation i order by RANDOM() LIMIT 1")
	public List<Quotation> findRandomQuotation();
	
	Page<Quotation> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	public Quotation findByQuoteIgnoreCase(String quote);
}
