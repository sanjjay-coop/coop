package org.pf.coop.portal.repository;

import java.util.List;

import org.pf.coop.portal.model.BulkEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BulkEmailRepo extends JpaRepository<BulkEmail, Long>{

	@Query("select o "
			+ "from BulkEmail o "
			+ "where o.status = 'N' "
			+ "order by o.id asc "
			+ "limit 1")
	public List<BulkEmail> listBulkEmailForSending();
}
