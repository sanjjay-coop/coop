package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.Salutation;
import org.pf.coop.portal.repository.SalutationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSalutation implements Converter<String, Salutation>{

	@Autowired
	private SalutationRepo repo;
	
	@Override
	public Salutation convert(String source) {
		try {
			Optional<Salutation> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
