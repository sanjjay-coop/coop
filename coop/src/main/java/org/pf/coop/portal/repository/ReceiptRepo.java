package org.pf.coop.portal.repository;

import java.util.List;

import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReceiptRepo extends JpaRepository<Receipt, Long>{

	public Receipt findByReceiptNumberIgnoreCase(String receiptNumber);
	
	@Modifying
	@Query("delete from Receipt c where c.id = :id")
	void deleteById(Long id);
	
	List<Receipt> findByRecordAddDateIsNull();
	
	Receipt findByIdAndMember(Long id, Member member);
}
