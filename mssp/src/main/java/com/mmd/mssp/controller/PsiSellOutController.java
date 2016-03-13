package com.mmd.mssp.controller;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ObjectUtils.Null;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mmd.mssp.comm.ApiWarp;
import com.mmd.mssp.comm.CommonConfig;
import com.mmd.mssp.comm.Constants;
import com.mmd.mssp.domain.ApproveTemplate;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommApprovalLog;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiInventory;
import com.mmd.mssp.domain.PsiReason;
import com.mmd.mssp.domain.PsiSellInEstimateByMonth;
import com.mmd.mssp.domain.PsiSellOut;
import com.mmd.mssp.domain.PsiSellOutUpdateApplyLog;
import com.mmd.mssp.domain.PsiSellOutUpdateApproveLog;
import com.mmd.mssp.domain.UpdateLogByAdmin;
import com.mmd.mssp.domain.vo.MailFormat;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.domain.vo.PsiReportVo;
import com.mmd.mssp.domain.vo.SellOutUpdateVo;
import com.mmd.mssp.domain.vo.SellOutVo;
import com.mmd.mssp.domain.vo.SizeTotalVo;
import com.mmd.mssp.service.CommAgentService;
import com.mmd.mssp.service.CommApprovalLogService;
import com.mmd.mssp.service.CommService;
import com.mmd.mssp.service.CommUserService;
import com.mmd.mssp.service.MailService;
import com.mmd.mssp.service.ProcessStepService;
import com.mmd.mssp.service.ProductService;
import com.mmd.mssp.service.PsiInventoryService;
import com.mmd.mssp.service.PsiReasonService;
import com.mmd.mssp.service.PsiReportService;
import com.mmd.mssp.service.PsiSellInEstimateByMonthService;
import com.mmd.mssp.service.PsiSellInService;
import com.mmd.mssp.service.PsiSellOutService;
import com.mmd.mssp.service.PsiSellOutUpdateApplyLogService;
import com.mmd.mssp.service.UpdateLogByAdminService;
import com.mmd.mssp.service.impl.MailServiceImpl;
import com.mmd.mssp.util.DateUtils;
import com.mmd.mssp.util.StringUtil;

@Controller
public class PsiSellOutController {
	
	private static final int IS_NOT_DELETE = 0;
	private static final int IS_DELETE = 1;
	private static final int PAGE_SIZE = 50;

	@Resource
	PsiSellOutService psiSellOutService;
	
	@Resource
	CommService commService;
	
	@Resource
	PsiInventoryService psiInventoryService;
	
	@Resource
	PsiReasonService psiReasonService;
	
	@Resource
	ProductService productService;
	
	@Resource
	PsiSellInService psiSellInService;
	
	@Resource
	PsiSellOutUpdateApplyLogService psiSellOutUpdateApplyLogService;
	
	@Resource
	CommAgentService commAgentService;
	
	@Resource
	MailServiceImpl mailServiceImpl;
	
	@Resource
	CommUserService commUserService;
	
	@Resource(name = "sellOutUpdateMailFormat")
	private MailFormat sellOutUpdateMailFormat;
	
	@Resource(name = "messageMailFormat")
	private MailFormat messageMailFormat;
	
	@Resource
	CommApprovalLogService commApprovalLogService;
	
	@Resource
	UpdateLogByAdminService updateLogByAdminService;
	
	@Resource
	MailService mailService;
	
	@Resource
	ProcessStepService processStepService;
	
	@Resource
	PsiSellInEstimateByMonthService sellInEstimateByMonthService;
	
	@Resource
	PsiReportService reportService;
	
	@RequestMapping("/psi")
	public String psi(HttpServletRequest request){
		//List<SellOutVo> voList = psiSellOutService.getSellOutList();
			List<PsiSellOutUpdateApplyLog> psiList = psiSellOutUpdateApplyLogService.findSelloutUpdateByRole(commService.findCurrentUserByRequest(request).getCommRole());
			request.setAttribute("psiList", psiList);
			request.setAttribute("datadate", commService.getYestday());
			return "/psi/todo_list.jsp";
	}
	
	@RequestMapping("/psi/sellout.html")
	public String sellOut(HttpServletRequest request){
		//List<SellOutVo> voList = psiSellOutService.getSellOutList();
		List<CommAgent> commAgents = commService.listCurrentAgent(request);
		request.setAttribute("commAgents", commAgents);
		request.setAttribute("datadate", commService.getYestday());
		return "/psi/sell_out.jsp";
	}
	
