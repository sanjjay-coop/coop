package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.MessageText;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageTextToString implements Converter<MessageText, String>{

	@Override
	public String convert(MessageText source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
