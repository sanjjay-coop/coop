package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.EventUpdate;
import org.pf.coop.portal.repository.EventUpdateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEventUpdate implements Converter<String, EventUpdate>{

	@Autowired
	private EventUpdateRepo repo;
	
	@Override
	public EventUpdate convert(String source) {
		try {
			Optional<EventUpdate> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
