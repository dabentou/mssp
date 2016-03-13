package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.B2BCasualDetail;

@Repository
public interface B2BCasualDetailRepository extends CrudRepository<B2BCasualDetail, Long> {

	@Query(nativeQuery=true,value="SELECT DISTINCT product_id FROM b2b_casual_detail WHERE b2b_project_id=?1 ;")
	public int[] findProductByProject(int projectId);
	
	@Query("select a from B2BCasualDetail as a where a.project.id = (?1) and a.product.id = (?2) order by a.month")
	public List<B2BCasualDetail> findValByProjectAndProduct(int projectId,int productId);
}
