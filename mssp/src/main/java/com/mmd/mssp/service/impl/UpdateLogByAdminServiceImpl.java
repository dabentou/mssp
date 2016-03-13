package com.mmd.mssp.service.impl;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.PsiSellOutUpdateApplyLog;
import com.mmd.mssp.domain.UpdateLogByAdmin;
import com.mmd.mssp.repository.UpdateLogByAdminRepository;
import com.mmd.mssp.service.UpdateLogByAdminService;


@Service
public class UpdateLogByAdminServiceImpl implements UpdateLogByAdminService {

	@Resource
	UpdateLogByAdminRepository updateLogByAdminRepository;

	@Override
	public UpdateLogByAdmin save(UpdateLogByAdmin entity) {
		return updateLogByAdminRepository.save(entity);
	}

	@Override
	public List<UpdateLogByAdmin> findByPsiSellOutUpdateApplyLog(
			PsiSellOutUpdateApplyLog psiSellOutUpdateApplyLog) {
		return updateLogByAdminRepository.findByPsiSellOutUpdateApplyLog(psiSellOutUpdateApplyLog);
	}

}
