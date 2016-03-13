package com.mmd.mssp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
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

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mmd.mssp.comm.ApiWarp;
import com.mmd.mssp.comm.CommonConfig;
import com.mmd.mssp.comm.Constants;
import com.mmd.mssp.domain.ApplyCloseProject;
import com.mmd.mssp.domain.ApproveTemplate;
import com.mmd.mssp.domain.B2BApproveLog;
import com.mmd.mssp.domain.B2BCasualDetail;
import com.mmd.mssp.domain.B2BProject;
import com.mmd.mssp.domain.B2IApplyProduct;
import com.mmd.mssp.domain.B2ISponsorApplyProduct;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommBusiness;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommCustomer;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.SampleInOutLog;
import com.mmd.mssp.domain.SampleInventory;
import com.mmd.mssp.domain.SpecialApplyProduct;
import com.mmd.mssp.domain.SpecialProductUpdateLog;
import com.mmd.mssp.domain.vo.B2BCasualDetailDomain;
import com.mmd.mssp.domain.vo.MailFormat;
import com.mmd.mssp.domain.vo.PDFFormat;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.domain.vo.TitleMapper;
import com.mmd.mssp.repository.ApproveTemplateRepository;
import com.mmd.mssp.service.ApplyCloseProjectService;
import com.mmd.mssp.service.B2BCasualDetailService;
import com.mmd.mssp.service.B2BProjectService;
import com.mmd.mssp.service.CommAgentService;
import com.mmd.mssp.service.CommBusinessService;
import com.mmd.mssp.service.CommCityService;
import com.mmd.mssp.service.CommCustomerService;
import com.mmd.mssp.service.CommService;
import com.mmd.mssp.service.CommUserService;
import com.mmd.mssp.service.ExcelService;
import com.mmd.mssp.service.MailService;
import com.mmd.mssp.service.PDFService;
import com.mmd.mssp.service.ProcessStepService;
import com.mmd.mssp.service.SampleInOutLogService;
import com.mmd.mssp.service.SampleInventoryService;
import com.mmd.mssp.service.SpecialApplyProductService;
import com.mmd.mssp.service.SpecialProductUpdateLogService;
import com.mmd.mssp.util.DateUtils;
import com.mmd.mssp.util.StringUtil;

@Controller
public class B2BProjectController {
	
	private static final int PAGE_SIZE = 50;
	
	@Resource
	ExcelService excelService;

	@Resource
	B2BProjectService b2BProjectService;
	
	@Resource
	CommService commService;
	
	@Resource
	CommAgentService  commAgentService;
	
	@Resource
	SampleInventoryService sampleInventoryService;
	
	@Resource
	SpecialApplyProductService specialApplyProductService;
	
	@Resource
	SampleInOutLogService sampleInOutLogService;
	
	@Resource
	CommBusinessService commBusinessService;
	
	@Resource
	ApplyCloseProjectService applyCloseProjectService;
	
	@Resource
	MailService mailService;
	
	@Resource
	CommCustomerService commCustomerService;
	
	@Resource
	CommUserService commUserService;
	
	@Resource(name = "B2BProjectApplyMailFormat")
	private MailFormat B2BProjectApplyMailFormat;
	
	@Resource(name = "comentNotePDFFormat")
	private PDFFormat comentNotePDFFormat;
	
	@Resource(name = "sampleDealPDFFormat")
	private PDFFormat sampleDealPDFFormat;
	
	@Resource(name = "entrustPicPDFFormat")
	private PDFFormat entrustPicPDFFormat;
	
	@Resource
	B2BCasualDetailService b2BCasualDetailService;
	
	@Resource
	CommCityService cityService;
	
	@Resource
	ProcessStepService processStepService;
	
	@Resource
	CommBusinessService businessService;
	
	@Resource
	ApproveTemplateRepository approveTemplateRepository;

	@Resource
	PDFService pdfService;
	
	@Resource
	SpecialProductUpdateLogService specialProductUpdateLogService;
	
	@RequestMapping("/b2b")
	public String todo(HttpServletRequest request){
//		List<B2BProject> plist = b2BProjectService.findProjectListByRole(commService.findCurrentUserByRequest(request).getCommRole());
		List<CommAgent> commAgents = commService.listCurrentAgent(request);
		List<B2BProject> plist = null;
		if(commAgents.size()>0){
			plist = b2BProjectService.todo(commAgents,commService.findCurrentUserByRequest(request).getCommRole());
			request.setAttribute("plist", plist);
		}
		return "/b2b/todo_list.jsp";
	}
	
	/**
	* @author: sheng.tian
	* @Description: 项目报备
	* @param @param request
	* @param @return   
	* @return String   
	* @throws
	 */
	@RequestMapping("/b2b/project/input.html")
	public String applyProjectInput(HttpServletRequest request,Integer projectId,Integer agentId){
		if(projectId!=null){
			B2BProject b2bProject = b2BProjectService.findB2BProjectById(projectId);
			CommUser agentUser = commUserService.findByCommAgent(b2bProject.getAgent());
			CommUser commSiUser = null;
			if(b2bProject.getCommSi()!=null){
				commSiUser = commUserService.findByCommAgent(b2bProject.getCommSi());
			}
			List<SpecialApplyProduct> saList = specialApplyProductService.findSAPListByProject(b2bProject);
			request.setAttribute("saList", saList);
			request.setAttribute("b2bProject", b2bProject);
			request.setAttribute("agentUser", agentUser);
			request.setAttribute("commSiUser", commSiUser);
		}
		List<CommAgent> commAgents = commService.listCurrentAgent(request);
		CommAgent agent = null;
		String projectCode = "";
		List<CommAgent> siList = null;
		List<CommCustomer> customerList = null;
		if(commAgents.size()>0){
			agent = commAgents.get(0);
			projectCode = commService.getProjectCode(agent,Constants.PROJECT_CODE_TYPE_B2B);//-----------待处理 当前登陆用户要和城市关联起来，因为需要用到城市的CityCode生成项目编号的前两位
			siList = commService.findSiByAgent(agent);//二级代理商
			customerList = commCustomerService.listAllByAgentAndType(agent, Constants.B2B_CUSTOMER);
		}
		if(agentId!=null){
			agent = commAgentService.getById(agentId);
		}
		request.setAttribute("commAgents", commAgents);
		List<Product> productlist = commService.findSampleProductList();
		request.setAttribute("productlist", productlist);
		request.setAttribute("projectCode", projectCode);
//		CommAgent agent = commService.findCurrentAgent(request);//当前总代//-----------------------------------待处理
		request.setAttribute("agent", agent);
		request.setAttribute("siList", siList);
		request.setAttribute("customerList", customerList);
		List<CommBusiness> blist = commBusinessService.findBusinessList();
		request.setAttribute("blist", blist);
		return "/b2b/apply_project.jsp";
	}
	
