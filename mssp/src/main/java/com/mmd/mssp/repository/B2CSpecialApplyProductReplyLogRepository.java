package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.B2CProject;
import com.mmd.mssp.domain.B2CSpecialApplyProduct;
import com.mmd.mssp.domain.B2CSpecialApplyProductReplyLog;

@Repository
public interface B2CSpecialApplyProductReplyLogRepository extends
		CrudRepository<B2CSpecialApplyProductReplyLog, Integer> {
	
	@Query("select a from B2CSpecialApplyProductReplyLog as a where a.project = ?1 order by a.replyTime asc")
	List<B2CSpecialApplyProductReplyLog> findByProject(B2CProject project);
	
	@Query("select a from B2CSpecialApplyProductReplyLog as a where a.applyProduct = ?1 order by a.replyTime asc")
	List<B2CSpecialApplyProductReplyLog> findByApplyProduct(B2CSpecialApplyProduct applyProduct);
}
