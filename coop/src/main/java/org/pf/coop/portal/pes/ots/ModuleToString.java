package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Module;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ModuleToString implements Converter<Module, String>{

	@Override
	public String convert(Module source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
