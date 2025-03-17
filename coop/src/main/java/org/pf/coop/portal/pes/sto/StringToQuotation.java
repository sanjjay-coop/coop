package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.Quotation;
import org.pf.coop.portal.repository.QuotationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToQuotation implements Converter<String, Quotation>{

	@Autowired
	private QuotationRepo repo;
	
	@Override
	public Quotation convert(String source) {
		try {
			Optional<Quotation> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
