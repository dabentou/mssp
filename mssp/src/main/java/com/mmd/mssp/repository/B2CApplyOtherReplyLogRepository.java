package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mmd.mssp.domain.B2CApplyOther;
import com.mmd.mssp.domain.B2CApplyOtherReplyLog;
import com.mmd.mssp.domain.B2CProject;

public interface B2CApplyOtherReplyLogRepository extends CrudRepository<B2CApplyOtherReplyLog, Integer> {

	@Query("select a from B2CApplyOtherReplyLog as a where a.project = ?1 and a.type=?2 order by a.replyTime desc")
	List<B2CApplyOtherReplyLog> findByProjectAndType(B2CProject project,Integer type);
	
	@Query("select a from B2CApplyOtherReplyLog as a where a.applyOther = ?1 order by a.replyTime asc")
	List<B2CApplyOtherReplyLog> findByApplyOther(B2CApplyOther applyOther);
}
