package org.pf.coop.portal.repository;

import java.util.Date;
import java.util.List;

import org.pf.coop.portal.model.Sponsorship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SponsorshipRepo extends JpaRepository<Sponsorship, Long> {

	@Query("select art "
			+ "from Sponsorship art "
			+ "where "
			+ "art.pubDate <=:today "
			+ "and "
			+ "art.expDate >=:today "
			+ "order by art.pubDate desc")
	public List<Sponsorship> listSponsorshipForPublication(Date today);
	
	@Query("select art "
			+ "from Sponsorship art "
			+ "where "
			+ "art.pubDate <=:today "
			+ "and "
			+ "art.expDate >=:today "
			+ "and art.recordAddDate >=:date "
			+ "order by art.pubDate desc")
	public List<Sponsorship> listSponsorshipForBulletin(Date date, Date today);
	
	Page<Sponsorship> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	List<Sponsorship> findBySearchString(String searchString);
	
	List<Sponsorship> findByRecordAddDateIsNull();
	
	Page<Sponsorship> findByPubDateLessThanAndExpDateGreaterThan(Date today1, Date today2, Pageable pageable);

	Page<Sponsorship> findByPubDateLessThanAndExpDateGreaterThanAndSearchStringContainingIgnoreCase(Date today1, Date today2, String searchString, Pageable pageable);
	
}
