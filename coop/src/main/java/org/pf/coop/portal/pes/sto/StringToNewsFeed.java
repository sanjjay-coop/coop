package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.NewsFeed;
import org.pf.coop.portal.repository.NewsFeedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToNewsFeed implements Converter<String, NewsFeed>{

	@Autowired
	private NewsFeedRepo repo;
	
	@Override
	public NewsFeed convert(String source) {
		try {
			Optional<NewsFeed> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
