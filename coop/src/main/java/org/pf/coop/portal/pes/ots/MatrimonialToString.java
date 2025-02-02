package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Matrimonial;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MatrimonialToString implements Converter<Matrimonial, String>{

	@Override
	public String convert(Matrimonial source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