	@RequestMapping("/b2b/project/apply")
	public String  applyProject(HttpServletRequest request,RedirectAttributes attr,Integer projectId){
		B2BProject bProject = b2BProjectService.findB2BProjectById(projectId);
		if(bProject==null){//不修改
			bProject =getParam(request);
			b2BProjectService.saveProjectAndSpecial(bProject,request.getParameterValues("pronumid"),request.getParameterValues("pronum"),request.getParameterValues("cptf"),request.getParameterValues("cppf"),request.getParameterValues("cpnf"));
		}
		attr.addAttribute("projectId", bProject.getId());
//		attr.addAttribute("type",1);
		return "redirect:/b2b/project/input.html";
		/*		if("1".equals(request.getParameter("subType"))){//保存
			attr.addAttribute("projectId", bProject.getId());
//			attr.addAttribute("type",1);
			return "redirect:/b2b/project/input.html";
		}else if("2".equals(request.getParameter("subType"))){//授权申请
			attr.addAttribute("projectId", bProject.getId());
			return "redirect:/b2b/project/commitmentNote.html";
		}else if("3".equals(request.getParameter("subType"))){//样机申请
			attr.addAttribute("projectId", bProject.getId());
			return "redirect:/b2b/project/sampleDeal.html";
		}else{//特殊支持申请
			attr.addAttribute("projectId", bProject.getId());
			return "redirect:/b2b/project/next.html";
		}*/
	}
	
	@RequestMapping("/b2b/project/list.html")
	public String b2bProjectList(HttpServletRequest request, @RequestParam(required=false) Integer page,Integer type,Integer agentId){
		ApproveTemplate temp= commService.findTmpeType(CommonConfig.getTEMP_B2B_PROJECT());
		List<ProcessStep> processSteps = processStepService.findByTemp(temp);
		
		Integer startStep=0;
		Integer endStep=0;
		boolean isLastStep=false;//在结案查询时需要添加 ProcessStepID为-1（即审核完毕状态）
		if(type==1){//项目申请列表
			startStep=0;
			endStep=5;
		}else if(type==2){
			startStep=5;
			endStep=14;
		}else if(type==3){//返利
			startStep=13;
			endStep=1000;
			isLastStep=true;
		}
//		CommAgent agent = commService.findCurrentAgent(request);
		List<CommAgent> commAgents = commService.listCurrentAgent(request);
		CommAgent agent = null;
		if(commAgents.size()>0){
			agent = commAgents.get(0);
		}
		if(agentId!=null){
			agent = commAgentService.getById(agentId);
		}
		Paging<B2BProject> paging = null;
//		if(agent!=null){
			paging = b2BProjectService.listAll(startStep,endStep, commAgents,isLastStep,page,PAGE_SIZE);
//		}
		request.setAttribute("pagelist", paging);
		request.setAttribute("commAgents", commAgents);
		request.setAttribute("type", type);
		request.setAttribute("processSteps", processSteps);
		return "/b2b/project_list.jsp";
	}
	
	
	
	@RequestMapping("/b2b/project/query")
	public String sellOutUpdateQueryByDate(HttpServletRequest request,Integer agentId) throws ParseException{
		return "/b2b/project_list.jsp";
	}
	
	
	
	
	//特殊支持申请
	@RequestMapping("/b2b/project/next.html")
	public String step3(HttpServletRequest request,Integer projectId){
		B2BProject project = b2BProjectService.findB2BProjectById(projectId);
		List<SpecialApplyProduct> saList = specialApplyProductService.findSAPListByProject(project);
		request.setAttribute("project", project);	
		request.setAttribute("saList", saList);
		List<SampleInventory> list = sampleInventoryService.listAll();
		request.setAttribute("lists", list);
		return "/b2b/next.jsp";
	}
	
	//授权申请
	@RequestMapping("/b2b/project/commitmentNote.html")
	public String step1(HttpServletRequest request,Integer projectId){
		B2BProject project = b2BProjectService.findB2BProjectById(projectId);
		CommUser commUser = commUserService.findByCommAgent(project.getAgent());
		request.setAttribute("project", project);	
		request.setAttribute("commUser", commUser);	
		return "/b2b/commitmentNote.jsp";
	}
	
	//样机申请
	@RequestMapping("/b2b/project/sampleDeal.html")
	public String step2(HttpServletRequest request,Integer projectId){
		B2BProject project = b2BProjectService.findB2BProjectById(projectId);
//		List<SampleInventory>	sList = sampleInventoryService.listAll();
		List<SampleInventory>	sList = new  ArrayList<SampleInventory>();
		List<SampleInventory> list = sampleInventoryService.listAll();
		List<SpecialApplyProduct> sap = specialApplyProductService.findSAPListByProject(project);
		for(SampleInventory s : list){
			for(SpecialApplyProduct sp: sap){
				if((sp.getProductPrice().getProduct().getId()).equals(s.getProduct().getId())){
					sList.add(s);
				}
			}
		}
		request.setAttribute("lists", list);
		request.setAttribute("project", project);	
		request.setAttribute("sList", sList);
		return "/b2b/sampleDeal.jsp";
	}
	
	//一次性发货委托申请
	@RequestMapping("/b2b/project/entrustPic.html")
	public String stepEntrust(HttpServletRequest request, Integer projectId) {
		B2BProject project = b2BProjectService.findB2BProjectById(projectId);
		request.setAttribute("project", project);
		return "/b2b/entrustPic.jsp";
	}
	
