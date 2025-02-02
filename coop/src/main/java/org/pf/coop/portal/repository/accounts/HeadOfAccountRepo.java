package org.pf.coop.portal.repository.accounts;

import org.pf.coop.portal.model.accounts.HeadOfAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeadOfAccountRepo extends JpaRepository<HeadOfAccount, Long>{

	public HeadOfAccount findByCode(String code);
	public HeadOfAccount findByName(String name);
}
