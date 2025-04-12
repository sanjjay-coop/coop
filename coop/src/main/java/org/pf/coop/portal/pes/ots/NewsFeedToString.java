package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.NewsFeed;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NewsFeedToString implements Converter<NewsFeed, String>{

	@Override
	public String convert(NewsFeed source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
