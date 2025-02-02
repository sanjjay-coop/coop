package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Advert;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AdvertToString implements Converter<Advert, String>{

	@Override
	public String convert(Advert source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
