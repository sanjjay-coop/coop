package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.Occupation;
import org.pf.coop.portal.repository.OccupationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToOccupation implements Converter<String, Occupation>{

	@Autowired
	private OccupationRepo repo;
	
	@Override
	public Occupation convert(String source) {
		try {
			Optional<Occupation> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
