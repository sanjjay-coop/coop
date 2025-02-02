package org.pf.coop.portal.pes;

import java.util.Calendar;

import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CalendarToString implements Converter<Calendar, String>{

	@Override
	public String convert(Calendar cal) {
		if (cal == null)
			cal = Calendar.getInstance();
		DateTime dt = new DateTime(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH),
				cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),
				cal.get(Calendar.SECOND));
		return dt.toString("dd-MM-yyyy");
	}
}
