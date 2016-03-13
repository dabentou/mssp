package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.B2ISponsorApplyProduct;
import com.mmd.mssp.domain.B2ISponsorProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.Product;

@Repository
public interface B2IApplySponsorProductRepository extends CrudRepository<B2ISponsorApplyProduct, Integer> {

	@Query("select a from B2ISponsorApplyProduct as a where a.productPrice.product = ?1 and a.b2iSponsorProject.agent = ?2")
	public List<B2ISponsorApplyProduct> findByProductAndAgent(Product product,CommAgent agent);
	
	@Query("select a from B2ISponsorApplyProduct as a where a.b2iSponsorProject = ?1")
	public List<B2ISponsorApplyProduct> findByProject(B2ISponsorProject sponsorProject);

}
	