package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.MaritalStatus;
import org.pf.coop.portal.repository.MaritalStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToMaritalStatus implements Converter<String, MaritalStatus>{

	@Autowired
	private MaritalStatusRepo repo;
	
	@Override
	public MaritalStatus convert(String source) {
		try {
			Optional<MaritalStatus> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
