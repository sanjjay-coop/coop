package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.Caste;
import org.pf.coop.portal.repository.CasteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCaste implements Converter<String, Caste>{

	@Autowired
	private CasteRepo repo;
	
	@Override
	public Caste convert(String source) {
		try {
			Optional<Caste> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
