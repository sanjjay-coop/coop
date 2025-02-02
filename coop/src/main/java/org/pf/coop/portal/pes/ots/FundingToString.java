package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Funding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FundingToString implements Converter<Funding, String>{

	@Override
	public String convert(Funding source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
