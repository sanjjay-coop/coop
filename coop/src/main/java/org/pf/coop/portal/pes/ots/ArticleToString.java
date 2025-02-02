package org.pf.coop.portal.pes.ots;

import org.pf.coop.portal.model.Article;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArticleToString implements Converter<Article, String>{

	@Override
	public String convert(Article source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
