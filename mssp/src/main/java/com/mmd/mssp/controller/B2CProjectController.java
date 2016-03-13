package com.mmd.mssp.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.pdf.BaseFont;
import com.mmd.mssp.comm.ApiWarp;
import com.mmd.mssp.comm.CommonConfig;
import com.mmd.mssp.comm.Constants;
import com.mmd.mssp.domain.ApproveTemplate;
import com.mmd.mssp.domain.B2CApplyOther;
import com.mmd.mssp.domain.B2CApplyOtherReplyLog;
import com.mmd.mssp.domain.B2CApproveLog;
import com.mmd.mssp.domain.B2CCondition;
import com.mmd.mssp.domain.B2CProject;
import com.mmd.mssp.domain.B2CSpecialApplyProduct;
import com.mmd.mssp.domain.B2CSpecialApplyProductReplyLog;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.ProductPrice;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.domain.vo.TitleMapper;
import com.mmd.mssp.repository.B2CApplyOtherReplyLogRepository;
import com.mmd.mssp.repository.B2CApproveLogRepository;
import com.mmd.mssp.repository.B2CSpecialApplyProductReplyLogRepository;
import com.mmd.mssp.service.B2CApplyOtherService;
import com.mmd.mssp.service.B2CApprovalLogService;
import com.mmd.mssp.service.B2CConditionService;
import com.mmd.mssp.service.B2CProjectService;
import com.mmd.mssp.service.B2CSpecialApplyProductService;
import com.mmd.mssp.service.B2CSpecialProductUpdateLogService;
import com.mmd.mssp.service.CommAgentService;
import com.mmd.mssp.service.CommRoleService;
import com.mmd.mssp.service.CommService;
import com.mmd.mssp.service.ExcelService;
import com.mmd.mssp.service.PDFService;
import com.mmd.mssp.service.ProcessStepService;
import com.mmd.mssp.service.ProductPriceService;
import com.mmd.mssp.service.ProductService;
import com.mmd.mssp.util.DateUtils;

@Controller
public class B2CProjectController {

	private static final int PAGE_SIZE = 50;
	
	private static final int NOT_AGREE = 0;
	
	@Resource
	ExcelService excelService;
	@Resource
	CommService commService;
	@Resource
	B2CProjectService b2CProjectService;
	@Resource
	ProductService productService;
	@Resource
	B2CSpecialApplyProductService applyProductService;
	@Resource
	B2CApproveLogRepository b2CApproveLogRepository;
	@Resource
	B2CSpecialProductUpdateLogService b2CSpecialProductUpdateLogService;
	@Resource 
	ProcessStepService processStepService;
	@Resource
	CommAgentService agentService;
	@Resource
	B2CConditionService conditionService;
	@Resource
	ProductPriceService productPriceService;
	@Resource
	B2CApplyOtherService applyOtherService;
	@Resource
	B2CSpecialApplyProductReplyLogRepository applyProductReplyLogRepository;
	@Resource
	B2CApplyOtherReplyLogRepository applyOtherReplyLogRepository;
	@Resource
	B2CApprovalLogService b2cApprovalLogService;
	
	@Resource
	B2CSpecialApplyProductService b2cSpecialApplyProductService;
	
	@Resource
	B2CApplyOtherService b2cApplyOtherService;
	
	@Resource
	B2CConditionService b2cConditionService;
	
	@Resource
	CommRoleService commRoleService;
	
	@Resource
	PDFService pdfService;
	
	@RequestMapping("/b2c")
	public String todo(HttpServletRequest request,@RequestParam(required=false) Integer page){
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		Paging<B2CProject> pagelist = null;
		if(agentList.size()>0){
			pagelist = b2CProjectService.listAllProjectByAgentAndRole(agentList, user.getCommRole(),page, PAGE_SIZE);
		}
		request.setAttribute("agentList", agentList);
		request.setAttribute("pagelist", pagelist);
		request.setAttribute("AGENT", CommRole.DAILISHANG);//代理商
		request.setAttribute("AREA_MANAGER", CommRole.DAQUJINGLI);//区域经理
		request.setAttribute("XIAOFUZHUGUAN", CommRole.XIAOFUZHUGUAN);
		request.setAttribute("SALES_SERVICE_DIRECTOR", CommRole.XIAOSHOUZONGJINGLI);
		return "/b2c/todo_list.jsp";
	}
	
	@RequestMapping("/b2c/project/list.html")
	public String applyProjectList(HttpServletRequest request,@RequestParam(required=false) Integer page){
		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		ApproveTemplate approveTemplate = commService.findTmpeType(CommonConfig.getTEMP_B2C_PROJECT());
		List<ProcessStep> prSteps = processStepService.findByTemp(approveTemplate);
		Paging<B2CProject> paging = null;
		if(user.getCommRole().getId()==CommRole.DAILISHANG){//roleId=3即为代理商
			CommAgent agent = user.getCommAgent();
			paging = b2CProjectService.listAllProjectByAgentAndStep(agent,page, PAGE_SIZE);
		}else{
			List<CommAgent> agentList = commService.listCurrentAgent(request);
			request.setAttribute("agentList", agentList);
			if(agentList.size()>0){
				paging = b2CProjectService.listAllProjectByStep(agentList,page, PAGE_SIZE);
			}
		}
		request.setAttribute("pagelist", paging); 
		request.setAttribute("prSteps", prSteps);
		request.setAttribute("AGENT", CommRole.DAILISHANG);//代理商
		request.setAttribute("AREA_MANAGER", CommRole.DAQUJINGLI);//区域经理
		request.setAttribute("XIAOFUZHUGUAN", CommRole.XIAOFUZHUGUAN);
		request.setAttribute("SALES_SERVICE_DIRECTOR", CommRole.XIAOSHOUZONGJINGLI);
		return "/b2c/project_list.jsp";
	}
	
	@RequestMapping("/b2c/project/apply.html")
	public String applyProjectInput(HttpServletRequest request){
		
		Date date = new Date();
		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		List<Product> productlist = commService.findProductList(Product.NOT_DELETE);
		List<Map> agentMapList = new ArrayList();
		for (CommAgent agent : agentList) {
			Map map = new HashMap();
			if(agent!=null){
				map.put("projectCode", commService.getProjectCode(agent,Constants.PROJECT_CODE_TYPE_B2C));
				map.put("agentId", agent.getId());
				map.put("irName",agent.getIrName());
				map.put("cityName",agent.getCommCity().getCityName());
				agentMapList.add(map);
			}
		}
		List<B2CCondition> xjList = conditionService.findByType(2);
		List<B2CCondition> ydList = conditionService.findByType(3);
		List<B2CCondition> jdList = conditionService.findByType(4);
		List<B2CCondition> netList = conditionService.findByType(5);
		String product = productService.getProductNameArr(productlist);
		
		request.setAttribute("xjList", xjList);
		request.setAttribute("ydList", ydList);
		request.setAttribute("jdList", jdList);
		request.setAttribute("netList", netList);
		request.setAttribute("product", product);
		request.setAttribute("user", user);
		request.setAttribute("date", date);
		request.setAttribute("AGENT", CommRole.DAILISHANG);//代理商
		request.setAttribute("AREA_MANAGER", CommRole.DAQUJINGLI);//区域经理
		request.setAttribute("agentMapList", agentMapList);
		return "/b2c/apply_project.jsp";
	}
	
