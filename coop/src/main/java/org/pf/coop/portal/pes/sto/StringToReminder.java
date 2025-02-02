package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.Reminder;
import org.pf.coop.portal.repository.ReminderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToReminder implements Converter<String, Reminder>{

	@Autowired
	private ReminderRepo repo;
	
	@Override
	public Reminder convert(String source) {
		try {
			Optional<Reminder> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
