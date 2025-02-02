package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Reminder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReminderToString implements Converter<Reminder, String>{

	@Override
	public String convert(Reminder source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
