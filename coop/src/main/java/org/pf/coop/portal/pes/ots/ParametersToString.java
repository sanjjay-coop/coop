package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Parameters;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ParametersToString implements Converter<Parameters, String>{

	@Override
	public String convert(Parameters source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}