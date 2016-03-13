package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.PsiSellOutUpdateApplyLog;
import com.mmd.mssp.domain.UpdateLogByAdmin;

@Repository
public interface UpdateLogByAdminRepository extends CrudRepository<UpdateLogByAdmin, Integer> {
	
	List<UpdateLogByAdmin> findByPsiSellOutUpdateApplyLog(PsiSellOutUpdateApplyLog psiSellOutUpdateApplyLog);
}
