package com.mmd.mssp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommArea;
import com.mmd.mssp.domain.PsiSellInEstimateByMonth;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.domain.vo.PsiSellInEstimateByMonthVo;
import com.mmd.mssp.service.CommAgentService;
import com.mmd.mssp.service.CommService;
import com.mmd.mssp.service.PsiSellInEstimateByMonthService;
import com.mmd.mssp.util.DateUtils;

@Controller
public class PsiSellInEstimateByMonthController {
	
	@Resource
	CommAgentService commAgentService;
	@Resource
	PsiSellInEstimateByMonthService psiSellInEstimateByMonthService;
	@Resource
	CommService commService;
	
	@RequestMapping("/admin/psi/estimate/listall.html")
	public String list(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		List<CommAgent> commAgentList = commAgentService.listAll();
		List<PsiSellInEstimateByMonthVo> psiSellInEstimateByMonthVoList = new ArrayList<PsiSellInEstimateByMonthVo>();
		boolean isSubmit = false;
		
		Date dateBegin = commService.getCurrentMonthFisrtDay();
		Date dateEnd = new Date();
		
		for (CommAgent commAgent : commAgentList) {
			PsiSellInEstimateByMonthVo psiSellInEstimateByMonthVo = new PsiSellInEstimateByMonthVo();
			PsiSellInEstimateByMonth psiSellInEstimateByMonth = psiSellInEstimateByMonthService.getsellInVolumeByDateAndCommAgent(dateBegin, dateEnd, commAgent);
			if(psiSellInEstimateByMonth==null){
				psiSellInEstimateByMonthVo.setEstimateVolume(0);
				psiSellInEstimateByMonthVo.setIsEstimate(0);
			}else {
				psiSellInEstimateByMonthVo.setEstimateVolume(psiSellInEstimateByMonth.getSellInVolume());
				psiSellInEstimateByMonthVo.setIsEstimate(1);
				isSubmit = true;
			}
			psiSellInEstimateByMonthVo.setCommAgent(commAgent);
			psiSellInEstimateByMonthVoList.add(psiSellInEstimateByMonthVo);
		}
		request.setAttribute("isSubmit", isSubmit);
		request.setAttribute("psiSellInEstimateByMonthVoList", psiSellInEstimateByMonthVoList);
		return "/admin/psi/sell_in_estimate_by_month.jsp";
	}

	@RequestMapping(value="/admin/psi/estimate/save",method=RequestMethod.POST)
	public String save(HttpServletRequest request,Integer agentId[],Integer sellInVolume[]){
		
		Date createtime = new Date();
		Date dataMonth = DateUtils.getCurrentMonthFisrtDay();
		
		for (int i = 0; i < sellInVolume.length; i++) {
			CommAgent agent = commAgentService.getById(agentId[i]);
			PsiSellInEstimateByMonth psiSellInEstimateByMonth = new PsiSellInEstimateByMonth();
			
			psiSellInEstimateByMonth.setAgent(agent);
			psiSellInEstimateByMonth.setCreatetime(createtime);
			psiSellInEstimateByMonth.setSellInVolume(sellInVolume[i]);
			psiSellInEstimateByMonth.setDateMonth(dataMonth);
			
			psiSellInEstimateByMonthService.save(psiSellInEstimateByMonth);
		}
		
		return "redirect:/admin/psi/estimate/listall.html";
	}
}
