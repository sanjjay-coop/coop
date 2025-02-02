package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Audit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuditToString implements Converter<Audit, String>{

	@Override
	public String convert(Audit source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
