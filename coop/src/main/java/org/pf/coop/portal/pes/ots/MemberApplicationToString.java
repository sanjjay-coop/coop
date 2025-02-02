package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.MemberApplication;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MemberApplicationToString implements Converter<MemberApplication, String>{

	@Override
	public String convert(MemberApplication source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
