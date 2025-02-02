package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Occupation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OccupationToString implements Converter<Occupation, String>{

	@Override
	public String convert(Occupation source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}