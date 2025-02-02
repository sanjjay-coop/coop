package org.pf.coop.portal.repository;

import java.util.Date;
import java.util.List;

import org.pf.coop.portal.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdvertRepo extends JpaRepository<Advert, Long>{

	public Advert findByLocation(String location);
	public Advert findByName(String name);
	
	@Query("select art from Advert art where art.pubDate <=:today and art.expDate >=:today order by art.pubDate desc")
	public List<Advert> listAdvertForPublication(Date today);
	
	@Query("select art from Advert art where art.pubDate <=:today and art.expDate >=:today and art.location =:location order by art.pubDate desc")
	public List<Advert> listAdvertForPublication(Date today, String location);
}
