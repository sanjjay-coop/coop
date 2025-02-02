package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.BulkEmail;
import org.pf.coop.portal.repository.BulkEmailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToBulkEmail implements Converter<String, BulkEmail>{

	@Autowired
	private BulkEmailRepo repo;
	
	@Override
	public BulkEmail convert(String source) {
		try {
			Optional<BulkEmail> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

