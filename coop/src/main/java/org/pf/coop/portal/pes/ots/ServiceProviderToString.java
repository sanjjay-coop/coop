package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.ServiceProvider;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ServiceProviderToString implements Converter<ServiceProvider, String>{

	@Override
	public String convert(ServiceProvider source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
