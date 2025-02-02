package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleToString implements Converter<Role, String>{

	@Override
	public String convert(Role source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
