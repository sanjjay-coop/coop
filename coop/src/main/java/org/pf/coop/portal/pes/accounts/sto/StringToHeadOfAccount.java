package org.pf.coop.portal.pes.accounts.sto;

import java.util.Optional;

import org.pf.coop.portal.model.accounts.HeadOfAccount;
import org.pf.coop.portal.repository.accounts.HeadOfAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToHeadOfAccount implements Converter<String, HeadOfAccount>{

	@Autowired
	private HeadOfAccountRepo repo;
	
	@Override
	public HeadOfAccount convert(String source) {
		try {
			Optional<HeadOfAccount> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
