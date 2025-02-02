package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.MenuItem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MenuItemToString implements Converter<MenuItem, String>{

	@Override
	public String convert(MenuItem source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
