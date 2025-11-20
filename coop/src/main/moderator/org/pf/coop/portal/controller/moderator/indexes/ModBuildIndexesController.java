package org.pf.coop.portal.controller.moderator.indexes;

import java.security.Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Controller
public class ModBuildIndexesController extends ModeratorBaseController {

	private Logger logger = LogManager.getLogger();

	@Autowired
	private EntityManager entityManager;

	@Transactional
	@GetMapping("/moderator/indexes/build")
	public String onApplicationEvent(Model model, Principal principal) {
	
		logger.info("Started Initializing Indexes");
		
		SearchSession searchSession = Search.session(entityManager);
		
		MassIndexer indexer = searchSession.massIndexer().idFetchSize(150).batchSizeToLoadObjects(25).threadsToLoadObjects(12);
		
		try {
		
			indexer.startAndWait();
		
		} catch (InterruptedException e) {
		
			logger.warn("Failed to load data from database");
		
			Thread.currentThread().interrupt();
		
		}
		
		logger.info("Completed Indexing");
		
		return "moderator/indexes/build";
	
	}
}
