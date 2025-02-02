package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.MemberType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MemberTypeToString implements Converter<MemberType, String>{

	@Override
	public String convert(MemberType source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
