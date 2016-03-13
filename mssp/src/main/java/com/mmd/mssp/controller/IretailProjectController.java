package com.mmd.mssp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mmd.mssp.comm.ApiWarp;
import com.mmd.mssp.comm.CommonConfig;
import com.mmd.mssp.comm.Constants;
import com.mmd.mssp.domain.ApproveTemplate;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommArea;
import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.CommSeries;
import com.mmd.mssp.domain.DescSetting;
import com.mmd.mssp.domain.IretailApproveLog;
import com.mmd.mssp.domain.IretailBudget;
import com.mmd.mssp.domain.IretailMarket;
import com.mmd.mssp.domain.IretailProject;
import com.mmd.mssp.domain.IretailStoreLevel;
import com.mmd.mssp.domain.Material;
import com.mmd.mssp.domain.Pannel;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.ProductPrice;
import com.mmd.mssp.domain.ProductSeries;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.service.CommAgentService;
import com.mmd.mssp.service.CommAreaService;
import com.mmd.mssp.service.CommBusinessService;
import com.mmd.mssp.service.CommCityService;
import com.mmd.mssp.service.CommProvinceService;
import com.mmd.mssp.service.CommService;
import com.mmd.mssp.service.CommUserService;
import com.mmd.mssp.service.DescSettingService;
import com.mmd.mssp.service.ExcelService;
import com.mmd.mssp.service.IretailBudgetService;
import com.mmd.mssp.service.IretailMarketService;
import com.mmd.mssp.service.IretailProjectService;
import com.mmd.mssp.service.IretailR01Service;
import com.mmd.mssp.service.IretailStoreLevelService;
import com.mmd.mssp.service.MaterialService;
import com.mmd.mssp.service.ProcessStepService;
import com.mmd.mssp.util.DateUtils;
import com.mmd.mssp.util.StringUtil;

@Controller
public class IretailProjectController {
	
	private static final int PAGE_SIZE = 50;
	
	@Resource
	CommService commService;
	
	@Resource
	CommBusinessService commBusinessService;
	
	@Resource
	IretailProjectService iretailProjectService;
	
	@Resource
	IretailR01Service iretailR01Service;
	
	@Resource
	CommCityService commCityService;
	
	@Resource
	CommProvinceService commProvinceService;
	
	@Resource
	CommAgentService commAgentService;
	
	@Resource
	MaterialService materialService;
	
	@Resource
	IretailStoreLevelService iretailStoreLevelService;
	
	@Resource
	CommAreaService commAreaService;
	
	@Resource
	ProcessStepService processStepService;
	
	@Resource
	DescSettingService descSettingService;
	
	@Resource
	IretailMarketService iretailMarketService;
	
	@Resource
	CommUserService commUserService;
	
	@Resource
	ExcelService excelService;
	
	@Resource
	IretailBudgetService iretailBudgetService;
	
	String dataType="";
	
	@RequestMapping("/iretail")
	public String todo(HttpServletRequest request){
		List<CommProvince>  provinces = commService.listCurrentProvince(request);
		List<IretailProject> plist = iretailProjectService.findProjectListByRole(commService.findCurrentUserByRequest(request).getCommRole(),provinces);
		request.setAttribute("plist", plist);
		return "/iretail/todo_list.jsp";
	}
	
	@RequestMapping("/iretail/project/applyMenuList.html")
	public String applyList(HttpServletRequest request){
		String idataType = request.getParameter("idataType");
		dataType=idataType;
		request.getSession().setAttribute("dataType", dataType);
		return "/iretail/apply_menu_list.jsp";
	}
	
