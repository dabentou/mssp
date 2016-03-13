package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.PsiSellOutUpdateApplyLog;
import com.mmd.mssp.domain.UpdateLogByAdmin;


public interface UpdateLogByAdminService {
	
	UpdateLogByAdmin save(UpdateLogByAdmin updateLogByAdmin);
	
	List<UpdateLogByAdmin> findByPsiSellOutUpdateApplyLog(PsiSellOutUpdateApplyLog psiSellOutUpdateApplyLog);
}
