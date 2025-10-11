package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.Tribe;
import org.pf.coop.portal.repository.TribeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTribe implements Converter<String, Tribe>{

	@Autowired
	private TribeRepo repo;
	
	@Override
	public Tribe convert(String source) {
		try {
			Optional<Tribe> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}