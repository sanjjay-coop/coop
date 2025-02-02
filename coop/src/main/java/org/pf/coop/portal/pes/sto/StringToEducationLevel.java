package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.EducationLevel;
import org.pf.coop.portal.repository.EducationLevelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEducationLevel implements Converter<String, EducationLevel>{

	@Autowired
	private EducationLevelRepo repo;
	
	@Override
	public EducationLevel convert(String source) {
		try {
			Optional<EducationLevel> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
