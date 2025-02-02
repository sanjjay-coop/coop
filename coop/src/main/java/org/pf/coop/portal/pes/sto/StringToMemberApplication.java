package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.MemberApplication;
import org.pf.coop.portal.repository.MemberApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToMemberApplication implements Converter<String, MemberApplication>{

	@Autowired
	private MemberApplicationRepo repo;
	
	@Override
	public MemberApplication convert(String source) {
		try {
			Optional<MemberApplication> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
