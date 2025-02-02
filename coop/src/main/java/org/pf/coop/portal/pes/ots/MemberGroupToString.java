package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.MemberGroup;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MemberGroupToString implements Converter<MemberGroup, String>{

	@Override
	public String convert(MemberGroup source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

