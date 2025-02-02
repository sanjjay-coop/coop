package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.Sponsorship;
import org.pf.coop.portal.repository.SponsorshipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSponsorship implements Converter<String, Sponsorship>{

	@Autowired
	private SponsorshipRepo repo;
	
	@Override
	public Sponsorship convert(String source) {
		try {
			Optional<Sponsorship> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