	@RequestMapping("/b2b/project/sampleDeal/exportPDF")
	public String sampleDealExportPDF(HttpServletRequest request,HttpServletResponse response,Integer projectId) throws UnsupportedEncodingException{
		B2BProject project = b2BProjectService.findB2BProjectById(projectId);
		List<SampleInventory> list = sampleInventoryService.listAll();
		String number = StringUtil.getHttpQueryParam(request, "number");
		String productId = StringUtil.getHttpQueryParam(request, "productId");
		String receiveName = StringUtil.getHttpQueryParam(request, "receiveName");
		String receiveAddress = StringUtil.getHttpQueryParam(request, "receiveAddress");
		String otherText = StringUtil.getHttpQueryParam(request, "otherText");
		String sendTime = StringUtil.getHttpQueryParam(request, "sendTime");
		String startYear = StringUtil.getHttpQueryParam(request, "startYear");
		String startMon = StringUtil.getHttpQueryParam(request, "startMon");
		String startDay = StringUtil.getHttpQueryParam(request, "startDay");
		String endYear = StringUtil.getHttpQueryParam(request, "endYear");
		String endMon = StringUtil.getHttpQueryParam(request, "endMon");
		String endDay = StringUtil.getHttpQueryParam(request, "endDay");
		String countDays = StringUtil.getHttpQueryParam(request, "countDays");
		String countNum = StringUtil.getHttpQueryParam(request, "countNum");
		String[] productIdArr = productId.split(",");
		String[] numberdArr = number.split(",");
		Map map = new HashMap();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		map.put("agentName", project.getAgent().getIrName());
		map.put("projectCode ", project.getpCode());
		map.put("projectTime", df.format(project.getpBidTime()));
		map.put("receiveName", receiveName);
		map.put("receiveAddress", receiveAddress);
		map.put("otherText", otherText);
		map.put("sendTime", sendTime);
		map.put("startYear", startYear);
		map.put("startMon", startMon);
		map.put("startDay", startDay);
		map.put("endYear", endYear);
		map.put("endMon", endMon);
		map.put("endDay", endDay);
		map.put("countDays", countDays);
		String html = "";
		int flag = 0;
		for(int i=0; i<list.size(); i++){
			for(int j=0; j<numberdArr.length; j++){
				if(list.get(i).getId()==Integer.parseInt(productIdArr[j])){
					//插入一条样机申请记录sample_in_out_log
					SampleInOutLog sampleInOutLog = new SampleInOutLog();
					sampleInOutLog.setB2bProject(project);
					sampleInOutLog.setChangeVolue(Integer.parseInt(numberdArr[j]));
					sampleInOutLog.setSampleInventory(list.get(i));
					sampleInOutLog.setStatus("-1");
					sampleInOutLog.setCreatetime(new Date());
					sampleInOutLogService.save(sampleInOutLog);
					if(flag==0){
						html += "<tr name = 'tr1' >"+
						      		"<td>"+project.getCustomer().getName()+"</td>"+
						      		"<td>"+list.get(i).getProduct().getName()+"</td>"+
						      		"<td>"+list.get(i).getTotal()+"</td>"+
						      		"<td>"+list.get(i).getBalance()+"</td>"+
						      		"<td>"+numberdArr[j]+"</td>"+
//						      		"<td>"+list.get(i).getProduct().getPublicPrice()+"</td>"+
						      		"<td></td>"+
						      		"<td>"+project.getAgent().getIrName()+"</td>"+
						      		"<td rowspan='"+(productIdArr.length+1)+"'>样机申请</td>"+
				      			"</tr>";
					}else{
						html += "<tr name = 'tr1' >"+
					      		"<td>"+project.getCustomer().getName()+"</td>"+
					      		"<td>"+list.get(i).getProduct().getName()+"</td>"+
					      		"<td>"+list.get(i).getTotal()+"</td>"+
					      		"<td>"+list.get(i).getBalance()+"</td>"+
					      		"<td>"+numberdArr[j]+"</td>"+
//					      		"<td>"+list.get(i).getProduct().getPublicPrice()+"</td>"+
					      		"<td></td>"+
					      		"<td>"+project.getAgent().getIrName()+"</td>"+
			      			"</tr>";
					}
					flag++;
				}
			}
			
		}
		map.put("html", html);
		map.put("sumNum",countNum);
		String filePath = pdfService.htmlToPDF(request,sampleDealPDFFormat,map);
		pdfService.downloadPDF(response, filePath);
		return null;
	}
	
	@RequestMapping("/b2b/project/commitmentNote/exportPDF")
	public String commitmentNoteExportPDF(HttpServletRequest request,HttpServletResponse response,Integer projectId,String projectName,String projectCode) throws UnsupportedEncodingException{
		projectName = StringUtil.getHttpQueryParam(request, "projectName");
		projectCode = StringUtil.getHttpQueryParam(request, "projectCode");
		String projectPlace = StringUtil.getHttpQueryParam(request, "projectPlace");
		String chargePerPhone = StringUtil.getHttpQueryParam(request, "chargePerPhone");
		String chargePerson = StringUtil.getHttpQueryParam(request, "chargePerson");
		String proxyName = StringUtil.getHttpQueryParam(request, "proxyName");
		String year = StringUtil.getHttpQueryParam(request, "year");
		String mon = StringUtil.getHttpQueryParam(request, "mon");
		String day = StringUtil.getHttpQueryParam(request, "day");
		B2BProject project = b2BProjectService.findB2BProjectById(projectId);
		CommUser commUser = commUserService.findByCommAgent(project.getAgent());
		Map map = new HashMap();
		map.put("agentName", project.getAgent().getIrName());
		map.put("agentAddress", project.getAgent().getAddress());
		map.put("userPhone", commUser.getPhone());
		map.put("userName", commUser.getRealName());
		map.put("projectName", projectName);
		map.put("projectCode", projectCode);
		map.put("projectPlace", projectPlace);
		map.put("chargePerPhone", chargePerPhone);
		map.put("chargePerson", chargePerson);
		map.put("proxyName", proxyName);
		map.put("year", year);
		map.put("mon", mon);
		map.put("day", day);
		String filePath = pdfService.htmlToPDF(request,comentNotePDFFormat,map);
		pdfService.downloadPDF(response, filePath);
		return null;
	}
	
	@RequestMapping("/b2b/project/entrustPic/exportPDF")
	public String entrustPicExportPDF(HttpServletRequest request,HttpServletResponse response,Integer projectId,String validTime,String receiveCompany,String receiveAddress,String receivePerson,String phone,String idcardNum,String idUrl,String customerSign) throws UnsupportedEncodingException{
		B2BProject project = b2BProjectService.findB2BProjectById(projectId);
		CommUser commUser = commUserService.findByCommAgent(project.getAgent());
		Map map = new HashMap();
		map.put("validTime",validTime);
		map.put("receiveCompany",receiveCompany);
		map.put("receiveAddress",receiveAddress);
		map.put("receivePerson",receivePerson);
		map.put("phone",phone);
		map.put("idcardNum",idcardNum);
		map.put("idUrl",idUrl);
		map.put("customerSign",customerSign);
		String filePath = pdfService.htmlToPDF(request,entrustPicPDFFormat,map);
		pdfService.downloadPDF(response, filePath);
		return null;
	}
	
	
	@RequestMapping("/b2b/project/view-{id}-{type}")
	public String b2bProjectEdit(HttpServletRequest request,@PathVariable Integer id,@PathVariable Integer type){
		B2BProject project = b2BProjectService.findB2BProjectById(id);
		List<SpecialApplyProduct> SAPlist = specialApplyProductService.findSAPListByProject(project);
		List<SampleInOutLog> lists = sampleInOutLogService.findByProjectId(project.getId());
		if(type==3){
			List<SpecialApplyProduct> new_SAPlist = new ArrayList<SpecialApplyProduct>();
			BigDecimal sumRabate = new BigDecimal(0);
			for(int i=0; i<SAPlist.size(); i++){
				SpecialApplyProduct specialApplyProduct = SAPlist.get(i);
				BigDecimal applyNum = new BigDecimal(specialApplyProduct.getNumber());
				BigDecimal rabate = applyNum.multiply(specialApplyProduct.getSellMangePrice().subtract(specialApplyProduct.getProductPrice().getNetPrice()));
				sumRabate = sumRabate.add(rabate);
				specialApplyProduct.setRebate(rabate);
				new_SAPlist.add(specialApplyProduct);
			}
			if(new_SAPlist.size()>0){
				SAPlist = new_SAPlist;
			}
			request.setAttribute("sumRabate", sumRabate);
		}
		request.setAttribute("type", type);
		request.setAttribute("NATIONAL_MANAGER", CommRole.HANGYEJINGLI);
		request.setAttribute("DAQUJINGLI", CommRole.DAQUJINGLI);
		request.setAttribute("SALES_DIRECTOR", CommRole.XIAOSHOUZONGJINGLI);
		request.setAttribute("project", project);
		request.setAttribute("saList", SAPlist);
		request.setAttribute("lists", lists);
		List<B2BApproveLog> logList  = b2BProjectService.findApproveLogList(project);
		request.setAttribute("logList", logList);
		List<B2BCasualDetailDomain> casual = b2BCasualDetailService.getCasualDetails(project);
		request.setAttribute("casual", casual);
		request.setAttribute("enable", false);
		ApplyCloseProject applyCloseProject = applyCloseProjectService.findByb2bProject(project);
		request.setAttribute("applyCloseProject", applyCloseProject);
		return "/b2b/project_view.jsp";
	}
	