	@RequestMapping("/psi/selloutupdate.html")
	public String sellOutUpdate(HttpServletRequest request,Integer agentId) {
//		CommUser user = (CommUser) request.getSession().getAttribute(Constants.USER_KEY);
		Date startDate = new Date();
		Date endDate = startDate;
		List<Product> productList = productService.listAll(false);
		List<CommAgent> commAgents = commService.listCurrentAgent(request);
		CommAgent agent = null;
		if(commAgents.size()>0){
			agent = commAgents.get(0);
		}
		if(agentId != null){
			agent = commAgentService.getById(agentId);
		}
		List<PsiSellOutUpdateApplyLog> list = psiSellOutUpdateApplyLogService.findByCommAgent(agent);
		request.setAttribute("list", list);
		//测试期间该功能每天都可以用
//		boolean enabled = commService.isDateBefore(3);

//		List<PsiSellOutUpdateApplyLog> list = psiSellOutUpdateApplyLogService.findByCommAgent();
//		request.setAttribute("list", list);
		request.setAttribute("LINGSHOUZHUANYUAN", CommRole.LINGSHOUZHUANYUAN);
		request.setAttribute("DAQUJINGLI", CommRole.DAQUJINGLI);
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("agent", agent);
		request.setAttribute("productList", productList);
		request.setAttribute("commAgents", commAgents);
		request.setAttribute("enabled", true);
		return "/psi/sell_out_update.jsp";
	}
	
	@RequestMapping("/psi/sellOutUpdate/detail.html")
	public String sellOutUpdateDetail(HttpServletRequest request,Integer applyLogId) {
		PsiSellOutUpdateApplyLog psiSellOutUpdateApplyLog = psiSellOutUpdateApplyLogService.getById(applyLogId);
		CommApprovalLog commApprovalLog = null;
		if(psiSellOutUpdateApplyLog!=null){
			commApprovalLog = commApprovalLogService.getByDataId(psiSellOutUpdateApplyLog.getId());
		}
		request.setAttribute("applyLog", psiSellOutUpdateApplyLog);
		request.setAttribute("commApprovalLog", commApprovalLog);

		if(psiSellOutUpdateApplyLog.getStep().getPnextId()!=null&&(psiSellOutUpdateApplyLog.getStep().getPnextId().getCommRole().getId().equals(commService.findCurrentUserByRequest(request).getCommRole().getId()))){
			request.setAttribute(Constants.IS_SHOW_APPROVE, true);
		}
		List<PsiSellOutUpdateApproveLog> approveList = psiSellOutUpdateApplyLogService.findApproveList(psiSellOutUpdateApplyLog);
		request.setAttribute("approveList", approveList);
		return "/psi/sell_out_update_detail.jsp";
	}

	@RequestMapping("/psi/selloutupdate/apply.html")
	public String sellOutUpdateApply(HttpServletRequest request,Integer applyLogId,Integer userId) {
		CommUser commUser = (CommUser) request.getSession().getAttribute(Constants.USER_KEY);
		List<CommAgent> commAgents = commService.listCurrentAgent(request);
		if(applyLogId!=null){
			commUser = commUserService.getCommUserById(userId);
			PsiSellOutUpdateApplyLog psiSellOutUpdateApplyLog = psiSellOutUpdateApplyLogService.getById(applyLogId);
			request.setAttribute("applyLog", psiSellOutUpdateApplyLog);
		}
		List<PsiReason> psiReasonsList = psiReasonService
				.listAll(Boolean.FALSE);
		List<Product> productList = commService.findProductList(0);
		request.setAttribute("commAgents", commAgents);
		request.setAttribute("psiReasonsList", psiReasonsList);
		request.setAttribute("productList", productList);
		request.setAttribute("commUser", commUser);
		return "/psi/sell_out_update_apply.jsp";
	}
	
	
	@RequestMapping(value="/psi/sellOutUpdate/search")
	public String search(HttpServletRequest request,Integer page,String startDate,String endDate,Integer channelType,Integer[] productId,Integer[] agentId) throws Exception{
				
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		List<Product> productList = productService.listAll(false);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse(startDate);
		Date date2 = sdf.parse(endDate);
		Paging<PsiReportVo> paging = reportService.findByDateChannelTypeProduct(date1, date2, channelType, productId, agentId,page);
		Calendar ca = Calendar.getInstance();
		ca.setTime(date1);
		request.setAttribute("LINGSHOUZHUANYUAN", CommRole.LINGSHOUZHUANYUAN);
		request.setAttribute("DAQUJINGLI", CommRole.DAQUJINGLI);
		request.setAttribute("pagelist", paging);
		request.setAttribute("productList", productList);
		request.setAttribute("productId", productId);
		request.setAttribute("startDate", date1);
		request.setAttribute("endDate", date2);
		request.setAttribute("channelType", channelType);
		request.setAttribute("commAgents", agentList);
		request.setAttribute("agentId", agentId);
		return "/psi/sell_out_update.jsp";
	}
	
