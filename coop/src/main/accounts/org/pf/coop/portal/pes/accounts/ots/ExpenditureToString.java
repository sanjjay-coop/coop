package org.pf.coop.portal.pes.accounts.ots;

import org.pf.coop.portal.model.accounts.Expenditure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ExpenditureToString implements Converter<Expenditure, String>{

	@Override
	public String convert(Expenditure source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
