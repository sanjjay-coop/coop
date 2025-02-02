package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Member;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MemberToString implements Converter<Member, String>{

	@Override
	public String convert(Member source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
