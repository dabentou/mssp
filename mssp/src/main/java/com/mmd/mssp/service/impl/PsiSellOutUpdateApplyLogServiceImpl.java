package com.mmd.mssp.service.impl;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mmd.mssp.comm.Constants;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.PsiSellOutUpdateApplyLog;
import com.mmd.mssp.domain.PsiSellOutUpdateApproveLog;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.PsiSellOutUpdateApplyLogRepository;
import com.mmd.mssp.repository.PsiSellOutUpdateApproveLogRepository;
import com.mmd.mssp.service.ProcessStepService;
import com.mmd.mssp.service.PsiSellOutUpdateApplyLogService;

@Service
@Transactional
public class PsiSellOutUpdateApplyLogServiceImpl implements PsiSellOutUpdateApplyLogService {
	
	@Resource
	PsiSellOutUpdateApplyLogRepository psiSellOutUpdateApplyLogRepository;
	
	@Resource
	PsiSellOutUpdateApproveLogRepository psiSellOutUpdateApproveLogRepository;
	
	@Resource
	ProcessStepService processStepService;

	@Override
	public PsiSellOutUpdateApplyLog save(PsiSellOutUpdateApplyLog entity) {
		return psiSellOutUpdateApplyLogRepository.save(entity);
	}

	@Override
	public List<PsiSellOutUpdateApplyLog> findByCommAgent(CommAgent agent) {
		return psiSellOutUpdateApplyLogRepository.findByCommAgent(agent);
	}

	@Override
	public Paging<PsiSellOutUpdateApplyLog> fandAll(Integer applyLogId,Integer page,int size) {
		Paging<PsiSellOutUpdateApplyLog> paging = new Paging<PsiSellOutUpdateApplyLog>(page, size);
		Page<PsiSellOutUpdateApplyLog> result = null;
		if(applyLogId!=null){
			result = psiSellOutUpdateApplyLogRepository.pageById(applyLogId, paging.toPage());
		}else{
			result = psiSellOutUpdateApplyLogRepository.fandAll(paging.toPage());
		}
		paging.setResult(result);
		return paging;
	}

	@Override
	public PsiSellOutUpdateApplyLog getById(Integer id) {
		return psiSellOutUpdateApplyLogRepository.findOne(id);
	}

	@Override
	public Paging<PsiSellOutUpdateApplyLog> fandByAgentAndStatusAndProduc(
			Integer agentid, Integer approveStatus, String productName,
			Integer page, int size) {
		Paging<PsiSellOutUpdateApplyLog> paging = new Paging<PsiSellOutUpdateApplyLog>(page, size);
		Page<PsiSellOutUpdateApplyLog> result = null;
		if(agentid==null){
			if(approveStatus==null && "".equals(productName)){
				result = psiSellOutUpdateApplyLogRepository.fandAll(paging.toPage());
			}else if(approveStatus==null && !"".equals(productName)){
				result = psiSellOutUpdateApplyLogRepository.fandByProduct(productName,paging.toPage());
			}else if(approveStatus!=null && "".equals(productName)){
				result = psiSellOutUpdateApplyLogRepository.fandByStatus(approveStatus,paging.toPage());
			}else if(approveStatus!=null && !"".equals(productName)){
				result = psiSellOutUpdateApplyLogRepository.fandByStatusAndProduct(approveStatus,productName,paging.toPage());
			}
		}else{
			if(approveStatus==null && "".equals(productName)){
				result = psiSellOutUpdateApplyLogRepository.fandByAgent(agentid,paging.toPage());
			}else if(approveStatus==null && !"".equals(productName)){
				result = psiSellOutUpdateApplyLogRepository.fandByAgentAndPorduct(agentid,productName,paging.toPage());
			}else if(approveStatus!=null && "".equals(productName)){
				result = psiSellOutUpdateApplyLogRepository.fandByAgentAndStatud(agentid,approveStatus,paging.toPage());
			}else if(approveStatus!=null && !"".equals(productName)){
				result = psiSellOutUpdateApplyLogRepository.fandByAgentAndStatudAndProduct(agentid,approveStatus,productName,paging.toPage());
			}
		}
		paging.setResult(result);
		return paging;
	}

	@Override
	public List<PsiSellOutUpdateApplyLog> findByApplyDate(Date applyDate,CommAgent commAgent) {
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(applyDate);
		endCal.add(Calendar.DATE, 1);
		Date endTime = endCal.getTime();
		return psiSellOutUpdateApplyLogRepository.findByApplyDate(applyDate,endTime,commAgent);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.PsiSellOutUpdateApplyLogService#findApproveList(com.mmd.mssp.domain.PsiSellOutUpdateApplyLog)
	 */
	@Override
	public List<PsiSellOutUpdateApproveLog> findApproveList(PsiSellOutUpdateApplyLog psiSellOutUpdateApplyLog) {
		return psiSellOutUpdateApproveLogRepository.findByUpdateApply(psiSellOutUpdateApplyLog);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.PsiSellOutUpdateApplyLogService#saveLogAndApproveLog(com.mmd.mssp.domain.PsiSellOutUpdateApplyLog, com.mmd.mssp.domain.PsiSellOutUpdateApproveLog)
	 */
	@Override
	public void saveLogAndApproveLog(PsiSellOutUpdateApplyLog applyLog,
			PsiSellOutUpdateApproveLog approveLog) {
		ProcessStep step = processStepService.findById(applyLog.getStep().getId());
		if(step.getPnextId()==null){
			applyLog.setStep(processStepService.findById(Constants.APPROVE_FINSHED));
		}
		psiSellOutUpdateApplyLogRepository.save(applyLog);
		psiSellOutUpdateApproveLogRepository.save(approveLog);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.PsiSellOutUpdateApplyLogService#findSelloutUpdateByRole(com.mmd.mssp.domain.CommRole)
	 */
	@Override
	public List<PsiSellOutUpdateApplyLog> findSelloutUpdateByRole(
			CommRole commRole) {
		return psiSellOutUpdateApplyLogRepository.findSelloutUpdateByRole(commRole);
	}

	
}
