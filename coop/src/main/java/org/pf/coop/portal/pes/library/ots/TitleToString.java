package org.pf.coop.portal.pes.library.ots;

import org.pf.coop.portal.model.library.Title;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TitleToString implements Converter<Title, String>{

	@Override
	public String convert(Title source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
