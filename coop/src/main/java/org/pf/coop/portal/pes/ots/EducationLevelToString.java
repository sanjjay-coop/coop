package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.EducationLevel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EducationLevelToString implements Converter<EducationLevel, String>{

	@Override
	public String convert(EducationLevel source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