	@RequestMapping("admin/psi/selloutupdate/list.html")
	public String adminSellOutUpdateList(HttpServletRequest request,Integer applyLogId,Integer page) {
		List<CommAgent> list = commAgentService.fandAll(Boolean.FALSE);
		Paging<PsiSellOutUpdateApplyLog> paging = psiSellOutUpdateApplyLogService.fandAll(applyLogId,page, PAGE_SIZE);
		List<PsiReason> psiReasonsList = psiReasonService
				.listAll(Boolean.FALSE);
		ApproveTemplate temp = commService.findTmpeType(CommonConfig.getTEMP_PSI_SELLOUTUPDATE());
		List<ProcessStep> prStepList = processStepService.findByTemp(temp);
		request.setAttribute("psiReasonsList", psiReasonsList);
		request.setAttribute("prStepList", prStepList);
		request.setAttribute("agentList", list);
		request.setAttribute("paging", paging);
		return "/admin/psi/sell_out_update_list.jsp";
	}
	
	@RequestMapping("admin/psi/sellOutUpdate/detail.html")
	public String adminSellOutUpdateDetail(HttpServletRequest request,Integer applyLogId) {
		PsiSellOutUpdateApplyLog psiSellOutUpdateApplyLog = psiSellOutUpdateApplyLogService.getById(applyLogId);
		CommApprovalLog commApprovalLog = null;
		if(psiSellOutUpdateApplyLog!=null){
			commApprovalLog = commApprovalLogService.getByDataId(psiSellOutUpdateApplyLog.getId());
			List<PsiSellOutUpdateApproveLog> approveList = psiSellOutUpdateApplyLogService.findApproveList(psiSellOutUpdateApplyLog);
			List<UpdateLogByAdmin> adminLog = updateLogByAdminService.findByPsiSellOutUpdateApplyLog(psiSellOutUpdateApplyLog);
			request.setAttribute("approveList", approveList);
			request.setAttribute("adminLog", adminLog);
		}
		request.setAttribute("applyLog", psiSellOutUpdateApplyLog);
		request.setAttribute("commApprovalLog", commApprovalLog);
		return "/admin/psi/sell_out_update_detail.jsp";
	}
	
	
	@RequestMapping(value="/psi/selloutupdate/apply/post",method=RequestMethod.POST)
	public String sellOutUpdateApplyPost(HttpServletRequest request,Integer agentId,PsiSellOutUpdateApplyLog psiSellOutUpdateApplyLog,Integer productId,Integer psiReasonId,Integer psiInventorValuey,Integer currentInventoryValue) {
		CommUser commUser = (CommUser) request.getSession().getAttribute(Constants.USER_KEY);
		CommAgent agent = commAgentService.getById(agentId);
		Product product = productService.getProductById(productId);
		PsiReason psiReason = psiReasonService.getPsiReasonById(psiReasonId);
		psiSellOutUpdateApplyLog.setCommAgent(agent);
		psiSellOutUpdateApplyLog.setProduct(product);
		psiSellOutUpdateApplyLog.setLlInventory(psiInventorValuey);//上上月期初库存
		psiSellOutUpdateApplyLog.setlInventory(currentInventoryValue);//上月期初库存
		psiSellOutUpdateApplyLog.setPsiReason(psiReason);
		psiSellOutUpdateApplyLog.setApplyTime(new Date());
		ApproveTemplate temp = commService.findTmpeType(CommonConfig.getTEMP_PSI_SELLOUTUPDATE());
		psiSellOutUpdateApplyLog.setTemp(temp);
		psiSellOutUpdateApplyLog.setStep(commService.findFisrtStep(temp));
		psiSellOutUpdateApplyLog.setStatus(commService.findFisrtStep(temp).getId());
		PsiSellOutUpdateApplyLog newEntity = psiSellOutUpdateApplyLogService.save(psiSellOutUpdateApplyLog);
		/*
		 * //固定用户 测试
		 */
/*		CommUser commUser2 = commUserService.getCommUserById(8);
		CommAgent commAgent = commService.findCurrentAgent(request);
		if(StringUtil.isEmail(commUser2.getEmail())){
			Map map = new HashMap();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
			String[] arr = df.format(new Date()).split("\\|");
			map.put("date", arr[0]);
			map.put("time", arr[1]);
			map.put("userName", commUser2.getRealName());
			map.put("agentName", commAgent.getIrName());
			map.put("applyLogId", Integer.toString(newEntity.getId()));
			map.put("userId", Integer.toString(commUser2.getId()));
			mailServiceImpl.sendHTMLMail(sellOutUpdateMailFormat, commUser2.getEmail(),null, map);
		}*/
		//邮件通知
		String emailUserIds = psiSellOutUpdateApplyLog.getStep().getEmailUserIds();
		if(!StringUtils.isBlank(emailUserIds)){
			String[] userIds = emailUserIds.split(",");
			for(int i=0; i<userIds.length; i++){
				if(!StringUtils.isBlank(userIds[i])){
					CommUser recCommUser = commUserService.getCommUserById(Integer.parseInt(userIds[i]));
					if(StringUtil.isEmail(recCommUser.getEmail())){
						Map map = new HashMap();
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
						String[] arr = df.format(new Date()).split("\\|");
						map.put("date", arr[0]);
						map.put("time", arr[1]);
						map.put("userName", recCommUser.getRealName());
						map.put("agentName", agent.getIrName());
						map.put("applyLogId", Integer.toString(newEntity.getId()));
						mailService.sendHTMLMail(sellOutUpdateMailFormat, recCommUser.getEmail(),null, map);
					}
				}
			}
		}
		return "redirect:/psi/selloutupdate.html";
	}
	
