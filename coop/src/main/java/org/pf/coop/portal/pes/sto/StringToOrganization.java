package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.Organization;
import org.pf.coop.portal.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToOrganization implements Converter<String, Organization>{

	@Autowired
	private OrganizationRepo repo;
	
	@Override
	public Organization convert(String source) {
		try {
			Optional<Organization> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
