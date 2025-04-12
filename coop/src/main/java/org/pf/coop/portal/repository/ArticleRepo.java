package org.pf.coop.portal.repository;

import java.util.Date;
import java.util.List;

import org.pf.coop.portal.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepo extends JpaRepository<Article, Long>{
	
	@Query("select art "
			+ "from Article art "
			+ "where "
			+ "art.pubDate <=:today "
			+ "and "
			+ "art.expDate >=:today "
			+ "and "
			+ "art.publish = TRUE "
			+ "order by art.pubDate desc")
	public List<Article> listArticleForPublication(Date today);
	
	@Query("select count(*) "
			+ "from Article art "
			+ "where "
			+ "art.pubDate <=:today "
			+ "and "
			+ "art.expDate >=:today "
			+ "and "
			+ "art.publish = TRUE ")
	public long countArticleForPublication(Date today);
	
	@Query("select art "
			+ "from Article art "
			+ "where "
			+ "art.pubDate <=:today "
			+ "and "
			+ "art.expDate >=:today "
			+ "and "
			+ "art.publish = TRUE "
			+ "order by art.pubDate desc "
			+ "limit 5")
	public List<Article> listArticleRecent(Date today);
	
	@Query("select art "
			+ "from Article art "
			+ "where "
			+ "art.pubDate <=:today "
			+ "and "
			+ "art.expDate >=:today "
			+ "and "
			+ "art.recordAddDate >=:date "
			+ "and "
			+ "art.publish = TRUE "
			+ "order by art.title asc ")
	public List<Article> listArticleForBulletin(Date date, Date today);
	
	public Page<Article> findByPublishAndPubDateLessThanEqualAndExpDateGreaterThanEqual(Boolean publish, Date pubDate, Date expDate, Pageable pageable);
	
	@Query("select distinct art from Article art "
			+ "join art.categories cat "
			+ "where "
			+ "art.pubDate <=:today and "
			+ "art.expDate >=:today and "
			+ "art.publish = TRUE and "
			+ "cat.name =:categoryName ")
	public Page<Article> listArticleBlog(Date today, String categoryName, Pageable pageable);
	
	Page<Article> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	List<Article> findBySearchString(String searchString);
}
