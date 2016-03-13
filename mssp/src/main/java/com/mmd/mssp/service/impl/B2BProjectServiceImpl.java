package com.mmd.mssp.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mmd.mssp.comm.Constants;
import com.mmd.mssp.domain.ApplyCloseProject;
import com.mmd.mssp.domain.B2BApproveLog;
import com.mmd.mssp.domain.B2BProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommBusiness;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.ProductPrice;
import com.mmd.mssp.domain.SpecialApplyProduct;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.ApplyCloseProjectRepository;
import com.mmd.mssp.repository.ApproveTemplateRepository;
import com.mmd.mssp.repository.B2BApproveLogRepository;
import com.mmd.mssp.repository.B2BProjectRepository;
import com.mmd.mssp.repository.CommBusinessRepository;
import com.mmd.mssp.repository.CommCityRepository;
import com.mmd.mssp.repository.ProcessStepRepository;
import com.mmd.mssp.repository.ProductPriceRepository;
import com.mmd.mssp.repository.ProductRepository;
import com.mmd.mssp.repository.SpecialApplyProductRepository;
import com.mmd.mssp.service.B2BProjectService;
import com.mmd.mssp.service.CommAgentService;
import com.mmd.mssp.service.CommService;
import com.mmd.mssp.service.ProcessStepService;

@Service
@Transactional
public class B2BProjectServiceImpl implements B2BProjectService {
	
	@Resource
	B2BProjectRepository b2BProjectRepository;
	
	@Resource
	CommService commService;
	
	@Resource
	SpecialApplyProductRepository specialApplyProductRepository;
	
	@Resource
	B2BApproveLogRepository b2BApproveLogRepository;
	
	@Resource
	ApplyCloseProjectRepository applyCloseProjectRepository;
	
	@Resource
	ProductRepository productRepository;
	
	@Resource
	CommCityRepository cityRepository;
	
	@Resource
	CommBusinessRepository businessRepository;
	
	@Resource
	ApproveTemplateRepository approveTemplateRepository;
	
	@Resource
	ProcessStepRepository processStepRepository;
	
	@Resource
	ProcessStepService processStepService;
	
	@Resource
	CommAgentService commAgentService;
	