	@RequestMapping("/psi/sellout/query")
	public String sellOutQueryByChannelType(HttpServletRequest request,String channelType,Integer agentId){
		//CommAgent agent = commService.findCurrentAgent(request);
		CommAgent agent = commAgentService.getById(agentId);
		List<SellOutVo> voList = psiSellOutService.getSellOutList(channelType,agent);
		List<CommAgent> commAgents = commService.listCurrentAgent(request);
		PsiSellInEstimateByMonth sellInEstimateByMonth = sellInEstimateByMonthService.findByAgentAndDateMonth(agent, DateUtils.getCurrentMonthFisrtDay());
		boolean isUpload = true;
		if(sellInEstimateByMonth==null){
			isUpload = false;
		}
		int tmSizeSellin = 0,tmSizeSellout = 0,tmSizeInventory = 0;
		for (SellOutVo vo : voList) {
			if("中尺寸".equals(vo.getProduct().getSize2())){
				tmSizeSellin = tmSizeSellin + vo.getTotalSellin();
				tmSizeSellout = tmSizeSellout + vo.getTotalSellout();
				tmSizeInventory = tmSizeInventory + vo.getCurrentInventory();
			}
		}
		int tbSizeSellin = 0,tbSizeSellout = 0,tbSizeInventory = 0;
		for (SellOutVo vo : voList) {
			if("大尺寸".equals(vo.getProduct().getSize2())){
				tbSizeSellin = tbSizeSellin + vo.getTotalSellin();
				tbSizeSellout = tbSizeSellout + vo.getTotalSellout();
				tbSizeInventory = tbSizeInventory + vo.getCurrentInventory();
			}
		}
		int tebSizeSellin = 0,tebSizeSellout = 0,tebSizeInventory = 0;
		for (SellOutVo vo : voList) {
			if("超大尺寸".equals(vo.getProduct().getSize2())){
				tebSizeSellin = tebSizeSellin + vo.getTotalSellin();
				tebSizeSellout = tebSizeSellout + vo.getTotalSellout();
				tebSizeInventory = tebSizeInventory + vo.getCurrentInventory();
			}
		}
		request.setAttribute("isUpload", isUpload);
		request.setAttribute("commAgents", commAgents);
		request.setAttribute("list", voList);
		request.setAttribute("agentId", agentId);
		request.setAttribute("channelType", channelType);
		request.setAttribute("datadate", commService.getYestday());
		request.setAttribute("sellInEstimateByMonth", sellInEstimateByMonth);
		request.setAttribute("tmSizeSellin", tmSizeSellin);
		request.setAttribute("tmSizeSellout", tmSizeSellout);
		request.setAttribute("tmSizeInventory", tmSizeInventory);
		request.setAttribute("tbSizeSellin", tbSizeSellin);
		request.setAttribute("tbSizeSellout", tbSizeSellout);
		request.setAttribute("tbSizeInventory", tbSizeInventory);
		request.setAttribute("tebSizeSellin", tebSizeSellin);
		request.setAttribute("tebSizeSellout", tebSizeSellout);
		request.setAttribute("tebSizeInventory", tebSizeInventory);
		return "/psi/sell_out.jsp";
	}
	

