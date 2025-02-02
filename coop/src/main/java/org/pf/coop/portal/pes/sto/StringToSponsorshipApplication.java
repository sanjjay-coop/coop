package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.SponsorshipApplication;
import org.pf.coop.portal.repository.SponsorshipApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSponsorshipApplication implements Converter<String, SponsorshipApplication>{

	@Autowired
	private SponsorshipApplicationRepo repo;
	
	@Override
	public SponsorshipApplication convert(String source) {
		try {
			Optional<SponsorshipApplication> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