	/**
	 * 进入申请页面
	 * @param request
	 * @param type
	 * @return
	 */
	@RequestMapping("/iretail/project/apply.html")
	public String apply(HttpServletRequest request,String type){
		String ppn = commService.getPName(type);
		CommAgent commAgent = commService.findCurrentAgent(request);
		List<CommProvince> provinces = commService.listCurrentProvince(request);//登录用户的省份
		//选择代理商旗下的门店
		List<CommAgent> store = new ArrayList<CommAgent>();
		if("R01".equals(type)){
			for(CommProvince p:provinces){
				List<CommAgent> stores = commAgentService.findStoreByProvince(p);
				store.addAll(stores);
			}
		}else{
			 store.addAll(commAgentService.findStoreByAgent(commAgent.getId()));
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String applyDate = df.format(new Date());
		Date endTime = DateUtils.getDate(2);
		request.setAttribute("ppn", ppn);
		request.setAttribute("provinces", provinces);
		request.setAttribute("store", store);
		request.setAttribute("commAgent", commAgent);
		request.setAttribute("applyDate", applyDate);
		request.setAttribute("endTime", df.format(endTime));
		if("R01".equals(type)){
			List<CommAgent> commAgents = new ArrayList<CommAgent>();
			for(CommProvince p:provinces){
				commAgents.add(commService.findAgentByProvince(p));
			}
			request.setAttribute("commAgents", commAgents);
			List<Material> listMaterials = materialService.listByIsDelete(Boolean.FALSE);
//			List<CommAgent> storeList = commAgentService.findByIrLevelAndIsDelete(6, Boolean.FALSE);0000000
			List<IretailMarket> storeList = iretailMarketService.findAll();//所有的卖场
			request.setAttribute("storeList", storeList);
			request.setAttribute("listMaterials", listMaterials);
			return "/iretail/r01_store_desc_apply.jsp";
		}else if("R02".equals(type)){
			return "/iretail/r02_sample_apply.jsp";
		}else if("R03".equals(type)){
			List<CommAgent> storeList = commAgentService.findByIrLevelAndIsDelete(6, Boolean.FALSE);
			request.setAttribute("storeList", storeList);
			return "/iretail/r03_store_bind_apply.jsp";
		}else if("R04".equals(type)){
			return "/iretail/r04_propaganda_apply.jsp";
		}else if("R05".equals(type)){
			return "/iretail/r05_store_meeting_apply.jsp";
		}else if("R06".equals(type)){
			return  "/iretail/r06_wages_apply.jsp";
		}else if("R07".equals(type)){
			return  "/iretail/r07_media_ad_apply.jsp";
		}else if("R08".equals(type)){
			return  "/iretail/r08_roadshow_apply.jsp";
		}else if("R09".equals(type)){
			return  "/iretail/r09_sales_apply.jsp";
		}else if("R10".equals(type)){
			return  "/iretail/r10_gold_apply.jsp";
		}else if("R11".equals(type)){
			return  "/iretail/r11_integral_apply.jsp";
		}else if("R12".equals(type)){
			return  "/iretail/r12_reward_apply.jsp";
		}else{
			return  "redirect:/iretail/project/applyMenuList.html";
		}
	}
	
	/**
	 * 申请列表
	 * @param request
	 * @param type
	 * @param page
	 * @return
	 */
	@RequestMapping("/iretail/project/applylist.html")
	public String applylist(HttpServletRequest request,String type,@RequestParam(required=false) Integer page){
		CommAgent commAgent = commService.findCurrentAgent(request);
		
		List<CommProvince> currentProvinces = commService.listCurrentProvince(request);
		
		Paging<IretailProject> paging;// = iretailProjectService.findIretailProjectByTypeAndAgent(type,commAgent,page, PAGE_SIZE);
		if("R01".equalsIgnoreCase(type)){
			paging = iretailProjectService.findIretailProjectByTypeAndAgent(type,currentProvinces,page, PAGE_SIZE);
		}else{
			int startStep = 0;
			int endStep=0;
			//PO核销
			if(dataType.equals(Constants.IRETAIL_PO_SHENQING)){
				startStep=0;
				endStep=10;
			}else{//PO申请
				startStep=10;
				endStep=100;
			}
			paging = iretailProjectService.findIretailProjectByTypeAndAgent(type,currentProvinces,startStep,endStep,page, PAGE_SIZE);
		}
		ApproveTemplate temp;
		if("R01".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R01());
		}else if("R02".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R02());
		}else if("R05".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R05());
		}else if("R06".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R06());
		}else if("R09".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R09());
		}else if("R10".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R10());
		}else if("R11".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R11());
		}else if("R12".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R12());
		}else{
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_PROJECT());
		}
		List<ProcessStep> processSteps = processStepService.findByTemp(temp);
		List<ProcessStep> processStepTotal = new ArrayList<ProcessStep>();
		if(!("R01".equals(type))){
			if(dataType.equals("1")){//PO申请
				for(ProcessStep s : processSteps){
					if(s.getStatusValue()<10){
						processStepTotal.add(s);
					}
				}
			}
			if(dataType.equals("2")){//PO核销
				for(ProcessStep s : processSteps){
					if(s.getStatusValue() > 9){
						processStepTotal.add(s);
					}
				}
			}
		}else{
			processStepTotal = processSteps;
		}
		List<CommArea> areas = commAreaService.listAll(Boolean.FALSE);
		List<CommProvince> provinces = commProvinceService.listAll();
		/*for(IretailProject project:paging.getContent()){
			if(project.getProcessStep().getPnextId() != null&&project.getProcessStep().getPnextId().getStatusValue() == 10){
				IretailApproveLog logList = iretailProjectService.findApproveLogListByProjectAndProcessStep(project,project.getProcessStep());
				Date dateLogTime = logList.getApproveTime();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dateLogTime);
				calendar.add(Calendar.DATE, 15);
				if(calendar.getTime().getTime()<(new Date()).getTime()){
					project.setStates(1);
					iretailProjectService.save(project);
				}
			}
		}*/
		request.setAttribute("enabled", true);
		request.setAttribute("paging", paging);
		request.setAttribute("type", type);
		request.setAttribute("areas", areas);
		request.setAttribute("provinces", currentProvinces);
		request.setAttribute("processSteps", processStepTotal);
		if("R01".equals(type)){
			List<IretailStoreLevel> storeLevels = iretailStoreLevelService.listByIsDelete(Boolean.FALSE);
			request.setAttribute("storeLevels", storeLevels);
			request.setAttribute("enabled", false);
		}
		return  "/iretail/applylist.jsp";
	}
	
	@RequestMapping("/iretail/project/search")
	public String search(HttpServletRequest request,Integer processStepId, Integer provinceId,String ppn,String type,String applyTimeStart,String applyTimeEnd,Integer storeLevelId,@RequestParam(required=false) Integer page) throws Exception{
		CommAgent commAgent = commService.findCurrentAgent(request);
//		ApproveTemplate temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_PROJECT());
		ApproveTemplate temp;
		if("R01".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R01());
		}else if("R02".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R02());
		}else if("R05".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R05());
		}else if("R06".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R06());
		}else if("R09".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R09());
		}else if("R10".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R10());
		}else if("R11".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R11());
		}else if("R12".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R12());
		}else{
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_PROJECT());
		}
		List<ProcessStep> processSteps = processStepService.findByTemp(temp);
		List<ProcessStep> processStepTotal = new ArrayList<ProcessStep>();
		if(!("R01".equals(type))){
			if(dataType.equals("1")){//PO申请
				for(ProcessStep s : processSteps){
					if(s.getStatusValue()<10){
						processStepTotal.add(s);
					}
				}
			}
			if(dataType.equals("2")){//PO核销
				for(ProcessStep s : processSteps){
					if(s.getStatusValue() > 9){
						processStepTotal.add(s);
					}
				}
			}
		}else{
			processStepTotal = processSteps;
		}
		List<CommProvince> currentProvinces = commService.listCurrentProvince(request);
		List<CommProvince> provinces = commProvinceService.listAll();
		request.setAttribute("enabled", true);
		if("R01".equals(type)){
			List<IretailStoreLevel> storeLevels = iretailStoreLevelService.listByIsDelete(Boolean.FALSE);
			request.setAttribute("storeLevels", storeLevels);
			request.setAttribute("enabled", false);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date ayDateStart;
		Date ayDateEnd;
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)+1,0,0,0);
		if(applyTimeStart!=null && !"".equals(applyTimeStart)){
			ayDateStart = sdf.parse(applyTimeStart);
		}else{
			ayDateStart = DateUtils.getCurrentMonthFisrtDay();
		}
		if(applyTimeEnd!=null && !"".equals(applyTimeEnd)){
			ayDateEnd = sdf.parse(applyTimeEnd);
		}else{
			ayDateEnd = calendar.getTime();
		}
		String decorateLevel = StringUtil.getHttpQueryParam(request, "decorateLevel");
		Paging<IretailProject> paging;
		if(processStepId != null && processStepId == 0){
			paging =iretailProjectService.findIretailProjectBySearchByApply(provinceId,currentProvinces,ppn,type,page, PAGE_SIZE);
		}else if(processStepId == null && dataType.equals("1")){//po申请的请选择
			paging = iretailProjectService.findIretailProjectBySearchByApply1(provinceId,currentProvinces,ppn,type,page, PAGE_SIZE);
		}else if(processStepId == null && dataType.equals("2")){//po核销的请选择
			paging = iretailProjectService.findIretailProjectBySearchByApply2(provinceId,currentProvinces,ppn,type,page, PAGE_SIZE);
		}else{
			 paging = iretailProjectService.findIretailProjectBySearch(processStepId,provinceId,currentProvinces,ppn,type,page, PAGE_SIZE);
		}
		
		request.setAttribute("paging", paging);
		request.setAttribute("type", type);
		request.setAttribute("provinces", currentProvinces);
		request.setAttribute("ppn", ppn);
		request.setAttribute("processStepId", processStepId);
		request.setAttribute("provinceId", provinceId);
		request.setAttribute("storeLevelId", storeLevelId);
		request.setAttribute("decorateLevel", decorateLevel);
		request.setAttribute("applyTimeStart", applyTimeStart);
		request.setAttribute("applyTimeEnd", applyTimeEnd);
		request.setAttribute("processSteps", processStepTotal);
		return  "/iretail/applylist.jsp";
	}
	
	/**
	 * 申请查看
	 * @param request
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping("/iretail/project/view-{id}-{type}")
	public String applyView(HttpServletRequest request,@PathVariable Integer id,@PathVariable String type){
		IretailProject project = iretailProjectService.findProjectById(id);
		request.setAttribute("project", project);
		request.setAttribute("type", type);
		List<IretailApproveLog> logList  =  iretailProjectService.findApproveLogListByProject(project);
		request.setAttribute("logList", logList);
		String applyCont = project.getApplyContent();
		if(project.getImage() == null||"".equals(project.getImage())){
			request.setAttribute("check", Boolean.FALSE);
		}else{
			request.setAttribute("check", Boolean.TRUE);
			if("R09".equals(type)||"R11".equals(type)){
				JSONArray invoiceInformation = JSON.parseArray(JSON.parseObject(project.getImage()).getString("invoiceInformation"));
				request.setAttribute("invoiceInformation", invoiceInformation);
			}
		}
		request.setAttribute("image", JSON.parseObject(project.getImage()));
		if("R01".equals(type)){
			if(project.getImage() != null && !(project.getImage().equals("")) && JSON.parseObject(project.getImage()).get("over1") != null && !(JSON.parseObject(project.getImage()).get("over1").equals(""))){
				request.setAttribute("check2", Boolean.TRUE);
			}else{
				request.setAttribute("check2", Boolean.FALSE);
			}
			JSONObject jsObject = JSON.parseObject(applyCont);
			JSONArray materialDetail = JSON.parseArray(jsObject.getString("materialDetail"));
			request.setAttribute("iretailR01", jsObject);
			request.setAttribute("materialDetail", materialDetail);
			return "/iretail/r01_view.jsp";
		}else if("R02".equals(type)){
			JSONObject jsObject = JSON.parseObject(applyCont);
			String[] str = jsObject.get("samleContent").toString().split(",");
			request.setAttribute("samleContent", str);
			return "/iretail/r02_view.jsp";
		}else if("R03".equals(type)){
			JSONObject jsObject = JSON.parseObject(applyCont);
			request.setAttribute("applyContent", jsObject);
			return "/iretail/r03_view.jsp";
		}else if("R04".equals(type)){
			JSONArray jsArray = JSON.parseArray(applyCont);
			request.setAttribute("applyContent", jsArray);
			return "/iretail/r04_view.jsp";
		}else if("R05".equals(type)){
			JSONObject jsObject = JSON.parseObject(applyCont);
			request.setAttribute("applyContent", jsObject);
			JSONArray meeting = JSON.parseArray(jsObject.get("meetingSchedule").toString());
			JSONArray costing = JSON.parseArray(jsObject.get("costDetail").toString());
			JSONArray disadvantage = JSON.parseArray(jsObject.get("disadvantage").toString());
			JSONArray questionAndAnswer = JSON.parseArray(jsObject.get("questionAndAnswer").toString());
			request.setAttribute("meeting", meeting);
			request.setAttribute("costing", costing);
			request.setAttribute("disadvantage", disadvantage);
			request.setAttribute("questionAndAnswer", questionAndAnswer);
			if(project.getImage() != null && !(project.getImage().equals(""))){
				String signUrl = JSON.parseObject(project.getImage()).getString("signUrl");
				String sceneUrl = JSON.parseObject(project.getImage()).getString("sceneUrl");
				String summaryUrl = JSON.parseObject(project.getImage()).getString("summaryUrl");
				String waterUrl = JSON.parseObject(project.getImage()).getString("waterUrl");
				String invoiceUrl = JSON.parseObject(project.getImage()).getString("invoiceUrl");
				String invoiceRealUrl = JSON.parseObject(project.getImage()).getString("invoiceRealUrl");
				JSONArray invoiceInformation = JSON.parseArray(JSON.parseObject(project.getImage()).getString("invoiceInformation"));
				request.setAttribute("signUrl", signUrl);
				request.setAttribute("summaryUrl", summaryUrl);
				request.setAttribute("waterUrl", waterUrl);
				request.setAttribute("sceneUrl", sceneUrl);
				request.setAttribute("invoiceUrl", invoiceUrl);
				request.setAttribute("invoiceRealUrl", invoiceRealUrl);
				request.setAttribute("invoiceInformation", invoiceInformation);
			}
			return "/iretail/r05_view.jsp";
		}else if("R06".equals(type)){
			JSONArray jsonArray=JSONArray.parseArray(applyCont);
			request.setAttribute("jsonArray", jsonArray);
			return "/iretail/r06_view.jsp";
		}else if("R07".equals(type)){
			JSONArray jsonArray=JSONArray.parseArray(applyCont);
			request.setAttribute("jsonArray", jsonArray);
			return "/iretail/r07_view.jsp";
		}else if("R08".equals(type)){
			JSONArray jsonArray=JSONArray.parseArray(applyCont);
			request.setAttribute("jsonArray", jsonArray);
			return "/iretail/r08_view.jsp";
		}else if("R09".equals(type)){
			JSONArray jsonArray=JSONArray.parseArray(applyCont);
			request.setAttribute("jsonArray", jsonArray);
			return "/iretail/r09_view.jsp";
		}else if("R10".equals(type)){
			JSONObject jsObject = JSON.parseObject(applyCont);
			String[] str = jsObject.get("applyCont").toString().split(",");
			request.setAttribute("applyCont", str);
			return "/iretail/r10_view.jsp";
		}else if("R11".equals(type)){
			JSONArray jsonArray=JSONArray.parseArray(applyCont);
			request.setAttribute("jsonArray", jsonArray);
			return "/iretail/r11_view.jsp";
		}else if("R12".equals(type)){
			JSONObject jsObject = JSON.parseObject(applyCont);
			String[] str = jsObject.get("applyCont").toString().split(",");
			request.setAttribute("applyCont", str);
			return "/iretail/r12_view.jsp";
		}else{
			return "redirect:/iretail/project/applyMenuList.html";
		}
	}
	
	/*
	 * 删除项目
	 * 
	 */
	@RequestMapping("/iretail/project/delete-{id}-{type}")
	public String deleteProject(HttpServletRequest request,@PathVariable Integer id,@PathVariable String type){
		IretailProject project = iretailProjectService.findProjectById(id);
		project.setIsDelete(1);
		iretailProjectService.save(project);
		return "redirect:/iretail/project/applylist.html?type="+type;
	}
	
	/*
	 * 申请提交后保存
	 * 
	 */
	
	@RequestMapping(value="/iretail/project/apply/post",method=RequestMethod.POST)
	public String saveApply(HttpServletRequest request,String type){
		IretailProject iretailProject = getIretailProjectParam(request);
		String applyContent = null;
		if("R01".equals(type)){
			applyContent = getIretailR01Param(request);
			iretailProject.setApplyContent(applyContent);
		}else if("R02".equals(type)){
			applyContent = getIretailR02Param(request);
			iretailProject.setApplyContent(applyContent);
			iretailProject.setBeginTime(null);//R02删除有效期
			iretailProject.setEndTime(null);
		}else if("R03".equals(type)){
			applyContent = getIretailR03Param(request);
			iretailProject.setApplyContent(applyContent);
			iretailProject.setBeginTime(null);//R03删除有效期
			iretailProject.setEndTime(null);
		}else if("R04".equals(type)){
			applyContent = getIretailR04Param(request);
			iretailProject.setApplyContent(applyContent);
			iretailProject.setRemark(null);
		}else if("R05".equals(type)){
			applyContent = getIretailR05Param(request);
			iretailProject.setApplyContent(applyContent);
		}else if("R06".equals(type)){
			getIretail06Param(request,iretailProject);
		}else if("R07".equals(type)){
			getIretail07Param(request,iretailProject);
		}else if("R08".equals(type)){
			getIretail08Param(request,iretailProject);
		}else if("R09".equals(type)){
			getIretail09Param(request,iretailProject);
		}else if("R10".equals(type)){
			applyContent = getIretailR10Param(request);
			iretailProject.setApplyContent(applyContent);
		}else if("R11".equals(type)){
			getIretail11Param(request,iretailProject);
		}else if("R12".equals(type)){
			applyContent = getIretail12Param(request);
			iretailProject.setApplyContent(applyContent);
		}
		ApproveTemplate temp;
		if("R01".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R01());
		}else if("R02".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R02());
		}else if("R05".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R05());
		}else if("R06".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R06());
		}else if("R09".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R09());
		}else if("R10".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R10());
		}else if("R11".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R11());
		}else if("R12".equals(type)){
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_R12());
		}else{
			temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_PROJECT());
		}
		iretailProject.setApproveTemplate(temp);
		if("R01".equals(type)){
			iretailProject.setProcessStep(commService.findFisrtStep(temp).getPnextId());
		}else{
			iretailProject.setProcessStep(commService.findFisrtStep(temp));
		}
		
		iretailProjectService.save(iretailProject);
		if(iretailProject.getProcessStep().getPnextId() != null && iretailProject.getProcessStep().getPnextId().getIsAutoApprove() == 1){//自动审批的话
			changeProjectStep(request, iretailProject,0);
			iretailProjectService.save(iretailProject);
		}
		if("R01".equals(type)&&request.getParameter("decorateLevel").equals("新建")){
			newIretailStore(request);
		}
		return "redirect:/iretail/project/applylist.html?type="+type;
	}
	
	private IretailProject getIretailProjectParam(HttpServletRequest request){
		IretailProject iretailProject = new IretailProject();
		CommAgent commAgent = commService.findCurrentAgent(request);
		CommProvince commProvince = commProvinceService.getCommProvinceById(request.getParameter("provinceId")==null?0:Integer.parseInt(request.getParameter("provinceId")));
		iretailProject.setApproveEmail(request.getParameter("approveEmail"));
		iretailProject.setPpn(request.getParameter("ppn"));
		
		iretailProject.setProvince(commProvince);
		iretailProject.setApplyDate(new Date());
		if(request.getParameter("type").equals("R01")){
			iretailProject.setApplyTheme("道具申请");
//			iretailProject.setStore(commAgentService.getById(Integer.valueOf(request.getParameter("commAgentId"))));
			if(request.getParameter("decorateLevel").equals("翻新"))
			iretailProject.setStore(commAgentService.getById(Integer.parseInt(request.getParameter("storeId"))));
			
		}else{
			iretailProject.setApplyTheme(request.getParameter("applyTheme1")+request.getParameter("applyTheme2")+request.getParameter("applyTheme3")+request.getParameter("applyTheme4"));
			iretailProject.setStore(commAgentService.getById(Integer.parseInt(request.getParameter("storeId"))));
		}
		iretailProject.setApplyPrice(request.getParameter("applyPrice")==null?new BigDecimal(0):new BigDecimal(request.getParameter("applyPrice")));
		iretailProject.setBeginTime(new Date());
		iretailProject.setEndTime(DateUtils.getDate(2));
		iretailProject.setProvider(request.getParameter("provider"));
		iretailProject.setiType(request.getParameter("type"));
		iretailProject.setRemark(request.getParameter("remark"));
		iretailProject.setIsDelete(0);
		iretailProject.setApplyUser(commAgent);
		return iretailProject;
		
	}
	
	/**
	 * 获取R01零售店装修申请参数
	 * @param request
	 * @param chargesDetail
	 * @return
	 */
	private String getIretailR01Param(HttpServletRequest request){
		JSONObject applyContJs = new JSONObject();
		if(request.getParameter("decorateLevel").equals("新建")){
			applyContJs.put("iName", request.getParameter("iName"));
		}else{
			applyContJs.put("iName", commAgentService.getById(Integer.valueOf(request.getParameter("storeId"))).getIrName());
		}
		applyContJs.put("decorateLevel", request.getParameter("decorateLevel"));
		applyContJs.put("cityLevel", request.getParameter("cityLevel"));
		applyContJs.put("cityName", request.getParameter("cityName"));
		applyContJs.put("shopPlace", request.getParameter("shopPlace"));
		applyContJs.put("iLevel", request.getParameter("iLevel"));
		applyContJs.put("iLocation", request.getParameter("iLocation"));
		applyContJs.put("yqVolume", request.getParameter("yqVolume"));
		applyContJs.put("iAcreage", request.getParameter("iAcreage"));
		applyContJs.put("lsVolume", request.getParameter("lsVolume"));
		applyContJs.put("iPrincipal", request.getParameter("iPrincipal"));
		applyContJs.put("lsRate", request.getParameter("lsRate"));
		applyContJs.put("phone", request.getParameter("phone"));
		applyContJs.put("iTargetVolume", request.getParameter("iTargetVolume"));
		applyContJs.put("remark", request.getParameter("remark"));
		applyContJs.put("iRealPic", request.getParameter("iRealPic"));
		applyContJs.put("iResultPic", request.getParameter("iResultPic"));
		applyContJs.put("chargesDetail", request.getParameter("chargesDetail"));
		String[] materialNameArr = request.getParameterValues("materialName");
		String[] materialCountArr = request.getParameterValues("materialCount");
		JSONArray jsArray = new JSONArray();
		for(int i=0; i<materialNameArr.length; i++){
			if(materialCountArr[i]!=null){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("material", materialNameArr[i]);
				jsonObject.put("count", materialCountArr[i]);
				jsArray.add(jsonObject);
			}
		}
		applyContJs.put("materialDetail", jsArray.toJSONString());
		return applyContJs.toJSONString();
	}
	
	public void newIretailStore(HttpServletRequest request){
//		CommAgent agent = commService.findCurrentAgent(request);
		CommAgent agent = commAgentService.getById(Integer.parseInt(request.getParameter("commAgentId")));
		CommAgent store = new CommAgent();
		store.setIrName(request.getParameter("iName"));
		store.setpId(agent.getId());
		store.setScore(0);
		store.setIrLevel(6);
		store.setVerifyStatus(0);//审核状态，0，未审核  1，已审核通过   -1，审核未通过
		store.setIrStatus(0);//店面状态 ，  0，为冻结   1，已冻结   2，已流失 
		store.setType(0);
		store.setProvince(agent.getProvince());
		store.setCommCity(agent.getCommCity());
		store.setCreatetime(new Date());
		store.setIsDelete(0);
		commAgentService.save(store);
	}
	/**
	 * 获取R02零售店样机参数
	 * @param request
	 * @return
	 */
	private String getIretailR02Param(HttpServletRequest request){
		String samleContent = request.getParameter("samleContent");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("samleContent", samleContent);
		return jsonObject.toJSONString();
	}
	
	/**
	 * 存取R06促销员工资申请管理
	 * @param request
	 * @param iretailProject
	 * @return
	 */
	private void getIretail06Param(HttpServletRequest request,IretailProject iretailProject){
		JSONArray json = new JSONArray();
		String[] name = request.getParameterValues("applyName");
		for(int i=0;i<name.length;i++){
			JSONObject content = new JSONObject();
			content.put("name", request.getParameterValues("applyName")[i]);
			content.put("IDCardNumber", request.getParameterValues("applyIDCardNumber")[i]);
			content.put("phone", request.getParameterValues("applyPhone")[i]);
			content.put("totalSalary", request.getParameterValues("totalSalary")[i]);
			content.put("remark", request.getParameterValues("remark")[i]);
			content.put("retailName", request.getParameterValues("retailName")[i]);
			content.put("monthProductSale", request.getParameterValues("monthProductSale")[i]);
			content.put("monthRetailSale", request.getParameterValues("monthRetailSale")[i]);
			content.put("salerPhotoUrl", request.getParameterValues("photoUrl")[i]);
			content.put("salerPhotoContent", request.getParameterValues("salerPhotoContent")[i]);
			content.put("salerMarkContent", request.getParameterValues("salerMarkContent")[i]);
			content.put("retailProductPercent", request.getParameterValues("retailProductPercent")[i]);
			json.add(content);
		}
		
		iretailProject.setApplyContent(json.toString());
		
	}
	
	/**
	 * 存取R07区域媒体广告申请
	 * @param request
	 * @param iretailProject
	 * @return
	 */
	private void getIretail07Param(HttpServletRequest request,IretailProject iretailProject){
		JSONArray json = new JSONArray();
		String[] array = request.getParameterValues("mediaName");
		for(int i=0;i<array.length;i++){
			if(array[i] != null){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("媒体名称", request.getParameterValues("mediaName")[i]);
				jsonObject.put("媒体性质", request.getParameterValues("mediaNature")[i]);
				jsonObject.put("广告尺寸", request.getParameterValues("adSize")[i]);
				jsonObject.put("发布费用", request.getParameterValues("releaseCost")[i]);
				jsonObject.put("情况说明", request.getParameterValues("remark")[i]);
				jsonObject.put("效果图url", request.getParameterValues("pictureUrl")[i]);
				json.add(jsonObject);
			}
		}
		iretailProject.setApplyContent(json.toJSONString());
		
	}
	
	/**
	 * 存取R08路演申请管理
	 * @param request
	 * @param iretailProject
	 * @return
	 */
	private void getIretail08Param(HttpServletRequest request,IretailProject iretailProject){
		JSONArray json = new JSONArray();
		String[] array = request.getParameterValues("storeName");
		for(int i=0;i<array.length;i++){
			if(array[i] != null){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("卖场名称", request.getParameterValues("storeName")[i]);
				jsonObject.put("场地位置", request.getParameterValues("storeArea")[i]);
				jsonObject.put("活动期间零售产品销量", request.getParameterValues("saleVolume")[i]);
				jsonObject.put("场租费用", request.getParameterValues("rentalCost")[i]);
				jsonObject.put("搭建费用", request.getParameterValues("constructionCost")[i]);
				jsonObject.put("人员费用", request.getParameterValues("memberCost")[i]);
				jsonObject.put("实景图url", request.getParameterValues("pictureUrl")[i]);
				json.add(jsonObject);
			}
		}
		iretailProject.setApplyContent(json.toJSONString());
	}
	
	/**
	 * 存取R09终端用户促销申请
	 * @param request
	 * @param iretailProject
	 * @return
	 */
	private void getIretail09Param(HttpServletRequest request,IretailProject iretailProject){
		JSONArray json = new JSONArray();
		String[] array = request.getParameterValues("col1");
		for(int i=0;i<array.length;i++){
			if(array[i] != null){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("促销型号", array[i]);
				jsonObject.put("计划销量", request.getParameterValues("col2")[i]);
				jsonObject.put("实际销量", request.getParameterValues("col3")[i]);
				jsonObject.put("赠品名称", request.getParameterValues("col4")[i]);
				jsonObject.put("赠品单价", request.getParameterValues("col5")[i]);
				jsonObject.put("赠品数量", request.getParameterValues("col6")[i]);
				jsonObject.put("赠品全额", request.getParameterValues("col7")[i]);
				json.add(jsonObject);
			}
		}
		iretailProject.setApplyContent(json.toJSONString());
	}
	
	/**
	 * 存取R10零售店支持金申请管理
	 * @param request
	 * @param iretailProject
	 * @return
	 */
	private String getIretailR10Param(HttpServletRequest request){
		String applyCont = request.getParameter("applyCont");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("applyCont", applyCont);
		return jsonObject.toJSONString();
	}
	
	/**
	 * 存取R11精英网积分兑换申请管理
	 * @param request
	 * @param iretailProject
	 * @return
	 */
	private void getIretail11Param(HttpServletRequest request,IretailProject iretailProject){
		JSONArray json = new JSONArray();
		String[] array = request.getParameterValues("col1");
		for(int i=0;i<array.length;i++){
			if(array[i] != null){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("下单人姓名", array[i]);
				jsonObject.put("商品名称", request.getParameterValues("col2")[i]);
				jsonObject.put("商品数量", request.getParameterValues("col3")[i]);
				jsonObject.put("商品单价", request.getParameterValues("col4")[i]);
				jsonObject.put("产品积分", request.getParameterValues("col5")[i]);
				jsonObject.put("订单积分", request.getParameterValues("col6")[i]);
				jsonObject.put("下单时间", request.getParameterValues("col7")[i]);
				jsonObject.put("订单状态", request.getParameterValues("col8")[i]);
				jsonObject.put("当月代理成本价", request.getParameterValues("col9")[i]);
				json.add(jsonObject);
			}
		}
		iretailProject.setApplyContent(json.toJSONString());
	}
	
	/**
	 * 存取R12门店零售奖励申请
	 * @param request
	 * @param iretailProject
	 * @return
	 */
	private String getIretail12Param(HttpServletRequest request){
		String applyCont = request.getParameter("applyCont");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("applyCont", applyCont);
		return jsonObject.toJSONString();
	}
		

	/**获取R03卖场绑定申请参数
	 * @param request
	 * @return
	 */
	private String getIretailR03Param(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("storeName", request.getParameter("storeName"));
		jsonObject.put("storeAddress", request.getParameter("storeAddress"));
		jsonObject.put("SSMoney", request.getParameter("SSMoney"));
		jsonObject.put("SSStoreCount", request.getParameter("SSStoreCount"));
		jsonObject.put("AOCMoney", request.getParameter("AOCMoney"));
		jsonObject.put("AOCtoreCount", request.getParameter("AOCtoreCount"));
		jsonObject.put("ASUSMoney", request.getParameter("ASUSMoney"));
		jsonObject.put("ASUStoreCount", request.getParameter("ASUStoreCount"));
		jsonObject.put("PH", request.getParameter("PH"));
		jsonObject.put("totalInvest", request.getParameter("totalInvest"));
		jsonObject.put("MMInvest", request.getParameter("MMInvest"));
		jsonObject.put("agentInvest", request.getParameter("agentInvest"));
		String[] retailStoreNameArr =  request.getParameterValues("retailStoreName");
		String[] estimateSumArr = request.getParameterValues("estimateSum");
		JSONArray jsArray = new JSONArray();
		for(int i=0; i<retailStoreNameArr.length; i++){
			JSONObject storeJson = new JSONObject();
			storeJson.put("retailStoreName", retailStoreNameArr[i]);
			storeJson.put("estimateSum", estimateSumArr[i]);
			jsArray.add(storeJson);
		}
		jsonObject.put("retailStore", jsArray);
		return jsonObject.toJSONString();
	}
	
	/**
	 * 获取R04宣传品申请参数
	 * @param request
	 * @return
	 */
	private String getIretailR04Param(HttpServletRequest request){
		JSONArray jsArray = new JSONArray();
		String[] propagandaLevelArr = request.getParameterValues("propagandaLevel");
		String[] propagandaSizeArr = request.getParameterValues("propagandaSize");
		String[] propagandaCountArr = request.getParameterValues("propagandaCount");
		String[] priceArr = request.getParameterValues("price");
		String[] moneySumArr = request.getParameterValues("moneySum");
		String[] propagandaThemeArr = request.getParameterValues("propagandaTheme");
		String[] iResultPicArr = request.getParameterValues("iResultPic");
		String[] remarkArr = request.getParameterValues("remark");
		for(int i=0; i<propagandaLevelArr.length; i++){
			JSONObject jsObject = new JSONObject();
			jsObject.put("propagandaLevel", propagandaLevelArr[i]);
			jsObject.put("propagandaSize", propagandaSizeArr[i]);
			jsObject.put("propagandaCount", propagandaCountArr[i]);
			jsObject.put("price", priceArr[i]);
			jsObject.put("moneySum", moneySumArr[i]);
			jsObject.put("propagandaTheme", propagandaThemeArr[i]);
			jsObject.put("iResultPic", iResultPicArr[i]);
			jsObject.put("remark", remarkArr[i]);
			jsArray.add(jsObject);
		}
		return jsArray.toJSONString();
	}
	
	/**
	 * 获取R05零售店会议申请
	 * @param request
	 * @return
	 */
	private String getIretailR05Param(HttpServletRequest request){
		JSONObject jsObject = new JSONObject();
		jsObject.put("meetingName", request.getParameter("meetingName"));
		jsObject.put("meetingPlace", request.getParameter("meetingPlace"));
		jsObject.put("meetingTime", request.getParameter("meetingTime"));
		
		
		JSONArray array = new JSONArray();//行程安排
		String[] time = request.getParameterValues("applyTime");
		for(int i=0;i<time.length;i++){
			JSONObject apply = new JSONObject();
			apply.put("applyTime", request.getParameterValues("applyTime")[i]);
			apply.put("applyContent", request.getParameterValues("applyContent")[i]);
			apply.put("applyPerson", request.getParameterValues("applyPerson")[i]);
			array.add(apply);
		}
		jsObject.put("meetingSchedule", array.toJSONString());
		jsObject.put("travelPosition", request.getParameter("travelPosition"));
		jsObject.put("travelTotalPeople", request.getParameter("travelTotalPeople"));
		jsObject.put("travelTrip", request.getParameter("travelTrip"));
		JSONArray arrays = new JSONArray();//费用明细
		String[] costMeetRoom = request.getParameterValues("costMeetRoom");
		for(int j=0;j<costMeetRoom.length;j++){
			JSONObject apply = new JSONObject();
			apply.put("costMeetRoom", request.getParameterValues("costMeetRoom")[j]);
			apply.put("costLunch", request.getParameterValues("costLunch")[j]);
			apply.put("costDinner", request.getParameterValues("costDinner")[j]);
			apply.put("costOther", request.getParameterValues("costOther")[j]);
			apply.put("costTotal", request.getParameterValues("costTotal")[j]);
			arrays.add(apply);
		}
		jsObject.put("costDetail", arrays.toJSONString());
		
		//开始存储会议流程的内容
		jsObject.put("trainTime", request.getParameter("trainTime"));//培训时间
		jsObject.put("trainPlace", request.getParameter("trainPlace"));//培训地点
		jsObject.put("trainContent",request.getParameter("trainContent"));//培训内容
		jsObject.put("trainObject",request.getParameter("trainObject"));//培训对象
		jsObject.put("studentEnvironment", request.getParameter("studentEnvironment"));//学员对环境的评价
		jsObject.put("studentEnvironmentScore", request.getParameter("studentEnvironmentScore"));//学员对环境打分
		jsObject.put("studentCourse", request.getParameter("studentCourse"));//学员对课件内容评价
		jsObject.put("studentCourseScore", request.getParameter("studentCourseScore"));//学员对课件内容打分
		jsObject.put("studentTeacher", request.getParameter("studentTeacher"));//学员对讲师评价
		jsObject.put("studentTeacherScore", request.getParameter("studentTeacherScore"));//学员对讲师评价打分
		jsObject.put("advantage", request.getParameter("advantage"));//培训总结的优点
		JSONArray disadvantage = new JSONArray();//缺点
		String[] shortcoming = request.getParameterValues("shortcoming");
		for(int k=0;k<shortcoming.length;k++){
			JSONObject apply = new JSONObject();
			apply.put("shortcoming", request.getParameterValues("shortcoming")[k]);
			apply.put("improving", request.getParameterValues("improving")[k]);
			apply.put("chargePerson", request.getParameterValues("chargePerson")[k]);
			apply.put("timePlace", request.getParameterValues("timePlace")[k]);
			disadvantage.add(apply);
		}
		jsObject.put("disadvantage", disadvantage.toJSONString());
		JSONArray questionAndAnswer = new JSONArray();//学员提问
		String[] studentAsk = request.getParameterValues("studentAsk");
		for(int m=0;m<studentAsk.length;m++){
			JSONObject apply = new JSONObject();
			apply.put("studentAsk", request.getParameterValues("studentAsk")[m]);
			apply.put("reply", request.getParameterValues("reply")[m]);
			questionAndAnswer.add(apply);
		}
		jsObject.put("questionAndAnswer", questionAndAnswer.toJSONString());
		
		jsObject.put("others", request.getParameter("others"));
		return jsObject.toJSONString();
	}
	
	/**
	 * 获取文件完整存储路径
	 * @param request
	 * @param file
	 * @return
	 */
	public String getFilePath(HttpServletRequest request,MultipartFile file){
		String fileName = file.getOriginalFilename();
		fileName = UUID.randomUUID() + "_" + fileName.substring(fileName.lastIndexOf("."));
		String filePath = null;
		InputStream inputStream;
		try {
			inputStream = file.getInputStream();
			String path = request.getSession().getServletContext().getRealPath("/")+"WEB-INF"+ File.separator+"upload"+File.separator+fileName;
			File files = new File(path);
			FileOutputStream fos = new FileOutputStream(files);
			byte[] b = new byte[1024];
			while(inputStream.read(b)!=-1){
				fos.write(b);
				fos.flush();
			}
			filePath ="http://" + request.getHeader("Host") + request.getSession().getServletContext().getContextPath() +"/upload/"+ fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath;
	}
	
	@RequestMapping("iretail/project/flow-{id}/{flowType}")
	public String flow(HttpServletRequest request,@PathVariable Integer id,@PathVariable Integer flowType){
		IretailProject project = iretailProjectService.findProjectById(id);
		List<IretailApproveLog> logList = iretailProjectService.findApproveLogListByProject(project);
		request.setAttribute("logList", logList);
		request.setAttribute("project", project);
		request.setAttribute("flowType", flowType);
		
		if(flowType==1){//审批
			//只有当前用户登录的角色等于该项目审核步骤中下一步的操作角色 时  页面上才出现审核的按钮
			if(project.getImage() == null||"".equals(project.getImage())){
				request.setAttribute("check", Boolean.FALSE);
			}else{
				request.setAttribute("check", Boolean.TRUE);
			}
			if(project.getiType().equals("R01") && project.getProcessStep().getStatusValue()>99){
//			if(JSON.parseObject(project.getImage()).get("over1") != null && !(JSON.parseObject(project.getImage()).get("over1").equals(""))){
				request.setAttribute("check2", Boolean.TRUE);
			}else{
				request.setAttribute("check2", Boolean.FALSE);
			}
			request.setAttribute("image", JSON.parseObject(project.getImage()));
			if(commService.findCurrentUserByRequest(request).getCommRole().getId().equals(project.getProcessStep().getCommRole().getId())){
				request.setAttribute(Constants.IS_SHOW_APPROVE, Boolean.TRUE);
			}
		}else if(flowType==2){//上传核销资料
			request.setAttribute(Constants.IS_UPLOAD_IMGS, Boolean.TRUE);
		}else if(flowType == 3){
			request.setAttribute("R01Over", Boolean.TRUE);
		}
		if("R01".equalsIgnoreCase(project.getiType())){
			if(project.getImage() == null||"".equals(project.getImage())){
				request.setAttribute("check", Boolean.FALSE);
			}else{
				request.setAttribute("check", Boolean.TRUE);
			}
			request.setAttribute("image", JSON.parseObject(project.getImage()));
			
			String applyCont = project.getApplyContent();
			JSONObject jsObject = JSON.parseObject(applyCont);
			JSONArray materialDetail = JSON.parseArray(jsObject.getString("materialDetail"));
			request.setAttribute("iretailR01", jsObject);
			request.setAttribute("materialDetail", materialDetail);
			return "/iretail/r01_view.jsp";
		}
		else if("R02".equalsIgnoreCase(project.getiType())){
			String applyCont = project.getApplyContent();
			JSONObject jsObject = JSON.parseObject(applyCont);
			String[] str = jsObject.get("samleContent").toString().split(",");
			request.setAttribute("samleContent", str);
			return "/iretail/r02_view.jsp";
		}
		else if("R03".equalsIgnoreCase(project.getiType()))
			return "/iretail/r03_view.jsp";
		else if("R04".equalsIgnoreCase(project.getiType()))
			return "/iretail/r04_view.jsp";
		else if("R05".equalsIgnoreCase(project.getiType())){
			String applyCont = project.getApplyContent();
			JSONObject jsObject = JSON.parseObject(applyCont);
			request.setAttribute("applyContent", jsObject);
			JSONArray meeting = JSON.parseArray(jsObject.get("meetingSchedule").toString());
			JSONArray costing = JSON.parseArray(jsObject.get("costDetail").toString());
			if(project.getImage() != null && !(project.getImage().equals(""))){
				String signUrl = JSON.parseObject(project.getImage()).getString("signUrl");
				String sceneUrl = JSON.parseObject(project.getImage()).getString("sceneUrl");
				String summaryUrl = JSON.parseObject(project.getImage()).getString("summaryUrl");
				String waterUrl = JSON.parseObject(project.getImage()).getString("waterUrl");
				String invoiceUrl = JSON.parseObject(project.getImage()).getString("invoiceUrl");
				String invoiceRealUrl = JSON.parseObject(project.getImage()).getString("invoiceRealUrl");
				request.setAttribute("signUrl", signUrl);
				request.setAttribute("summaryUrl", summaryUrl);
				request.setAttribute("waterUrl", waterUrl);
				request.setAttribute("sceneUrl", sceneUrl);
				request.setAttribute("invoiceUrl", invoiceUrl);
				request.setAttribute("invoiceRealUrl", invoiceRealUrl);
			}
			request.setAttribute("meeting", meeting);
			request.setAttribute("costing", costing);
			return "/iretail/r05_view.jsp";
		}
		else if("R06".equalsIgnoreCase(project.getiType())){
			String applyCont = project.getApplyContent();
			JSONArray jsonArray=JSONArray.parseArray(applyCont);
			request.setAttribute("jsonArray", jsonArray);
			return "/iretail/r06_view.jsp";
		}
		else if("R07".equalsIgnoreCase(project.getiType())){
			String applyCont = project.getApplyContent();
			JSONArray jsonArray=JSONArray.parseArray(applyCont);
			request.setAttribute("jsonArray", jsonArray);
			return "/iretail/r07_view.jsp";
		}	
		else if("R08".equalsIgnoreCase(project.getiType())){
			String applyCont = project.getApplyContent();
			JSONArray jsonArray=JSONArray.parseArray(applyCont);
			request.setAttribute("jsonArray", jsonArray);
			return "/iretail/r08_view.jsp";
		}
		else if("R09".equalsIgnoreCase(project.getiType())){
			String applyCont = project.getApplyContent();
			JSONArray jsonArray=JSONArray.parseArray(applyCont);
			request.setAttribute("jsonArray", jsonArray);
			return "/iretail/r09_view.jsp";
		}
		else if("R10".equalsIgnoreCase(project.getiType())){
			String applyCont = project.getApplyContent();
			JSONObject jsObject = JSON.parseObject(applyCont);
			String[] str = jsObject.get("applyCont").toString().split(",");
			request.setAttribute("applyCont", str);
			return "/iretail/r10_view.jsp";
		}
		else if("R11".equalsIgnoreCase(project.getiType())){
			String applyCont = project.getApplyContent();
			JSONArray jsonArray=JSONArray.parseArray(applyCont);
			request.setAttribute("jsonArray", jsonArray);
			return "/iretail/r11_view.jsp";
		}
		else if("R12".equalsIgnoreCase(project.getiType())){
			String applyCont = project.getApplyContent();
			JSONObject jsObject = JSON.parseObject(applyCont);
			String[] str = jsObject.get("applyCont").toString().split(",");
			request.setAttribute("applyCont", str);
			return "/iretail/r12_view.jsp";
		}
		return null;
	}
	
	@RequestMapping(value="iretail/project/flow",method=RequestMethod.POST)
	public String flowStep(HttpServletRequest request){
		Integer projectId = Integer.parseInt(request.getParameter("projectId"));
		Integer flowType = Integer.parseInt(request.getParameter("flowType"));
		Integer isPass = Integer.parseInt(request.getParameter("isPass"));
		IretailProject project = iretailProjectService.findProjectById(projectId);
		if((project.getProvider() == null) || (project.getProvider().equals(""))){
			project.setProvider(request.getParameter("provider"));
		}
		request.setAttribute("project", project);
		request.setAttribute("flowType", flowType);
		changeProjectStep(request,project,isPass);
		List<IretailApproveLog> logList  =  iretailProjectService.findApproveLogListByProject(project);
		request.setAttribute("logList", logList);
		return "redirect:/iretail/project/applylist.html?type="+project.getiType();
	}
	
	private  void changeProjectStep(HttpServletRequest request,IretailProject project,Integer isPass){
		//如果当前用户的角色不等于当前b2b项目中的下一个审核步骤的角色，则不能审核
		IretailApproveLog log = new IretailApproveLog();
		log.setIsPass(isPass==1?1:0);
		log.setStep(project.getProcessStep());
		log.setProject(project);
		log.setUser(commService.findCurrentUserByRequest(request));
		log.setApproveTime(new Date());
		log.setApproveMsg(request.getParameter("approveMsg"));
		if(isPass==0){
			if(project.getProcessStep().getPnextId() == null){
				project.setProcessStep(processStepService.findById(Constants.APPROVE_FINSHED));
			}else{
				project.setProcessStep(project.getProcessStep().getPnextId());
			}
		}else{
			project.setProcessStep(project.getProcessStep().getRnextId());	
		}
		iretailProjectService.saveProjectAndApproveLog(project,log);
		//改变一次状态，修改一次待办事宜总量
		request.getSession().setAttribute(Constants.IRETAIL_TO_DO_NUM, commService.countIretailToDo(commService.findCurrentUserByRequest(request).getCommRole(),commService.listCurrentProvince(request)));
		project = iretailProjectService.findProjectById(project.getId());
		if(project.getProcessStep() != null && project.getProcessStep().getIsAutoApprove() == 1){//自动审批的话
			changeProjectStep(request, project,0);
		}
	}
	
	/**
	 * 查询店面级别
	 * @param request
	 * @param iTargetVolume
	 * @return
	 */
	@RequestMapping(value="/iretail/project/getILevel",method=RequestMethod.POST)
	@ResponseBody
	public String getILevel(HttpServletRequest request,Integer iTargetVolume){
		ApiWarp apiWarp = new ApiWarp();
		List<IretailStoreLevel> storeLevels = iretailStoreLevelService.listByIsDelete(Boolean.FALSE);
		IretailStoreLevel iretailStoreLevel = null;
		for(int i=0; i<storeLevels.size(); i++){
			if(iTargetVolume>storeLevels.get(i).getTargetVolume()){
				iretailStoreLevel = storeLevels.get(i);
				break;
			}
		}
		if(iretailStoreLevel==null){
			return apiWarp.addError("未匹配上相应店面级别，最低目标量为："+storeLevels.get(storeLevels.size()-1).getTargetVolume()).toJsonString();
		}
		return apiWarp.putData(iretailStoreLevel).toJsonString();
	}
	
	/**
	 * 校验省份是否可以装修
	 * @param request
	 * @param provinceId
	 * @return
	 */
	@RequestMapping(value="/iretail/project/descsetting",method=RequestMethod.POST)
	@ResponseBody
	public String checkIsDesc(HttpServletRequest request,Integer provinceId){
		ApiWarp apiWarp = new ApiWarp();
		CommProvince province = commProvinceService.getCommProvinceById(provinceId);
		DescSetting descSetting = descSettingService.findByProvince(province);
		if(descSetting==null){
			return apiWarp.addError(province.getProvince()+"暂不可装修！").toJsonString();
		}
		return apiWarp.putData("").toJsonString();
	}
	
	
	/**
	 * 校验申请费用
	 * @param request
	 * @param provinceId
	 * @param applyPrice
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/iretail/project/checkApplyPrice",method=RequestMethod.POST)
	@ResponseBody
	public String checkApplyPrice(HttpServletRequest request,Integer provinceId,BigDecimal applyPrice,String type){
		ApiWarp apiWarp = new ApiWarp();
		CommProvince province = commProvinceService.getCommProvinceById(provinceId);
		Date endDate = new Date();
		Calendar calNow = Calendar.getInstance();
		calNow.setTime(endDate);
		int year = calNow.get(Calendar.YEAR);
		Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        Date currYearFirst = calendar.getTime(); 
		List<IretailProject> iretailProjects = iretailProjectService.listByTypeAndApplyDate(type, currYearFirst, endDate);
		BigDecimal applyBigDecimal = new BigDecimal(0);//本月已经申请的费用
		for(int i=0; i<iretailProjects.size(); i++){
			applyBigDecimal = applyBigDecimal.add(iretailProjects.get(i).getApplyPrice());
		}
		List<IretailBudget> iretailBudgets = iretailBudgetService.listByYearsAndMonthAndProvince(year, province);
		BigDecimal totalBgBigDecimal = new BigDecimal(0);
		for(int i=0; i<iretailBudgets.size(); i++){
			if("R02".equals(type)){
				totalBgBigDecimal = totalBgBigDecimal.add(iretailBudgets.get(i).getR02());
			}else if("R03".equals(type)){
				totalBgBigDecimal = totalBgBigDecimal.add(iretailBudgets.get(i).getR03());
			}else if("R04".equals(type)){
				totalBgBigDecimal = totalBgBigDecimal.add(iretailBudgets.get(i).getR04());
			}else if("R05".equals(type)){
				totalBgBigDecimal = totalBgBigDecimal.add(iretailBudgets.get(i).getR05());
			}else if("R06".equals(type)){
				totalBgBigDecimal = totalBgBigDecimal.add(iretailBudgets.get(i).getR06());
			}else if("R07".equals(type)){
				totalBgBigDecimal = totalBgBigDecimal.add(iretailBudgets.get(i).getR07());
			}else if("R08".equals(type)){
				totalBgBigDecimal = totalBgBigDecimal.add(iretailBudgets.get(i).getR08());
			}else if("R09".equals(type)){
				totalBgBigDecimal = totalBgBigDecimal.add(iretailBudgets.get(i).getR09());
			}else if("R10".equals(type)){
				totalBgBigDecimal = totalBgBigDecimal.add(iretailBudgets.get(i).getR10());
			}else if("R11".equals(type)){
				totalBgBigDecimal = totalBgBigDecimal.add(iretailBudgets.get(i).getR11());
			}else if("R12".equals(type)){
				totalBgBigDecimal = totalBgBigDecimal.add(iretailBudgets.get(i).getR12());
			}
		}
		BigDecimal available = totalBgBigDecimal.subtract(applyBigDecimal);
		if(available.compareTo(new BigDecimal(0))==-1){
			apiWarp.addError("当前剩余费用为0！");
		}
		if(available.compareTo(applyPrice)==-1){
			apiWarp.addError("申请费用必须小于当前可用费用!可用"+available.doubleValue());
		}
		return apiWarp.putData("").toJsonString();
	}
	
	@RequestMapping("/iretail/project/upload")
	public String upload(HttpServletRequest request,Integer projectId,@RequestParam("files") MultipartFile[] files,RedirectAttributes attr){
		IretailProject project = iretailProjectService.findProjectById(projectId);
		JSONObject jsonObject = new JSONObject();
		String type = project.getiType();
		if (type.equals("R09")) {
			for (int i = 0; i < files.length; i++) {
				if(i == 0){
					jsonObject.put("invoice", fileStore(request, files[i]));
				}
				if(i == 1){
					jsonObject.put("invoiceReal", fileStore(request, files[i]));
				}
				if(i == 2){
					jsonObject.put("picture", fileStore(request, files[i]));
				}
				if(i == 3){
					jsonObject.put("finalReport", fileStore(request, files[i]));
				}
				if(i == 4){
					jsonObject.put("receiveForm", fileStore(request, files[i]));
				}
			}
			JSONArray array = new JSONArray();
			String[] invoiceCode = request.getParameterValues("invoiceCode");
			for(int i=0;i<invoiceCode.length;i++){
				JSONObject apply = new JSONObject();
				apply.put("invoiceCode", request.getParameterValues("invoiceCode")[i]);
				apply.put("invoiceNumber", request.getParameterValues("invoiceNumber")[i]);
				apply.put("invoiceValue", request.getParameterValues("invoiceValue")[i]);
				array.add(apply);
			}
			jsonObject.put("invoiceInformation", array.toJSONString());
			
		}
		else if (type.equals("R11")) {
			for (int i = 0; i < files.length; i++) {
				if(i == 0){
					jsonObject.put("invoice", fileStore(request, files[i]));
				}
				if(i == 1){
					jsonObject.put("invoiceReal", fileStore(request, files[i]));
				}
				if(i == 2){
					jsonObject.put("receiveImage", fileStore(request, files[i]));
				}
				if(i == 3){
					jsonObject.put("signIn", fileStore(request, files[i]));
				}
			}
			JSONArray array = new JSONArray();
			String[] invoiceCode = request.getParameterValues("invoiceCode");
			for(int i=0;i<invoiceCode.length;i++){
				JSONObject apply = new JSONObject();
				apply.put("invoiceCode", request.getParameterValues("invoiceCode")[i]);
				apply.put("invoiceNumber", request.getParameterValues("invoiceNumber")[i]);
				apply.put("invoiceValue", request.getParameterValues("invoiceValue")[i]);
				array.add(apply);
			}
			jsonObject.put("invoiceInformation", array.toJSONString());
		} 
		else if(type.equals("R01")){
			for (int i = 0; i < files.length; i++) {
				if(i == 0){
					jsonObject.put("effectPicture", fileStore(request, files[i]));
				}
				if(i == 1){
					jsonObject.put("budget", fileStore(request, files[i]));
				}
			}
		}
		else {
			for (int i = 0; i < files.length; i++) {
				jsonObject.put("picture", fileStore(request, files[i]));
			}
		}
		project.setImage(jsonObject.toString());
		changeProjectStep(request,project,0);
		iretailProjectService.save(project);
//		return "redirect:/iretail/project/view-"+project.getId()+"-"+project.getiType();
		return "redirect:/iretail/project/applylist.html?type="+project.getiType();
	}
	
	@RequestMapping("/iretail/project/uploadR01Over")
	public String uploadR01Over(HttpServletRequest request,Integer projectId,@RequestParam("files") MultipartFile[] files,RedirectAttributes attr){
		IretailProject project = iretailProjectService.findProjectById(projectId);
		JSONObject  jsonObject = JSONObject.parseObject(project.getImage());
		for (int i = 0; i < files.length; i++) {
			if(i == 0){
				jsonObject.put("over1", fileStore(request, files[i]));
			}else{
				jsonObject.put("over2", fileStore(request, files[i]));
			}
		}
		project.setImage(jsonObject.toString());
		changeProjectStep(request,project,0);
		iretailProjectService.save(project);
//		return "redirect:/iretail/project/applylist.html";
		return  "redirect:/iretail/project/view-"+project.getId()+"-"+project.getiType();
	}
	
	@RequestMapping("/iretail/project/uploadR05")
	public String upload(HttpServletRequest request,Integer projectId){
		IretailProject project = iretailProjectService.findProjectById(projectId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("signUrl", request.getParameter("signUrl"));
		jsonObject.put("sceneUrl", request.getParameter("sceneUrl"));
		jsonObject.put("summaryUrl", request.getParameter("summaryUrl"));
		jsonObject.put("waterUrl", request.getParameter("waterUrl"));
		jsonObject.put("invoiceUrl", request.getParameter("invoiceUrl"));
		jsonObject.put("invoiceRealUrl", request.getParameter("invoiceRealUrl"));
		//新建个jsonarray存放发票信息
		JSONArray array = new JSONArray();
		String[] invoiceCode = request.getParameterValues("invoiceCode");
		for(int i=0;i<invoiceCode.length;i++){
			JSONObject apply = new JSONObject();
			apply.put("invoiceCode", request.getParameterValues("invoiceCode")[i]);
			apply.put("invoiceNumber", request.getParameterValues("invoiceNumber")[i]);
			apply.put("invoiceValue", request.getParameterValues("invoiceValue")[i]);
			array.add(apply);
		}
		jsonObject.put("invoiceInformation", array.toJSONString());
		project.setImage(jsonObject.toString());
		changeProjectStep(request,project,0);
		iretailProjectService.save(project);
		return "redirect:/iretail/project/applylist.html";
	}
	
	private String fileStore(HttpServletRequest request,MultipartFile file){
		String fileName = "";
		try {
			fileName = file.getOriginalFilename();
			fileName = UUID.randomUUID() + "_" + fileName.substring(fileName.lastIndexOf("."));
			InputStream inputStream = file.getInputStream();
			String path = request.getSession().getServletContext().getRealPath("/")+"WEB-INF"+ File.separator+"upload"+File.separator+fileName;
			File files = new File(path);
			FileOutputStream fos = new FileOutputStream(files);
			byte[] b = new byte[1024];
			//将文件流信息读取文件缓存区，如果读取结果不为-1就代表文件没有读取完毕，反之已经读取完毕
			while(inputStream.read(b)!=-1){
			//将缓存区中的内容写到afterfile文件中
				fos.write(b);
				fos.flush();
			}
			
			String site ="http://" + request.getHeader("Host") + request.getSession().getServletContext().getContextPath() +"/upload/"+ fileName;
			return site;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 生成pdf,暂未启动(待修改)
	 * @param request
	 * @param response
	 * @param projectIds
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/iretail/project/exportPDF")
	public String commitmentNoteExportPDF(HttpServletRequest request,HttpServletResponse response,String projectIds) throws IOException{
		String[] projectIdArr = projectIds.split(",");
		for(int i=0; i<projectIdArr.length; i++){
			iretailProjectService.exportPDF(request, response, Integer.parseInt(projectIdArr[i]));
		}
		return null;
	}
}
