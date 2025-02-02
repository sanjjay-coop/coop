package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Sponsorship;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SponsorshipToString implements Converter<Sponsorship, String>{

	@Override
	public String convert(Sponsorship source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
