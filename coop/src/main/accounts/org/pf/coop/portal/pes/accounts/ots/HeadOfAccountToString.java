package org.pf.coop.portal.pes.accounts.ots;

import org.pf.coop.portal.model.accounts.HeadOfAccount;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HeadOfAccountToString implements Converter<HeadOfAccount, String>{

	@Override
	public String convert(HeadOfAccount source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