	@Resource
	ProductPriceRepository productPriceRepository;

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.B2BProjectService#findById(java.lang.Integer)
	 */
	@Override
	public B2BProject findB2BProjectById(Integer projectId) {
		return b2BProjectRepository.findById(projectId);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.B2BProjectService#saveProjectAndSpecial(com.mmd.mssp.domain.B2BProject, java.lang.String[], java.lang.String[], java.lang.String[], java.lang.String[], java.lang.String[])
	 */
	@Override
	public void saveProjectAndSpecial(B2BProject bProject, String[] pronumids,
			String[] pronum, String[] cptf, String[] cppf, String[] cpnf) {
		bProject =b2BProjectRepository. save(bProject);
		List<SpecialApplyProduct> saList  = getSAPList(bProject,pronumids,pronum,cptf,cppf,cpnf);
		specialApplyProductRepository.save(saList);
	}

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param pronumids型号ID
	* @param @param pronum 型号数量
	* @param @param cptf  竞品型号
	* @param @param cppf 竞品价格
	* @param @param cpnf 竞品数量
	* @param @return   
	* @return List<SpecialApplyProduct>   
	* @throws
	*/
	private List<SpecialApplyProduct> getSAPList(B2BProject bProject,String[] pronumids,
			String[] pronum, String[] cptf, String[] cppf, String[] cpnf) {
		List<SpecialApplyProduct> sapList = new ArrayList<SpecialApplyProduct>();
		for (int i = 0; i < pronumids.length; i++) {
			SpecialApplyProduct sa = new SpecialApplyProduct();
			Product product = commService.findProductById(Integer.parseInt(pronumids[i]));
			ProductPrice productPrice = productPriceRepository.findByProductAndDateMonth(product);
			sa.setProductPrice(productPrice);
			sa.setNumber(Integer.parseInt(pronum[i]));
			sa.setCompeteProduct(cptf[i]);
			sa.setCompetePrice(BigDecimal.valueOf(Double.parseDouble(cppf[i])));
			sa.setB2bProject(bProject);
			sapList.add(sa);
		}
		return sapList;
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.B2BProjectService#saveSAPListAndProject(com.mmd.mssp.domain.B2BProject, java.util.List)
	 */
	@Override
	public void saveSAPListAndProject(B2BProject project,List<SpecialApplyProduct> sapList) {
		b2BProjectRepository.save(project);
		specialApplyProductRepository.save(sapList);
	}

	@Override
	public Paging<B2BProject> listAll(Integer startStep,Integer endStep,List<CommAgent> agent,boolean isLastStep,Integer page, Integer size) {
		int agentFlag = 1;
		
		if(agent != null){
			agentFlag = 2;
		}
		
		Paging<B2BProject> paging = new Paging<B2BProject>(page, size);
		Page<B2BProject> result=null;
		if(!isLastStep){
			result = b2BProjectRepository.listAll(agent,agentFlag, startStep, endStep,paging.toPage());
		}else{
			ProcessStep step = processStepService.findById(Constants.APPROVE_FINSHED);
			result = b2BProjectRepository.listAll(agent,agentFlag, startStep, endStep,step,paging.toPage());
		}
		paging.setResult(result);
		return paging;
	}
	
	@Override
	public void delete(B2BProject b2bProject,Boolean idDel) {
		B2BProject bProject = b2BProjectRepository.findById(b2bProject.getId());
		bProject.setIsDelete(idDel==true?1:0);
		b2BProjectRepository.save(bProject);
	}
	
	@Override
	public void save(B2BProject b2bProject) {
		b2BProjectRepository.save(b2bProject);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.B2BProjectService#findApproveLogList(com.mmd.mssp.domain.B2BProject)
	 */
	@Override
	public List<B2BApproveLog> findApproveLogList(B2BProject project) {
		return b2BApproveLogRepository.findByProject(project);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.B2BProjectService#saveProjectAndApproveLog(com.mmd.mssp.domain.B2BProject, com.mmd.mssp.domain.B2BApproveLog)
	 */
	@Override
	public void saveProjectAndApproveLog(B2BProject project, B2BApproveLog log) {
		ProcessStep step = processStepService.findById(project.getStep().getId());
		if(step.getPnextId()==null){
			project.setStep(processStepService.findById(Constants.APPROVE_FINSHED));
		}
		b2BProjectRepository.save(project);
		b2BApproveLogRepository.save(log);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.B2BProjectService#findProjectListByRole(com.mmd.mssp.domain.CommRole)
	 */
	@Override
	public List<B2BProject> findProjectListByRole(CommRole commRole) {
		return b2BProjectRepository.findProjectListByRole(commRole);
	}
	
	@Override
	public List<B2BProject> todo(List<CommAgent> commAgents, CommRole role) {
		return b2BProjectRepository.todot(commAgents, role);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.B2BProjectService#saveColseProject(com.mmd.mssp.domain.B2BProject, java.lang.String)
	 */
	@Override
	public void saveColseProject(B2BProject project, String projectInfo) {
		ApplyCloseProject cp  = new ApplyCloseProject();
		cp.setB2bProject(project);
		cp.setProInfo(projectInfo);
		applyCloseProjectRepository.save(cp);
	}

	@Override
	public Paging<B2BProject> listAllBySearch(CommAgent agent,Date applyTimeStart,
			Date applyTimeEnd, Date approveTimeStart,
			Date approveTimeEnd, Integer[] processStepId, Integer[] cityId,
			Integer[] businessId, String pCode, String pName, Integer[] pTypeId, Integer page,
			Integer size) {
		int agentFlag = 1;
		int processStepFlag = 1;
		int cityFlag = 1;
		int businessFlag = 1;
		int pCodeFlag = 1;
		int pNameFlag = 1;
		int pTypeFlag = 1;
		
		//ApproveTemplate temp = approveTemplateRepository.findTempByType(CommonConfig.getTEMP_B2B_PROJECT());
		
		List<ProcessStep> processStepList = new ArrayList<ProcessStep>();
		List<CommCity> cityList = new ArrayList<CommCity>();
		List<CommBusiness> businessList = new ArrayList<CommBusiness>();
		List<Integer> pTypeList = new ArrayList<Integer>();
		
		if(agent!=null){
			agentFlag = 2;
		}
		if(processStepId!=null){
			processStepFlag = 2;
			for(int i=0; i<processStepId.length; i++){
				processStepList.add(processStepRepository.getById(processStepId[i]));
			}
		}else{
			processStepList.add(new ProcessStep());
		}
		if(cityId!=null){
			cityFlag = 2;
			for(int i=0; i<cityId.length; i++){
				cityList.add(cityRepository.getById(cityId[i]));
			}
		}else{
			cityList.add(new CommCity());
		}
		if(businessId!=null){
			businessFlag = 2;
			for(int i=0; i<businessId.length; i++){
				businessList.add(businessRepository.getById(businessId[i]));
			}
		}else{
			businessList.add(new CommBusiness());
		}
		if(!StringUtils.isBlank(pCode)){
			pCodeFlag = 2;
		}
		if(!StringUtils.isBlank(pName)){
			pNameFlag = 2;
		}
		if(pTypeId!=null){
			pTypeFlag = 2;
			for(int i=0; i<pTypeId.length; i++){
				pTypeList.add(pTypeId[i]);
			}
		}else{
			pTypeList.add(5);//pTypeFlag不存在的（存在的是1,2）
		}
		
		Paging<B2BProject> paging = new Paging<B2BProject>(page, size);
		Page<B2BProject> result = b2BProjectRepository.listAllBySearch(agent, agentFlag, processStepList, processStepFlag, cityList, cityFlag, businessList, businessFlag, "%"+pCode+"%", pCodeFlag, "%"+pName+"%", pNameFlag, pTypeList, pTypeFlag, paging.toPage());
		paging.setResult(result);
		return paging;
	}

	@Override
	public List<B2BProject> listProjectByAgents(List<CommAgent> commAgents) {
		return b2BProjectRepository.listProjectByAgents(commAgents);
	}
	
	@Override
	public Paging<B2BProject> search(String ppn, String startDate,String endDate, String status,
			String agentId,List<CommAgent> commAgents,Integer page,Integer size) throws Exception{
		int ppnFlag = 1;
		int startDateFlag = 1;
		int endDateFlag = 1;
		int statusFlag = 1;
		Date startApplyDate = null; 
		Date endApplyDate = null; 
		int statusId=0;
		if(ppn == null || "".equals(ppn)){
			ppnFlag = 2;
		}
		if(startDate == null || "".equals(startDate)){
			startDateFlag = 2;
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			startApplyDate = sdf.parse(startDate);
		}
		if(endDate == null || "".equals(endDate)){
			endDateFlag = 2;
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			endApplyDate = sdf.parse(endDate);
			Calendar ca = Calendar.getInstance();
			ca.setTime(endApplyDate);
			ca.add(Calendar.DATE, 1);
			endApplyDate = ca.getTime();
		}
		if(status == null || "".equals(status)){
			statusFlag = 2;
		}else{
			statusId = Integer.parseInt(status);
		}
		if(agentId != null ){//
			commAgents = new ArrayList<CommAgent>();
			commAgents.add(commAgentService.getById(Integer.parseInt(agentId)));
		}
		Paging<B2BProject> paging = new Paging<B2BProject>(page, size);
		Page<B2BProject> result = b2BProjectRepository.search(ppn,ppnFlag,startApplyDate,startDateFlag,endApplyDate,endDateFlag,statusId,statusFlag,commAgents,paging.toPage());
		paging.setResult(result);
		return paging;
	}
}
