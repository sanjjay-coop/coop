package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Contact;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ContactToString implements Converter<Contact, String>{

	@Override
	public String convert(Contact source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}