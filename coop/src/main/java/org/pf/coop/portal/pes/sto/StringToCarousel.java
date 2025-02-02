package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.Carousel;
import org.pf.coop.portal.repository.CarouselRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCarousel implements Converter<String, Carousel>{

	@Autowired
	private CarouselRepo repo;
	
	@Override
	public Carousel convert(String source) {
		try {
			Optional<Carousel> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