	@RequestMapping("/b2b/project/search")
	public String search(HttpServletRequest request, @RequestParam(required=false) Integer page) throws Exception{ 
		ApproveTemplate temp= commService.findTmpeType(CommonConfig.getTEMP_B2B_PROJECT());
		List<ProcessStep> processSteps = processStepService.findByTemp(temp);
		List<CommAgent> commAgents = commService.listCurrentAgent(request);//所有当前省份的代理商
		String ppn = request.getParameter("ppn");
		String startApplyTime = request.getParameter("startApplyTime");
		String endApplyTime = request.getParameter("endApplyTime");
		String status = request.getParameter("processStepId");
		String agentId = request.getParameter("agentId");
		Paging<B2BProject> paging = b2BProjectService.search(ppn,startApplyTime,endApplyTime,status,agentId,commAgents,page,PAGE_SIZE);
//		
		request.setAttribute("pagelist", paging);
		request.setAttribute("startApplyTime", startApplyTime);
		request.setAttribute("endApplyTime", endApplyTime);
		request.setAttribute("ppn", ppn);
		request.setAttribute("processStepId", status);
		request.setAttribute("agentId", agentId);
		request.setAttribute("processSteps", processSteps);
		request.setAttribute("commAgents", commAgents);
		return "/b2b/project_list.jsp";
	}
	
	@RequestMapping("/b2b/project/delete")
	public String delete(Integer id,RedirectAttributes attr){
		B2BProject project = b2BProjectService.findB2BProjectById(id);
		b2BProjectService.delete(project,Boolean.TRUE);
		attr.addAttribute("type", 1);
		return "redirect:/b2b/project/list.html";
	}
	
	@RequestMapping("/b2b/project/editor")
	public String editor(HttpServletRequest request,Integer id){
		B2BProject project = b2BProjectService.findB2BProjectById(id);
		request.setAttribute("project", project);
		
		
		List<CommBusiness> blist = commBusinessService.findBusinessList();
		request.setAttribute("blist", blist);
		
		List<CommAgent> commAgents = commService.listCurrentAgent(request);
		List<CommCustomer> customerList = commCustomerService.listAllByAgentAndType(commAgents.get(0), Constants.B2B_CUSTOMER);
		request.setAttribute("customerList", customerList);
		return "/b2b/edit_project.jsp";
//		return "redirect:/b2b/project/list.html";
	}
	
	@RequestMapping(value ="/b2b/project/next",method=RequestMethod.POST)  
	public String step3(HttpServletRequest request,HttpServletResponse response, MultipartFile files,RedirectAttributes attr,Integer subType){
		B2BProject project = b2BProjectService.findB2BProjectById(Integer.parseInt(request.getParameter("projectId")));
		List<SpecialApplyProduct> sapList = new ArrayList<SpecialApplyProduct>();
		if(subType==2){//授权申请
			project.setCommitmentNote(request.getParameter("samleContent"));
//			fileStore(request, files,project,1);
		}else if(subType==3){//样机申请
//			fileStore(request, files,project,2);
			project.setSampleDeal(request.getParameter("samleContent"));
			sampleDeal(request,project);
		}else if(subType==5){//一次性发货委托
//			String site = fileStore(request, files,project,5);
//			project.setEntrustPic(site);
			project.setEntrustPic(request.getParameter("samleContent"));
		}else if(subType==4){//特殊支持申请 
			project=getB2BProjectPrarm(project,request);
			project.setUploadApplyData(fileStore(request,files,project,3));
			sapList = specialApplyProductService.findSAPListByProject(project);
			sapList  = getSAPParam(request,sapList);
			//申请完成进行下一步
			//B2BProject project = b2BProjectService.findB2BProjectById(projectId);
			changeProjectStep(request, project, 0);
		}
		b2BProjectService.saveSAPListAndProject(project,sapList);
		casualDetail(request,project,sapList);
		attr.addAttribute("type",1);
		
		return "redirect:/b2b/project/list.html";
	}
	
	@RequestMapping("/b2b/project/authorization.html")
	public String applySample(HttpServletRequest request){
		return "/b2b/apply_authorization.jsp";
	}
	
	@RequestMapping("/b2b/project/sample.html")
	public String applySamples(HttpServletRequest request){
		B2BProject project = b2BProjectService.findB2BProjectById(39);
		List<SpecialApplyProduct> SAPlist = specialApplyProductService.findSAPListByProject(project);
		request.setAttribute("project", project);
		request.setAttribute("saList", SAPlist);
		return "/b2b/finish_apply.jsp";
	}
	
	
/*	@RequestMapping("/b2b/project/approve.html")
	public String approveData(HttpServletRequest request){
		B2BProject project = b2BProjectService.findB2BProjectById(Integer.parseInt(request.getParameter("projectId")));
		List<SpecialApplyProduct> SAPlist = specialApplyProductService.findSAPListByProject(project);
		List<SampleInOutLog> lists = sampleInOutLogService.findByProjectId(project.getId());
		request.setAttribute("project", project);
		request.setAttribute("SAPlist", SAPlist);
		request.setAttribute("lists", lists);
		request.setAttribute(Constants.IS_SHOW_APPROVE, true);
		List<B2BApproveLog> logList  = b2BProjectService.findApproveLogList(project);
		request.setAttribute("logList", logList);
		return "/b2b/project_view.jsp";
	}*/
	
/*	@RequestMapping("/b2b/project/approve")
	public String approve(HttpServletRequest request,Integer projectId,Integer isPass,@RequestParam("files") MultipartFile[] files){
		B2BProject project = b2BProjectService.findB2BProjectById(projectId);
		String path = "";
		for(int i = 0;i<files.length;i++){
			if(i == 0){
				path = fileStore(request, files[i],project,3);
			}else{
				path =path+";"+ fileStore(request, files[i],project,3);
			}
		}
		project.setUploadImgs(path);
		b2BProjectService.save(project);
		return null;
		
		
		
//		//如果当前用户的角色不等于当前b2b项目中的下一个审核步骤的角色，则不能审核
//		List<B2BApproveLog> logList =null;
//		if(!commService.findCurrentUserByRequest(request).getCommRole().getId().equals(project.getStep().getPnextId().getCommRole().getId())){
//			logList=b2BProjectService.findApproveLogList(project);
//			request.setAttribute("logList", logList);
//			return "/b2b/project_view.jsp";
//		}
//		B2BApproveLog log = new B2BApproveLog();
//		log.setIsPass(isPass);
//		log.setStep(project.getStep().getPnextId());
//		log.setProject(project);
//		log.setUser(commService.findCurrentUserByRequest(request));
//		log.setApproveTime(new Date());
//		log.setApproveMsg(request.getParameter("approveMsg"));
//		if(isPass==0){
//			project.setStep(project.getStep().getPnextId());
//		}else{
//			project.setStep(project.getStep().getRnextId());	
//		}
//		b2BProjectService.saveProjectAndApproveLog(project,log);
//		logList  = b2BProjectService.findApproveLogList(project);
//		request.setAttribute("logList", logList);
//		return "/b2b/project_view.jsp";
	}*/
	
/*	@RequestMapping("b2b/project/upload.html")
	public String upload(HttpServletRequest request){
		B2BProject project = b2BProjectService.findB2BProjectById(Integer.parseInt(request.getParameter("projectId")));
		List<SpecialApplyProduct> SAPlist = specialApplyProductService.findSAPListByProject(project);
		List<SampleInOutLog> lists = sampleInOutLogService.findByProjectId(project.getId());
		request.setAttribute("project", project);
		request.setAttribute("SAPlist", SAPlist);
		request.setAttribute("lists", lists);
		List<B2BApproveLog> logList  = b2BProjectService.findApproveLogList(project);
		request.setAttribute("logList", logList);
		request.setAttribute(Constants.IS_UPLOAD_IMGS, true);
		return "/b2b/project_view.jsp";
	}*/
	
