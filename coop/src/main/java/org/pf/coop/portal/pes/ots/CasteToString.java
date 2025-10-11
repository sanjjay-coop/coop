package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Caste;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CasteToString implements Converter<Caste, String>{

	@Override
	public String convert(Caste source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
