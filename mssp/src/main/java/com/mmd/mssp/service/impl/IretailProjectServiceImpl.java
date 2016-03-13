package com.mmd.mssp.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lowagie.text.pdf.BaseFont;
import com.mmd.mssp.comm.Constants;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.IretailApproveLog;
import com.mmd.mssp.domain.IretailProject;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.CommAreaRepository;
import com.mmd.mssp.repository.CommProvinceRepository;
import com.mmd.mssp.repository.IretailApproveLogRepository;
import com.mmd.mssp.repository.IretailProjectRepository;
import com.mmd.mssp.repository.ProcessStepRepository;
import com.mmd.mssp.service.CommAreaService;
import com.mmd.mssp.service.CommProvinceService;
import com.mmd.mssp.service.IretailProjectService;
import com.mmd.mssp.service.PDFService;
import com.mmd.mssp.service.ProcessStepService;

@Service
public class IretailProjectServiceImpl implements IretailProjectService {

	@Resource
	IretailProjectRepository iretailProjectRepository;
	
	@Resource
	IretailApproveLogRepository iretailApproveLogRepository;
	
	@Resource
	ProcessStepService processStepService;
	
	@Resource
	CommAreaService commAreaService;
	
	@Resource
	CommProvinceService commProvinceService;
	
	@Resource
	ProcessStepRepository processStepRepository;
	
	@Resource
	CommAreaRepository areaRepository;
	
	@Resource
	CommProvinceRepository provinceRepository;
	
	@Resource
	PDFService pdfService;
	
	@Override
	public IretailProject save(IretailProject iretailProject) {
		return iretailProjectRepository.save(iretailProject);
	}
	
	@Override
	public Paging<IretailProject> findIretailProjectByTypeAndAgent(String type,
			List<CommProvince> currentProvinces,Integer page, Integer size) {
		//return iretailProjectRepository.findIretailProjectByTypeAndAgent(type,commAgent);
		Paging<IretailProject> paging = new Paging<IretailProject>(page, size);
		Page<IretailProject> result = iretailProjectRepository.findIretailProjectByTypeAndAgent(type,currentProvinces,paging.toPage());
		paging.setResult(result);
		return paging;
	}
	
	@Override
	public Paging<IretailProject> findIretailProjectByTypeAndAgent(String type,
			List<CommProvince> currentProvinces,Integer startStep,Integer endStep,Integer page, Integer size) {
		//return iretailProjectRepository.findIretailProjectByTypeAndAgent(type,commAgent);
		Paging<IretailProject> paging = new Paging<IretailProject>(page, size);
		Page<IretailProject> result = iretailProjectRepository.findIretailProjectByTypeAndAgent(type,currentProvinces,startStep,endStep,paging.toPage());
		paging.setResult(result);
		return paging;
	}
	
	
	@Override
	public Paging<IretailProject> findIretailProjectBySearch(Integer processStepId, Integer provinceId,List<CommProvince> currentProvinces,String ppn,String type
			,Integer page, Integer size) {
		//return iretailProjectRepository.findIretailProjectByTypeAndAgent(type,commAgent);
		ProcessStep processStep = null;
		Integer processStepIdFlag = 1;
		Integer provinceIdFlag = 1;
		CommProvince commProvince = null;
		Integer ppnFlag = 1;
		Integer storeLevelIdtypeFlag = 1;
		Integer decorateLevelFlag = 1;
		if(processStepId != null){
			processStep = processStepService.findById(processStepId);
			processStepIdFlag = 2;
		}
		if(provinceId != null){
			commProvince = commProvinceService.getCommProvinceById(provinceId);
			provinceIdFlag = 2;
		}
		if(ppn != null && !"".equals(ppn)){
			ppnFlag = 2;
		}
		/*if(decorateLevel != null){
			decorateLevelFlag = 2;
		}*/
		
		Paging<IretailProject> paging = new Paging<IretailProject>(page, size);
		Page<IretailProject> result = iretailProjectRepository.findIretailProjectBySearch(processStep,processStepIdFlag,commProvince,provinceIdFlag,currentProvinces,ppn,ppnFlag,type,paging.toPage());
		paging.setResult(result);
		return paging;
	}
	
