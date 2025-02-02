package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Job;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JobToString implements Converter<Job, String>{

	@Override
	public String convert(Job source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
