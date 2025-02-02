package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.Receipt;
import org.pf.coop.portal.repository.ReceiptRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToReceipt implements Converter<String, Receipt>{

	@Autowired
	private ReceiptRepo repo;
	
	@Override
	public Receipt convert(String source) {
		try {
			Optional<Receipt> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