	@Override//审核中
	public Paging<IretailProject> findIretailProjectBySearchByApply(Integer provinceId, List<CommProvince> currentProvinces,
			String ppn, String type, Integer page, Integer size) {
		Integer provinceIdFlag = 1;
		CommProvince commProvince = null;
		Integer ppnFlag = 1;
		Integer storeLevelIdtypeFlag = 1;
		Integer decorateLevelFlag = 1;
		if(provinceId != null){
			commProvince = commProvinceService.getCommProvinceById(provinceId);
			provinceIdFlag = 2;
		}
		if(ppn != null && !"".equals(ppn)){
			ppnFlag = 2;
		}
		Paging<IretailProject> paging = new Paging<IretailProject>(page, size);
		Page<IretailProject> result = iretailProjectRepository.findIretailProjectBySearchByApply(commProvince,provinceIdFlag,currentProvinces,ppn,ppnFlag,type,paging.toPage());
		paging.setResult(result);
		return paging;
	}
	
	
	@Override
	public Paging<IretailProject> findIretailProjectBySearchByApply1(
			Integer provinceId, List<CommProvince> currentProvinces,
			String ppn, String type, Integer page, Integer size) {
		Integer provinceIdFlag = 1;
		CommProvince commProvince = null;
		Integer ppnFlag = 1;
		Integer storeLevelIdtypeFlag = 1;
		Integer decorateLevelFlag = 1;
		if(provinceId != null){
			commProvince = commProvinceService.getCommProvinceById(provinceId);
			provinceIdFlag = 2;
		}
		if(ppn != null && !"".equals(ppn)){
			ppnFlag = 2;
		}
		Paging<IretailProject> paging = new Paging<IretailProject>(page, size);
		Page<IretailProject> result = iretailProjectRepository.findIretailProjectBySearchByApply1(commProvince,provinceIdFlag,currentProvinces,ppn,ppnFlag,type,paging.toPage());
		paging.setResult(result);
		return paging;
	}
	
	@Override
	public Paging<IretailProject> findIretailProjectBySearchByApply2(
			Integer provinceId, List<CommProvince> currentProvinces,
			String ppn, String type, Integer page, Integer size) {
		Integer provinceIdFlag = 1;
		CommProvince commProvince = null;
		Integer ppnFlag = 1;
		Integer storeLevelIdtypeFlag = 1;
		Integer decorateLevelFlag = 1;
		if(provinceId != null){
			commProvince = commProvinceService.getCommProvinceById(provinceId);
			provinceIdFlag = 2;
		}
		if(ppn != null && !"".equals(ppn)){
			ppnFlag = 2;
		}
		Paging<IretailProject> paging = new Paging<IretailProject>(page, size);
		Page<IretailProject> result = iretailProjectRepository.findIretailProjectBySearchByApply2(commProvince,provinceIdFlag,currentProvinces,ppn,ppnFlag,type,paging.toPage());
		paging.setResult(result);
		return paging;
	}
	
	
	@Override
	public Paging<IretailProject> findAllIretailProject(Integer page,
			Integer size) {
		Paging<IretailProject> paging = new Paging<IretailProject>(page, size);
		Page<IretailProject> result = iretailProjectRepository.findAllIretailProject(paging.toPage());
		paging.setResult(result);
		return paging;
	}

