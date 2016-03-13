package com.mmd.mssp.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.comm.Constants;
import com.mmd.mssp.domain.B2CApproveLog;
import com.mmd.mssp.domain.B2CProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.B2CApproveLogRepository;
import com.mmd.mssp.repository.B2CProjectRepository;
import com.mmd.mssp.service.B2CProjectService;
import com.mmd.mssp.service.ProcessStepService;

@Service
public class B2CProjectServiceImpl implements B2CProjectService {

	@Resource
	B2CProjectRepository b2CProjectRepository;
	
	@Resource
	B2CApproveLogRepository  b2CApproveLogRepository;
	
	@Resource
	ProcessStepService processStepService;
	
	@Override
	public B2CProject save(B2CProject b2cProject) {
		return b2CProjectRepository.save(b2cProject);
	}

	@Override
	public Paging<B2CProject> listAllProjectByAgentAndStep(CommAgent agent,Integer page, Integer size) {
		Paging<B2CProject> paging = new Paging<B2CProject>(page, size);
		Page<B2CProject> result = b2CProjectRepository.listAllProjectByAgentAndStep(agent, paging.toPage());
		paging.setResult(result);
		
		return paging;
	}

	@Override
	public B2CProject getB2CProjectById(Integer id) {
		return b2CProjectRepository.getById(id);
	}

	@Override
	public void delete(B2CProject b2CProject) {
		b2CProjectRepository.delete(b2CProject);
	}