	@RequestMapping(value="/a/input",method=RequestMethod.POST)
	public String SellInInput(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "file", required = false) MultipartFile file){
		String fileName = "";
		try {
			fileName = file.getOriginalFilename();
			InputStream inputStream = file.getInputStream();
			String path = request.getSession().getServletContext().getRealPath("/")+"WEB-INF\\upload\\"+fileName;
			File files = new File(path);
			FileOutputStream fos = new FileOutputStream(files);
			byte[] b = new byte[1024];
			//将文件流信息读取文件缓存区，如果读取结果不为-1就代表文件没有读取完毕，反之已经读取完毕
			while(inputStream.read(b)!=-1){
			//将缓存区中的内容写到afterfile文件中
				fos.write(b);
				fos.flush();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<SampleInventory> list = sampleInventoryService.listAll();
		
		
		String site = "http://localhost:7070/mssp/upload/" + fileName;
		request.setAttribute("img", site);
		request.setAttribute("list", list);
		return "/b2b/apply_sample.jsp";
	}

	
	
	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param request
	* @param @return   
	* @return B2BProject   
	* @throws
	*/
	private B2BProject getParam(HttpServletRequest request) {
		B2BProject bean = new B2BProject();
		bean.setpName(request.getParameter("pName"));
		bean.setpCode(request.getParameter("pCode"));
		bean.setpIsBid(Integer.parseInt(request.getParameter("pIsBid")));
		bean.setpBidTime(DateUtils.getDate(request.getParameter("pBidTime")));
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		bean.setCustomer(commCustomerService.getCustomerById(customerId));
		/*bean.setCustomerName(request.getParameter("customerName"));
		bean.setCustomerLinkman(request.getParameter("customerLinkman"));
		bean.setCustomerPhone(request.getParameter("customerPhone"));*/
		bean.setBusiness(commBusinessService.findById(Integer.parseInt(request.getParameter("business"))));
//		bean.setCustomerAddress(request.getParameter("customerAddress"));
		bean.setProto(Integer.parseInt(request.getParameter("proto")));
		bean.setEntrust(Integer.parseInt(request.getParameter("entrust")));
		bean.setAgent( commAgentService.getById(Integer.parseInt(request.getParameter("agent"))));
		if(!(request.getParameter("commSi").equals("-1"))){
			bean.setCommSi(commAgentService.getById(Integer.parseInt(request.getParameter("commSi"))));
		}
		bean.setSendCompany(request.getParameter("sendCompany"));
		bean.setSendName(request.getParameter("sendName"));
		bean.setSendAddress(request.getParameter("sendAddress"));
		bean.setSendAddress1(request.getParameter("sendAddress1"));
		bean.setSendAddress2(request.getParameter("sendAddress2"));
		bean.setSendZipcode(request.getParameter("sendZipcode"));
		bean.setSendCity(request.getParameter("sendCity"));
		bean.setSendProvince(request.getParameter("sendProvince"));
		bean.setSendCountry(request.getParameter("sendCountry"));
		bean.setSendPhone(request.getParameter("sendPhone"));
		bean.setSendFax(request.getParameter("sendFax"));
		bean.setSendEmail(request.getParameter("sendEmail"));
		bean.setReceiveCompany(request.getParameter("receiveCompany"));
		bean.setReceiveName(request.getParameter("receiveName"));
		bean.setReceiveAddress(request.getParameter("receiveAddress"));
		bean.setReceiveAddress1(request.getParameter("receiveAddress1"));
		bean.setReceiveAddress2(request.getParameter("receiveAddress2"));
		bean.setReceiveZipcode(request.getParameter("receiveZipcode"));
		bean.setReceiveCity(request.getParameter("receiveCity"));
		bean.setReceiveProvince(request.getParameter("receiveProvince"));
		bean.setReceiveCountry(request.getParameter("receiveCountry"));
		bean.setReceivePhone(request.getParameter("receivePhone"));
		bean.setReceiveFax(request.getParameter("receiveFax"));
		bean.setReceiveEmail(request.getParameter("receiveEmail"));
		bean.setAgentSendTime(DateUtils.getDate(request.getParameter("agentSendTime")));
		bean.setCustomerReceiveTime(DateUtils.getDate(request.getParameter("customerReceiveTime")));
		bean.setProductUseInfo(request.getParameter("productUseInfo"));
		bean.setAfterSalesService(request.getParameter("afterSalesService"));
		bean.setRemark(request.getParameter("remark"));
		ApproveTemplate temp= commService.findTmpeType(CommonConfig.getTEMP_B2B_PROJECT());
		bean.setTemp(temp);
		bean.setStep(commService.findFisrtStep(temp));
		bean.setApplyTime(new Date());
		bean.setIsDelete(0);
		return bean;
	}
	

	private List<SpecialApplyProduct> getSAPParam(HttpServletRequest request,
			List<SpecialApplyProduct> sapList) {
		for (int i = 0; i < sapList.size(); i++) {
			sapList.get(i).setApplyPrice(BigDecimal.valueOf(Double.parseDouble(request.getParameterValues("applyPrice")[i])));
			String discountRateStr = request.getParameterValues("discountRate")[i];
			String discountRate = discountRateStr.substring(0, discountRateStr.indexOf("%"));
			sapList.get(i).setDiscountRate(BigDecimal.valueOf(Double.parseDouble(discountRate)));
			sapList.get(i).setWinRate(Integer.parseInt(request.getParameterValues("winRate")[i]));
			sapList.get(i).setRemark(request.getParameterValues("remark")[i]);
		}
		return sapList;
	}
	
	
	private B2BProject getB2BProjectPrarm(B2BProject project,
			HttpServletRequest request) {
		project.setpType(Integer.parseInt(request.getParameter("pType")));
		if(Integer.parseInt(request.getParameter("pType")) == 1){
			project.setPriceValidBeginTime(DateUtils.getDate(request.getParameter("priceValidBeginTime")));
			project.setPriceValidEndTime(DateUtils.getDate(request.getParameter("priceValidEndTime")));
		}else{
			project.setPriceValidBeginTime(DateUtils.getYearMonth(request.getParameter("priceValidBeginTime")));
			project.setPriceValidEndTime(DateUtils.getYearMonth(request.getParameter("priceValidEndTime")));
		}
//		project.setCustomer(customerService.getCustomerById(Integer.parseInt(request.getParameter("customerId"))));
		/*project.setCustomerName(request.getParameter("customerName"));
		project.setCustomerLinkman(request.getParameter("customerLinkman"));
		project.setCustomerPhone(request.getParameter("customerPhone"));*/
		project.setDecisionInfo(request.getParameter("decisionInfo"));
		project.setApplyReason(request.getParameter("applyReason"));
		return project;
	}
	
	private String fileStore(HttpServletRequest request,MultipartFile file,B2BProject project,int type){
		String fileName = "";
		try {
			fileName = file.getOriginalFilename();
			fileName = UUID.randomUUID() + "_" + project.getpCode() + "_" + fileName.substring(fileName.lastIndexOf("."));
			InputStream inputStream = file.getInputStream();
//			String path = request.getSession().getServletContext().getRealPath("/")+"WEB-INF\\upload\\"+fileName;
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
//			String site = SysConfig.getConfig("imgUploadSite", null) + fileName;
			
			String site ="http://" + request.getHeader("Host") + request.getSession().getServletContext().getContextPath() +"/upload/"+ fileName;
			if(type == 1){
				project.setCommitmentNote(site);
			}else if(type == 2){
				project.setSampleDeal(site);
			}else{
				return site;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void sampleDeal(HttpServletRequest request,B2BProject project){
		List<SampleInventory> list = sampleInventoryService.listAll();
		List<SampleInOutLog> lists = new ArrayList<SampleInOutLog>();
		String[] datas = request.getParameterValues("number");
		for(int i=0;i<datas.length;i++){
			int a = Integer.parseInt(datas[i]);
			if(a!=0){
				list.get(i).setBalance(list.get(i).getBalance()-a);
				sampleInventoryService.save(list.get(i));
				SampleInOutLog sampleInOutLog = new SampleInOutLog();
				sampleInOutLog.setSampleInventory(list.get(i));
				sampleInOutLog.setStatus("-1");
				sampleInOutLog.setCreatetime(new Date());
				sampleInOutLog.setChangeVolue(a);
				sampleInOutLog.setB2bProject(project);
				lists.add(sampleInOutLog);
			}
		}
		sampleInOutLogService.save(lists);
	}
	
	
	@RequestMapping("b2b/project/flow-{id}/{flowType}")
	public String flow(HttpServletRequest request,@PathVariable Integer id,@PathVariable Integer flowType){
		B2BProject project = b2BProjectService.findB2BProjectById(id);
		List<SpecialApplyProduct> SAPlist = specialApplyProductService.findSAPListByProject(project);
		List<SampleInOutLog> siolist = sampleInOutLogService.findByProjectId(project.getId());
		List<B2BApproveLog> logList  = b2BProjectService.findApproveLogList(project);
		List<SampleInventory> silist = sampleInventoryService.listAll();
		ApplyCloseProject applyCloseProject = applyCloseProjectService.findByb2bProject(project);
		request.setAttribute("NATIONAL_MANAGER", CommRole.HANGYEJINGLI);
		request.setAttribute("DAQUJINGLI", CommRole.DAQUJINGLI);
		request.setAttribute("SALES_DIRECTOR", CommRole.XIAOSHOUZONGJINGLI);
		request.setAttribute("applyCloseProject", applyCloseProject);
		request.setAttribute("project", project);
		request.setAttribute("saList", SAPlist);
		request.setAttribute("siolist", siolist);
		request.setAttribute("flowType", flowType);
		List<B2BCasualDetailDomain> casual = b2BCasualDetailService.getCasualDetails(project);
		request.setAttribute("casual", casual);
		request.setAttribute("enable", false);
		if(flowType==1){//审批
			request.setAttribute("logList", logList);
			//只有当前用户登录的角色等于该项目审核步骤中下一步的操作角色 时  页面上才出现审核的按钮
			if(commService.findCurrentUserByRequest(request).getCommRole().getId().equals(project.getStep().getPnextId().getCommRole().getId())){
				request.setAttribute(Constants.IS_SHOW_APPROVE, Boolean.TRUE);
			}
			request.setAttribute(Constants.IS_UPLOAD_IMGS, Boolean.FALSE);
			request.setAttribute("enable", true);
			return "/b2b/project_view.jsp";
		}else if(flowType==2){//填写申请
			request.setAttribute("lists", silist);
			return "/b2b/next.jsp";
		}else if(flowType==3){//上传资料
			request.setAttribute("logList", logList);
			request.setAttribute(Constants.IS_UPLOAD_IMGS, Boolean.TRUE);
			return "/b2b/project_view.jsp";
		}else if(flowType==4){//申请结案
			request.setAttribute("logList", logList);
			return "/b2b/project_close.jsp";
		}else if(flowType==5){//申请返利
			request.setAttribute("logList", logList);
			request.setAttribute(Constants.IS_SHOW_APPROVE, Boolean.TRUE);
			request.setAttribute(Constants.IS_UPLOAD_IMGS, Boolean.FALSE);
			return "/b2b/project_view.jsp";
		}else if(flowType==55){//行业经理上传授权书
			request.setAttribute("giveRight", Boolean.TRUE);
			request.setAttribute("logList", logList);
			return "/b2b/project_view.jsp";
		}else if(flowType == 66){//申请返利
			List<SpecialApplyProduct> new_SAPlist = new ArrayList<SpecialApplyProduct>();
			BigDecimal sumRabate = new BigDecimal(0);
			for(int i=0; i<SAPlist.size(); i++){
				SpecialApplyProduct specialApplyProduct = SAPlist.get(i);
				BigDecimal applyNum = new BigDecimal(specialApplyProduct.getNumber());
				BigDecimal rabate = applyNum.multiply(specialApplyProduct.getSellMangePrice().subtract(specialApplyProduct.getProductPrice().getNetPrice()));
				sumRabate = sumRabate.add(rabate);
				specialApplyProduct.setRebate(rabate);
				new_SAPlist.add(specialApplyProduct);
			}
			if(new_SAPlist.size()>0){
				SAPlist = new_SAPlist;
			}
			request.setAttribute("saList", SAPlist);
			request.setAttribute("sumRabate", sumRabate);
			request.setAttribute("rebate", Boolean.TRUE);
			request.setAttribute("logList", logList);
			return "/b2b/project_view.jsp";
		}else{
			request.setAttribute(Constants.IS_UPLOAD_IMGS, Boolean.FALSE);
			request.setAttribute("logList", logList);
			return "/b2b/project_view.jsp";
		}
	}
	
	@RequestMapping(value="b2b/project/flow",method=RequestMethod.POST)
	public String flowStep(HttpServletRequest request,@RequestParam(value="filesImg",required=false) MultipartFile[] filesImg){
		Integer projectId = Integer.parseInt(request.getParameter("projectId"));
		Integer flowType = Integer.parseInt(request.getParameter("flowType"));
		Integer isPass = Integer.parseInt(request.getParameter("isPass"));
		B2BProject project = b2BProjectService.findB2BProjectById(projectId);
		List<SpecialApplyProduct> SAPlist = specialApplyProductService.findSAPListByProject(project);
		List<SampleInOutLog> siolist = sampleInOutLogService.findByProjectId(project.getId());
		if(filesImg!=null){
			String path = fileStore(request, filesImg[0],project,3);
			project.setGranImg(path);
		}

		request.setAttribute("NATIONAL_MANAGER", CommRole.HANGYEJINGLI);
		request.setAttribute("DAQUJINGLI", CommRole.DAQUJINGLI);
		request.setAttribute("SALES_DIRECTOR", CommRole.XIAOSHOUZONGJINGLI);
		request.setAttribute("project", project);
		request.setAttribute("saList", SAPlist);
		request.setAttribute("lists", siolist);
		request.setAttribute("flowType", flowType);
		changeProjectStep(request,project,isPass);
		List<B2BApproveLog> logList  = b2BProjectService.findApproveLogList(project);
		request.setAttribute("logList", logList);
		if(flowType==1){//审批
			return "/b2b/project_view.jsp";
		}else if(flowType==2){//填写申请
			return "/b2b/next.jsp";
		}else if(flowType==3){//上传资料
			return "/b2b/project_view.jsp";
		}else if(flowType==4){//申请结案
			return "/b2b/project_close.jsp";
		}else if(flowType==5){//申请返利
			return "/b2b/project_view.jsp";
		}else{
			return "/b2b/project_view.jsp";
		}
	}
	private  void changeProjectStep(HttpServletRequest request,B2BProject project,Integer isPass){
		//如果当前用户的角色不等于当前b2b项目中的下一个审核步骤的角色，则不能审核
		B2BApproveLog log = new B2BApproveLog();
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
		
		//邮件通知
		String emailUserIds = project.getStep().getEmailUserIds();
		if(!StringUtils.isBlank(emailUserIds)){
			String[] userIds = emailUserIds.split(",");
			for(int i=0; i<userIds.length; i++){
				if(!StringUtils.isBlank(userIds[i])){
					CommUser commUser = commUserService.getCommUserById(Integer.parseInt(userIds[i]));
					if(StringUtil.isEmail(commUser.getEmail())){
						Map map = new HashMap();
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
						String[] arr = df.format(new Date()).split("\\|");
						map.put("date", arr[0]);
						map.put("time", arr[1]);
						map.put("userName", commUser.getRealName());
						map.put("agentName", project.getAgent().getIrName());
						map.put("projectId", Integer.toString(project.getId()));
						map.put("flowType", "1");
						mailService.sendHTMLMail(B2BProjectApplyMailFormat, commUser.getEmail(),null, map);
					}
				}
			}
		}
		b2BProjectService.saveProjectAndApproveLog(project,log);
		//改变一次状态，修改一次待办事宜总量
		List<CommAgent> commAgents = commService.listCurrentAgent(request);
		request.getSession().setAttribute(Constants.B2B_TO_DO_NUM, commService.countb2bToDo(commAgents,commService.findCurrentUserByRequest(request).getCommRole()));
	}
	
	@RequestMapping("/b2b/project/upload")
	public String upload(HttpServletRequest request,Integer projectId,@RequestParam("files") MultipartFile[] files,RedirectAttributes attr){
		B2BProject project = b2BProjectService.findB2BProjectById(projectId);
		String path = "";
		for(int i = 0;i<files.length;i++){
			if(i == 0){
				path = fileStore(request, files[i],project,3);
			}else{
				path =path+";"+ fileStore(request, files[i],project,3);
			}
		}
		project.setUploadImgs(path);
		b2BProjectService.save(project);
		changeProjectStep(request, project, 0);
		attr.addAttribute("type",2);
		return "redirect:/b2b/project/list.html";
	}
	
	@RequestMapping("/b2b/project/uploadGrantImg")
	public String uploadGrantImg(HttpServletRequest request,Integer projectId,@RequestParam("file") MultipartFile file,RedirectAttributes attr){
		B2BProject project = b2BProjectService.findB2BProjectById(projectId);
		project.setGranImg(fileStore(request, file,project,3));
		b2BProjectService.save(project);
		changeProjectStep(request, project, 0);
		attr.addAttribute("type",1);
		return "redirect:/b2b/project/list.html";
	}
	
	@RequestMapping("/b2b/project/rebate")
	public String rebate(HttpServletRequest request,Integer projectId,RedirectAttributes attr){
		B2BProject bean = b2BProjectService.findB2BProjectById(projectId);
		bean.setSendCompany(request.getParameter("sendCompany"));
		bean.setSendName(request.getParameter("sendName"));
		bean.setSendAddress(request.getParameter("sendAddress"));
		bean.setSendAddress1(request.getParameter("sendAddress1"));
		bean.setSendAddress2(request.getParameter("sendAddress2"));
		bean.setSendZipcode(request.getParameter("sendZipcode"));
		bean.setSendCity(request.getParameter("sendCity"));
		bean.setSendProvince(request.getParameter("sendProvince"));
		bean.setSendCountry(request.getParameter("sendCountry"));
		bean.setSendPhone(request.getParameter("sendPhone"));
		bean.setSendFax(request.getParameter("sendFax"));
		bean.setSendEmail(request.getParameter("sendEmail"));
		bean.setReceiveCompany(request.getParameter("receiveCompany"));
		bean.setReceiveName(request.getParameter("receiveName"));
		bean.setReceiveAddress(request.getParameter("receiveAddress"));
		bean.setReceiveAddress1(request.getParameter("receiveAddress1"));
		bean.setReceiveAddress2(request.getParameter("receiveAddress2"));
		bean.setReceiveZipcode(request.getParameter("receiveZipcode"));
		bean.setReceiveCity(request.getParameter("receiveCity"));
		bean.setReceiveProvince(request.getParameter("receiveProvince"));
		bean.setReceiveCountry(request.getParameter("receiveCountry"));
		bean.setReceivePhone(request.getParameter("receivePhone"));
		bean.setReceiveFax(request.getParameter("receiveFax"));
		bean.setReceiveEmail(request.getParameter("receiveEmail"));
		bean.setAgentSendTime(DateUtils.getDate(request.getParameter("agentSendTime")));
		bean.setCustomerReceiveTime(DateUtils.getDate(request.getParameter("customerReceiveTime")));
		bean.setProductUseInfo(request.getParameter("productUseInfo"));
		bean.setAfterSalesService(request.getParameter("afterSalesService"));
		changeProjectStep(request, bean, 0);
		attr.addAttribute("type",3);
		return "redirect:/b2b/project/list.html";
	}
	
	@RequestMapping("/b2b/project/applyClose")
	public String approve(HttpServletRequest request,Integer projectId,RedirectAttributes attr){
		B2BProject project = b2BProjectService.findB2BProjectById(projectId);
		b2BProjectService.saveColseProject(project,request.getParameter("projectInfo"));
		changeProjectStep(request, project, 0);
		attr.addAttribute("type",4);
		return "redirect:/b2b/project/list.html";
	}
	@RequestMapping("/b2b/project/close")
	public String ProjectClose(HttpServletRequest request,Integer projectId){
		B2BProject project = b2BProjectService.findB2BProjectById(projectId);
		String info = request.getParameter("textarea");
		ApplyCloseProject s = new ApplyCloseProject();
		s.setProInfo(info);
		s.setB2bProject(project);
		applyCloseProjectService.save(s);
		
		return null;
	}
	
	public void casualDetail(HttpServletRequest request,B2BProject project,List<SpecialApplyProduct> sapList ){
		List<B2BCasualDetail> list = new ArrayList<B2BCasualDetail>();
		for(int j=0; j<sapList.size(); j++){
			SpecialApplyProduct  specialApplyProduct = sapList.get(j);
			for(int i=1;i<13;i++){
				B2BCasualDetail b2BCasualDetail = new B2BCasualDetail();
				b2BCasualDetail.setProject(project);
				b2BCasualDetail.setProduct(specialApplyProduct.getProductPrice().getProduct());
				b2BCasualDetail.setMonth(i);
				String name = specialApplyProduct.getProductPrice().getProduct().getName() + Integer.toString(i);
				String nu = request.getParameter(name);
				int num;
				if(nu.equals("")){
					num = 0;
				}else{
					num= Integer.parseInt(request.getParameter(name));
				}
				b2BCasualDetail.setVolumn(num);
				list.add(b2BCasualDetail);
			}
		}
		b2BCasualDetailService.save(list);
	}
	
	@RequestMapping("/b2b/project/report.html")
	public String b2bProjectReport(HttpServletRequest request, @RequestParam(required=false) Integer page,String applyTimeStart,String applyTimeEnd,
					String approveTimeStart,String approveTimeEnd,Integer[] processStepId,Integer[] cityId,Integer[] businessId,Integer[] pTypeId,String pCode,String pName) throws Exception {
		CommAgent agent = commService.findCurrentAgent(request);
		ApproveTemplate temp = approveTemplateRepository.findTempByType(CommonConfig.getTEMP_B2B_PROJECT());
		
		//List<CommCity> cityList = cityService.listAllCity(false);
		List<CommCity> cityList = commService.listCurrentCity(request);
		List<CommBusiness> businessList = businessService.findBusinessList();
		List<ProcessStep> processStepList = processStepService.findByTemp(temp);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date ayDate1;
		Date ayDate2;
		Date aeDate1;
		Date aeDate2;
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),0,0,0);
		if(applyTimeStart!=null){
			ayDate1 = sdf.parse(applyTimeStart);
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
		
		Paging<B2BProject> paging = b2BProjectService.listAllBySearch(agent, ayDate1, ayDate2, aeDate1, aeDate2, processStepId, cityId, businessId, pCode, pName, pTypeId, page, PAGE_SIZE);
		
		request.setAttribute("applyTimeStart", ayDate1);
		request.setAttribute("applyTimeEnd", ayDate2);
		request.setAttribute("approveTimeStart", aeDate1);
		request.setAttribute("approveTimeEnd", aeDate2);
		request.setAttribute("cityList", cityList);
		request.setAttribute("businessList", businessList);
		request.setAttribute("processStepList", processStepList);
		request.setAttribute("cityId", cityId);
		request.setAttribute("businessId", businessId);
		request.setAttribute("processStepId", processStepId);
		request.setAttribute("pTypeId", pTypeId);
		request.setAttribute("pCode", pCode);
		request.setAttribute("pName", pName);
		
		request.setAttribute("pagelist", paging);
		return "/b2b/report.jsp";
	}
	
	@RequestMapping(value="/b2b/report/excel")
	public String export(HttpServletRequest request,HttpServletResponse response,String applyTimeStart,String applyTimeEnd,String approveTimeStart,String approveTimeEnd,
			Integer[] processStepId,Integer[] cityId,Integer[] businessId,Integer[] pTypeId,String pCode,String pName) throws Exception{
		CommAgent agent = commService.findCurrentAgent(request);
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
		
		List<SpecialApplyProduct> applyProductList = specialApplyProductService.listToExport(agent, ayDate1, ayDate2, aeDate1, aeDate2, processStepId, cityId, businessId, pCode, pName, pTypeId);

		List<Map> mapList = specialApplyProductService.getMapBySearch(applyProductList);
		
		response.setContentType("application/vnd.ms-excel;charset=GBK");  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setHeader("Content-Disposition", "attachment;filename=test.csv");  
        response.setCharacterEncoding("GBK");
        OutputStreamWriter osw = null;
        try {
        	osw = new OutputStreamWriter(response.getOutputStream(), "GBK");  
        	osw.write(this.excelService.convert2Cvs(TitleMapper.b2bReportMapper, mapList));
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
	
	@RequestMapping(value="/b2b/applyProduct/modify",method=RequestMethod.POST)
	@ResponseBody
	public String modify(HttpServletRequest request,Integer id,BigDecimal discountRate,Integer discountRate0,
			BigDecimal applyPrice,BigDecimal applyPrice0,Integer number,Integer number0){
		ApiWarp apiWarp = new ApiWarp();
		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		SpecialApplyProduct specialApplyProduct= specialApplyProductService.findSAPListById(id);
		specialApplyProduct.setApplyPrice(applyPrice);
		specialApplyProduct.setNumber(number);
		specialApplyProduct.setDiscountRate(discountRate);
		specialApplyProductService.save(specialApplyProduct);
		SpecialProductUpdateLog log = new SpecialProductUpdateLog();
		log.setUser(user);
		log.setSpecialApplyProduct(specialApplyProduct);
		log.setOldNumber(number0);
		log.setTargetNumber(number);
		log.setOldPrice(applyPrice0);
		log.setTargetPrice(applyPrice);
		specialProductUpdateLogService.save(log);
		return apiWarp.toJsonString();
	}
	
	@RequestMapping(value="/b2b/project/saveReplyPrice",method=RequestMethod.POST)
	@ResponseBody
	public String saveDescSetting(HttpServletRequest request,BigDecimal replyPrice,Integer applyProductId){
		ApiWarp apiWarp = new ApiWarp();
		SpecialApplyProduct specialApplyProduct= specialApplyProductService.findSAPListById(applyProductId);
		if(specialApplyProduct==null){
			apiWarp.addError("为找到匹配数据id="+applyProductId).toJsonString();
		}
		if(commService.findCurrentUserByRequest(request).getCommRole().getId()==CommRole.DAQUJINGLI){//大区经理
			specialApplyProduct.setAreaMangePrice(replyPrice);
		}
		if(commService.findCurrentUserByRequest(request).getCommRole().getId()==CommRole.HANGYEJINGLI){//行业经理
			specialApplyProduct.setIndustryMangePrice(replyPrice);
		}
		if(commService.findCurrentUserByRequest(request).getCommRole().getId()==CommRole.XIAOSHOUZONGJINGLI){//销售总监   -- 销售总监改为实际为销售总经理
			specialApplyProduct.setSellMangePrice(replyPrice);
		}
		specialApplyProductService.save(specialApplyProduct);
		return apiWarp.putData(specialApplyProduct).toJsonString();
	}
}





