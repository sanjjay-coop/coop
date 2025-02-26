package org.pf.coop.portal.pes.accounts.ots;

import org.pf.coop.portal.model.accounts.Income;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IncomeToString implements Converter<Income, String>{

	@Override
	public String convert(Income source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
