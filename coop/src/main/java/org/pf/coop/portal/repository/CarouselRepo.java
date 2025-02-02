package org.pf.coop.portal.repository;

import java.util.Date;
import java.util.List;

import org.pf.coop.portal.model.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarouselRepo extends JpaRepository<Carousel, Long>{
	
	@Query("select cs from Carousel cs where cs.pubEndDate >=:today order by id desc")
	public List<Carousel> listCarouselForPublication(Date today);

}

