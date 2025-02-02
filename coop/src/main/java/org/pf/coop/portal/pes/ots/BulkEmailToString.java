package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.BulkEmail;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BulkEmailToString implements Converter<BulkEmail, String>{

	@Override
	public String convert(BulkEmail source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
