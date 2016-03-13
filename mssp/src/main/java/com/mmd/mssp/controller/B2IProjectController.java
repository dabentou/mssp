package com.mmd.mssp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mmd.mssp.comm.ApiWarp;
import com.mmd.mssp.comm.Common;
import com.mmd.mssp.comm.CommonConfig;
import com.mmd.mssp.comm.Constants;
import com.mmd.mssp.domain.ApproveTemplate;
import com.mmd.mssp.domain.B2IApplyProduct;
import com.mmd.mssp.domain.B2IApproveLog;
import com.mmd.mssp.domain.B2IMeetingApproveLog;
import com.mmd.mssp.domain.B2IMeetingProject;
import com.mmd.mssp.domain.B2IProject;
import com.mmd.mssp.domain.B2ISponsorApplyProduct;
import com.mmd.mssp.domain.B2ISponsorApproveLog;
import com.mmd.mssp.domain.B2ISponsorProject;
import com.mmd.mssp.domain.B2iApplyTemplate;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommBusiness;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommCustomer;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.ProductPrice;
import com.mmd.mssp.domain.UserCity;
import com.mmd.mssp.domain.UserProvince;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.domain.vo.ProductSellInVo;
import com.mmd.mssp.domain.vo.TitleMapper;
import com.mmd.mssp.repository.ApproveTemplateRepository;
import com.mmd.mssp.repository.UserCityRepository;
import com.mmd.mssp.repository.UserProvinceRepository;
import com.mmd.mssp.service.B2IApplyProductService;
import com.mmd.mssp.service.B2IApplySponsorProductService;
import com.mmd.mssp.service.B2IMeetingProjectService;
import com.mmd.mssp.service.B2IProjectService;
import com.mmd.mssp.service.B2ISponsorProjectService;
import com.mmd.mssp.service.B2iApplyTemplateService;
import com.mmd.mssp.service.CommAgentService;
import com.mmd.mssp.service.CommBusinessService;
import com.mmd.mssp.service.CommCityService;
import com.mmd.mssp.service.CommCustomerService;
import com.mmd.mssp.service.CommRoleService;
import com.mmd.mssp.service.CommService;
import com.mmd.mssp.service.CommUserService;
import com.mmd.mssp.service.ExcelService;
import com.mmd.mssp.service.MailService;
import com.mmd.mssp.service.ProcessStepService;
import com.mmd.mssp.service.ProductPriceService;
import com.mmd.mssp.service.ProductService;
import com.mmd.mssp.service.PsiSellInService;
import com.mmd.mssp.util.DateUtils;

@Controller
public class B2IProjectController {
	

	private static final int PAGE_SIZE = 50;
	@Resource
	CommService commService;
	
	@Resource
	ExcelService excelService;
	
	@Resource
	CommCustomerService customerService;
	
	@Resource
	CommAgentService agentService;
	
	@Resource
	PsiSellInService psiSellInService;

	@Resource
	CommBusinessService commBusinessService;
	
	@Resource
	CommRoleService commRoleService;
	
	@Resource
	B2IProjectService b2IProjectService;
	
	@Resource
	ProductService productService;
	
	@Resource
	B2IApplyProductService b2IApplyProductService;
	
	@Resource
	CommUserService commUserService;
	
	@Resource
	MailService mailService;
	
	@Resource
	ApproveTemplateRepository approveTemplateRepository;
	
	@Resource
	CommCityService cityService;
	
	@Resource
	ProcessStepService processStepService;
	
	@Resource
	B2iApplyTemplateService b2iApplyTemplateService;
	
	@Resource
	CommAgentService commAgentService;
	
	@Resource
	UserProvinceRepository userProvinceRepository;
	@Resource
	UserCityRepository userCityRepository;
	
	@Resource
	ProductPriceService productPriceService;
	
	@Resource
	B2IApplySponsorProductService b2iApplySponsorProductService;
	
	@Resource
	B2ISponsorProjectService b2iSponsorProjectService;
	
	@Resource
	B2IMeetingProjectService b2iMeetingProjectService;
	
	
	@RequestMapping("/b2i")
	public String todo(HttpServletRequest request){
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		List<B2IProject> plist = b2IProjectService.findProjectListByRoleAndAgent(commService.findCurrentUserByRequest(request).getCommRole(),agentList);
		List<B2ISponsorProject> sponsorProjects = b2iSponsorProjectService.findProjectListByRoleAndAgent(commService.findCurrentUserByRequest(request).getCommRole(),agentList);
		List<B2IMeetingProject> meetingProjects = b2iMeetingProjectService.findProjectListByRoleAndAgent(commService.findCurrentUserByRequest(request).getCommRole(),agentList);
		JSONArray jsArray = new JSONArray();
		for(int i=0; i<plist.size(); i++){
			JSONObject b2iProject = new JSONObject();
			b2iProject.put("id", plist.get(i).getId());
			b2iProject.put("pCode", plist.get(i).getpCode());
			b2iProject.put("pName", plist.get(i).getpName());
			b2iProject.put("applyTemplate", plist.get(i).getApplyTemplate());
			b2iProject.put("step", plist.get(i).getStep());
			jsArray.add(b2iProject);
		}
		for(int i=0; i<sponsorProjects.size(); i++){
			JSONObject sponorProject = new JSONObject();
			sponorProject.put("id", sponsorProjects.get(i).getId());
			sponorProject.put("pCode", sponsorProjects.get(i).getpCode());
			sponorProject.put("pName", sponsorProjects.get(i).getpName());
			sponorProject.put("applyTemplate", sponsorProjects.get(i).getApplyTemplate());
			sponorProject.put("step", sponsorProjects.get(i).getStep());
			jsArray.add(sponorProject);
		}
		for(int i=0; i<meetingProjects.size(); i++){
			JSONObject meetingProject = new JSONObject();
			meetingProject.put("id", meetingProjects.get(i).getId());
			meetingProject.put("pCode", meetingProjects.get(i).getpCode());
			meetingProject.put("pName", meetingProjects.get(i).getpName());
			meetingProject.put("applyTemplate", meetingProjects.get(i).getApplyTemplate());
			meetingProject.put("step", meetingProjects.get(i).getStep());
			jsArray.add(meetingProject);
		}
		request.setAttribute("plist", jsArray);
		return "/b2i/todo_list.jsp";
	}
	
