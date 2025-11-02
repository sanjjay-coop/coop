package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.OrderCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderCategoryToString implements Converter<OrderCategory, String>{

	@Override
	public String convert(OrderCategory source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
