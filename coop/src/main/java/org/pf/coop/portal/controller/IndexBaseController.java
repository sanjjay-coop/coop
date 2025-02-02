package org.pf.coop.portal.controller;

import java.util.Calendar;
import java.util.List;

import org.pf.coop.portal.model.Article;
import org.pf.coop.portal.model.Event;
import org.pf.coop.portal.repository.ArticleRepo;
import org.pf.coop.portal.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

public class IndexBaseController extends BaseController {

	@Autowired
	private ArticleRepo articleRepo;
	
	@Autowired
	private EventRepo eventRepo;
	
	@ModelAttribute("listArticle")
	public List<Article> getListArticle(){
		return (List<Article>) this.articleRepo.listArticleRecent((Calendar.getInstance()).getTime());
	}
	
	@ModelAttribute("countArticle")
	public long getCountArticle() {
		return this.articleRepo.countArticleForPublication(Calendar.getInstance().getTime());
	}
	
	@ModelAttribute("listEvent")
	public List<Event> getListEvent(){
		return (List<Event>) this.eventRepo.listEventRecent((Calendar.getInstance()).getTime());
	}
	
	@ModelAttribute("countEvent")
	public long getCountEvent() {
		return this.eventRepo.countEventForPublication(Calendar.getInstance().getTime());
	}
}
