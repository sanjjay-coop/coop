package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.MemberType;
import org.pf.coop.portal.repository.MemberTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToMemberType implements Converter<String, MemberType>{

	@Autowired
	private MemberTypeRepo repo;
	
	@Override
	public MemberType convert(String source) {
		try {
			Optional<MemberType> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
