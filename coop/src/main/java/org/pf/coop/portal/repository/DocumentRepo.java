package org.pf.coop.portal.repository;

import java.util.List;

import org.pf.coop.portal.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepo extends JpaRepository<Document, Long>{

	Page<Document> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	List<Document> findBySearchString(String searchString);
	
	List<Document> findByRecordAddDateIsNull();
	
}
