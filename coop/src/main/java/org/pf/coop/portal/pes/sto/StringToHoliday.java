package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.Holiday;
import org.pf.coop.portal.repository.HolidayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToHoliday implements Converter<String, Holiday>{

	@Autowired
	private HolidayRepo repo;
	
	@Override
	public Holiday convert(String source) {
		try {
			Optional<Holiday> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
