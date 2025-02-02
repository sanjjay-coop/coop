package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Article;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ArticleValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return Article.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Article obj = (Article) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "article.title.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "article.content.required");
		
		if (obj.getTitle()!=null){
			if (!this.lengthRange(obj.getTitle(), 1, 500)){
				errors.rejectValue("title", "article.title.size");
			}
		}

		if (obj.getContent()!=null){
			if (obj.getContent().length()<1){
				errors.rejectValue("content", "article.content.required");
			}
		}

		if (obj.getPubDate()==null){
			errors.rejectValue("pubDate", "article.pubDate.required");
		}

		if (obj.getExpDate()==null){
			errors.rejectValue("expDate", "article.expDate.required");
		}
	}
}
