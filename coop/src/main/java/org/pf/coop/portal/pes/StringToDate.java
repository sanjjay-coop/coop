package org.pf.coop.portal.pes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDate implements Converter<String, Date>  {

	@Override
	public Date convert(String source){
		try {
			DateFormat formatter;
			Date date;
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			date = (Date) formatter.parse(source);
			return date;
		} catch (Exception e) {
			return null;
		}
	}
}
