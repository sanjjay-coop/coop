package org.pf.coop.portal.repository;

import java.util.List;

import org.pf.coop.portal.model.NewsFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NewsFeedRepo extends JpaRepository<NewsFeed, Long>{
	
	@Query("select o "
			+ "from NewsFeed o "
			+ "order by o.id desc "
			+ "limit 4")
	public List<NewsFeed> listNewsFeedRecent();
	
	Page<NewsFeed> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	List<NewsFeed> findBySearchString(String searchString);
	
	List<NewsFeed> findByRecordAddDateIsNull();
}

