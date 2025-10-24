package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.Gallery;
import org.pf.coop.portal.repository.GalleryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToGallery implements Converter<String, Gallery>{

	@Autowired
	private GalleryRepo repo;
	
	@Override
	public Gallery convert(String source) {
		try {
			Optional<Gallery> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
