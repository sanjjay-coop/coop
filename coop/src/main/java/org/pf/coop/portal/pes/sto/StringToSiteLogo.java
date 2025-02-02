package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.SiteLogo;
import org.pf.coop.portal.repository.SiteLogoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSiteLogo implements Converter<String, SiteLogo>{

	@Autowired
	private SiteLogoRepo repo;
	
	@Override
	public SiteLogo convert(String source) {
		try {
			Optional<SiteLogo> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
