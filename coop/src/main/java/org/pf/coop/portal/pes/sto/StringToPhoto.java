package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.Photo;
import org.pf.coop.portal.repository.PhotoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPhoto implements Converter<String, Photo>{

	@Autowired
	private PhotoRepo repo;
	
	@Override
	public Photo convert(String source) {
		try {
			Optional<Photo> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
