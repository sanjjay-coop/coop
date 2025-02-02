package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.EventUpdate;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EventUpdateToString implements Converter<EventUpdate, String>{

	@Override
	public String convert(EventUpdate source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
