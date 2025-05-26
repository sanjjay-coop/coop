package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Organization;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrganizationToString implements Converter<Organization, String>{

	@Override
	public String convert(Organization source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