	@Override
	public IretailProject findProjectById(Integer id) {
		return iretailProjectRepository.getById(id);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.IretailProjectService#findApproveLogListByProject(com.mmd.mssp.domain.IretailProject)
	 */
	@Override
	public List<IretailApproveLog> findApproveLogListByProject(IretailProject project) {
		return iretailApproveLogRepository.findByProject(project);
	}
	
	@Override
	public IretailApproveLog findApproveLogListByProjectAndProcessStep(
			IretailProject project, ProcessStep step) {
		return iretailApproveLogRepository.findByProjectAndProcessStep(project,step);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.IretailProjectService#saveProjectAndApproveLog(com.mmd.mssp.domain.IretailProject, com.mmd.mssp.domain.IretailApproveLog)
	 */
	@Override
	public void saveProjectAndApproveLog(IretailProject project,
			IretailApproveLog log) {
//		ProcessStep step = processStepService.findById(project.getProcessStep().getId());
//		if(step.getPnextId()==null){
//			project.setProcessStep(processStepService.findById(Constants.APPROVE_FINSHED));
//		}
		iretailProjectRepository.save(project);
		iretailApproveLogRepository.save(log);
		
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.IretailProjectService#findProjectListByRole(com.mmd.mssp.domain.CommRole)
	 */
	@Override
	public List<IretailProject> findProjectListByRole(CommRole commRole,List<CommProvince>  provinces) {
		return iretailProjectRepository.findProjectListByRole(commRole,provinces);
	}

	@Override
	public List<IretailProject> findIretailProjectByType(String type) {
		return iretailProjectRepository.findIretailProjectByType(type);
	}

	@Override
	public List<Map> getMap(List<IretailProject> list,String type) {
		List<Map> mapList = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (IretailProject project:list){
			Map map = new HashMap();
			CommProvince province = project.getProvince();
			CommAgent agent = project.getStore();
			String applyCont = project.getApplyContent();
			
			map.put("province", province==null?"":province.getProvince());
			map.put("ppn", project.getPpn());
			map.put("agent", agent.getIrName());
			map.put("applyTheme", project.getApplyTheme());
			map.put("applyDate", sdf.format(project.getApplyDate()));
			map.put("applyPrice", project.getApplyPrice());
			map.put("approveEmail", project.getApproveEmail());
			map.put("operateStatus", project.getProcessStep().getOperateStatus());
			
			if("R01".equals(type)){
				JSONObject iretailR01 = JSON.parseObject(applyCont);
				JSONArray materialDetail = JSON.parseArray(iretailR01.getString("materialDetail"));
				String material ="";
				map.put("beginTime", sdf.format(project.getBeginTime()));
				map.put("endTime", sdf.format(project.getEndTime()));
				map.put("provider", project.getProvider());
				map.put("chargesDetail", iretailR01.get("chargesDetail"));
				map.put("cityLevel", iretailR01.get("cityLevel"));
				map.put("decorateLevel", iretailR01.get("decorateLevel"));
				map.put("iAcreage", iretailR01.get("iAcreage"));
				map.put("iLevel", iretailR01.get("iLevel"));
				map.put("iLocation", iretailR01.get("iLocation"));
				map.put("iName", iretailR01.get("iName"));
				map.put("iPrincipal", iretailR01.get("iPrincipal"));
				map.put("iRealPic", iretailR01.get("iRealPic").toString().replace(",", ";"));
				map.put("iResultPic", iretailR01.get("iResultPic").toString().replace(",", ";"));
				map.put("iTargetVolume", iretailR01.get("iTargetVolume"));
				map.put("lsRate", iretailR01.get("lsRate"));
				map.put("lsVolume", iretailR01.get("lsVolume"));
				map.put("phone", iretailR01.get("phone"));
				map.put("shopPlace", iretailR01.get("shopPlace"));
				map.put("yqVolume", iretailR01.get("yqVolume"));
				map.put("remark", iretailR01.get("remark"));
				for(Object s:materialDetail){
					JSONObject jsonObject=JSONObject.parseObject(s.toString());
					material = material + jsonObject.get("material") + ":" + jsonObject.get("count") +";";
				}
				map.put("material", material);
				mapList.add(map);
			}else if("R02".equals(type)){
				JSONObject iretailR02 = JSON.parseObject(applyCont);
				map.put("samleContent", iretailR02.get("samleContent"));
				map.put("remark", project.getRemark());
				mapList.add(map);
			}else if("R03".equals(type)){
				JSONObject iretailR03 = JSON.parseObject(applyCont);
				JSONArray retailStore = JSON.parseArray(iretailR03.getString("retailStore"));
				String storeEstimateSum ="";
				map.put("AOCMoney", iretailR03.get("AOCMoney"));
				map.put("AOCtoreCount", iretailR03.get("AOCtoreCount"));
				map.put("ASUSMoney", iretailR03.get("ASUSMoney"));
				map.put("ASUStoreCount", iretailR03.get("ASUStoreCount"));
				map.put("MMInvest", iretailR03.get("MMInvest"));
				map.put("PH", iretailR03.get("PH"));
				map.put("SSMoney", iretailR03.get("SSMoney"));
				map.put("SSStoreCount", iretailR03.get("SSStoreCount"));
				map.put("agentInvest", iretailR03.get("agentInvest"));
				map.put("storeAddress", iretailR03.get("storeAddress"));
				map.put("storeName", iretailR03.get("storeName"));
				map.put("totalInvest", iretailR03.get("totalInvest"));
				for(Object s:retailStore){
					JSONObject jsonObject=JSONObject.parseObject(s.toString());
					storeEstimateSum = storeEstimateSum + jsonObject.get("retailStoreName") + ":" + jsonObject.get("estimateSum") +";";
				}
				map.put("retailStore", storeEstimateSum);
				map.put("remark", project.getRemark());
				mapList.add(map);
			}else if("R04".equals(type)){
				JSONArray r04ApplyCont = JSON.parseArray(applyCont);
				for(Object s:r04ApplyCont){
					JSONObject iretailR04=JSONObject.parseObject(s.toString());
					Map mapR04 = new HashMap();
					mapR04.put("province", province==null?"":province.getProvince());
					mapR04.put("ppn", project.getPpn());
					mapR04.put("agent", agent.getIrName());
					mapR04.put("applyTheme", project.getApplyTheme());
					mapR04.put("applyDate", sdf.format(project.getApplyDate()));
					mapR04.put("beginTime", sdf.format(project.getBeginTime()));
					mapR04.put("endTime", sdf.format(project.getEndTime()));
					mapR04.put("provider", project.getProvider());
					mapR04.put("applyPrice", project.getApplyPrice());
					mapR04.put("approveEmail", project.getApproveEmail());
					
					mapR04.put("iResultPic", iretailR04.get("iResultPic").toString().replace(",", ";"));
					mapR04.put("moneySum", iretailR04.get("moneySum"));
					mapR04.put("price", iretailR04.get("price"));
					mapR04.put("propagandaCount", iretailR04.get("propagandaCount"));
					mapR04.put("propagandaLevel", iretailR04.get("propagandaLevel"));
					mapR04.put("propagandaSize", iretailR04.get("propagandaSize"));
					mapR04.put("propagandaTheme", iretailR04.get("propagandaTheme"));
					mapR04.put("remark", iretailR04.get("remark"));
					mapList.add(mapR04);
				}
			}else if("R05".equals(type)){
				map.put("provider", project.getProvider());
				map.put("endTime", sdf.format(project.getEndTime()));
				map.put("beginTime", sdf.format(project.getBeginTime()));
				map.put("remark", project.getRemark());
				JSONObject iretailR05 = JSON.parseObject(applyCont);
				map.put("chargesDetail", iretailR05.get("chargesDetail"));
				map.put("meetingPerNum", iretailR05.get("meetingPerNum"));
				map.put("meetingPlace", iretailR05.get("meetingPlace"));
				map.put("meetingProcess", iretailR05.get("meetingProcess"));
				map.put("others", iretailR05.get("others"));
				map.put("travelProcess", iretailR05.get("travelProcess"));
				mapList.add(map);
			}else if("R06".equals(type)){
				JSONArray r06ApplyCont = JSON.parseArray(applyCont);
				for(Object s:r06ApplyCont){
					JSONObject iretailR06=JSONObject.parseObject(s.toString());
					Map mapR06 = new HashMap();
					mapR06.put("province", province==null?"":province.getProvince());
					mapR06.put("ppn", project.getPpn());
					mapR06.put("agent", agent.getIrName());
					mapR06.put("applyTheme", project.getApplyTheme());
					mapR06.put("applyDate", sdf.format(project.getApplyDate()));
					mapR06.put("applyPrice", project.getApplyPrice());
					mapR06.put("approveEmail", project.getApproveEmail());
					
					mapR06.put("IDCardNumber", iretailR06.get("IDCardNumber"));
					mapR06.put("basePay", iretailR06.get("basePay"));
					mapR06.put("bonus", iretailR06.get("bonus"));
					mapR06.put("monthProductSale", iretailR06.get("monthProductSale"));
					mapR06.put("monthRetailSale", iretailR06.get("monthRetailSale"));
					mapR06.put("name", iretailR06.get("name"));
					mapR06.put("phone", iretailR06.get("phone"));
					mapR06.put("retailName", iretailR06.get("retailName"));
					mapR06.put("retailProductPercent", iretailR06.get("retailProductPercent"));
					mapR06.put("salerMarkContent", iretailR06.get("salerMarkContent"));
					mapR06.put("salerMarkUrl", iretailR06.get("salerMarkUrl"));
					mapR06.put("salerPhotoContent", iretailR06.get("salerPhotoContent"));
					mapR06.put("salerPhotoUrl", iretailR06.get("salerPhotoUrl"));
					mapR06.put("totalSalary", iretailR06.get("totalSalary"));
					mapR06.put("remark", iretailR06.get("remark"));
					mapList.add(mapR06);
				}
			}else if("R07".equals(type)){
				JSONArray r07ApplyCont = JSON.parseArray(applyCont);
				for(Object s:r07ApplyCont){
					JSONObject iretailR07=JSONObject.parseObject(s.toString());
					Map mapR07 = new HashMap();
					mapR07.put("province", province==null?"":province.getProvince());
					mapR07.put("ppn", project.getPpn());
					mapR07.put("agent", agent.getIrName());
					mapR07.put("applyTheme", project.getApplyTheme());
					mapR07.put("applyDate", sdf.format(project.getApplyDate()));
					mapR07.put("applyPrice", project.getApplyPrice());
					mapR07.put("approveEmail", project.getApproveEmail());
					
					mapR07.put("发布费用", iretailR07.get("发布费用"));
					mapR07.put("媒体名称", iretailR07.get("媒体名称"));
					mapR07.put("媒体性质", iretailR07.get("媒体性质"));
					mapR07.put("广告尺寸", iretailR07.get("广告尺寸"));
					mapR07.put("效果图url", iretailR07.get("效果图url"));
					mapR07.put("情况说明", iretailR07.get("情况说明"));
					mapList.add(mapR07);
				}
			}else if("R08".equals(type)){
				JSONArray r08ApplyCont = JSON.parseArray(applyCont);
				for(Object s:r08ApplyCont){
					JSONObject iretailR08=JSONObject.parseObject(s.toString());
					Map mapR08 = new HashMap();
					mapR08.put("province", province==null?"":province.getProvince());
					mapR08.put("ppn", project.getPpn());
					mapR08.put("agent", agent.getIrName());
					mapR08.put("applyTheme", project.getApplyTheme());
					mapR08.put("applyDate", sdf.format(project.getApplyDate()));
					mapR08.put("applyPrice", project.getApplyPrice());
					mapR08.put("beginTime", sdf.format(project.getBeginTime()));
					mapR08.put("endTime", sdf.format(project.getEndTime()));
					mapR08.put("provider", project.getProvider());
					mapR08.put("approveEmail", project.getApproveEmail());
					mapR08.put("remark", project.getRemark());
					
					mapR08.put("人员费用", iretailR08.get("人员费用"));
					mapR08.put("卖场名称", iretailR08.get("卖场名称"));
					mapR08.put("场地位置", iretailR08.get("场地位置"));
					mapR08.put("场租费用", iretailR08.get("场租费用"));
					mapR08.put("实景图url", iretailR08.get("实景图url"));
					mapR08.put("搭建费用", iretailR08.get("搭建费用"));
					mapR08.put("活动期间零售产品销量", iretailR08.get("活动期间零售产品销量"));
					mapList.add(mapR08);
				}
			}else if("R09".equals(type)){
				JSONArray r09ApplyCont = JSON.parseArray(applyCont);
				for(Object s:r09ApplyCont){
					JSONObject iretailR09=JSONObject.parseObject(s.toString());
					Map mapR09 = new HashMap();
					mapR09.put("province", province==null?"":province.getProvince());
					mapR09.put("ppn", project.getPpn());
					mapR09.put("agent", agent.getIrName());
					mapR09.put("applyTheme", project.getApplyTheme());
					mapR09.put("applyDate", sdf.format(project.getApplyDate()));
					mapR09.put("applyPrice", project.getApplyPrice());
					mapR09.put("beginTime", sdf.format(project.getBeginTime()));
					mapR09.put("endTime", sdf.format(project.getEndTime()));
					mapR09.put("provider", project.getProvider());
					mapR09.put("approveEmail", project.getApproveEmail());
					mapR09.put("remark", project.getRemark());
					
					mapR09.put("促销型号", iretailR09.get("促销型号"));
					mapR09.put("实际销量", iretailR09.get("实际销量"));
					mapR09.put("计划销量", iretailR09.get("计划销量"));
					mapR09.put("赠品全额", iretailR09.get("赠品全额"));
					mapR09.put("赠品单价", iretailR09.get("赠品单价"));
					mapR09.put("赠品名称", iretailR09.get("赠品名称"));
					mapR09.put("赠品数量", iretailR09.get("赠品数量"));
					mapList.add(mapR09);
				}
			}else if("R10".equals(type)){//R10申请内容待定，暂时为空
				map.put("beginTime", sdf.format(project.getBeginTime()));
				map.put("endTime", sdf.format(project.getEndTime()));
				map.put("provider", project.getProvider());
				map.put("remark", project.getRemark());
				mapList.add(map);
			}else if("R11".equals(type)){
				JSONArray r11ApplyCont = JSON.parseArray(applyCont);
				for(Object s:r11ApplyCont){
					JSONObject iretailR11=JSONObject.parseObject(s.toString());
					Map mapR11 = new HashMap();
					mapR11.put("province", province==null?"":province.getProvince());
					mapR11.put("ppn", project.getPpn());
					mapR11.put("agent", agent.getIrName());
					mapR11.put("applyTheme", project.getApplyTheme());
					mapR11.put("applyDate", sdf.format(project.getApplyDate()));
					mapR11.put("applyPrice", project.getApplyPrice());
					mapR11.put("beginTime", sdf.format(project.getBeginTime()));
					mapR11.put("endTime", sdf.format(project.getEndTime()));
					mapR11.put("provider", project.getProvider());
					mapR11.put("approveEmail", project.getApproveEmail());
					mapR11.put("remark", project.getRemark());
					
					mapR11.put("下单人姓名", iretailR11.get("下单人姓名"));
					mapR11.put("下单时间", iretailR11.get("下单时间"));
					mapR11.put("产品积分", iretailR11.get("产品积分"));
					mapR11.put("商品单价", iretailR11.get("商品单价"));
					mapR11.put("商品名称", iretailR11.get("商品名称"));
					mapR11.put("商品数量", iretailR11.get("商品数量"));
					mapR11.put("当月代理成本价", iretailR11.get("当月代理成本价"));
					mapR11.put("订单状态", iretailR11.get("订单状态"));
					mapR11.put("订单积分", iretailR11.get("订单积分"));
					mapList.add(mapR11);
				}
			}else if("R12".equals(type)){//R12申请内容待定，暂时为空
				map.put("beginTime", sdf.format(project.getBeginTime()));
				map.put("endTime", sdf.format(project.getEndTime()));
				map.put("provider", project.getProvider());
				map.put("remark", project.getRemark());
				mapList.add(map);
			}
		}
		return mapList;
	}

	@Override
	public Paging<IretailProject> findIretailProjectBySearch(Date applyTimeStart, Date applyTimeEnd,String type,
			String ppn, Integer[] provinceId,Integer[] processStepId,
		    Integer page, Integer size) {
		int areaFlag = 1;
		int processStepFlag = 1;
		int ppnFlag = 1;
		int typeFlag =1;
		List<ProcessStep> processStepList = new ArrayList<ProcessStep>();
		List<CommProvince> provinceList = new ArrayList<CommProvince>();
		
		if(processStepId!=null){
			processStepFlag = 2;
			for(int i=0; i<processStepId.length; i++){
				processStepList.add(processStepRepository.getById(processStepId[i]));
			}
		}else{
			processStepList.add(new ProcessStep());
		}
		if(provinceId!=null){ 
			areaFlag = 2;
			for(int i=0; i<provinceId.length; i++){
				provinceList.add(provinceRepository.getById(provinceId[i]));
			}
		}else{
			provinceList.add(new CommProvince());
		}
		if(ppn != null && !"".equals(ppn)){
			ppnFlag = 2;
		}
		if(type != null && !"".equals(type)){
			typeFlag = 2;
		}
		Paging<IretailProject> paging = new Paging<IretailProject>(page, size);
		Page<IretailProject> result = iretailProjectRepository.findIretailProject(applyTimeStart, applyTimeEnd, type,typeFlag ,provinceList, areaFlag, processStepList, processStepFlag, ppn, ppnFlag, paging.toPage());
		paging.setResult(result);
		return paging;
	}

	@Override
	public List<IretailProject> findIretailProjectBySearchToExport(
			Date applyTimeStart, Date applyTimeEnd, String type, String ppn,
			Integer[] provinceId, Integer[] processStepId) {
		int areaFlag = 1;
		int processStepFlag = 1;
		int ppnFlag = 1;
		List<ProcessStep> processStepList = new ArrayList<ProcessStep>();
		List<CommProvince> provinceList = new ArrayList<CommProvince>();
		
		if(processStepId!=null){
			processStepFlag = 2;
			for(int i=0; i<processStepId.length; i++){
				processStepList.add(processStepRepository.getById(processStepId[i]));
			}
		}else{
			processStepList.add(new ProcessStep());
		}
		if(provinceId!=null){
			areaFlag = 2;
			for(int i=0; i<provinceId.length; i++){
				provinceList.add(provinceRepository.getById(provinceId[i]));
			}
		}else{
			provinceList.add(new CommProvince());
		}
		if(ppn != null && !"".equals(ppn)){
			ppnFlag = 2;
		}
		return iretailProjectRepository.findIretailProjectBySearchToExport(applyTimeStart, applyTimeEnd, type, provinceList, areaFlag, processStepList, processStepFlag, ppn, ppnFlag);
	}

	@Override
	public IretailProject findByPPNAndIType(String ppn, String iType) {
		Integer iTypeFlag = 1;
		if(iType!=null){
			iTypeFlag = 2;
		}
		return iretailProjectRepository.findByPPNAndIType(ppn, iType, iTypeFlag);
	}

	@Override
	public String exportPDF(HttpServletRequest request,
			HttpServletResponse response, Integer id) {
		IretailProject project = iretailProjectRepository.findOne(id);
		request.setAttribute("project", project);
		String type = project.getiType();
		String applyCont = project.getApplyContent();
		String jspPath = "";
		if("R01".equals(type)){
			JSONObject jsObject = JSON.parseObject(applyCont);
			JSONArray materialDetail = JSON.parseArray(jsObject.getString("materialDetail"));
			request.setAttribute("iretailR01", jsObject);
			request.setAttribute("materialDetail", materialDetail);
			jspPath = "/WEB-INF/jsp/admin/iretail/pdf/r01_pdfview.jsp";
		}else if("R02".equals(type)){
			JSONObject jsObject = JSON.parseObject(applyCont);
			request.setAttribute("samleContent", jsObject.getString("samleContent"));
			jspPath = "/WEB-INF/jsp/admin/iretail/pdf/r02_pdfview.jsp";
		}else if("R03".equals(type)){
			JSONObject jsObject = JSON.parseObject(applyCont);
			request.setAttribute("applyContent", jsObject);
			jspPath = "/WEB-INF/jsp/admin/iretail/pdf/r03_pdfview.jsp";
		}else if("R04".equals(type)){
			JSONArray jsArray = JSON.parseArray(applyCont);
			request.setAttribute("applyContent", jsArray);
			jspPath = "/WEB-INF/jsp/admin/iretail/pdf/r04_pdfview.jsp";
		}else if("R05".equals(type)){
			JSONObject jsObject = JSON.parseObject(applyCont);
			request.setAttribute("applyContent", jsObject);
			JSONArray meeting = JSON.parseArray(jsObject.get("meetingSchedule").toString());
			JSONArray costing = JSON.parseArray(jsObject.get("costDetail").toString());
			request.setAttribute("meeting", meeting);
			request.setAttribute("costing", costing);
			if(project.getImage() != null && !(project.getImage().equals(""))){
				String signUrl = JSON.parseObject(project.getImage()).getString("signUrl");
				String sceneUrl = JSON.parseObject(project.getImage()).getString("sceneUrl");
				String summaryUrl = JSON.parseObject(project.getImage()).getString("summaryUrl");
				String waterUrl = JSON.parseObject(project.getImage()).getString("waterUrl");
				String invoiceUrl = JSON.parseObject(project.getImage()).getString("invoiceUrl");
				request.setAttribute("signUrl", signUrl);
				request.setAttribute("summaryUrl", summaryUrl);
				request.setAttribute("waterUrl", waterUrl);
				request.setAttribute("sceneUrl", sceneUrl);
				request.setAttribute("invoiceUrl", invoiceUrl);
			}
			jspPath = "/WEB-INF/jsp/admin/iretail/pdf/r05_pdfview.jsp";
		}else if("R06".equals(type)){
			JSONArray jsonArray=JSONArray.parseArray(applyCont);
			request.setAttribute("jsonArray", jsonArray);
			jspPath = "/WEB-INF/jsp/admin/iretail/pdf/r06_pdfview.jsp";
		}else if("R07".equals(type)){
			JSONArray jsonArray=JSONArray.parseArray(applyCont);
			request.setAttribute("jsonArray", jsonArray);
			jspPath = "/WEB-INF/jsp/admin/iretail/pdf/r07_pdfview.jsp";
		}else if("R08".equals(type)){
			JSONArray jsonArray=JSONArray.parseArray(applyCont);
			request.setAttribute("jsonArray", jsonArray);
			jspPath = "/WEB-INF/jsp/admin/iretail/pdf/r08_pdfview.jsp";
		}else if("R09".equals(type)){
			JSONArray jsonArray=JSONArray.parseArray(applyCont);
			request.setAttribute("jsonArray", jsonArray);
			jspPath = "/WEB-INF/jsp/admin/iretail/pdf/r09_pdfview.jsp";
		}else if("R10".equals(type)){
			JSONArray jsonArray=JSONArray.parseArray(applyCont);
			request.setAttribute("jsonArray", jsonArray);
			jspPath = "/WEB-INF/jsp/admin/iretail/pdf/r10_pdfview.jsp";
		}else if("R11".equals(type)){
			JSONArray jsonArray=JSONArray.parseArray(applyCont);
			request.setAttribute("jsonArray", jsonArray);
			jspPath = "/WEB-INF/jsp/admin/iretail/pdf/r11_pdfview.jsp";
		}else if("R12".equals(type)){
			JSONArray jsonArray=JSONArray.parseArray(applyCont);
			request.setAttribute("jsonArray", jsonArray);
			jspPath = "/WEB-INF/jsp/admin/iretail/pdf/r12_pdfview.jsp";
		}
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final PrintWriter rWriter = new PrintWriter(out);
		HttpServletResponseWrapper nres = new HttpServletResponseWrapper(response){
			@Override
			public PrintWriter getWriter() throws IOException {
				return rWriter;
			}
		};
		try {
			request.getRequestDispatcher(jspPath).include(request, nres);
			rWriter.flush();
			String html = new String(out.toByteArray());
			OutputStream os = null;
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName =type+"项目申请"+"_"+df.format(new Date())+".pdf";
			String pubPath = request.getSession().getServletContext().getRealPath("/");
			String outputFile =  pubPath+"WEB-INF"+File.separator+"upload"+File.separator+"pdf"+File.separator+fileName;
			File file = new File(pubPath+"WEB-INF"+File.separator+"upload"+File.separator+"pdf");
			if(!file.exists()){
				file.mkdirs();
			}
			os = new FileOutputStream(outputFile);
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(html);
			ITextFontResolver fontResolver = renderer.getFontResolver();
			String fontPath = pubPath + "WEB-INF"+File.separator+"fonts"+File.separator+"SIMSUN.TTC";
			fontResolver.addFont(fontPath,BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			renderer.layout();      
			renderer.createPDF(os);
			os.flush();  
			os.close();
			//下载pdf
			pdfService.downloadPDF(response, outputFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<IretailProject> listByTypeAndApplyDate(String type,
			Date applyDate,Date endDate) {
		return iretailProjectRepository.listByTypeAndApplyDate(type, applyDate, endDate);
	}


}

