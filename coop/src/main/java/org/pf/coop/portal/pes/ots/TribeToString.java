package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Tribe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TribeToString implements Converter<Tribe, String>{

	@Override
	public String convert(Tribe source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}