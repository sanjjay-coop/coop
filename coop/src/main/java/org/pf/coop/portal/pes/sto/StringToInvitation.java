package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.Invitation;
import org.pf.coop.portal.repository.InvitationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToInvitation implements Converter<String, Invitation>{

	@Autowired
	private InvitationRepo repo;
	
	@Override
	public Invitation convert(String source) {
		try {
			Optional<Invitation> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
