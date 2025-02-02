package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.EventType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EventTypeToString implements Converter<EventType, String>{

	@Override
	public String convert(EventType source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
