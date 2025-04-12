package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Holiday;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HolidayToString implements Converter<Holiday, String>{

	@Override
	public String convert(Holiday source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
