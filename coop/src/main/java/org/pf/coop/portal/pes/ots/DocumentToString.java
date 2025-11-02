package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DocumentToString implements Converter<Document, String>{

	@Override
	public String convert(Document source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
