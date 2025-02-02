package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Carousel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CarouselToString implements Converter<Carousel, String>{

	@Override
	public String convert(Carousel source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
