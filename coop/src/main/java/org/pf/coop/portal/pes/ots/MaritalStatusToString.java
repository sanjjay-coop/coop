package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.MaritalStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MaritalStatusToString implements Converter<MaritalStatus, String>{

	@Override
	public String convert(MaritalStatus source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
