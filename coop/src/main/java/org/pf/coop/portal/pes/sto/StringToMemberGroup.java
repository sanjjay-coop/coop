package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.MemberGroup;
import org.pf.coop.portal.repository.MemberGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToMemberGroup implements Converter<String, MemberGroup>{

	@Autowired
	private MemberGroupRepo repo;
	
	@Override
	public MemberGroup convert(String source) {
		try {
			Optional<MemberGroup> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
