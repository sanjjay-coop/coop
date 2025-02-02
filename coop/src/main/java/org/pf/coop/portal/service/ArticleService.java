package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Article;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.repository.ArticleRepo;
import org.pf.coop.portal.repository.AuditRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ArticleService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private ArticleRepo articleRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Article> oe = this.articleRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addArticle(Article obj, String updateBy) {
		
		obj.setAuthor(updateBy);
		obj.setLastUpdateBy(updateBy);
		obj.setUpdateDate(Calendar.getInstance().getTime());
		if (obj.getPublish()==null) obj.setPublish(true);
		
		obj = articleRepo.save(obj);
	
		audit = new Audit(updateBy, "Article", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteArticle(Long id, String updateBy) {

		Optional<Article> oe = this.articleRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Article obj = oe.get();
		
		audit = new Audit(updateBy, "Article", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		articleRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateArticle(Article article, String updateBy) {
		
		Optional<Article> oe = this.articleRepo.findById(article.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Article obj = oe.get();
				
		obj.setCategories(article.getCategories());
		obj.setContent(article.getContent());
		obj.setExpDate(article.getExpDate());
		obj.setLastUpdateBy(updateBy);
		obj.setPubDate(article.getPubDate());
		obj.setPublish(article.getPublish());
		obj.setTitle(article.getTitle());
		obj.setUpdateDate(Calendar.getInstance().getTime());
		
		obj = articleRepo.save(obj);
		
		audit = new Audit(updateBy, "Article", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}
