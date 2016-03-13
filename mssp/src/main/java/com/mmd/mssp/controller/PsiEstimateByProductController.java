package com.mmd.mssp.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mmd.mssp.comm.Constants;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.PsiEstimateByProduct;
import com.mmd.mssp.domain.PsiSellInEstimateTemplate;
import com.mmd.mssp.repository.PsiSellInEstimateTemplateRepository;
import com.mmd.mssp.service.CommAgentService;
import com.mmd.mssp.service.CommService;
import com.mmd.mssp.service.ProductService;
import com.mmd.mssp.service.PsiEstimateService;
import com.mmd.mssp.util.DateUtils;

@Controller
public class PsiEstimateByProductController {

	private static final int IS_NOT_DELETE = 0;
	private static final int IS_DELETE = 1;
	private static final int PAGE_SIZE = 50;
	
	@Resource
	ProductService productService;
	@Resource
	PsiEstimateService psiEstimateService;
	@Resource
	CommAgentService commAgentService;
	@Resource
	CommService commService;
	@Resource
	PsiSellInEstimateTemplateRepository sellInEstimateTemplateRepository;
	
	@RequestMapping("/psi/estimate.html")
	public String estimate(HttpServletRequest request,Integer agentId) throws Exception{
		boolean isSubmit = true;
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		int month = Calendar.MONTH;
		Date dateMonth = DateUtils.getCurrentMonthFisrtDay();
		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		if(user.getCommRole().getId()==CommRole.DAILISHANG){//3为代理商
			isSubmit = psiEstimateService.isSubmitByAgent(user.getCommAgent(), dateMonth);
			if(isSubmit){
				List<PsiEstimateByProduct> psiEstimateByProductList = psiEstimateService.listByAgentAndDateMonth(user.getCommAgent(), dateMonth);
				request.setAttribute("psiEstimateByProductList", psiEstimateByProductList);
			}else{
				List<PsiSellInEstimateTemplate> sellInTempList = sellInEstimateTemplateRepository.listByDateMonth(dateMonth);
				request.setAttribute("sellInTempList", sellInTempList);
			}
			request.setAttribute("agent", user.getCommAgent());
		}else if(user.getCommRole().getId()==CommRole.DAQUJINGLI){//4为大区经理
			request.setAttribute("agentList", agentList);
		}
		
		request.setAttribute("user", user);
		request.setAttribute("isSubmit", isSubmit);
		request.setAttribute("month", month);
		
		return "/psi/estimate_by_product.jsp";
	}
	
	@RequestMapping(value="/psi/estimate/save",method=RequestMethod.POST)
	public String save(HttpServletRequest request,Integer tempId[],Integer nextMonthEstimateVolume[],Integer nnextMonthEstimateVolume[],Integer agentId[],Integer[] estimateId){
		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		Date createtime = new Date();
		Date dateMonth = DateUtils.getCurrentMonthFisrtDay();
		for (int i = 0; i < tempId.length; i++) {
			CommAgent agent = commAgentService.getById(agentId[i]);
			PsiSellInEstimateTemplate temp = sellInEstimateTemplateRepository.getById(tempId[i]);
			if(user.getCommRole().getId()==CommRole.DAILISHANG){
				PsiEstimateByProduct psiEstimateByProduct = new PsiEstimateByProduct();
				psiEstimateByProduct.setAgent(agent);
				psiEstimateByProduct.setTemp(temp);
				psiEstimateByProduct.setNextMonthEstimateVolume(nextMonthEstimateVolume[i]);
				psiEstimateByProduct.setNnextMonthEstimateVolume(nnextMonthEstimateVolume[i]);
				psiEstimateByProduct.setCreatetime(createtime);
				psiEstimateByProduct.setDateMonth(dateMonth);
				psiEstimateService.save(psiEstimateByProduct);
			}
			if(user.getCommRole().getId()==CommRole.DAQUJINGLI){
				PsiEstimateByProduct psiEstimateByProduct = psiEstimateService.getById(estimateId[i]);
				psiEstimateByProduct.setNextMonthEstimateVolume(nextMonthEstimateVolume[i]);
				psiEstimateByProduct.setNnextMonthEstimateVolume(nnextMonthEstimateVolume[i]);
				psiEstimateByProduct.setUser(user);
				psiEstimateService.save(psiEstimateByProduct);
			}
		}
		return "redirect:/psi/estimate.html";
	}
	@RequestMapping(value="/psi/estimate/search",method=RequestMethod.POST)
	@ResponseBody
	public String searchByAgent(HttpServletRequest request,Integer agentId){
		JSONObject obj = new JSONObject();
		Date dateMonth = DateUtils.getCurrentMonthFisrtDay();
		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		CommAgent agent = commAgentService.getById(agentId);
		boolean isSubmit = psiEstimateService.isSubmitByManager(user, agent, dateMonth);
		List<PsiEstimateByProduct> psiEstimateByProductList = psiEstimateService.listByAgentAndDateMonth(agent, dateMonth);
		obj.put("psiEstimateByProductList", psiEstimateByProductList);
		obj.put("isSubmit", isSubmit);
		return obj.toJSONString();
	}
}
