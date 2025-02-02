package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Receipt;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReceiptToString implements Converter<Receipt, String>{

	@Override
	public String convert(Receipt source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
