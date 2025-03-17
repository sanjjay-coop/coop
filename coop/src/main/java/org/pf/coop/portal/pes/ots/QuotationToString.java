package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Quotation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class QuotationToString implements Converter<Quotation, String>{

	@Override
	public String convert(Quotation source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
