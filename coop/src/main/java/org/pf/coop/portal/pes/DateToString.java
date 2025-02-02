package org.pf.coop.portal.pes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DateToString implements Converter<Date, String> {

	@Override
	public String convert(Date date) {
		if (date == null)
			date = Calendar.getInstance().getTime();
		
		DateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
	       
        // print date in the specified format
        String date_string = date_format.format(date);
		return date_string;
	}
}