	@RequestMapping(value="/b2c/project/getProductPrice",method=RequestMethod.POST)
	@ResponseBody
	public String saveDescSetting(HttpServletRequest request,String productName){
		ApiWarp apiWarp = new ApiWarp();
		Product product = productService.findByName(productName);
		if(product==null){
			return apiWarp.addError("未找到匹配型号！name="+productName).toJsonString();
		}
		ProductPrice productPrice = productPriceService.findByProductAndDateMonth(product, DateUtils.getCurrentMonthFisrtDay());
		apiWarp.putData(productPrice);
		return apiWarp.toJsonString();
	}

	
	@RequestMapping(value="/b2c/apply/save",method=RequestMethod.POST)
	public String newSave(HttpServletRequest request,Integer projectId,String projectcode,Integer agentId,String theme,String applyReason,Integer[] applyType,String conditions[],String[] content,
			BigDecimal[] applyPrice, Integer[] number,Integer[] isGrowth, String[] growthRate, String[] compProduct, BigDecimal[] compPrice,BigDecimal[] applyTotalPrice,
			String[] remark,Integer[] conditionId2,String[] content2,Integer[] conditionId3,String[] content3,Integer[] conditionId4,String[] content4,Integer[] conditionId5,String[] content5){
		CommAgent agent = agentService.getById(agentId);
		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		Date applytime = new Date();
		B2CProject b2CProject = b2CProjectService.getB2CProjectById(projectId);
		if(b2CProject==null){
			b2CProject = new B2CProject();
			b2CProject.setpCode(projectcode);
			b2CProject.setAgent(agent);
			b2CProject.setTheme(theme);
			b2CProject.setApplyReason(applyReason);
			b2CProject.setApplytime(applytime);
			b2CProject.setUser(user);
			ApproveTemplate temp = commService.findTmpeType(CommonConfig.getTEMP_B2C_PROJECT());
			b2CProject.setApproveTemplate(temp);
			ProcessStep firstStep = commService.findFisrtStep(temp);
			if(user.getCommRole().getId()==CommRole.DAQUJINGLI){
				b2CProject.setProcessStep(firstStep.getPnextId());
			}else{
				b2CProject.setProcessStep(firstStep);
			}
		}else{
			List<B2CSpecialApplyProduct> applyProductList = applyProductService.getByB2CProject(b2CProject);
			List<B2CApplyOther> xjApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 2);
			List<B2CApplyOther> ydApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 3);
			List<B2CApplyOther> jdApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 4);
			List<B2CApplyOther> netApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 5);
			for(int i=0; i<applyProductList.size(); i++){
				B2CSpecialApplyProduct specialApplyProduct = applyProductList.get(i);
				applyProductService.delete(specialApplyProduct);
			}
			for(int i=0; i<xjApplyOtherList.size(); i++){
				B2CApplyOther applyOther = xjApplyOtherList.get(i);
				applyOtherService.delete(applyOther);
			}
			for(int i=0; i<ydApplyOtherList.size(); i++){
				B2CApplyOther applyOther = ydApplyOtherList.get(i);
				applyOtherService.delete(applyOther);
			}
			for(int i=0; i<jdApplyOtherList.size(); i++){
				B2CApplyOther applyOther = jdApplyOtherList.get(i);
				applyOtherService.delete(applyOther);
			}
			for(int i=0; i<netApplyOtherList.size(); i++){
				B2CApplyOther applyOther = netApplyOtherList.get(i);
				applyOtherService.delete(applyOther);
			}
			b2CProject.setTheme(theme);
			b2CProject.setApplyReason(applyReason);
			Integer flowType = 1;//表示大区经理改数据
			changeProjectStep(request,b2CProject,0,flowType,Boolean.FALSE);
		}
		b2CProjectService.save(b2CProject);
		
		for (Integer type : applyType) {
			if(type==1){
				for(int i=0; i<conditions.length; i++){
					B2CSpecialApplyProduct applyProduct = new B2CSpecialApplyProduct();
					applyProduct.setConditions(conditions[i]);
					applyProduct.setContent(content[i]);
					applyProduct.setApplyPrice(applyPrice[i]);
					applyProduct.setNumber(number[i]);
//					applyProduct.setIsPicking(isPicking[i]);
					applyProduct.setIsGrowth(isGrowth[i]);
					if(growthRate.length==0){
						applyProduct.setGrowthRate("");
					}else{
						applyProduct.setGrowthRate(growthRate[i]);
					}
					applyProduct.setCompProduct(compProduct[i]);
					applyProduct.setCompPrice(compPrice[i]);
					applyProduct.setApplyTotalPrice(applyTotalPrice[i]);
					if(remark.length==0){
						applyProduct.setRemark("");
					}else{
						applyProduct.setRemark(remark[i]);
					}
					applyProduct.setProject(b2CProject);
					applyProduct.setCommUser(user);
					applyProductService.save(applyProduct);
				}
			}else if(type==2){
				for(int i=0; i<conditionId2.length; i++){
					B2CApplyOther applyOther = new B2CApplyOther();
					B2CCondition conditions2 = conditionService.getConditionById(conditionId2[i]);
					applyOther.setCondition(conditions2);
					applyOther.setContent(content2[i]);
					applyOther.setType(type);
					applyOther.setB2CProject(b2CProject);
					applyOther.setCommUser(user);
					applyOtherService.save(applyOther);
				}
			}else if(type==3){
				for(int i=0; i<conditionId3.length; i++){
					B2CApplyOther applyOther = new B2CApplyOther();
					B2CCondition conditions3 = conditionService.getConditionById(conditionId3[i]);
					applyOther.setCondition(conditions3);
					applyOther.setContent(content3[i]);
					applyOther.setType(type);
					applyOther.setB2CProject(b2CProject);
					applyOther.setCommUser(user);
					applyOtherService.save(applyOther);
				}
			}else if(type==4){
				for(int i=0; i<conditionId4.length; i++){
					B2CApplyOther applyOther = new B2CApplyOther();
					B2CCondition conditions4 = conditionService.getConditionById(conditionId4[i]);
					applyOther.setCondition(conditions4);
					applyOther.setContent(content4[i]);
					applyOther.setType(type);
					applyOther.setB2CProject(b2CProject);
					applyOther.setCommUser(user);
					applyOtherService.save(applyOther);
				}
			}else {
				for(int i=0; i<conditionId5.length; i++){
					B2CApplyOther applyOther = new B2CApplyOther();
					B2CCondition conditions5 = conditionService.getConditionById(conditionId5[i]);
					applyOther.setCondition(conditions5);
					applyOther.setContent(content5[i]);
					applyOther.setType(type);
					applyOther.setB2CProject(b2CProject);
					applyOther.setCommUser(user);
					applyOtherService.save(applyOther);
				}
			}
		}
		return "redirect:/b2c/project/list.html";
	}
	
	@RequestMapping(value="/b2c/apply/editSave",method=RequestMethod.POST)
	public String editSave(HttpServletRequest request,Integer isSave,Integer flowType,Integer projectId, String theme,String applyReason,Integer[] applyId, String conditions[],String[] content,
			BigDecimal[] applyPrice, Integer[] number,Integer[] isGrowth, String[] growthRate, String[] compProduct, BigDecimal[] compPrice,BigDecimal[] applyTotalPrice,
			String[] remark,Integer[] applyId2,Integer[] conditionId2,String[] content2,Integer[] applyId3,Integer[] conditionId3,String[] content3,Integer[] applyId4,Integer[] conditionId4,String[] content4,Integer[] applyId5,Integer[] conditionId5,String[] content5){
		B2CProject project = b2CProjectService.getB2CProjectById(projectId);
		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		Boolean isEnd = false;
		if(flowType==1){//大区经理改数据
			if(isSave==1){//表示 大区经理第一次修改
				if(applyId!=null){
					for(int i=0; i< applyId.length; i++){
						B2CSpecialApplyProduct applyProduct = applyProductService.getByApplyProductId(applyId[i]);
						applyProduct.setConditions(conditions[i]);
						applyProduct.setContent(content[i]);
						applyProduct.setApplyPrice(applyPrice[i]);
						applyProduct.setNumber(number[i]);
//						applyProduct.setIsPicking(isPicking[i]);
						applyProduct.setIsGrowth(isGrowth[i]);
						if(growthRate.length==0){
							applyProduct.setGrowthRate("");
						}else{
							applyProduct.setGrowthRate(growthRate[i]);
						}
						applyProduct.setCompProduct(compProduct[i]);
						applyProduct.setCompPrice(compPrice[i]);
						applyProduct.setApplyTotalPrice(applyTotalPrice[i]);
						applyProduct.setRemark(remark[i]);
						applyProductService.save(applyProduct);
					}
				}
				if(applyId2!=null){
					for(int i=0; i< applyId2.length; i++){
						B2CApplyOther applyOther = applyOtherService.getB2CApplyOtherById(applyId2[i]);
						B2CCondition conditions2 = conditionService.getConditionById(conditionId2[i]);
						applyOther.setCondition(conditions2);
						applyOther.setContent(content2[i]);
						applyOtherService.save(applyOther);
					}
				}
				if(applyId3!=null){
					for(int i=0; i< applyId3.length; i++){
						B2CApplyOther applyOther = applyOtherService.getB2CApplyOtherById(applyId3[i]);
						B2CCondition conditions3 = conditionService.getConditionById(conditionId3[i]);
						applyOther.setCondition(conditions3);
						applyOther.setContent(content3[i]);
						applyOtherService.save(applyOther);
					}
				}
				if(applyId4!=null){
					for(int i=0; i< applyId4.length; i++){
						B2CApplyOther applyOther = applyOtherService.getB2CApplyOtherById(applyId4[i]);
						B2CCondition conditions4 = conditionService.getConditionById(conditionId4[i]);
						applyOther.setCondition(conditions4);
						applyOther.setContent(content4[i]);
						applyOtherService.save(applyOther);
					}
				}
				if(applyId5!=null){
					for(int i=0; i< applyId5.length; i++){
						B2CApplyOther applyOther = applyOtherService.getB2CApplyOtherById(applyId5[i]);
						B2CCondition conditions5 = conditionService.getConditionById(conditionId5[i]);
						applyOther.setCondition(conditions5);
						applyOther.setContent(content5[i]);
						applyOtherService.save(applyOther);
					}
				}
			}else{//如果申请数据中 isAgren都是1则单子过
				List<B2CSpecialApplyProduct> applyProductList = applyProductService.getByB2CProject(project);
				List<B2CApplyOther> applyOthers = b2cApplyOtherService.findByProject(project);
				int isAgreeCount = 0;
				for(int i=0; i<applyProductList.size(); i++){
					B2CSpecialApplyProduct specialApplyProduct = applyProductList.get(i);
					Integer isAgree = specialApplyProduct.getIsAgree();
					if(isAgree==null || isAgree==1){//销服总监全部同意了
						specialApplyProduct.setIsAgree(1);
						b2cSpecialApplyProductService.save(specialApplyProduct);
						isAgreeCount++;
					}else{
						CommRole doRole = specialApplyProduct.getCommUser().getCommRole();
						if(doRole!=null && !user.getCommRole().getId().equals(doRole.getId())){
							isAgreeCount++;
						}
					}
				}
				for(int i=0; i<applyOthers.size(); i++){
					B2CApplyOther applyOther = applyOthers.get(i);
					Integer isAgree = applyOther.getIsAgree();
					if(isAgree==null  || isAgree==1){
						applyOther.setIsAgree(1);
						b2cApplyOtherService.save(applyOther);
						isAgreeCount++;
					}else{
						CommRole doRole = applyOther.getCommUser().getCommRole();
						if(doRole!=null && !user.getCommRole().getId().equals(doRole.getId())){
							isAgreeCount++;
						}
					}
				}
				if(isAgreeCount==(applyProductList.size()+applyOthers.size())){//全部同意了
					//存最终数据
					for(int i=0; i<applyProductList.size(); i++){
						B2CSpecialApplyProduct specialApplyProduct = applyProductList.get(i);
						List<B2CSpecialApplyProductReplyLog> replyLogs = applyProductReplyLogRepository.findByApplyProduct(specialApplyProduct);
						if(replyLogs.size()>0){
							specialApplyProduct.setReplyLog(replyLogs.get(replyLogs.size()-1));
							b2cSpecialApplyProductService.save(specialApplyProduct);
						}
					}
					
					for(int i=0; i<applyOthers.size(); i++){
						B2CApplyOther applyOther = applyOthers.get(i);
						List<B2CApplyOtherReplyLog> replyLogs = applyOtherReplyLogRepository.findByApplyOther(applyOther);
						if(replyLogs.size()>0){
							applyOther.setReplyLog(replyLogs.get(replyLogs.size()-1));
							b2cApplyOtherService.save(applyOther);
						}
					}
					isEnd = true;
					
				}
			}
		}else if(flowType==2){//销服主管填意见
		}
		changeProjectStep(request,project,0,flowType,isEnd);
		return "redirect:/b2c";
	}
	
	@RequestMapping("/b2c/project/edit-{id}")
	public String areaEdit(HttpServletRequest request,@PathVariable Integer id){
		Boolean areaManagerEdit = true;
		B2CProject b2CProject = b2CProjectService.getB2CProjectById(id);
		List<Product> productlist = commService.findProductList(Product.NOT_DELETE);
		String product = productService.getProductNameArr(productlist);
		List<B2CSpecialApplyProduct> applyProductList = applyProductService.getByB2CProject(b2CProject);
		Integer isSave = 1;//提交表单时，是否重新保存表单数据(1、保存。0、不保存)
		int sumApplyNumber = 0;
		BigDecimal sumApplyTotalPrice = new BigDecimal(0);
		for(B2CSpecialApplyProduct pdt:applyProductList){
			sumApplyNumber += pdt.getNumber();
			sumApplyTotalPrice = sumApplyTotalPrice.add(pdt.getApplyTotalPrice());
			List<B2CSpecialApplyProductReplyLog> replyLogList = applyProductReplyLogRepository.findByApplyProduct(pdt);
			for(int j=0; j<replyLogList.size(); j++){//合计申请数量和支持金总额
				B2CSpecialApplyProductReplyLog replyLog = replyLogList.get(j);
				sumApplyNumber += replyLog.getNumber();
				BigDecimal tempApplyTotalPrice = null;
				if(replyLog.getApplyTotalPrice()!=null){
					tempApplyTotalPrice = replyLog.getApplyTotalPrice();
				}else{
					tempApplyTotalPrice = new BigDecimal(0);
				}
				sumApplyTotalPrice = sumApplyTotalPrice.add(tempApplyTotalPrice);
			}
			if(replyLogList.size()>0){
				isSave = 0;
			}
			pdt.setReplyLogList(replyLogList);
		}
		List<B2CSpecialApplyProduct> new_applyProductList = new ArrayList<B2CSpecialApplyProduct>();
		for(int i=0; i<applyProductList.size(); i++){
			B2CSpecialApplyProduct specialApplyProduct = applyProductList.get(i);
			String prodectName = specialApplyProduct.getContent();
			Product applyProduct = productService.findByName(prodectName);
			if(product!=null){
				ProductPrice productPrice = productPriceService.findByProductAndDateMonth(applyProduct, DateUtils.getCurrentMonthFisrtDay());
				if(productPrice!=null){
					specialApplyProduct.setPoPrice(productPrice.getPoPrice());
					specialApplyProduct.setNetPrice(productPrice.getNetPrice());
					specialApplyProduct.setFinancePrice(productPrice.getFinancePrice());
					specialApplyProduct.setSyPrice(productPrice.getSyPrice());
				}
			}
			new_applyProductList.add(specialApplyProduct);
		}
		List<B2CApplyOther> xjApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 2);
		List<B2CApplyOther> ydApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 3);
		List<B2CApplyOther> jdApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 4);
		List<B2CApplyOther> netApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 5);
		for(B2CApplyOther applyOther:xjApplyOtherList){
			List<B2CApplyOtherReplyLog> replyLogList = applyOtherReplyLogRepository.findByApplyOther(applyOther);
			if(replyLogList.size()>0){
				isSave=0;
			}
			applyOther.setReplyLogList(replyLogList);
		}
		for(B2CApplyOther applyOther:ydApplyOtherList){
			List<B2CApplyOtherReplyLog> replyLogList = applyOtherReplyLogRepository.findByApplyOther(applyOther);
			if(replyLogList.size()>0){
				isSave=0;
			}
			applyOther.setReplyLogList(replyLogList);
		}
		for(B2CApplyOther applyOther:jdApplyOtherList){
			List<B2CApplyOtherReplyLog> replyLogList = applyOtherReplyLogRepository.findByApplyOther(applyOther);
			if(replyLogList.size()>0){
				isSave=0;
			}
			applyOther.setReplyLogList(replyLogList);
		}
		for(B2CApplyOther applyOther:netApplyOtherList){
			List<B2CApplyOtherReplyLog> replyLogList = applyOtherReplyLogRepository.findByApplyOther(applyOther);
			if(replyLogList.size()>0){
				isSave=0;
			}
			applyOther.setReplyLogList(replyLogList);
		}
		List<B2CCondition> xjList = conditionService.findByType(2);
		List<B2CCondition> ydList = conditionService.findByType(3);
		List<B2CCondition> jdList = conditionService.findByType(4);
		List<B2CCondition> netList = conditionService.findByType(5);
		
		List<B2CApproveLog > logList = b2cApprovalLogService.listByProject(b2CProject);
		for(int i=0; i<logList.size(); i++){
			B2CApproveLog approveLog = logList.get(i);
			if(approveLog.getStep().getStatusValue()==3){//销服主管填写意见的，操作值(不能改)
				areaManagerEdit = false;
				break;
			}
		}
		
		
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		List<Map> agentMapList = new ArrayList();
		for (CommAgent agent : agentList) {
			Map map = new HashMap();
			if(agent!=null){
				map.put("projectCode", commService.getProjectCode(agent,Constants.PROJECT_CODE_TYPE_B2C));
				map.put("agentId", agent.getId());
				map.put("irName",agent.getIrName());
				map.put("cityName",agent.getCommCity().getCityName());
				agentMapList.add(map);
			}
		}
		
		
		request.setAttribute("agentMapList", agentMapList);
		request.setAttribute("sumApplyNumber", sumApplyNumber);
		request.setAttribute("sumApplyTotalPrice", sumApplyTotalPrice.doubleValue());
		request.setAttribute("AGENT", CommRole.DAILISHANG);//代理商
		request.setAttribute("AREA_MANAGER", CommRole.DAQUJINGLI);//区域经理
		request.setAttribute("b2CProject", b2CProject);
		request.setAttribute("product", product);
		request.setAttribute("applyProductList", new_applyProductList);
		request.setAttribute("xjApplyOtherList", xjApplyOtherList);
		request.setAttribute("ydApplyOtherList", ydApplyOtherList);
		request.setAttribute("jdApplyOtherList", jdApplyOtherList);
		request.setAttribute("netApplyOtherList", netApplyOtherList);
		request.setAttribute("xjList", xjList);
		request.setAttribute("ydList", ydList);
		request.setAttribute("jdList", jdList);
		request.setAttribute("netList", netList);
		request.setAttribute("isSave", isSave);
		if(areaManagerEdit){
			if(applyProductList.size()>0){
				request.setAttribute("type1", true);
			}
			if(xjApplyOtherList.size()>0){
				request.setAttribute("type2", true);
			}
			if(ydApplyOtherList.size()>0){
				request.setAttribute("type3", true);
			}
			if(jdApplyOtherList.size()>0){
				request.setAttribute("type4", true);
			}
			if(netApplyOtherList.size()>0){
				request.setAttribute("type5", true);
			}
			return "/b2c/apply_project.jsp";
		}
		return "/b2c/project_edit.jsp";
	}
	
	@RequestMapping("/b2c/project/view-{id}")
	public String view(HttpServletRequest request,@PathVariable Integer id){
		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		B2CProject b2CProject = b2CProjectService.getB2CProjectById(id);
		List<Product> productlist = commService.findProductList(Product.NOT_DELETE);
		String product = productService.getProductNameArr(productlist);
		int sumApplyNumber = 0;
		BigDecimal sumApplyTotalPrice = new BigDecimal(0);
		List<B2CSpecialApplyProduct> applyProductList = applyProductService.getByB2CProject(b2CProject);
		for(B2CSpecialApplyProduct pdt:applyProductList){
			sumApplyNumber += pdt.getNumber();
			sumApplyTotalPrice = sumApplyTotalPrice.add(pdt.getApplyTotalPrice());
			List<B2CSpecialApplyProductReplyLog> replyLogList = applyProductReplyLogRepository.findByApplyProduct(pdt);
			for(int j=0; j<replyLogList.size(); j++){//合计申请数量和支持金总额
				B2CSpecialApplyProductReplyLog replyLog = replyLogList.get(j);
				sumApplyNumber += replyLog.getNumber();
				BigDecimal tempApplyTotalPrice = null;
				if(replyLog.getApplyTotalPrice()!=null){
					tempApplyTotalPrice = replyLog.getApplyTotalPrice();
				}else{
					tempApplyTotalPrice = new BigDecimal(0);
				}
				sumApplyTotalPrice = sumApplyTotalPrice.add(tempApplyTotalPrice);
			}
			pdt.setReplyLogList(replyLogList);
		}
		List<B2CApplyOther> xjApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 2);
		List<B2CApplyOther> ydApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 3);
		List<B2CApplyOther> jdApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 4);
		List<B2CApplyOther> netApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 5);
		for(B2CApplyOther applyOther:xjApplyOtherList){
			List<B2CApplyOtherReplyLog> replyLogList = applyOtherReplyLogRepository.findByApplyOther(applyOther);
			applyOther.setReplyLogList(replyLogList);
		}
		for(B2CApplyOther applyOther:ydApplyOtherList){
			List<B2CApplyOtherReplyLog> replyLogList = applyOtherReplyLogRepository.findByApplyOther(applyOther);
			applyOther.setReplyLogList(replyLogList);
		}
		for(B2CApplyOther applyOther:jdApplyOtherList){
			List<B2CApplyOtherReplyLog> replyLogList = applyOtherReplyLogRepository.findByApplyOther(applyOther);
			applyOther.setReplyLogList(replyLogList);
		}
		for(B2CApplyOther applyOther:netApplyOtherList){
			List<B2CApplyOtherReplyLog> replyLogList = applyOtherReplyLogRepository.findByApplyOther(applyOther);
			applyOther.setReplyLogList(replyLogList);
		}
		List<B2CCondition> xjList = conditionService.findByType(2);
		List<B2CCondition> ydList = conditionService.findByType(3);
		List<B2CCondition> jdList = conditionService.findByType(4);
		List<B2CCondition> netList = conditionService.findByType(5);
		List<B2CApproveLog > logList = b2cApprovalLogService.listByProject(b2CProject);
		List<B2CSpecialApplyProduct> new_applyProductList = new ArrayList<B2CSpecialApplyProduct>();
		for(int i=0; i<applyProductList.size(); i++){
			B2CSpecialApplyProduct specialApplyProduct = applyProductList.get(i);
			String prodectName = specialApplyProduct.getContent();
			Product applyProduct = productService.findByName(prodectName);
			if(product!=null){
				ProductPrice productPrice = productPriceService.findByProductAndDateMonth(applyProduct, DateUtils.getCurrentMonthFisrtDay());
				if(productPrice!=null){
					specialApplyProduct.setPoPrice(productPrice.getPoPrice());
					specialApplyProduct.setNetPrice(productPrice.getNetPrice());
					specialApplyProduct.setFinancePrice(productPrice.getFinancePrice());
					specialApplyProduct.setSyPrice(productPrice.getSyPrice());
				}
			}
			new_applyProductList.add(specialApplyProduct);
		}
		if(user.getCommRole().getId()==CommRole.XIAOSHOUZONGJINGLI){
			List<CommAgent> agentList = new ArrayList<CommAgent>();
			agentList.add(b2CProject.getAgent());
			List<B2CProject> closePorjects = b2CProjectService.listAllPorjectByAgentsAndStep(agentList);
			BigDecimal applyTotalPrice = new BigDecimal(0);
			for(int i=0; i<closePorjects.size(); i++){
				List<B2CSpecialApplyProduct> applyProductClose = applyProductService.getByB2CProject(closePorjects.get(i));
				for(int j=0; j<applyProductClose.size(); j++){
					B2CSpecialApplyProduct temProduct = applyProductClose.get(j);
					applyTotalPrice = applyTotalPrice.add(temProduct.getApplyTotalPrice());
				}
			}
			request.setAttribute("applyTotalPrice", applyTotalPrice.doubleValue());
		}
		
		request.setAttribute("sumApplyNumber", sumApplyNumber);
		request.setAttribute("sumApplyTotalPrice", sumApplyTotalPrice.doubleValue());
		request.setAttribute("b2CProject", b2CProject);
		request.setAttribute("product", product);
		request.setAttribute("applyProductList", new_applyProductList);
		request.setAttribute("xjApplyOtherList", xjApplyOtherList);
		request.setAttribute("ydApplyOtherList", ydApplyOtherList);
		request.setAttribute("jdApplyOtherList", jdApplyOtherList);
		request.setAttribute("netApplyOtherList", netApplyOtherList);
		request.setAttribute("xjList", xjList);
		request.setAttribute("ydList", ydList);
		request.setAttribute("jdList", jdList);
		request.setAttribute("netList", netList);
		request.setAttribute("logList", logList);
		request.setAttribute("XIAOFUZHUGUAN", CommRole.XIAOFUZHUGUAN);
		request.setAttribute("XIAOSHOUZONGJINGLI", CommRole.XIAOSHOUZONGJINGLI);
		request.setAttribute("AGENT", CommRole.DAILISHANG);
		return "/b2c/project_view.jsp";
	}
	
	@RequestMapping("/b2c/project/delete")
	public String delete(Integer id){
		B2CProject b2CProject = b2CProjectService.getB2CProjectById(id);
		List<B2CSpecialApplyProduct> applyProductList = applyProductService.getByB2CProject(b2CProject);
		
		for (B2CSpecialApplyProduct b2cSpecialApplyProduct : applyProductList) {
			applyProductService.delete(b2cSpecialApplyProduct);
		}
		
		b2CProjectService.delete(b2CProject);
		return "redirect:/b2c/project/list.html";
	}
	
	@RequestMapping("/b2c/report.html")
	public String report(HttpServletRequest request,@RequestParam(required=false) Integer page){
		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		Paging<B2CProject> paging = null;
		
		Date startDate = DateUtils.getCurrentMonthFisrtDay();
		Date endDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),0,0,0);
		endDate = calendar.getTime();
		List<Integer> projectIds = new ArrayList<Integer>();
		if(user.getCommRole().getId()==CommRole.DAILISHANG){//roleId=3即为代理商
			CommAgent agent = user.getCommAgent();
			paging = b2CProjectService.searchProjectByAgentAndStep(startDate, endDate, "", agent,projectIds, page, PAGE_SIZE);
		}else{
			List<CommAgent> agentList = commService.listCurrentAgent(request);
			request.setAttribute("agentList", agentList);
			if(agentList.size()>0){
				paging = b2CProjectService.searchProjectAndStep(startDate, endDate, "" ,agentList,projectIds, page, PAGE_SIZE);
			}
		}

		request.setAttribute("AREA_MANAGER", CommRole.DAQUJINGLI);//区域经理
		request.setAttribute("SALES_SERVICE_DIRECTOR", CommRole.XIAOSHOUZONGJINGLI);
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("pagelist", paging);
		
		return "/b2c/report.jsp";
	}
	
	/**
	 * 退回项目查询
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/b2c/retProject.html")
	public String retProjectList(HttpServletRequest request,@RequestParam(required=false) Integer page){
		List<CommAgent> agents = commService.listCurrentAgent(request);
		CommRole role = commRoleService.getCommRoleById(CommRole.DAQUJINGLI);
		List<B2CProject> projects = b2CProjectService.findProjectListByRoleAndAgent(role,agents);
		List<Integer> projectIds = new ArrayList<Integer>();
		for(int i=0; i<projects.size(); i++){
			List<B2CSpecialApplyProduct> specialApplyProducts = applyProductService.getByB2CProject(projects.get(i));
			List<B2CApplyOther> applyOthers = applyOtherService.findByProject(projects.get(i));
			//查看是否有被销售总经理退回的单子
			for(int j=0; j<specialApplyProducts.size(); j++){
				B2CSpecialApplyProduct applyProduct = specialApplyProducts.get(j);
				if(applyProduct.getIsAgree()!=null && applyProduct.getIsAgree()==0 && applyProduct.getCommUser().getCommRole().getId().equals(CommRole.XIAOSHOUZONGJINGLI)){
					projectIds.add(projects.get(i).getId());
					break;
				}
			}
			
			for(int k=0; k<applyOthers.size(); k++){
				B2CApplyOther applyOther = applyOthers.get(k);
				if(applyOther.getIsAgree()!=null && applyOther.getIsAgree()==0 && applyOther.getCommUser().getCommRole().getId().equals(CommRole.XIAOSHOUZONGJINGLI)){
					projectIds.add(projects.get(i).getId());
					break;
				}
			}
		}
		Paging<B2CProject> paging = null;
		if(projectIds.size()>0){
			paging = b2CProjectService.listAllProjectByIds(projectIds, page, PAGE_SIZE);
		}
		request.setAttribute("pagelist", paging);
		
		return "/b2c/retProject_list.jsp";
	}
	
	@RequestMapping("/b2c/report/search")
	public String reportSearch(HttpServletRequest request,@RequestParam(required=false) Integer page,String startDate,String endDate,String approvStartDate,String approvEndDate,String pCode,Integer agentId) throws Exception{

		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		Paging<B2CProject> paging = null;
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		if(!"".equals(startDate) && startDate!=null){
			date1 = sdf.parse(startDate);
		}
		Date date2 = null;
		if(!"".equals(endDate) && endDate!=null){
			date2 = sdf.parse(endDate);
		}
		
		Date approvDate1 = null;
		if(approvStartDate!=null && !"".equals(approvStartDate)){
			approvDate1 = sdf.parse(approvStartDate);
		}
		Date approvDate2 = null;
		if(approvEndDate!=null && !"".equals(approvEndDate)){
			approvDate2 = sdf.parse(approvEndDate);
		}
		
		List<B2CApproveLog> approveLogs = b2cApprovalLogService.listByApprovDateAndStep(approvDate1, approvDate2,agentList);
		List<Integer> projectIds = new ArrayList<Integer>();
		for(int i=0; i<approveLogs.size(); i++){
			projectIds.add(approveLogs.get(i).getProject().getId());
		}
		if(user.getCommRole().getId()==CommRole.DAILISHANG){//roleId=3即为代理商
			CommAgent agent = user.getCommAgent();
			paging = b2CProjectService.searchProjectByAgentAndStep(date1, date2, pCode, agent,projectIds, page, PAGE_SIZE);
		}else{
			request.setAttribute("agentList", agentList);
			//代理商筛选
			if(agentId==null){
				agentList = commService.listCurrentAgent(request);
			}else{
				agentList = new ArrayList<CommAgent>();
				CommAgent agent = agentService.getById(agentId);
				agentList.add(agent);
			}
			if(agentList.size()>0){
				paging = b2CProjectService.searchProjectAndStep(date1, date2, pCode ,agentList,projectIds, page, PAGE_SIZE);
			}
		}

		request.setAttribute("AREA_MANAGER", CommRole.DAQUJINGLI);//区域经理
		request.setAttribute("SALES_SERVICE_DIRECTOR", CommRole.XIAOSHOUZONGJINGLI);
		request.setAttribute("pCode", pCode);
		request.setAttribute("startDate", date1);
		request.setAttribute("endDate", date2);
		request.setAttribute("approvStartDate", approvDate1);
		request.setAttribute("approvEndDate", approvDate2);
		request.setAttribute("pagelist", paging);
		
		return "/b2c/report.jsp";
	}
	
	
	/**
	 * 项目列表也筛选查询
	 * @param request
	 * @param page
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @param agentId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/b2c/project/search")
	public String projectSearch(HttpServletRequest request,@RequestParam(required=false) Integer page,String startDate,String endDate,Integer status,Integer agentId) throws Exception{
		Paging<B2CProject> paging = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		if(!StringUtils.isBlank(startDate)){
			date1 = sdf.parse(startDate);
		}
		Date date2 = null;
		if(!StringUtils.isBlank(endDate)){
			date2 = sdf.parse(endDate);
		}
		List<CommAgent> agentList = null;
		//代理商筛选
		if(agentId==null){
			agentList = commService.listCurrentAgent(request);
		}else{
			agentList = new ArrayList<CommAgent>();
			CommAgent agent = agentService.getById(agentId);
			agentList.add(agent);
		}
		if(agentList.size()>0){
			paging = b2CProjectService.searchProjectByDateAndStatusAndStep(date1, date2, status, agentList, page, PAGE_SIZE);
		}
		ApproveTemplate approveTemplate = commService.findTmpeType(CommonConfig.getTEMP_B2C_PROJECT());
		List<ProcessStep> prSteps = processStepService.findByTemp(approveTemplate);
		request.setAttribute("prSteps", prSteps);
		request.setAttribute("AGENT", CommRole.DAILISHANG);//代理商
		request.setAttribute("AREA_MANAGER", CommRole.DAQUJINGLI);//区域经理
		request.setAttribute("XIAOFUZHUGUAN", CommRole.XIAOFUZHUGUAN);
		request.setAttribute("SALES_SERVICE_DIRECTOR", CommRole.XIAOSHOUZONGJINGLI);
		request.setAttribute("startDate", date1);
		request.setAttribute("endDate", date2);
		request.setAttribute("status", status);
		request.setAttribute("pagelist", paging);
		return "/b2c/project_list.jsp";
	}

	/**
	 * 代办 事宜 筛选查询
	 * @param request
	 * @param page
	 * @param startDate
	 * @param endDate
	 * @param agentId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/b2c/project/todo/search")
	public String projectSearch(HttpServletRequest request,@RequestParam(required=false) Integer page,String startDate,String endDate,Integer agentId) throws Exception{
		Paging<B2CProject> paging = null;
		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		if(!StringUtils.isBlank(startDate)){
			date1 = sdf.parse(startDate);
		}
		Date date2 = null;
		if(!StringUtils.isBlank(endDate)){
			date2 = sdf.parse(endDate);
		}
		List<CommAgent> agentList = null;
		//代理商筛选
		if(agentId==null){
			agentList = commService.listCurrentAgent(request);
		}else{
			agentList = new ArrayList<CommAgent>();
			CommAgent agent = agentService.getById(agentId);
			agentList.add(agent);
		}
		if(agentList.size()>0){
			paging = b2CProjectService.searchProjectByDateAndRole(date1, date2, agentList, user.getCommRole(),page, PAGE_SIZE);
		}
		List<CommAgent> agents = commService.listCurrentAgent(request);
		request.setAttribute("agentList", agents);
		request.setAttribute("AGENT", CommRole.DAILISHANG);//代理商
		request.setAttribute("AREA_MANAGER", CommRole.DAQUJINGLI);//区域经理
		request.setAttribute("XIAOFUZHUGUAN", CommRole.XIAOFUZHUGUAN);
		request.setAttribute("SALES_SERVICE_DIRECTOR", CommRole.XIAOSHOUZONGJINGLI);
		request.setAttribute("startDate", date1);
		request.setAttribute("endDate", date2);
		request.setAttribute("agentId", agentId);
		request.setAttribute("pagelist", paging);
		return "/b2c/todo_list.jsp";
	}
	
	@RequestMapping(value="/b2c/report/excel")
	public String export(HttpServletRequest request,HttpServletResponse response,String startDate,String endDate,String approvStartDate,String approvEndDate,String pCode,Integer agentId) throws Exception{
		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		Paging<B2CProject> paging = null;
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		if(!"".equals(startDate) && startDate!=null){
			date1 = sdf.parse(startDate);
		}
		Date date2 = null;
		if(!"".equals(endDate) && endDate!=null){
			date2 = sdf.parse(endDate);
		}
		
		Date approvDate1 = null;
		if(approvStartDate!=null && !"".equals(approvStartDate)){
			approvDate1 = sdf.parse(approvStartDate);
		}
		Date approvDate2 = null;
		if(approvEndDate!=null && !"".equals(approvEndDate)){
			approvDate2 = sdf.parse(approvEndDate);
		}
		
		List<B2CApproveLog> approveLogs = b2cApprovalLogService.listByApprovDateAndStep(approvDate1, approvDate2,agentList);
		List<Integer> projectIds = new ArrayList<Integer>();
		for(int i=0; i<approveLogs.size(); i++){
			projectIds.add(approveLogs.get(i).getProject().getId());
		}
		if(agentId==null){
			agentList = commService.listCurrentAgent(request);
		}else{
			agentList = new ArrayList<CommAgent>();
			CommAgent agent = agentService.getById(agentId);
			agentList.add(agent);
		}
		List<B2CSpecialApplyProduct> applyProductList = applyProductService.searchProjectToExport(date1, date2, pCode, agentList, projectIds);
		List<B2CApplyOther> applyOtherList = applyOtherService.searchProjectToExport(date1, date2, pCode, agentList, projectIds);
		List<Map> productMapList = applyProductService.getMapBySearch(applyProductList,user);
		List<Map> otherMapList = applyOtherService.getMapBySearch(applyOtherList, user);
		productMapList.addAll(otherMapList);
		/*CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse(startDate);
		Date date2 = sdf.parse(endDate);
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		List<B2CSpecialApplyProduct> applyProductList = applyProductService.searchProjectToExport(date1, date2, pCode,agentList);
		List<B2CApplyOther> applyOtherList = applyOtherService.searchProjectToExport(date1, date2, pCode,agentList);
		
		List<Map> productMapList = applyProductService.getMapBySearch(applyProductList,user);
		List<Map> otherMapList = applyOtherService.getMapBySearch(applyOtherList, user);
		
		productMapList.addAll(otherMapList);*/
		
		response.setContentType("application/vnd.ms-excel;charset=GBK");  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setHeader("Content-Disposition", "attachment;filename=test.csv");  
        response.setCharacterEncoding("GBK");
        OutputStreamWriter osw = null;
        try {
        	osw = new OutputStreamWriter(response.getOutputStream(), "GBK");  
        	osw.write(this.excelService.convert2Cvs(TitleMapper.b2cReportMapper, productMapList));
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
	
	@RequestMapping(value="b2c/project/flow-{id}")
	public String flow(HttpServletRequest request,@PathVariable Integer id){
		B2CProject b2CProject = b2CProjectService.getB2CProjectById(id);
		List<Product> productlist = commService.findProductList(Product.NOT_DELETE);
		String product = productService.getProductNameArr(productlist);
		List<B2CSpecialApplyProduct> applyProductList = applyProductService.getByB2CProject(b2CProject);
		int sumApplyNumber = 0;
		BigDecimal sumApplyTotalPrice = new BigDecimal(0);
		for(B2CSpecialApplyProduct pdt:applyProductList){
			List<B2CSpecialApplyProductReplyLog> replyLogList = applyProductReplyLogRepository.findByApplyProduct(pdt);
			if(pdt.getIsAgree()==null || pdt.getIsAgree()==1){
				sumApplyNumber += pdt.getNumber();
				sumApplyTotalPrice = sumApplyTotalPrice.add(pdt.getApplyTotalPrice());
			}else{
				for(int j=0; j<replyLogList.size(); j++){//合计申请数量和支持金总额
					B2CSpecialApplyProductReplyLog replyLog = replyLogList.get(j);
					sumApplyNumber += replyLog.getNumber();
					BigDecimal tempApplyTotalPrice = null;
					if(replyLog.getApplyTotalPrice()!=null){
						tempApplyTotalPrice = replyLog.getApplyTotalPrice();
					}else{
						tempApplyTotalPrice = new BigDecimal(0);
					}
					sumApplyTotalPrice = sumApplyTotalPrice.add(tempApplyTotalPrice);
				}
			}
			
			pdt.setReplyLogList(replyLogList);
		}
		List<B2CSpecialApplyProduct> new_applyProductList = new ArrayList<B2CSpecialApplyProduct>();
		for(int i=0; i<applyProductList.size(); i++){
			B2CSpecialApplyProduct specialApplyProduct = applyProductList.get(i);
			String prodectName = specialApplyProduct.getContent();
			Product applyProduct = productService.findByName(prodectName);
			if(product!=null){
				ProductPrice productPrice = productPriceService.findByProductAndDateMonth(applyProduct, DateUtils.getCurrentMonthFisrtDay());
				if(productPrice!=null){
					specialApplyProduct.setPoPrice(productPrice.getPoPrice());
					specialApplyProduct.setNetPrice(productPrice.getNetPrice());
					specialApplyProduct.setFinancePrice(productPrice.getFinancePrice());
					specialApplyProduct.setSyPrice(productPrice.getSyPrice());
				}
			}
			new_applyProductList.add(specialApplyProduct);
		}
		List<B2CApplyOther> xjApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 2);
		List<B2CApplyOther> ydApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 3);
		List<B2CApplyOther> jdApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 4);
		List<B2CApplyOther> netApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 5);
		List<B2CCondition> xjList = conditionService.findByType(2);
		List<B2CCondition> ydList = conditionService.findByType(3);
		List<B2CCondition> jdList = conditionService.findByType(4);
		List<B2CCondition> netList = conditionService.findByType(5);
		
		for(B2CApplyOther applyOther:xjApplyOtherList){
			List<B2CApplyOtherReplyLog> replyLogList = applyOtherReplyLogRepository.findByApplyOther(applyOther);
			applyOther.setReplyLogList(replyLogList);
		}
		for(B2CApplyOther applyOther:ydApplyOtherList){
			List<B2CApplyOtherReplyLog> replyLogList = applyOtherReplyLogRepository.findByApplyOther(applyOther);
			applyOther.setReplyLogList(replyLogList);
		}
		for(B2CApplyOther applyOther:jdApplyOtherList){
			List<B2CApplyOtherReplyLog> replyLogList = applyOtherReplyLogRepository.findByApplyOther(applyOther);
			applyOther.setReplyLogList(replyLogList);
		}
		for(B2CApplyOther applyOther:netApplyOtherList){
			List<B2CApplyOtherReplyLog> replyLogList = applyOtherReplyLogRepository.findByApplyOther(applyOther);
			applyOther.setReplyLogList(replyLogList);
		}
		request.setAttribute("sumApplyNumber", sumApplyNumber);
		request.setAttribute("sumApplyTotalPrice", sumApplyTotalPrice.doubleValue());
		request.setAttribute("b2CProject", b2CProject);
		request.setAttribute("product", product);
		request.setAttribute("applyProductList", new_applyProductList);
		request.setAttribute("xjApplyOtherList", xjApplyOtherList);
		request.setAttribute("ydApplyOtherList", ydApplyOtherList);
		request.setAttribute("jdApplyOtherList", jdApplyOtherList);
		request.setAttribute("netApplyOtherList", netApplyOtherList);
		request.setAttribute("xjList", xjList);
		request.setAttribute("ydList", ydList);
		request.setAttribute("jdList", jdList);
		request.setAttribute("netList", netList);
		if(b2CProject.getProcessStep().getPnextId().getCommRole().getId()==CommRole.DAILISHANG){//代理商
			return "redirect:/b2c/project/view-"+id;
		}else if(b2CProject.getProcessStep().getPnextId().getCommRole().getId()==CommRole.XIAOFUZHUGUAN){//销服主管
			return "redirect:/b2c/project/view-"+id;
		}else if(b2CProject.getProcessStep().getPnextId().getCommRole().getId()==CommRole.DAQUJINGLI){//大区经理
			return "redirect:/b2c/project/edit-"+id;
		}else if(b2CProject.getProcessStep().getPnextId().getCommRole().getId()==CommRole.XIAOSHOUZONGJINGLI){//销售总经理
			List<CommAgent> agentList = new ArrayList<CommAgent>();
			agentList.add(b2CProject.getAgent());
			List<B2CProject> closePorjects = b2CProjectService.listAllPorjectByAgentsAndStep(agentList);
			BigDecimal applyTotalPrice = new BigDecimal(0);
			for(int i=0; i<closePorjects.size(); i++){
				List<B2CSpecialApplyProduct> applyProductClose = applyProductService.getByB2CProject(closePorjects.get(i));
				for(int j=0; j<applyProductClose.size(); j++){
					B2CSpecialApplyProduct temProduct = applyProductClose.get(j);
					applyTotalPrice = applyTotalPrice.add(temProduct.getApplyTotalPrice());
				}
			}
			request.setAttribute("applyTotalPrice", applyTotalPrice.doubleValue());
			return "/b2c/project_look.jsp";
		}else{
			return "redirect:/b2c";
		}
	}
	
	@RequestMapping(value="b2c/project/flowStep",method=RequestMethod.POST)
	public String flowStep(HttpServletRequest request,Integer projectId,Integer isPass){
		B2CProject b2CProject = b2CProjectService.getB2CProjectById(projectId);
		List<B2CSpecialApplyProduct> applyProductList = applyProductService.getByB2CProject(b2CProject);
		List<B2CApplyOther> applyOthers = b2cApplyOtherService.findByProject(b2CProject);
		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		int isAgreeCount = 0;
		for(int i=0; i<applyProductList.size(); i++){
			B2CSpecialApplyProduct specialApplyProduct = applyProductList.get(i);
			Integer isAgree = specialApplyProduct.getIsAgree();
			if(isAgree==null || isAgree==1){//销服总监全部同意了
				specialApplyProduct.setIsAgree(1);
				b2cSpecialApplyProductService.save(specialApplyProduct);
				isAgreeCount++;
			}else{
				CommRole doRole = specialApplyProduct.getCommUser().getCommRole();
				if(doRole!=null && !user.getCommRole().getId().equals(doRole.getId())){
					isAgreeCount++;
				}
			}
		}
		for(int i=0; i<applyOthers.size(); i++){
			B2CApplyOther applyOther = applyOthers.get(i);
			Integer isAgree = applyOther.getIsAgree();
			if(isAgree==null  || isAgree==1){
				applyOther.setIsAgree(1);
				b2cApplyOtherService.save(applyOther);
				isAgreeCount++;
			}else{
				CommRole doRole = applyOther.getCommUser().getCommRole();
				if(doRole!=null && !user.getCommRole().getId().equals(doRole.getId())){
					isAgreeCount++;
				}
			}
		}
		if(isAgreeCount==(applyProductList.size()+applyOthers.size())){
			isPass = 0; //通过
			//存最终数据
			for(int i=0; i<applyProductList.size(); i++){
				B2CSpecialApplyProduct specialApplyProduct = applyProductList.get(i);
				List<B2CSpecialApplyProductReplyLog> replyLogs = applyProductReplyLogRepository.findByApplyProduct(specialApplyProduct);
				if(replyLogs.size()>0){
					specialApplyProduct.setReplyLog(replyLogs.get(replyLogs.size()-1));
					b2cSpecialApplyProductService.save(specialApplyProduct);
				}
			}
			
/*			for(int i=0; i<applyOthers.size(); i++){
				B2CApplyOther applyOther = applyOthers.get(i);
				List<B2CApplyOtherReplyLog> replyLogs = applyOtherReplyLogRepository.findByApplyOther(applyOther);
				if(replyLogs.size()>0){
					applyOther.setReplyLog(replyLogs.get(replyLogs.size()-1));
					b2cApplyOtherService.save(applyOther);
				}
			}*/
		}else{
			isPass = 1;//不通过
		}
		request.setAttribute("b2CProject", b2CProject);
		request.setAttribute("applyProductList", applyProductList);
		changeProjectStep(request,b2CProject,isPass,0,Boolean.FALSE);
		List<B2CApproveLog> logList  = b2CProjectService.findApproveLogList(b2CProject);
		request.setAttribute("logList", logList);
		//如果当前步骤下一步为空，则说明是最后一步
/*		if(b2CProject.getProcessStep().getPnextId()!=null){
			//只有当前用户登录的角色等于该项目审核步骤中下一步的操作角色 时  页面上才出现审核的按钮	
			if(commService.findCurrentUserByRequest(request).getCommRole().getId().equals(b2CProject.getProcessStep().getPnextId().getCommRole().getId())){
				request.setAttribute(Constants.IS_SHOW_APPROVE, Boolean.TRUE);
			}
		}*/
		return "redirect:/b2c/project/list.html";
	}
	
	/**
	 * 改变审批步骤
	 * @param request
	 * @param project 
	 * @param isPass
	 * @param isEnd(可不传) 大区经理审批时，如果申请数据都同意了，则该流程结束
	 * @param flowType 当前提交的步骤(可不传)，1:大区经理改数据  2:销服主管填写意见   
	 */
	private  void changeProjectStep(HttpServletRequest request,B2CProject project,Integer isPass,Integer flowType,Boolean isEnd){
		//如果当前用户的角色不等于当前b2b项目中的下一个审核步骤的角色，则不能审核
		B2CApproveLog log = new B2CApproveLog();
		log.setIsPass(isPass==1?1:0);
		log.setStep(project.getProcessStep().getPnextId());
		log.setProject(project);
		log.setUser(commService.findCurrentUserByRequest(request));
		log.setApproveTime(new Date());
		log.setApproveMsg(request.getParameter("approveMsg"));
		if(isPass==0){
			if(flowType==1){
				if(isEnd){//可以结束流程
					ProcessStep step = project.getProcessStep().getPnextId();
					ProcessStep nextStep = processStepService.findById(step.getId());
					ProcessStep nextNextStep = processStepService.findById(nextStep.getPnextId().getId());
					project.setProcessStep(nextNextStep.getPnextId());
				}else{
					if(isSkip(project)){
						ProcessStep step = project.getProcessStep().getPnextId();
						ProcessStep nextStep = processStepService.findById(step.getId());
						project.setProcessStep(nextStep.getPnextId());
					}else{
						project.setProcessStep(project.getProcessStep().getPnextId());
					}
				}
				
			}else{
				project.setProcessStep(project.getProcessStep().getPnextId());
			}
		}else{
			project.setProcessStep(project.getProcessStep().getRnextId());	
		}
		b2CProjectService.saveProjectAndApproveLog(project,log);
		//改变一次状态，修改一次待办事宜总量
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		request.getSession().setAttribute(Constants.B2C_TO_DO_NUM, commService.countb2cToDo(commService.findCurrentUserByRequest(request).getCommRole(),agentList));
	}
	
	@RequestMapping(value="/b2c/modify/product",method=RequestMethod.POST)
	@ResponseBody
	public String modifyPro(HttpServletRequest request,Integer projectId,Integer applyId,String conditions,String content,BigDecimal replyPrice,Integer number,String replyAdvice,BigDecimal applyTotalPrice){
		ApiWarp apiWarp = new ApiWarp();
		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		B2CProject project = b2CProjectService.getB2CProjectById(projectId);
		B2CSpecialApplyProduct applyProduct = applyProductService.getByApplyProductId(applyId);
		
		B2CSpecialApplyProductReplyLog log = new B2CSpecialApplyProductReplyLog();
		log.setApplyProduct(applyProduct);
		log.setConditions(conditions);
		log.setContent(content);
		log.setReplyPrice(replyPrice);
		log.setNumber(number);
		log.setReplyAdvice(replyAdvice);
		log.setApplyTotalPrice(applyTotalPrice);
		log.setProject(project);
		log.setUser(user);
		log.setReplyTime(new Date());
		applyProductReplyLogRepository.save(log);
		
		applyProduct.setIsAgree(NOT_AGREE);
		applyProduct.setCommUser(user);
		applyProductService.save(applyProduct);
		
		return apiWarp.toJsonString();
	}
	
	@RequestMapping(value="/b2c/modify/other",method=RequestMethod.POST)
	@ResponseBody
	public String modify(HttpServletRequest request,Integer projectId,Integer applyId,Integer conditionId,String content,String approveSug,String approveRem,String replyAdvice){
		ApiWarp apiWarp = new ApiWarp();
		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
//		B2CProject project = b2CProjectService.getB2CProjectById(projectId);
		B2CApplyOther applyOther = applyOtherService.getB2CApplyOtherById(applyId);
		B2CCondition condition = conditionService.getConditionById(conditionId);
		if(applyOther!=null){
			applyOther.setIsAgree(NOT_AGREE);
			applyOther.setCommUser(user);
			applyOther.setCondition(condition);
			applyOther.setContent(content);
			if(user.getCommRole().getId()==CommRole.XIAOSHOUZONGJINGLI){
				applyOther.setApproveSug(approveSug);
				applyOther.setApproveRem(approveRem);
			}
			applyOtherService.save(applyOther);
		}
		
/*		B2CApplyOtherReplyLog log = new B2CApplyOtherReplyLog();
		log.setCondition(condition);
		log.setContent(content);
		log.setReplyAdvice(replyAdvice);
		log.setProject(project);
		log.setUser(user);
		log.setApplyOther(applyOther);
		log.setReplyTime(new Date());
		applyOtherReplyLogRepository.save(log);
		
		applyOther.setIsAgree(NOT_AGREE);
		applyOther.setCommUser(user);
		applyOtherService.save(applyOther);*/
		
		return apiWarp.toJsonString();
	}
	
	/**
	 * 大区经理同意修改后 操作
	 * @param request
	 * @param type
	 * @param applyId
	 * @return
	 */
	@RequestMapping(value="/b2c/project/productStatus",method=RequestMethod.POST)
	@ResponseBody
	public String changeApplyStatus(HttpServletRequest request,Integer type,Integer applyId){
		ApiWarp apiWarp = new ApiWarp();
		if(type==1){//特殊资源申请
			B2CSpecialApplyProduct specialApplyProduct = b2cSpecialApplyProductService.getByApplyProductId(applyId);
			if(specialApplyProduct==null){
				return apiWarp.addError("未找到匹配数据!").toJsonString();
			}
			specialApplyProduct.setIsAgree(1);
			b2cSpecialApplyProductService.save(specialApplyProduct);
		}else{
			B2CApplyOther applyOther = b2cApplyOtherService.getB2CApplyOtherById(applyId);
			if(applyOther==null){
				return apiWarp.addError("未找到匹配数据!").toJsonString();
			}
			applyOther.setIsAgree(1);
			b2cApplyOtherService.save(applyOther);
		}
		apiWarp.putData("操作成功!");
		return apiWarp.toJsonString();
	}
	
	
	@RequestMapping(value="/b2c/project/deleteApplyProduct",method=RequestMethod.POST)
	@ResponseBody
	public String deleteApplyProduct(HttpServletRequest request,Integer type,Integer id){
		ApiWarp apiWarp = new ApiWarp();
		if(type==1){//特殊资源申请
			B2CSpecialApplyProduct specialApplyProduct = b2cSpecialApplyProductService.getByApplyProductId(id);
			if(specialApplyProduct!=null){
				b2cSpecialApplyProductService.delete(specialApplyProduct);
			}
		}else{
			B2CApplyOther applyOther = b2cApplyOtherService.getB2CApplyOtherById(id);
			if(applyOther!=null){
				b2cApplyOtherService.delete(applyOther);
			}
		}
		apiWarp.putData("操作成功!");
		return apiWarp.toJsonString();
	}
	
	
	@RequestMapping(value="/b2c/project/appeal",method=RequestMethod.POST)
	@ResponseBody
	public String appeal(HttpServletRequest request,Integer type,Integer applyId,Integer projectId,String conditions,String content,BigDecimal replyPrice,Integer number,
			Integer conditionId){
		ApiWarp apiWarp = new ApiWarp();
		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		B2CProject b2cProject = b2CProjectService.getB2CProjectById(projectId);
		if(type==1){//特殊资源申请
			B2CSpecialApplyProduct specialApplyProduct = b2cSpecialApplyProductService.getByApplyProductId(applyId);
			B2CSpecialApplyProductReplyLog specialApplyProductReplyLog = new B2CSpecialApplyProductReplyLog();
			specialApplyProductReplyLog.setConditions(conditions);
			specialApplyProductReplyLog.setContent(content);
			specialApplyProductReplyLog.setReplyPrice(replyPrice);
			specialApplyProductReplyLog.setNumber(number);
			specialApplyProductReplyLog.setApplyProduct(specialApplyProduct);
			specialApplyProductReplyLog.setProject(b2cProject);
			specialApplyProductReplyLog.setReplyTime(new Date());
			applyProductReplyLogRepository.save(specialApplyProductReplyLog);
/*			
			if(specialApplyProduct==null){
				return apiWarp.addError("未找到匹配数据!").toJsonString();
			}
			specialApplyProduct.setIsAgree(1);
			b2cSpecialApplyProductService.save(specialApplyProduct);*/
		}else{
			B2CApplyOther applyOther = b2cApplyOtherService.getB2CApplyOtherById(applyId);
			B2CApplyOtherReplyLog applyOtherReplyLog = new B2CApplyOtherReplyLog();
			B2CCondition b2cCondition = b2cConditionService.getConditionById(conditionId);
			applyOtherReplyLog.setCondition(b2cCondition);
			applyOtherReplyLog.setContent(content);
			applyOtherReplyLog.setReplyTime(new Date());
			applyOtherReplyLog.setApplyOther(applyOther);
			applyOtherReplyLog.setProject(b2cProject);
			applyOtherReplyLog.setUser(user);
			applyOtherReplyLog.setType(type);
			applyOtherReplyLogRepository.save(applyOtherReplyLog);
	/*		
			if(applyOther==null){
				return apiWarp.addError("未找到匹配数据!").toJsonString();
			}
			applyOther.setIsAgree(1);
			b2cApplyOtherService.save(applyOther);*/
		}
		apiWarp.putData("操作成功!");
		return apiWarp.toJsonString();
	}
	
	/**
	 * 判断是否需要跳过   销服主管填写意见
	 * @param project
	 * @return
	 */
	private Boolean isSkip(B2CProject project){
		List<B2CSpecialApplyProduct> specialApplyProducts = b2cSpecialApplyProductService.getByB2CProject(project);
		boolean isAutoApprove = false;
		for (B2CSpecialApplyProduct specialApplyProduct : specialApplyProducts) {
			List<B2CSpecialApplyProductReplyLog> specialApplyProductReplyLogs = applyProductReplyLogRepository.findByApplyProduct(specialApplyProduct);
			if(specialApplyProductReplyLogs.size()>0){
				isAutoApprove = true;
				break;
			}
		}
		List<B2CApplyOther> applyOthers = b2cApplyOtherService.findByProject(project);
		for (B2CApplyOther b2cApplyOther : applyOthers) {
			List<B2CApplyOtherReplyLog> applyOtherReplyLogs = applyOtherReplyLogRepository.findByApplyOther(b2cApplyOther);
			if(applyOtherReplyLogs.size()>0){
				isAutoApprove = true;
				break;
			}
		}
		return isAutoApprove;
	}
	
	@RequestMapping("/b2c/project/exportPDF")
	public String exportPDF(HttpServletRequest request,HttpServletResponse response,String projectIds) throws IOException{
		try {
			String[] projectIdArr = projectIds.split(",");
			List<Integer> projectIdList = new ArrayList<Integer>();
			for(int i=0; i<projectIdArr.length; i++){
				projectIdList.add(Integer.parseInt(projectIdArr[i]));
			}
			request.setAttribute("projectIdArr", projectIdArr);
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			final PrintWriter rWriter = new PrintWriter(out);
			HttpServletResponseWrapper nres = new HttpServletResponseWrapper(response){
				@Override
				public PrintWriter getWriter() throws IOException {
					return rWriter;
				}
			};
			request.getRequestDispatcher("/WEB-INF/jsp/b2c/project_pdf.jsp").include(request, nres);
			rWriter.flush();
			String html = new String(out.toByteArray());
			OutputStream os = null;
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = "项目申请"+"_"+df.format(new Date())+".pdf";
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
		
		@RequestMapping("/b2c/project/export_inner")
		public String exportPDFInner(HttpServletRequest request,HttpServletResponse response,Integer projectId) throws IOException{
			CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
			B2CProject b2CProject = b2CProjectService.getB2CProjectById(projectId);
			List<Product> productlist = commService.findProductList(Product.NOT_DELETE);
			String product = productService.getProductNameArr(productlist);
			int sumApplyNumber = 0;
			BigDecimal sumApplyTotalPrice = new BigDecimal(0);
			List<B2CSpecialApplyProduct> applyProductList = applyProductService.getByB2CProject(b2CProject);
			for(B2CSpecialApplyProduct pdt:applyProductList){
				sumApplyNumber += pdt.getNumber();
				sumApplyTotalPrice = sumApplyTotalPrice.add(pdt.getApplyTotalPrice());
				List<B2CSpecialApplyProductReplyLog> replyLogList = applyProductReplyLogRepository.findByApplyProduct(pdt);
				for(int j=0; j<replyLogList.size(); j++){//合计申请数量和支持金总额
					B2CSpecialApplyProductReplyLog replyLog = replyLogList.get(j);
					sumApplyNumber += replyLog.getNumber();
					BigDecimal tempApplyTotalPrice = null;
					if(replyLog.getApplyTotalPrice()!=null){
						tempApplyTotalPrice = replyLog.getApplyTotalPrice();
					}else{
						tempApplyTotalPrice = new BigDecimal(0);
					}
					sumApplyTotalPrice = sumApplyTotalPrice.add(tempApplyTotalPrice);
				}
				pdt.setReplyLogList(replyLogList);
			}
			List<B2CApplyOther> xjApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 2);
			List<B2CApplyOther> ydApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 3);
			List<B2CApplyOther> jdApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 4);
			List<B2CApplyOther> netApplyOtherList = applyOtherService.findByProjectAndType(b2CProject, 5);
			for(B2CApplyOther applyOther:xjApplyOtherList){
				List<B2CApplyOtherReplyLog> replyLogList = applyOtherReplyLogRepository.findByApplyOther(applyOther);
				applyOther.setReplyLogList(replyLogList);
			}
			for(B2CApplyOther applyOther:ydApplyOtherList){
				List<B2CApplyOtherReplyLog> replyLogList = applyOtherReplyLogRepository.findByApplyOther(applyOther);
				applyOther.setReplyLogList(replyLogList);
			}
			for(B2CApplyOther applyOther:jdApplyOtherList){
				List<B2CApplyOtherReplyLog> replyLogList = applyOtherReplyLogRepository.findByApplyOther(applyOther);
				applyOther.setReplyLogList(replyLogList);
			}
			for(B2CApplyOther applyOther:netApplyOtherList){
				List<B2CApplyOtherReplyLog> replyLogList = applyOtherReplyLogRepository.findByApplyOther(applyOther);
				applyOther.setReplyLogList(replyLogList);
			}
			List<B2CCondition> xjList = conditionService.findByType(2);
			List<B2CCondition> ydList = conditionService.findByType(3);
			List<B2CCondition> jdList = conditionService.findByType(4);
			List<B2CCondition> netList = conditionService.findByType(5);
			List<B2CApproveLog > logList = b2cApprovalLogService.listByProject(b2CProject);
			List<B2CSpecialApplyProduct> new_applyProductList = new ArrayList<B2CSpecialApplyProduct>();
			for(int k=0; k<applyProductList.size(); k++){
				B2CSpecialApplyProduct specialApplyProduct = applyProductList.get(k);
				String prodectName = specialApplyProduct.getContent();
				Product applyProduct = productService.findByName(prodectName);
				if(product!=null){
					ProductPrice productPrice = productPriceService.findByProductAndDateMonth(applyProduct, DateUtils.getCurrentMonthFisrtDay());
					if(productPrice!=null){
						specialApplyProduct.setPoPrice(productPrice.getPoPrice());
						specialApplyProduct.setNetPrice(productPrice.getNetPrice());
						specialApplyProduct.setFinancePrice(productPrice.getFinancePrice());
						specialApplyProduct.setSyPrice(productPrice.getSyPrice());
					}
				}
				new_applyProductList.add(specialApplyProduct);
			}
			if(user.getCommRole().getId()==CommRole.XIAOSHOUZONGJINGLI){
				List<CommAgent> agentList = new ArrayList<CommAgent>();
				agentList.add(b2CProject.getAgent());
				List<B2CProject> closePorjects = b2CProjectService.listAllPorjectByAgentsAndStep(agentList);
				BigDecimal applyTotalPrice = new BigDecimal(0);
				for(int m=0; m<closePorjects.size(); m++){
					List<B2CSpecialApplyProduct> applyProductClose = applyProductService.getByB2CProject(closePorjects.get(m));
					for(int j=0; j<applyProductClose.size(); j++){
						B2CSpecialApplyProduct temProduct = applyProductClose.get(j);
						applyTotalPrice = applyTotalPrice.add(temProduct.getApplyTotalPrice());
					}
				}
				request.setAttribute("applyTotalPrice", applyTotalPrice.doubleValue());
			}
			
			request.setAttribute("sumApplyNumber", sumApplyNumber);
			request.setAttribute("sumApplyTotalPrice", sumApplyTotalPrice.doubleValue());
			request.setAttribute("b2CProject", b2CProject);
			request.setAttribute("product", product);
			request.setAttribute("applyProductList", new_applyProductList);
			request.setAttribute("xjApplyOtherList", xjApplyOtherList);
			request.setAttribute("ydApplyOtherList", ydApplyOtherList);
			request.setAttribute("jdApplyOtherList", jdApplyOtherList);
			request.setAttribute("netApplyOtherList", netApplyOtherList);
			request.setAttribute("xjList", xjList);
			request.setAttribute("ydList", ydList);
			request.setAttribute("jdList", jdList);
			request.setAttribute("netList", netList);
			request.setAttribute("logList", logList);
			request.setAttribute("XIAOFUZHUGUAN", CommRole.XIAOFUZHUGUAN);
			request.setAttribute("XIAOSHOUZONGJINGLI", CommRole.XIAOSHOUZONGJINGLI);
			request.setAttribute("AGENT", CommRole.DAILISHANG);
			return "/b2c/project_pdf_inner.jsp";
	}
}
