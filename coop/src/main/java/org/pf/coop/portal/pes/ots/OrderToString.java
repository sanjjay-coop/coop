package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToString implements Converter<Order, String>{

	@Override
	public String convert(Order source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
