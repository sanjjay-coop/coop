package org.pf.coop.portal.pes.library.ots;

import org.pf.coop.portal.model.library.CheckOut;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CheckOutToString implements Converter<CheckOut, String>{

	@Override
	public String convert(CheckOut source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
