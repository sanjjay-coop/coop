package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Salutation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SalutationToString implements Converter<Salutation, String>{

	@Override
	public String convert(Salutation source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