	@RequestMapping("/psi/selloutupdate/query")
	public String sellOutUpdateQueryByDate(HttpServletRequest request,Integer agentId) throws ParseException{
		List<CommAgent> commAgents = commService.listCurrentAgent(request);
		CommAgent agent = commAgentService.getById(agentId);
		List<PsiSellOutUpdateApplyLog> list = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date applyDate = null;
		if (!"".equals(request.getParameter("applyDate"))) {
			applyDate = sdf.parse(request.getParameter("applyDate"));
		}
		if(applyDate==null){
			list = psiSellOutUpdateApplyLogService.findByCommAgent(agent);
//			list = psiSellOutUpdateApplyLogService.findByCommAgent();
		}else{
			list = psiSellOutUpdateApplyLogService.findByApplyDate(applyDate, agent);
		}
		//测试期间该功能每天都可以用
//		boolean enabled = commService.isDateBefore(3);
		request.setAttribute("enabled", true);
		request.setAttribute("commAgents", commAgents);
		request.setAttribute("list", list);
		request.setAttribute("agent", agent);
		request.setAttribute("applyDate", request.getParameter("applyDate"));
		return "/psi/sell_out_update.jsp";
	}
	
	@RequestMapping("/admin/psi/sellOutApply/search")
	public String adminSellOutApplySearch(HttpServletRequest request,Integer agentId,Integer approveStatus,String productName,Integer page){
		List<CommAgent> list = commAgentService.fandAll(Boolean.FALSE);
		ApproveTemplate temp = commService.findTmpeType(CommonConfig.getTEMP_PSI_SELLOUTUPDATE());
		List<ProcessStep> prStepList = processStepService.findByTemp(temp);
		Integer status = null;
		if(approveStatus!=null){
			ProcessStep processStep = processStepService.findById(approveStatus);
			if(processStep==null){
				status = approveStatus;
			}else{
				status = processStep.getRnextId().getId();
			}
		}
		Paging<PsiSellOutUpdateApplyLog> paging = psiSellOutUpdateApplyLogService.fandByAgentAndStatusAndProduc(agentId,status,productName,page, PAGE_SIZE);
		List<PsiReason> psiReasonsList = psiReasonService
				.listAll(Boolean.FALSE);
		request.setAttribute("prStepList", prStepList);
		request.setAttribute("psiReasonsList", psiReasonsList);
		request.setAttribute("agentList", list);
		request.setAttribute("paging", paging);
		request.setAttribute("agentId", agentId);
		request.setAttribute("productName", productName);
		request.setAttribute("approveStatus", approveStatus);
		return "/admin/psi/sell_out_update_list.jsp";
	}
	
	@RequestMapping(value="/psi/sellout/input",method=RequestMethod.POST)
	@ResponseBody
	public String sellOutInput(HttpServletRequest request,String inputVol,String channelType,Integer agentId){
		ApiWarp apiWarp = new ApiWarp();
		try {
			JSONObject jsonObject = JSON.parseObject(inputVol);
			List<PsiSellOut> list = new ArrayList<PsiSellOut>();
//			CommAgent commAgent = commService.findCurrentAgent(request);
			CommAgent commAgent = commAgentService.getById(agentId);
			 for (Entry<String, Object> entry : jsonObject.entrySet()) {
				  PsiSellOut bean = psiSellOutService.findByProductAndAgentAndCreatetimeAndChannelType(commService.findProductbyProductName(entry.getKey()),commAgent,DateUtils.getDate(new Date()),Integer.parseInt(channelType));
				  if(bean!=null){
					  bean.setSellInOutlvolume(Integer.parseInt(entry.getValue().toString()));
					  list.add(bean);
				  }else{
					  bean = new PsiSellOut();
					  bean.setProduct(commService.findProductbyProductName(entry.getKey()));
					  bean.setSellInOutlvolume(Integer.parseInt(entry.getValue().toString()));
					  bean.setCreatetime(DateUtils.getDate(new Date()));
					  bean.setActiontime(DateUtils.getDate(commService.getYestday()));
					  bean.setChannelType(Integer.parseInt(channelType));
					  bean.setAgent(commService.findCurrentAgent(request));
					  bean.setSellOutType(0);
					  list.add(bean);
				  }
		     }
			 psiSellOutService.addBatchPsiSellOut(list);
		} catch (Exception e) {
			apiWarp.addError("sell out录入发生异常，异常信息："+e.getMessage());
			e.printStackTrace();
		}
		return apiWarp.toJsonString();
	}
	