	@Override
	public Paging<B2CProject> searchProjectByAgentAndStep(Date startDate, Date endDate,
			String pCode, CommAgent agent,List<Integer> projectIds, Integer page, Integer size) {
		
		int pCodeFlag = 1;
		int startDateFlag = 1;
		int endDateFlag = 1;
		if(!StringUtils.isBlank(pCode)){
			pCodeFlag = 2;
		}
		if(startDate!=null){
			startDateFlag = 2;
		}
		if(endDate!=null){
			endDateFlag = 2;
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			cal.add(Calendar.DATE, 1);
			endDate = cal.getTime();
		}
		
		Paging<B2CProject> paging = new Paging<B2CProject>(page, size);
		Page<B2CProject> result = null;
		if(projectIds.size()>0){
			result = b2CProjectRepository.searchProjectByAgentAndStepAndIds(startDate,startDateFlag, endDate,endDateFlag, "%"+pCode+"%", pCodeFlag, agent,projectIds, paging.toPage());
		}else{
			result = b2CProjectRepository.searchProjectByAgentAndStep(startDate,startDateFlag, endDate,endDateFlag, "%"+pCode+"%", pCodeFlag, agent, paging.toPage());
		}
		paging.setResult(result);
		
		return paging;
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.B2CProjectService#findApproveLogList(com.mmd.mssp.domain.B2CProject)
	 */
	@Override
	public List<B2CApproveLog> findApproveLogList(B2CProject b2cProject) {
		return b2CApproveLogRepository.findByProject(b2cProject);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.B2CProjectService#saveProjectAndApproveLog(com.mmd.mssp.domain.B2CProject, com.mmd.mssp.domain.B2CApproveLog)
	 */
	@Override
	public void saveProjectAndApproveLog(B2CProject project, B2CApproveLog log) {
		ProcessStep step = processStepService.findById(project.getProcessStep().getId());
		if(step.getPnextId()==null){
			project.setProcessStep(processStepService.findById(Constants.APPROVE_FINSHED));
		}
		 b2CProjectRepository.save(project);
		b2CApproveLogRepository.save(log);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.B2CProjectService#findProjectListByRole(com.mmd.mssp.domain.CommRole)
	 */
	@Override
	public List<B2CProject> findProjectListByRoleAndAgent(CommRole commRole,List<CommAgent> agents) {
		// TODO Auto-generated method stub
		return b2CProjectRepository.findProjectListByRoleAndAgent(commRole,agents);
	}

	@Override
	public Paging<B2CProject> searchProjectAndStep(Date startDate, Date endDate,
			String pCode,List<CommAgent> agents,List<Integer> projectIds, Integer page, Integer size) {
		int pCodeFlag = 1;
		int startDateFlag = 1;
		int endDateFlag = 1;
		if(!StringUtils.isBlank(pCode)){
			pCodeFlag = 2;
		}
		if(startDate!=null){
			startDateFlag =2;
		}
		if(endDate!=null){
			endDateFlag =2;
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			cal.add(Calendar.DATE, 1);
			endDate = cal.getTime();
		}
		Paging<B2CProject> paging = new Paging<B2CProject>(page, size);
		Page<B2CProject> result = null;
		if(projectIds.size()>0){
			result = b2CProjectRepository.searchProjectAndStepAndIds(startDate,startDateFlag, endDate,endDateFlag, "%"+pCode+"%", pCodeFlag,agents,projectIds, paging.toPage());
		}else {
			result = b2CProjectRepository.searchProjectAndStep(startDate,startDateFlag, endDate,endDateFlag, "%"+pCode+"%", pCodeFlag,agents, paging.toPage());
		}
		paging.setResult(result);
		
		return paging;
	}

	@Override
	public Paging<B2CProject> listAllProjectByStep(List<CommAgent> agents,Integer page, Integer size) {
		Paging<B2CProject> paging = new Paging<B2CProject>(page, size);
		Page<B2CProject> result = b2CProjectRepository.listAllProjectByStep(agents,paging.toPage());
		paging.setResult(result);
		
		return paging;
	}

	@Override
	public Paging<B2CProject> searchProjectByDateAndStatusAndStep(Date startDate,
			Date endDate, Integer status, List<CommAgent> agents, Integer page,
			Integer size) {
		int startDateFlag = 1;
		int endDateFlag = 1;
		int statusFlag = 1;
		if(startDate!=null){
			startDateFlag = 2;
		}
		if(endDate!=null){
			endDateFlag=2;
		}
		if(status!=null){
			statusFlag=2;
		}
		Paging<B2CProject> paging = new Paging<B2CProject>(page, size);
		Page<B2CProject> result = b2CProjectRepository.searchProjectByDateAndStatusAndStep(startDate, startDateFlag, endDate, endDateFlag, status, statusFlag, agents, paging.toPage());
		paging.setResult(result);
		return paging;
	}
	
	/**
	 * 代办事宜筛选查询
	 */
	@Override
	public Paging<B2CProject> searchProjectByDateAndRole(Date startDate,
			Date endDate, List<CommAgent> agents,CommRole role, Integer page,
			Integer size) {
		int startDateFlag = 1;
		int endDateFlag = 1;
		if(startDate!=null){
			startDateFlag = 2;
		}
		if(endDate!=null){
			endDateFlag=2;
		}
		Paging<B2CProject> paging = new Paging<B2CProject>(page, size);
		Page<B2CProject> result = b2CProjectRepository.searchProjectByDateAndRole(startDate, startDateFlag, endDate, endDateFlag,role, agents, paging.toPage());
		paging.setResult(result);
		return paging;
	}

	@Override
	public Paging<B2CProject> listAllProjectByAgentAndRole(
			List<CommAgent> agents, CommRole role,Integer page, Integer size) {
		Paging<B2CProject> paging = new Paging<B2CProject>(page, size);
		Page<B2CProject> result = b2CProjectRepository.listAllProjectByAgentAndRole(agents, role,paging.toPage());
		paging.setResult(result);
		return paging;
	}

	@Override
	public List<B2CProject> listAllPorjectByAgentsAndStep(List<CommAgent> agents) {
		return b2CProjectRepository.listAllPorjectByAgentsAndStep(agents);
	}

	@Override
	public Paging<B2CProject> listAllProjectByIds(List<Integer> ids,
			Integer page, Integer size) {
		Paging<B2CProject> paging = new Paging<B2CProject>(page, size);
		Page<B2CProject> result = b2CProjectRepository.listAllProjectByIds(ids,paging.toPage());
		paging.setResult(result);
		return paging;
	}

}