	@RequestMapping("/b2i/project/apply")
	public String  applyProject(HttpServletRequest request){
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),24);
		calendar.add(Calendar.MONTH, -1);
		Date firstDay = calendar.getTime();
		firstDay = commService.getFisrtDayAfter(firstDay);
		
		Date currentDate = new Date();
		//CommAgent agent = commService.findCurrentAgent(request);
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		List<B2iApplyTemplate> applyTemplates = b2iApplyTemplateService.listByIsDelete(Boolean.FALSE);
		
		//String projectCode = commService.getProjectCode(agent,Constants.PROJECT_CODE_TYPE_B2I);//-----------待处理 当前登陆用户要和城市关联起来，因为需要用到城市的CityCode生成项目编号的前两位
		List<Product> products = productService.listBySellType(1, Boolean.FALSE);//1，网吧型号。2，行业型号。3，零售型号
		String productNames = productService.getProductNameArr(products);
		/*List<CommCustomer> customerList = customerService.listAllByAgent(agent);
		List<CommAgent> siAgentList = agentService.findSiByAgent(agent.getId());*/
		List<CommBusiness> blist = commBusinessService.findBusinessList();
		List<ProductSellInVo> productSellInVolist = new ArrayList<ProductSellInVo>();
		
		/*for (Product product : productlist) {
			ProductSellInVo productSellInVo = new ProductSellInVo();
			int sellIn = psiSellInService.sumSellInCurrent(firstDay, currentDate, product, agent);//b2i上个月25号到目前的sellin累计
			productSellInVo.setProduct(product);
			productSellInVo.setSellIn(sellIn);
			productSellInVolist.add(productSellInVo);
		}*/
		//request.setAttribute("pCode", projectCode);
		/*request.setAttribute("customerList", customerList);
		request.setAttribute("siAgentList", siAgentList);*/
		request.setAttribute("productNames", productNames);
		request.setAttribute("applyDate", new Date());
		request.setAttribute("applyTemplates", applyTemplates);
		request.setAttribute("blist", blist);
		request.setAttribute("agentList", agentList);
		request.setAttribute("productSellInVolist", productSellInVolist);
		return "/b2i/apply_project.jsp";
	}
	

	@RequestMapping("/b2i/project/list.html")
	public String  listProject(HttpServletRequest request,@RequestParam(required=false) Integer page,Integer type){
		List<B2iApplyTemplate> applyTemplates = b2iApplyTemplateService.listByIsDelete(Boolean.FALSE);
		Integer startStep=0;
		Integer endStep=0;
		if(type==1){//项目申请列表
			startStep=0;
			endStep=10;
		}else if(type==2){//核销列表
			startStep=10;
			endStep=100;
		}else if(type==3){//返利列表
			startStep=100;
			endStep=1000;
		}
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		//CommAgent agent = commService.findCurrentAgent(request);
		Paging<B2IProject> paging = null;
		if(agentList.size()>0){
			paging = b2IProjectService.listAll(startStep,endStep, agentList,page,PAGE_SIZE);
		}
		request.setAttribute("type", type);
		request.setAttribute("applyTemplates", applyTemplates);
		request.setAttribute("pagelist", paging);
		return  "/b2i/project_list.jsp";
	}
	
	@RequestMapping("/b2i/project/applylistquery")
	public String sellOutUpdateQueryByDate(HttpServletRequest request,Integer type,Integer applyTempType,@RequestParam(required=false) Integer page) throws ParseException{
		List<B2iApplyTemplate> applyTemplates = b2iApplyTemplateService.listByIsDelete(Boolean.FALSE);
		Integer startStep=0;
		Integer endStep=0;
		if(type==1){//项目申请列表
			startStep=0;
			endStep=10;
		}else if(type==2){//核销列表
			startStep=10;
			endStep=100;
		}else if(type==3){//返利列表
			startStep=100;
			endStep=1000;
		}
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		Paging<B2IProject> b2iProjectList = null;
		Paging<B2ISponsorProject> b2ISponsorProjectList = null;
		Paging<B2IMeetingProject> b2IMeetingProject = null;
		if(applyTempType==1){//模板1
			if(agentList.size()>0){
				b2iProjectList = b2IProjectService.listAll(startStep,endStep, agentList,page,PAGE_SIZE);
			}
			request.setAttribute("pagelist", b2iProjectList);
		}else if(applyTempType==2){//模板2
			if(agentList.size()>0){
				b2ISponsorProjectList = b2iSponsorProjectService.listAll(startStep,endStep, agentList,page,PAGE_SIZE);
			}
			request.setAttribute("pagelist", b2ISponsorProjectList);
		}else if(applyTempType==3){//模板3(网吧 会议申请)
			if(agentList.size()>0){
				b2IMeetingProject = b2iMeetingProjectService.listAll(startStep,endStep, agentList,page,PAGE_SIZE);
			}
			request.setAttribute("pagelist", b2IMeetingProject);
		}
		request.setAttribute("applyTemplates", applyTemplates);
		request.setAttribute("applyTempType", applyTempType);
		request.setAttribute("type", type);
		return "/b2i/project_list.jsp";
	}
	
	@RequestMapping("/b2i/project/view-{id}-{applyTemplateType}-{type}")
	public String b2iProjectView(HttpServletRequest request,@PathVariable Integer id,@PathVariable Integer applyTemplateType,@PathVariable Integer type){
		request.setAttribute("applyTemplateType", applyTemplateType);
		request.setAttribute("type", type);
		request.setAttribute("NATIONAL_MANAGER", CommRole.HANGYEJINGLI);
		request.setAttribute("SALES_DIRECTOR", CommRole.XIAOSHOUZONGJINGLI);
		if(applyTemplateType==1){
			B2IProject project = b2IProjectService.findB2IProjectById(id);
			List<B2IApplyProduct> SAPlist = b2IApplyProductService.findSAPListByProject(project);
			if(type==3){
				List<B2IApplyProduct> new_SAPlist = new ArrayList<B2IApplyProduct>();
				BigDecimal sumRabate = new BigDecimal(0);
				for(int i=0; i<SAPlist.size(); i++){
					B2IApplyProduct b2iApplyProduct = SAPlist.get(i);
					BigDecimal applyNum = new BigDecimal(b2iApplyProduct.getApplyNumber());
					BigDecimal rabate = applyNum.multiply(b2iApplyProduct.getProductPrice().getNetPrice().subtract(b2iApplyProduct.getAreaMangePrice()));
					sumRabate = sumRabate.add(rabate);
					b2iApplyProduct.setRebate(rabate);
					new_SAPlist.add(b2iApplyProduct);
				}
				if(new_SAPlist.size()>0){
					SAPlist = new_SAPlist;
				}
				request.setAttribute("sumRabate", sumRabate);
			}
			List<B2IApproveLog> logList = b2IProjectService.findApproveLogList(project);
			request.setAttribute("logList", logList);
			request.setAttribute("project", project);
			request.setAttribute("SAPlist", SAPlist);
			return "/b2i/project_view.jsp";
		}else if(applyTemplateType==2){
			B2ISponsorProject sponsorProject = b2iSponsorProjectService.findById(id);
			List<B2ISponsorApplyProduct> sApplyProducts = b2iApplySponsorProductService.findByProject(sponsorProject);
			List<B2ISponsorApproveLog> sApproveLogs = b2iSponsorProjectService.findApproveLogList(sponsorProject);
			if(type==3){
				List<B2ISponsorApplyProduct> new_sApplyProducts = new ArrayList<B2ISponsorApplyProduct>();
				BigDecimal sumRabate = new BigDecimal(0);
				for(int i=0; i<sApplyProducts.size(); i++){
					B2ISponsorApplyProduct sponsorApplyProduct = sApplyProducts.get(i);
					BigDecimal applyNum = new BigDecimal(sponsorApplyProduct.getApplyNumber());
					BigDecimal rabate = applyNum.multiply(sponsorApplyProduct.getProductPrice().getNetPrice().subtract(sponsorApplyProduct.getAreaMangePrice()));
					sumRabate = sumRabate.add(rabate);
					sponsorApplyProduct.setRebate(rabate);
					new_sApplyProducts.add(sponsorApplyProduct);
				}
				if(new_sApplyProducts.size()>0){
					sApplyProducts = new_sApplyProducts;
				}
				request.setAttribute("sumRabate", sumRabate);
			}
			request.setAttribute("logList", sApproveLogs);
			request.setAttribute("SAPlist", sApplyProducts);
			request.setAttribute("project", sponsorProject);
			return "/b2i/sponsor_project_view.jsp";
		}else if(applyTemplateType==3){
			B2IMeetingProject meetingProject = b2iMeetingProjectService.findById(id);
			List<B2IMeetingApproveLog> mApproveLogs = b2iMeetingProjectService.findApproveLogList(meetingProject);
			request.setAttribute("project", meetingProject);
			request.setAttribute("logList", mApproveLogs);
			return "/b2i/meeting_project_view.jsp";
		}else{
			return "redirect:/b2i/project/list.html";
		}
		
	}
	
	@RequestMapping("/b2i/project/delete")
	public String b2iProjectDelete(Integer id,Integer applyTempType,RedirectAttributes attr){
		if(applyTempType==1){//第一个基础模板
			B2IProject project = b2IProjectService.findB2IProjectById(id);
			project.setIsDelete(1);
			b2IProjectService.save(project);
		}else if(applyTempType==2){//第二个赞助模板
			B2ISponsorProject sponsorProject = b2iSponsorProjectService.findById(id);
			sponsorProject.setIsDelete(1);
			b2iSponsorProjectService.save(sponsorProject);
		}else if(applyTempType==3){
			B2IMeetingProject meetingProject = b2iMeetingProjectService.findById(id);
			meetingProject.setIsDelete(1);
			b2iMeetingProjectService.save(meetingProject);
		}
		attr.addAttribute("type", 1);
		return "redirect:/b2i/project/list.html";
	}

	@RequestMapping(value="/b2i/apply/save",method=RequestMethod.POST)
	public String newSave(HttpServletRequest request,Integer templateType,String pName,String pCode,Integer agentId,String purchaseTime,Integer customerId,Integer commSi,String projectContext,
			String projectImportant,String competeCondition,String applyReason,Integer productPriceId[],Integer[] sellin,Integer[] number,String[] compProduct,
			BigDecimal[] compPrice,BigDecimal[] areaMangePrice,BigDecimal[] sellMangePrice,BigDecimal[] applyPrice,String specificSupportInfo,String photoUrl,
			BigDecimal[] replyPrice,BigDecimal[] supportMoney,String explain,Integer userId,RedirectAttributes attr, MultipartFile files,Integer businessId,BigDecimal meetingSupportMoney) throws Exception{
		Date applytime = new Date();
		CommAgent agent = agentService.getById(agentId);
		CommCustomer customer = customerService.getCustomerById(customerId);
		CommAgent siAgent = agentService.getById(commSi);
		B2iApplyTemplate applyTemplate = b2iApplyTemplateService.findById(templateType, Boolean.FALSE);
		if(templateType==1){//模板1
			Integer temp1CustomerId = Integer.parseInt(request.getParameter("temp1CustomerId"));
			Integer temp1CommSi = Integer.parseInt(request.getParameter("temp1CommSi"));
			customer = customerService.getCustomerById(temp1CustomerId);
			siAgent = agentService.getById(temp1CommSi);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date pcDate = sdf.parse(purchaseTime);
			B2IProject b2IProject = new B2IProject();
			b2IProject.setAgent(agent);
			b2IProject.setpCode(pCode);
			b2IProject.setpName(pName);
			b2IProject.setPurchaseTime(pcDate);
			b2IProject.setCustomer(customer);
			b2IProject.setCommSi(siAgent);
			b2IProject.setProjectContext(projectContext);
			b2IProject.setProjectImportant(projectImportant);
			b2IProject.setCompeteCondition(competeCondition);
			b2IProject.setApplyReason(applyReason);
			b2IProject.setApplytime(applytime);
			ApproveTemplate temp = commService.findTmpeType(CommonConfig.getTEMP_B2I_BASICS_PROJECT());
			b2IProject.setTemp(temp);
			b2IProject.setStep(commService.findFisrtStep(temp));
			b2IProject.setPhotoUrl(photoUrl);
			b2IProject.setSpecificSupportInfo(specificSupportInfo);
			b2IProject.setIsDelete(0);
			b2IProject.setApplyTemplate(applyTemplate);
			b2IProject.setImage(fileStore(request,files));
			b2IProjectService.save(b2IProject);
			for(int i = 0; i < productPriceId.length; i++){
				B2IApplyProduct applyProduct = new B2IApplyProduct();
				ProductPrice productPrice = productPriceService.getById(productPriceId[i]);
				applyProduct.setProductPrice(productPrice);
				applyProduct.setPsiSellIn(sellin[i]);
				applyProduct.setApplyNumber(number[i]);
				applyProduct.setCompeteProduct(compProduct[i]);
				applyProduct.setCompetePrice(compPrice[i]);
				applyProduct.setApplyPrice(applyPrice[i]);
				applyProduct.setAreaMangePrice(applyPrice[i]);
				applyProduct.setB2IProject(b2IProject);
				b2IApplyProductService.save(applyProduct);
			}
		}else if(templateType==2){
			CommUser applyPerson = commUserService.getCommUserById(userId);
			B2ISponsorProject sponsorProject = new B2ISponsorProject();
			sponsorProject.setpName(pName);
			sponsorProject.setpCode(pCode);
			sponsorProject.setCustomer(customer);
			sponsorProject.setAgent(agent);
			sponsorProject.setCommSi(siAgent);
			sponsorProject.setApplyPerson(applyPerson);
			sponsorProject.setExplain(explain);
			ApproveTemplate temp = commService.findTmpeType(CommonConfig.getTEMP_B2I_PROJECT_OTHER());
			sponsorProject.setTemp(temp);
			sponsorProject.setStep(commService.findFisrtStep(temp));
			sponsorProject.setApplyDate(applytime);
			sponsorProject.setIsDelete(0);
			sponsorProject.setApplyTemplate(applyTemplate);
			b2iSponsorProjectService.save(sponsorProject);
			for(int i = 0; i < productPriceId.length; i++){
				B2ISponsorApplyProduct sponsorApplyProduct = new B2ISponsorApplyProduct();
				ProductPrice productPrice = productPriceService.getById(productPriceId[i]);
				sponsorApplyProduct.setProductPrice(productPrice);
				sponsorApplyProduct.setApplyNumber(number[i]);
				sponsorApplyProduct.setApplyPrice(applyPrice[i]);
				sponsorApplyProduct.setAreaMangePrice(applyPrice[i]);
//				sponsorApplyProduct.setReplyPrice(replyPrice[i]);
				sponsorApplyProduct.setSupportMoney(supportMoney[i]);
				sponsorApplyProduct.setCompeteProduct(compProduct[i]);
				sponsorApplyProduct.setCompetePrice(compPrice[i]);
				sponsorApplyProduct.setB2iSponsorProject(sponsorProject);
				b2iApplySponsorProductService.save(sponsorApplyProduct);
			}
		}else if(templateType==3){//模板3
			String[] customers  =  request.getParameterValues("customerId");
			String[] commSis  =  request.getParameterValues("commSi");
			Integer temp1CustomerId = null;
			Integer temp1CommSi = null;
			for(int i=0; i<customers.length; i++){
				if(!"-1".equals(customers[i])){
					temp1CustomerId = Integer.parseInt(customers[i]);
				}
			}
			for(int i=0; i<commSis.length; i++){
				if(!"-1".equals(commSis[i])){
					temp1CommSi = Integer.parseInt(commSis[i]);
				}
			}
			customer = customerService.getCustomerById(temp1CustomerId);
			siAgent = agentService.getById(temp1CommSi);
			CommBusiness business = commBusinessService.findById(businessId);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date pcDate = sdf.parse(purchaseTime);
			B2IMeetingProject meetingProject = new B2IMeetingProject();
			meetingProject.setpName(pName);
			meetingProject.setpCode(pCode);
			meetingProject.setPurchaseTime(pcDate);
			meetingProject.setBusiness(business);
			meetingProject.setCustomer(customer);
			meetingProject.setAgent(agent);
			meetingProject.setCommSi(siAgent);
			meetingProject.setProjectContext(projectContext);
			meetingProject.setProjectImportant(projectImportant);
			meetingProject.setCompeteCondition(competeCondition);
			meetingProject.setApplyReason(applyReason);
			meetingProject.setApplytime(applytime);
			meetingProject.setSupportMoney(meetingSupportMoney);
			ApproveTemplate temp = commService.findTmpeType(CommonConfig.getTEMP_B2I_PROJECT_OTHER());
			meetingProject.setTemp(temp);
			meetingProject.setStep(commService.findFisrtStep(temp));
			meetingProject.setApplyTemplate(applyTemplate);
			meetingProject.setIsDelete(0);
			b2iMeetingProjectService.save(meetingProject);
		}
		attr.addAttribute("type", 1);
		return "redirect:/b2i/project/list.html";
	}
	
	public void getTemplate1Parems(HttpServletRequest request){
		
	}
	
	
	@RequestMapping("b2i/project/flow-{applyTempType}-{id}/{flowType}")
	public String flow(HttpServletRequest request,@PathVariable Integer id,@PathVariable Integer flowType,@PathVariable Integer applyTempType){
		B2IProject project = null;
		B2ISponsorProject sponsorProject = null;
		B2IMeetingProject meetingProject = null;
		request.setAttribute("applyTemplateType", applyTempType);
		request.setAttribute("NATIONAL_MANAGER", CommRole.HANGYEJINGLI);
		request.setAttribute("SALES_DIRECTOR", CommRole.XIAOSHOUZONGJINGLI);
		if(applyTempType==1){
			project = b2IProjectService.findB2IProjectById(id);
			List<B2IApplyProduct> applyProductList = b2IApplyProductService.findSAPListByProject(project);
			List<B2IApproveLog> logList = b2IProjectService.findApproveLogList(project);
			request.setAttribute("logList", logList);
			request.setAttribute("project", project);
			request.setAttribute("SAPlist", applyProductList);
		}else if(applyTempType==2){
			sponsorProject = b2iSponsorProjectService.findById(id);
			List<B2ISponsorApplyProduct> sApplyProducts = b2iApplySponsorProductService.findByProject(sponsorProject);
			List<B2ISponsorApproveLog> sApproveLogs = b2iSponsorProjectService.findApproveLogList(sponsorProject);
			request.setAttribute("logList", sApproveLogs);
			request.setAttribute("project", sponsorProject);
			request.setAttribute("SAPlist", sApplyProducts);
		}else if(applyTempType==3){//网吧会议申请模板（模板3）
			meetingProject = b2iMeetingProjectService.findById(id);
			List<B2IMeetingApproveLog> mApproveLogs = b2iMeetingProjectService.findApproveLogList(meetingProject);
			request.setAttribute("logList", mApproveLogs);
			request.setAttribute("project", meetingProject);
		}
		request.setAttribute("flowType", flowType);
		
		if(flowType==1){//审批
			//只有当前用户登录的角色等于该项目审核步骤中下一步的操作角色 时  页面上才出现审核的按钮
			if(applyTempType==1){
				if(commService.findCurrentUserByRequest(request).getCommRole().getId().equals(project.getStep().getPnextId().getCommRole().getId())){
					request.setAttribute(Constants.IS_SHOW_APPROVE, Boolean.TRUE);
				}
			}else if(applyTempType==2){
				if(commService.findCurrentUserByRequest(request).getCommRole().getId().equals(sponsorProject.getStep().getPnextId().getCommRole().getId())){
					request.setAttribute(Constants.IS_SHOW_APPROVE, Boolean.TRUE);
				}
			}else if(applyTempType==3){
				if(commService.findCurrentUserByRequest(request).getCommRole().getId().equals(meetingProject.getStep().getPnextId().getCommRole().getId())){
					request.setAttribute(Constants.IS_SHOW_APPROVE, Boolean.TRUE);
				}
			}
		}else if(flowType==2){//上传核销资料
			request.setAttribute(Constants.IS_UPLOAD_IMGS, Boolean.TRUE);
		}else if(flowType==3){//申请返利
			request.setAttribute(Constants.IS_SHOW_APPROVE, Boolean.TRUE);
			request.setAttribute(Constants.IS_UPLOAD_IMGS, Boolean.FALSE);
		}
		if(applyTempType==1){
			return "/b2i/project_view.jsp";
		}else if(applyTempType==2){
			return "/b2i/sponsor_project_view.jsp";
		}else if(applyTempType==3){
			return "/b2i/meeting_project_view.jsp";
		}else{
			return null;
		}
	}
	
	@RequestMapping(value="b2i/project/flow",method=RequestMethod.POST)
	public String flowStep(HttpServletRequest request){
		Integer projectId = Integer.parseInt(request.getParameter("projectId"));
		Integer applyTemplateType = Integer.parseInt(request.getParameter("applyTemplateType"));
		Integer flowType = Integer.parseInt(request.getParameter("flowType"));
		Integer isPass = Integer.parseInt(request.getParameter("isPass"));
		request.setAttribute("applyTemplateType", applyTemplateType);
		if(applyTemplateType==1){//第一个基础模板
			B2IProject project = b2IProjectService.findB2IProjectById(projectId);
			List<B2IApplyProduct> applyProductList = b2IApplyProductService.findSAPListByProject(project);
			request.setAttribute("project", project);
			request.setAttribute("applyProductList", applyProductList);
			changeProjectStep(request,project,isPass);
			List<B2IApproveLog> logList = b2IProjectService.findApproveLogList(project);
			request.setAttribute("logList", logList);
		}else if(applyTemplateType==2){
			B2ISponsorProject sponsorProject = b2iSponsorProjectService.findById(projectId);
			List<B2ISponsorApplyProduct> sApplyProducts = b2iApplySponsorProductService.findByProject(sponsorProject);
			request.setAttribute("project", sponsorProject);
			request.setAttribute("applyProductList", sApplyProducts);
			changeSponsorProjectStep(request,sponsorProject,isPass);
			List<B2ISponsorApproveLog> sApproveLogs = b2iSponsorProjectService.findApproveLogList(sponsorProject);
			request.setAttribute("logList", sApproveLogs);
		}else if(applyTemplateType==3){
			B2IMeetingProject meetingProject = b2iMeetingProjectService.findById(projectId);
			request.setAttribute("project", meetingProject);
			changeMeetingProjectStep(request,meetingProject,isPass);
			List<B2IMeetingApproveLog> mApproveLogs = b2iMeetingProjectService.findApproveLogList(meetingProject);
			request.setAttribute("logList", mApproveLogs);
		}
		request.setAttribute("flowType", flowType);
		if(applyTemplateType==1){
			return "/b2i/project_view.jsp";
		}else if(applyTemplateType==2){
			return "/b2i/sponsor_project_view.jsp";
		}else if(applyTemplateType==3){
			return "/b2i/meeting_project_view.jsp";
		}else {
			return null;
		}
		
	}
	
	/**
	 * 第一个基础模板审批修改步骤
	 * @param request
	 * @param project
	 * @param isPass
	 */
	private  void changeProjectStep(HttpServletRequest request,B2IProject project,Integer isPass){
		B2IApproveLog log = new B2IApproveLog();
		log.setIsPass(isPass==1?1:0);
		log.setStep(project.getStep().getPnextId());
		log.setProject(project);
		log.setUser(commService.findCurrentUserByRequest(request));
		log.setApproveTime(new Date());
		log.setApproveMsg(request.getParameter("approveMsg"));
		if(isPass==0){
			project.setStep(project.getStep().getPnextId());
		}else{
			project.setStep(project.getStep().getRnextId());	
		}
		b2IProjectService.saveProjectAndApproveLog(project, log);
		//改变一次状态，修改一次待办事宜总量
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		request.getSession().setAttribute(Constants.B2I_TO_DO_NUM, commService.countb2iToDo(commService.findCurrentUserByRequest(request).getCommRole(),agentList));
	}
	
	/**
	 * 第二个赞助申请模板审批步骤改变
	 * @param request
	 * @param sponsorProject
	 * @param isPass
	 */
	private  void changeSponsorProjectStep(HttpServletRequest request,B2ISponsorProject sponsorProject,Integer isPass){
		B2ISponsorApproveLog log = new B2ISponsorApproveLog();
		log.setIsPass(isPass==1?1:0);
		log.setStep(sponsorProject.getStep().getPnextId());
		log.setB2iSponsorProject(sponsorProject);
		log.setUser(commService.findCurrentUserByRequest(request));
		log.setApproveTime(new Date());
		log.setApproveMsg(request.getParameter("approveMsg"));
		if(isPass==0){
			sponsorProject.setStep(sponsorProject.getStep().getPnextId());
		}else{
			sponsorProject.setStep(sponsorProject.getStep().getRnextId());	
		}
		b2iSponsorProjectService.saveProjectAndApproveLog(sponsorProject, log);
		//改变一次状态，修改一次待办事宜总量
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		request.getSession().setAttribute(Constants.B2I_TO_DO_NUM, commService.countb2iToDo(commService.findCurrentUserByRequest(request).getCommRole(),agentList));
	}
	
	/**
	 * 网吧会议支持申请模板审批步骤改变
	 * @param request
	 * @param meetingProject
	 * @param isPass
	 */
	private  void changeMeetingProjectStep(HttpServletRequest request,B2IMeetingProject meetingProject,Integer isPass){
		B2IMeetingApproveLog log = new B2IMeetingApproveLog();
		log.setIsPass(isPass==1?1:0);
		log.setStep(meetingProject.getStep().getPnextId());
		log.setProject(meetingProject);
		log.setUser(commService.findCurrentUserByRequest(request));
		log.setApproveTime(new Date());
		log.setApproveMsg(request.getParameter("approveMsg"));
		if(isPass==0){
			meetingProject.setStep(meetingProject.getStep().getPnextId());
		}else{
			meetingProject.setStep(meetingProject.getStep().getRnextId());	
		}
		b2iMeetingProjectService.saveProjectAndApproveLog(meetingProject, log);
		//改变一次状态，修改一次待办事宜总量
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		request.getSession().setAttribute(Constants.B2I_TO_DO_NUM, commService.countb2iToDo(commService.findCurrentUserByRequest(request).getCommRole(),agentList));
	}
	
	
	@RequestMapping("/b2i/project/report.html")
	public String b2iProjectReport(HttpServletRequest request,Integer applyTempType, @RequestParam(required=false) Integer page,String applyTimeStart,String applyTimeEnd,
					String approveTimeStart,String approveTimeEnd,Integer[] processStepId,Integer[] cityId,String pCode,String pName) throws Exception {
		CommAgent agent = commService.findCurrentAgent(request);
		ApproveTemplate temp = approveTemplateRepository.findTempByType(CommonConfig.getTEMP_B2I_BASICS_PROJECT());
		List<CommCity> cityList = cityService.listAllCity(false);
		List<ProcessStep> processStepList = processStepService.findByTemp(temp);
		List<B2iApplyTemplate> applyTemplates = b2iApplyTemplateService.listByIsDelete(Boolean.FALSE);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date ayDate1;
		Date ayDate2;
		Date aeDate1;
		Date aeDate2;
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),0,0,0);
		if(applyTimeStart!=null){
			ayDate1 = sdf.parse(approveTimeStart);
		}else{
			ayDate1 = DateUtils.getCurrentMonthFisrtDay();
		}
		if(applyTimeEnd!=null){
			ayDate2 = sdf.parse(applyTimeEnd);
		}else{
			ayDate2 = calendar.getTime();
		}
		if(approveTimeStart!=null){
			aeDate1 = sdf.parse(approveTimeStart);
		}else{
			aeDate1 = DateUtils.getCurrentMonthFisrtDay();
		}
		if(approveTimeEnd!=null){
			aeDate2 = sdf.parse(approveTimeEnd);
		}else{
			aeDate2 = calendar.getTime();
		}
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		Paging<B2IProject> paging = null;
		Paging<B2ISponsorProject> spPaging = null;
		Paging<B2IMeetingProject> mpPaging = null;
		if(agentList.size()>0){
			if(pName==null){
				if(applyTempType==1){//第一个基础模板
					paging = b2IProjectService.listAllBySearch(agentList, ayDate1, ayDate2, aeDate1, aeDate2, processStepId, cityId, pCode, pName, page, PAGE_SIZE);
					request.setAttribute("pagelist", paging);
				}else if(applyTempType==2){//第二个赞助模板
					spPaging = b2iSponsorProjectService.listAllBySearch(agentList, ayDate1, ayDate2, aeDate1, aeDate2, processStepId, cityId, pCode, pName, page, PAGE_SIZE);
					request.setAttribute("pagelist", spPaging);
				}else if(applyTempType==3){
					mpPaging = b2iMeetingProjectService.listAllBySearch(agentList, ayDate1, ayDate2, aeDate1, aeDate2, processStepId, cityId, pCode, pName, page, PAGE_SIZE);
					request.setAttribute("pagelist", mpPaging);
				}
				request.setAttribute("pName", pName);
			}else{
				if(applyTempType==1){
					paging = b2IProjectService.listAllBySearch(agentList, ayDate1, ayDate2, aeDate1, aeDate2, processStepId, cityId, pCode, new String(pName.getBytes("iso-8859-1"),"UTF-8"), page, PAGE_SIZE);
					request.setAttribute("pagelist", paging);
				}else if(applyTempType==2){
					spPaging = b2iSponsorProjectService.listAllBySearch(agentList, ayDate1, ayDate2, aeDate1, aeDate2, processStepId, cityId, pCode, new String(pName.getBytes("iso-8859-1"),"UTF-8"), page, PAGE_SIZE);
					request.setAttribute("pagelist", spPaging);
				}else if(applyTempType==3){
					mpPaging = b2iMeetingProjectService.listAllBySearch(agentList, ayDate1, ayDate2, aeDate1, aeDate2, processStepId, cityId, pCode, new String(pName.getBytes("iso-8859-1"),"UTF-8"), page, PAGE_SIZE);
					request.setAttribute("pagelist", mpPaging);
				}
				request.setAttribute("pName", new String(pName.getBytes("iso-8859-1"),"UTF-8"));//解决中文乱码
			}
		}
		request.setAttribute("applyTempType", applyTempType);
		request.setAttribute("applyTemplates", applyTemplates);
		request.setAttribute("agent", agent);
		request.setAttribute("applyTimeStart", ayDate1);
		request.setAttribute("applyTimeEnd", ayDate2);
		request.setAttribute("approveTimeStart", aeDate1);
		request.setAttribute("approveTimeEnd", aeDate2);
		request.setAttribute("cityList", cityList);
		request.setAttribute("processStepList", processStepList);
		request.setAttribute("cityId", cityId);
		request.setAttribute("processStepId", processStepId);
		request.setAttribute("pCode", pCode);
		
		return "/b2i/report.jsp";
	}
	
	@RequestMapping(value="/b2i/report/excel")
	public String export(HttpServletRequest request,HttpServletResponse response,Integer applyTempType,String applyTimeStart,String applyTimeEnd,String approveTimeStart,String approveTimeEnd,
			Integer[] processStepId,Integer[] cityId,String pCode,String pName) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date ayDate1;
		Date ayDate2;
		Date aeDate1;
		Date aeDate2;
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),0,0,0);
		if(applyTimeStart!=null){
			ayDate1 = sdf.parse(approveTimeStart);
		}else{
			ayDate1 = DateUtils.getCurrentMonthFisrtDay();
		}
		if(applyTimeEnd!=null){
			ayDate2 = sdf.parse(applyTimeEnd);
		}else{
			ayDate2 = calendar.getTime();
		}
		if(approveTimeStart!=null){
			aeDate1 = sdf.parse(approveTimeStart);
		}else{
			aeDate1 = DateUtils.getCurrentMonthFisrtDay();
		}
		if(approveTimeEnd!=null){
			aeDate2 = sdf.parse(approveTimeEnd);
		}else{
			aeDate2 = calendar.getTime();
		}
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		List<B2IApplyProduct> applyProductList = b2IApplyProductService.listToExport(agentList, ayDate1, ayDate2, aeDate1, aeDate2, processStepId, cityId, pCode, pName);
		List<Map> mapList = b2IApplyProductService.getMapBySearch(applyProductList);
		
		response.setContentType("application/vnd.ms-excel;charset=GBK");  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setHeader("Content-Disposition", "attachment;filename=test.csv");  
        response.setCharacterEncoding("GBK");
        OutputStreamWriter osw = null;
        
        try {
        	osw = new OutputStreamWriter(response.getOutputStream(), "GBK");  
        	osw.write(this.excelService.convert2Cvs(TitleMapper.b2iReportMapper, mapList));
		} catch (IOException e) {
			e.printStackTrace();
		}  finally{
			try {
				osw.flush();
				osw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
		return null;
	}
	
	@RequestMapping("/b2i/project/upload")
	public String upload(HttpServletRequest request,Integer projectId,Integer applyTemplateType,@RequestParam("files") MultipartFile[] files,RedirectAttributes attr){
		B2IProject project = null;
		B2ISponsorProject sponsorProject = null;
		B2IMeetingProject meetingProject = null;
		if(applyTemplateType==1){
			project = b2IProjectService.findB2IProjectById(projectId);
			String path = "";
			for(int i = 0;i<files.length;i++){
				if(i == 0){
					path = fileStore(request, files[i]);
					project.setSignIn(path);
				}else{
					path =fileStore(request, files[i]);
					project.setContract(path);
				}
			}
			project = b2IProjectService.save(project);
			changeProjectStep(request,project,0);
		}else if(applyTemplateType==2){
			sponsorProject = b2iSponsorProjectService.findById(projectId);
			String path = "";
			for(int i = 0;i<files.length;i++){
				if(i == 0){
					path = fileStore(request, files[i]);
					sponsorProject.setSignIn(path);
				}else{
					path =fileStore(request, files[i]);
					sponsorProject.setContract(path);
				}
			}
			sponsorProject = b2iSponsorProjectService.save(sponsorProject);
			changeSponsorProjectStep(request, sponsorProject, 0);
		}else if(applyTemplateType==3){
			meetingProject = b2iMeetingProjectService.findById(projectId);
			String path = "";
			for(int i = 0;i<files.length;i++){
				if(i == 0){
					path = fileStore(request, files[i]);
					meetingProject.setDoor(path);
				}else if(i==1){
					path = fileStore(request, files[i]);
					meetingProject.setPersons(path);
				}else if(i==2){
					path = fileStore(request, files[i]);
					meetingProject.setMachineMoney(path);
				}else{
					path = fileStore(request, files[i]);
					meetingProject.setBgImg(path);
				}
			}
			meetingProject = b2iMeetingProjectService.save(meetingProject);
			changeMeetingProjectStep(request, meetingProject, 0);
		}
		attr.addAttribute("type",2);
		return "redirect:/b2i/project/list.html";
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
	
	@RequestMapping(value="/b2i/agent/change",method=RequestMethod.POST)
	@ResponseBody
	public String agentChange(HttpServletRequest request,Integer agentId){
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),24);
		calendar.add(Calendar.MONTH, -1);
		Date firstDay = calendar.getTime();
		firstDay = commService.getFisrtDayAfter(firstDay);
		Date currentDate = new Date();
		
		JSONObject obj = new JSONObject();
		
		CommAgent agent = agentService.getById(agentId);
		String projectCode = commService.getProjectCode(agent,Constants.PROJECT_CODE_TYPE_B2I);//-----------待处理 当前登陆用户要和城市关联起来，因为需要用到城市的CityCode生成项目编号的前两位
		//现在是所有产品型号,哪些是网吧型号,暂时无法区分
		List<Product> productlist = commService.findProductList(Product.NOT_DELETE);
		List<CommCustomer> customerList = customerService.listAllByAgentAndType(agent, Constants.B2I_CUSTOMER);
		List<CommAgent> siAgentList = agentService.findSiByAgent(agent.getId());
		List<ProductSellInVo> productSellInVolist = new ArrayList<ProductSellInVo>();
		
		for (Product product : productlist) {
			ProductSellInVo productSellInVo = new ProductSellInVo();
			int sellIn = psiSellInService.sumSellInCurrent(firstDay, currentDate, product, agent);//b2i上个月25号到目前的sellin累计
			productSellInVo.setProduct(product);
			productSellInVo.setSellIn(sellIn);
			productSellInVolist.add(productSellInVo);
		}
		//request.setAttribute("pCode", projectCode);
		request.setAttribute("customerList", customerList);
		request.setAttribute("siAgentList", siAgentList);
		
		obj.put("customerList", customerList);
		obj.put("agent", agent);
		obj.put("projectCode", projectCode);
		obj.put("productSellInVolist", productSellInVolist);
		obj.put("siAgentList", siAgentList);
		
		return obj.toJSONString();
	}
	
	@RequestMapping(value = {"/b2i/agent/list.html","/b2b/agent/list.html"})
	public String secondAgentList(HttpServletRequest request){
		CommAgent agent = commService.findCurrentAgent(request);
		List<CommAgent> list = commAgentService.findSiByAgent(agent.getId());
		request.setAttribute("list", list);
		return "/b2i/second_agent_list.jsp";
	}
	
	@RequestMapping("/b2i/agent/new.html")
	public String newSecondAgent(HttpServletRequest request){
		CommAgent agent = commService.findCurrentAgent(request);
		return "/b2i/second_agent_new.jsp";
	}
	
	@RequestMapping("/b2i/agent/newSave.html")
	public String newSaveSecondAgent(HttpServletRequest request,String irName,Integer pId, String phone,String email,String address,Integer isUser,String loginName,String realName){

		CommAgent currentAgent = commService.findCurrentAgent(request);
		
		CommAgent commAgent = new CommAgent();
		
		Date createtime = new Date();
		String password = "123456";
		CommRole commRole = commRoleService.getCommRoleById(3);
		
		commAgent.setIrName(irName);
		commAgent.setScore(0);
		commAgent.setIrStatus(0);
		commAgent.setIrLevel(7);//5总代，7二级代理
		commAgent.setpId(currentAgent.getId());
		commAgent.setVerifyStatus(1);
		commAgent.setType(0);
		commAgent.setPhone(phone);
		commAgent.setEmail(email);
		commAgent.setProvince(currentAgent.getProvince());
		commAgent.setCommCity(currentAgent.getCommCity());
		commAgent.setAddress(address);
		commAgent.setCreatetime(createtime);
		commAgent.setIsDelete(0);
		
		commAgentService.save(commAgent);
		
		if(isUser==1){
			CommUser commUser = new CommUser();
			UserProvince userProvince = new UserProvince();
			UserCity userCity = new UserCity();
			commUser.setCommAgent(commAgent);
			commUser.setPassword(Common.pwdMd5(password));
			commUser.setCommRole(commRole);
			commUser.setLoginName(loginName);
			commUser.setRealName(realName);
			commUser.setPhone(phone);
			commUser.setEmail(email);
			commUser.setCreatetime(createtime);
			commUser.setIsAdmin(0);
			commUser.setIsDelete(0);
			commUserService.save(commUser);

			userProvince.setProvince(currentAgent.getProvince());
			userProvince.setUser(commUser);
			userProvinceRepository.save(userProvince);
			
			userCity.setCity(currentAgent.getCommCity());
			userCity.setUser(commUser);
			userCityRepository.save(userCity);
		}
		return "redirect:/b2i/agent/list.html";
	}
	
	@RequestMapping("/b2i/agent/edit")
	public String editSecondAgent(HttpServletRequest request,Integer id){
		CommAgent agent = commAgentService.getById(id);
		request.setAttribute("agent", agent);
		return "/b2i/second_agent_edit.jsp";
	}
	
	@RequestMapping("/b2i/agent/editSave")
	public String editSaveSecondAgent(HttpServletRequest request,Integer id,String name,String phone,String email,String address){
		CommAgent agent = commAgentService.getById(id);
		agent.setIrName(name);
		agent.setPhone(phone);
		agent.setEmail(email);
		agent.setAddress(address);
		commAgentService.save(agent);
		return "redirect:/b2i/agent/list.html";
	}
	
	@RequestMapping("/b2i/agent/delete")
	public String deleteSecondAgent(HttpServletRequest request,Integer id){
		CommAgent agent = commAgentService.getById(id);
		agent.setIsDelete(1);
		commAgentService.save(agent);
		return "redirect:/b2i/agent/list.html";
	}
	
	@RequestMapping(value="/b2i/project/getProductPrice",method=RequestMethod.POST)
	@ResponseBody
	public String getProductPrice(HttpServletRequest request,String productName,Integer agentId){
		ApiWarp apiWarp = new ApiWarp();
		Product product = productService.findByName(productName);
		if(product==null){
			return apiWarp.addError("未找到匹配型号！name="+productName).toJsonString();
		}
		CommAgent agent = commAgentService.getById(agentId);
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),24);
		calendar.add(Calendar.MONTH, -1);
		Date firstDay = calendar.getTime();
		firstDay = commService.getFisrtDayAfter(firstDay);
		Date currentDate = new Date();
		int sellIn = psiSellInService.sumSellInCurrent(firstDay, currentDate, product, agent);//b2i上个月25号到目前的sellin累计
		ProductPrice productPrice = productPriceService.findByProductAndDateMonth(product, commService.getCurrentMonthFisrtDay());
		List<B2IApplyProduct> applyProducts = b2IApplyProductService.findByProductAndAgent(product, agent);
		List<B2ISponsorApplyProduct> sponsorApplyProducts = b2iApplySponsorProductService.findByProductAndAgent(product, agent);
		for(int i=0; i<applyProducts.size(); i++){
			sellIn -= applyProducts.get(i).getApplyNumber();
		}
		for(int i=0; i<sponsorApplyProducts.size(); i++){
			sellIn -= sponsorApplyProducts.get(i).getApplyNumber();
		}
		apiWarp.putData("sellin", sellIn);
		apiWarp.putData(productPrice);
		return apiWarp.toJsonString();
	}
	
	/**
	 * 保存批复价
	 * @param request
	 * @param replyPrice
	 * @param applyTempType
	 * @param applyProductId
	 * @return
	 */
	@RequestMapping(value="/b2i/project/saveReplyPrice",method=RequestMethod.POST)
	@ResponseBody
	public String saveDescSetting(HttpServletRequest request,BigDecimal replyPrice,Integer applyTempType,Integer applyProductId){
		ApiWarp apiWarp = new ApiWarp();
		if(applyTempType==1){//第一个基础模板
			B2IApplyProduct applyProduct = b2IApplyProductService.findById(applyProductId);
			if(commService.findCurrentUserByRequest(request).getCommRole().getId()==CommRole.HANGYEJINGLI){//全国经理 改为行业经理
				applyProduct.setAreaMangePrice(replyPrice);
			}
			if(commService.findCurrentUserByRequest(request).getCommRole().getId()==CommRole.XIAOSHOUZONGJINGLI){//销售总监  改为销售总经理
				applyProduct.setSellMangePrice(replyPrice);
			}
			b2IApplyProductService.save(applyProduct);
			return apiWarp.putData(applyProduct).toJsonString();
		}else if(applyTempType==2){//第二个暂时模板
			B2ISponsorApplyProduct sponsorApplyProduct = b2iApplySponsorProductService.findById(applyProductId);
			if(commService.findCurrentUserByRequest(request).getCommRole().getId()==CommRole.HANGYEJINGLI){//全国经理改为行业经理
				sponsorApplyProduct.setAreaMangePrice(replyPrice);
			}
			if(commService.findCurrentUserByRequest(request).getCommRole().getId()==CommRole.XIAOSHOUZONGJINGLI){//销售总监改为销售总经理
				sponsorApplyProduct.setSellMangePrice(replyPrice);
			}
			b2iApplySponsorProductService.save(sponsorApplyProduct);
			return apiWarp.putData(sponsorApplyProduct).toJsonString();
		}else{
			return apiWarp.addError("未找到匹配申请模板！type="+applyTempType).toJsonString(); 
		}
	}
}
