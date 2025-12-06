package org.pf.coop.portal.controller.mobile.article;

import java.util.Calendar;

import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.Article;
import org.pf.coop.portal.repository.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mobile/article")
public class MobileArticleViewController extends MobileBaseController {

	@Autowired
	ArticleRepo articleRepo;
	
	@GetMapping("/view/{id}")
	public String viewArticle(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		Article article = this.articleRepo.findByIdAndPublishAndPubDateLessThanEqualAndExpDateGreaterThanEqual(id, true, Calendar.getInstance().getTime(), Calendar.getInstance().getTime());
		
		if (article == null) {
			reat.addFlashAttribute("message", "No such record.");
			return "redirect:/mobile/index";
		}
	
		model.addAttribute("article", article);
			
		return "mobile/article/view";
	}
}
