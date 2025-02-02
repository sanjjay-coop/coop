package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Business;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BusinessToString implements Converter<Business, String>{

	@Override
	public String convert(Business source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
