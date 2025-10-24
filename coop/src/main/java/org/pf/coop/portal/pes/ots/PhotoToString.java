package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Photo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PhotoToString implements Converter<Photo, String>{

	@Override
	public String convert(Photo source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