	@RequestMapping(value="/psi/selloutupdate/apply/get",method=RequestMethod.POST)
	@ResponseBody
	public String getSelloutUpdateInfo(HttpServletRequest request,Integer channelType,Integer productId,Integer agentId) {
		ApiWarp apiWarp = new ApiWarp();
		CommAgent commAgent = commAgentService.getById(agentId);
		Product product = productService.getProductById(productId);
		if(product==null){
			return apiWarp.addError("未找到对应型号!productId="+productId).toJsonString();
		}
		PsiInventory psiInventory = psiInventoryService.findByProductAndCommAgentAndChannelType(product, commAgent, channelType, commService.getLastLastMonthLastDay());//上上月期初库存
		Integer sumSellInLastMonth = psiSellInService.sumSellInLastMonth(commService.getLastMonthFirstDay(), commService.getCurrentMonthFisrtDay(), commAgent, product, channelType);//上月累计sell in
		Integer sumSellOutLastMonth = psiSellOutService.sumSellOutLastMonth(commService.getLastMonthFirstDay(), commService.getCurrentMonthFisrtDay(), product, commAgent, channelType);//上月累计sell out
		PsiInventory currentInventory = psiInventoryService.findByProductAndCommAgentAndChannelType(product, commAgent, channelType, commService.getLastMonthLastDay()); //上月最后一天 库存
		SellOutUpdateVo sellOutUpdateVo = new SellOutUpdateVo();
		if(psiInventory==null){
			psiInventory = new PsiInventory();
			psiInventory.setInventoryVolume(0);
		}
		if(currentInventory==null){
			currentInventory = new PsiInventory();
			currentInventory.setInventoryVolume(0);
		}
		sellOutUpdateVo.setPsiInventory(psiInventory);
		sellOutUpdateVo.setSumSellInLastMonth(sumSellInLastMonth);
		sellOutUpdateVo.setSumSellOutLastMonth(sumSellOutLastMonth);
		sellOutUpdateVo.setCurrentInventory(currentInventory);
		return apiWarp.putData(sellOutUpdateVo).toJsonString();
	}
	
