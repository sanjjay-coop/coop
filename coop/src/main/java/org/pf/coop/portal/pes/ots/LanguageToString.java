package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Language;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LanguageToString implements Converter<Language, String>{

	@Override
	public String convert(Language source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
