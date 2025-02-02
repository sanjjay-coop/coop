package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.Matrimonial;
import org.pf.coop.portal.repository.MatrimonialRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToMatrimonial implements Converter<String, Matrimonial>{

	@Autowired
	private MatrimonialRepo repo;
	
	@Override
	public Matrimonial convert(String source) {
		try {
			Optional<Matrimonial> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
