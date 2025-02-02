package org.pf.coop.portal.repository;

import java.util.Date;
import java.util.List;

import org.pf.coop.portal.model.Sponsorship;
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
}
