package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Profession;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfessionToString implements Converter<Profession, String>{

	@Override
	public String convert(Profession source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