	/**
	 * 经理审批sellout 修改申请
	 * @param request
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("/psi/selloutUpdateApply/approval")
	@ResponseBody
	public String selloutUpdateApplyApproval(HttpServletRequest request,Integer id,Integer status,Integer sellOutGAP){
		ApiWarp apiWarp = new ApiWarp();
		PsiSellOut psiSellOut = null;
		PsiInventory currentInventory = null;
		PsiSellOutUpdateApplyLog psiSellOutUpdateApplyLog = psiSellOutUpdateApplyLogService.getById(id);
		CommUser commUser = (CommUser) request.getSession().getAttribute(Constants.USER_KEY);
		if(psiSellOutUpdateApplyLog==null){
			return apiWarp.addError("未找到对应申请记录!id="+id).toJsonString();
		}
		if(psiSellOutUpdateApplyLog.getStatus()!=0){
			return apiWarp.addError("申请已审批，不能重复审批!id="+psiSellOutUpdateApplyLog.getId()).toJsonString();
		}
		//保存审批log
		CommApprovalLog commApprovalLog = new CommApprovalLog();
		commApprovalLog.setDataType(0);
		commApprovalLog.setDataId(psiSellOutUpdateApplyLog.getId());
		commApprovalLog.setCommUser(commUser);
		commApprovalLog.setApprovalStatus(status);
		commApprovalLog.setApprovalTime(new Date());
		//修改申请的审批状态
		psiSellOutUpdateApplyLog.setStatus(status);
		if(status==1){//同意修改
			//sellout 表中增加一条记录
			psiSellOut = new PsiSellOut();
			psiSellOut.setChannelType(psiSellOutUpdateApplyLog.getChannelType());
			psiSellOut.setAgent(psiSellOutUpdateApplyLog.getCommAgent());
			psiSellOut.setProduct(psiSellOutUpdateApplyLog.getProduct());
			psiSellOut.setSellInOutlvolume(sellOutGAP);//GAP
			psiSellOut.setSellOutType(-1);//sellout 修改
			psiSellOut.setCreatetime(commService.getLastMonthLastDay());
			psiSellOut.setActiontime(commService.getLastMonthLastDay());
			
			//修改期初库存
			currentInventory = psiInventoryService.findByProductAndCommAgentAndChannelType(psiSellOutUpdateApplyLog.getProduct(), psiSellOutUpdateApplyLog.getCommAgent(), psiSellOutUpdateApplyLog.getChannelType(), commService.getLastMonthLastDay()); //上月期初库存
			currentInventory.setInventoryVolume(currentInventory.getInventoryVolume()-sellOutGAP);
			/*
			 * 发邮件通知管理员数据 有改动，此处固定用户，用于测试，发送给psiadmin
			 */
			CommUser commUser2 = commUserService.getCommUserById(2);
			CommAgent commAgent = psiSellOutUpdateApplyLog.getCommAgent();
			if(StringUtil.isEmail(commUser2.getEmail())){
				Map map = new HashMap();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
				String[] arr = df.format(new Date()).split("\\|");
				map.put("date", arr[0]);
				map.put("userName", commUser2.getRealName());
				map.put("message", String.format("%s %s %s代理商的sellOut值发生改动,原值[%s],新值[%s],sellOutGAP:[%s]",arr[0],arr[1], commAgent.getIrName(), psiSellOutUpdateApplyLog.getSellOutlVolume(), psiSellOutUpdateApplyLog.getRealSellOut(),psiSellOutUpdateApplyLog.getSellOutGap()));
				mailServiceImpl.sendHTMLMail(messageMailFormat, commUser2.getEmail(),null, map);
			}
		}
		psiSellOutService.saveApprovalLog(psiSellOutUpdateApplyLog, commApprovalLog, psiSellOut, null,currentInventory);
		return apiWarp.putData(psiSellOutUpdateApplyLog).toJsonString();
	}
	
	/**
	 * 管理员直接修改sellout
	 * @param request
	 * @param appLogId
	 * @param realSellOut
	 * @return
	 */
	@RequestMapping("admin/psi/sellOut/modify")
	public String adminSelloutUpdateApplyApproval(HttpServletRequest request,Integer appLogId,Integer realSellOut,Integer sellOutGAP){
		PsiSellOutUpdateApplyLog psiSellOutUpdateApplyLog = psiSellOutUpdateApplyLogService.getById(appLogId);
		CommUser commUser = (CommUser) request.getSession().getAttribute(Constants.USER_KEY);
		ApproveTemplate temp = commService.findTmpeType(CommonConfig.getTEMP_PSI_SELLOUTUPDATE());
		List<ProcessStep> prStepList = processStepService.findByTemp(temp);
		//保存管理员修改log
		UpdateLogByAdmin updateLogByAdmin = new UpdateLogByAdmin();
		updateLogByAdmin.setCommUser(commUser);
		updateLogByAdmin.setChangeVolume(realSellOut);
		updateLogByAdmin.setCreatetime(new Date());
		updateLogByAdmin.setPsiSellOutUpdateApplyLog(psiSellOutUpdateApplyLog);
		//保存审批log
		CommApprovalLog commApprovalLog = new CommApprovalLog();
		commApprovalLog.setDataType(0);
		commApprovalLog.setDataId(psiSellOutUpdateApplyLog.getId());
		commApprovalLog.setCommUser(commUser);
		commApprovalLog.setApprovalStatus(1);
		commApprovalLog.setApprovalTime(new Date());
		//sellout 表中增加一条记录
		PsiSellOut psiSellOut = new PsiSellOut();
		psiSellOut.setChannelType(psiSellOutUpdateApplyLog.getChannelType());
		psiSellOut.setAgent(psiSellOutUpdateApplyLog.getCommAgent());
		psiSellOut.setProduct(psiSellOutUpdateApplyLog.getProduct());
		psiSellOut.setSellInOutlvolume(sellOutGAP);//GAP
		psiSellOut.setSellOutType(-1);//sellout 修改
		psiSellOut.setCreatetime(commService.getLastMonthLastDay());
		psiSellOut.setActiontime(commService.getLastMonthLastDay());
		//修改申请的审批状态
		psiSellOutUpdateApplyLog.setStep(prStepList.get(prStepList.size()-1));
		psiSellOutUpdateApplyLog.setStatus(0);
		psiSellOutUpdateApplyLog.setRealSellOut(realSellOut);
		psiSellOutUpdateApplyLog.setSellOutGap(sellOutGAP);
		
		ProcessStep step = processStepService.findById(psiSellOutUpdateApplyLog.getStep().getId());
		if(step.getPnextId()==null){
			psiSellOutUpdateApplyLog.setStep(processStepService.findById(Constants.APPROVE_FINSHED));
		}
		
		//修改期初库存
		PsiInventory currentInventory = psiInventoryService.findByProductAndCommAgentAndChannelType(psiSellOutUpdateApplyLog.getProduct(), psiSellOutUpdateApplyLog.getCommAgent(), psiSellOutUpdateApplyLog.getChannelType(), commService.getLastMonthLastDay()); //上月期初库存
		currentInventory.setInventoryVolume(currentInventory.getInventoryVolume()-sellOutGAP);
		psiSellOutService.saveApprovalLog(psiSellOutUpdateApplyLog, commApprovalLog, psiSellOut, updateLogByAdmin,currentInventory);
		return "redirect:/admin/psi/selloutupdate/list.html";
	}
	
	
	@RequestMapping(value="/psi/selloutupdate/flow",method=RequestMethod.POST)
	public String flowStep(HttpServletRequest request,Integer logId){
		Integer isPass = Integer.parseInt(request.getParameter("isPass"));
		PsiSellOutUpdateApplyLog psiSellOutUpdateApplyLog = psiSellOutUpdateApplyLogService.getById(logId);
		changeProjectStep(request, psiSellOutUpdateApplyLog, isPass);
		List<PsiSellOutUpdateApproveLog> approveList = psiSellOutUpdateApplyLogService.findApproveList(psiSellOutUpdateApplyLog);
		request.setAttribute("approveList", approveList);
		request.setAttribute("applyLog", psiSellOutUpdateApplyLog);
		return "/psi/sell_out_update_detail.jsp";
	}
	
	private  void changeProjectStep(HttpServletRequest request,PsiSellOutUpdateApplyLog applyLog,Integer isPass){
		//如果当前用户的角色不等于当前b2b项目中的下一个审核步骤的角色，则不能审核
		PsiSellOutUpdateApproveLog approveLog = new PsiSellOutUpdateApproveLog();
		approveLog.setIsPass(isPass==1?1:0);
		approveLog.setStep(applyLog.getStep().getPnextId());
		approveLog.setUpdateApply(applyLog);
		approveLog.setUser(commService.findCurrentUserByRequest(request));
		approveLog.setApproveTime(new Date());
		approveLog.setApproveMsg(request.getParameter("approveMsg"));
		if(isPass==0){
			applyLog.setStep(applyLog.getStep().getPnextId());
			applyLog.setStatus(applyLog.getStep().getId());
			
		}else{
			applyLog.setStatus(applyLog.getStep().getId());
			applyLog.setStep(applyLog.getStep().getRnextId());
		}
		
		//邮件通知
		/*String emailUserIds = log.getStep().getEmailUserIds();
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
						//map.put("agentName", log.getAgent().getIrName());
						map.put("projectId", Integer.toString(log.getId()));
						map.put("flowType", "1");
						//mailService.sendHTMLMail(B2BProjectApplyMailFormat, commUser.getEmail(),null, map);
					}
				}
			}
		}*/
		psiSellOutUpdateApplyLogService.saveLogAndApproveLog(applyLog,approveLog);
		//改变一次状态，修改一次待办事宜总量
		request.getSession().setAttribute(Constants.PSI_TO_DO_NUM, commService.countPsiToDo(commService.findCurrentUserByRequest(request).getCommRole()));
	}
	
	
	@RequestMapping(value="/psi/sellOutUpdate/save",method=RequestMethod.POST)
	@ResponseBody
	public String saveSellOutUpdate(HttpServletRequest request,Integer channelType,Integer productId,Integer agentId,Integer GAP) {
		ApiWarp apiWarp = new ApiWarp();
		CommAgent commAgent = commAgentService.getById(agentId);
		Product product = productService.getProductById(productId);
		Date nowDate = new Date();
		Date lastWeekLastWeekDay = null;
		int weekDay = Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK);//当前是周几
		if(weekDay==1){
			lastWeekLastWeekDay = new Date(nowDate.getTime() - 7 * 24 * 60 * 60 * 1000);
		}else{
			lastWeekLastWeekDay = new Date(nowDate.getTime() - (weekDay-1) * 24 * 60 * 60 * 1000);
		}
		if(!commService.isWorkday(lastWeekLastWeekDay)){
			lastWeekLastWeekDay = commService.getFisrtDayBefore(lastWeekLastWeekDay);
		}
		Calendar calNow = Calendar.getInstance();
		calNow.setTime(nowDate);
		int nowMonth = calNow.get(Calendar.MONTH);
		Calendar cal = Calendar.getInstance();
		cal.setTime(lastWeekLastWeekDay);
		int lastmonth = cal.get(Calendar.MONTH);
		PsiSellOut psiSellOut = new PsiSellOut();
		psiSellOut.setChannelType(channelType);
		psiSellOut.setAgent(commAgent);
		psiSellOut.setProduct(product);
		psiSellOut.setSellInOutlvolume(GAP);//GAP
		psiSellOut.setSellOutType(-1);//sellout 修改
		if(nowMonth==lastmonth){//是同一月
			psiSellOut.setCreatetime(lastWeekLastWeekDay);
			psiSellOut.setActiontime(lastWeekLastWeekDay);
			psiSellOutService.save(psiSellOut);
		}else{//不是同一月
			psiSellOut.setCreatetime(commService.getLastMonthLastDay());
			psiSellOut.setActiontime(commService.getLastMonthLastDay());
			//修改期初库存
			PsiInventory currentInventory = psiInventoryService.findByProductAndCommAgentAndChannelType(product, commAgent, channelType, commService.getLastMonthLastDay()); //上月期初库存
			currentInventory.setInventoryVolume(currentInventory.getInventoryVolume()-GAP);
			psiSellOutService.saveSellOutAndPsiInventory(psiSellOut, currentInventory);
		}
		return apiWarp.putData(psiSellOut).toJsonString();
	}
}
