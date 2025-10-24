package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Gallery;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GalleryToString implements Converter<Gallery, String>{

	@Override
	public String convert(Gallery source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
