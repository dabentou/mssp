package com.mmd.mssp.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mmd.mssp.domain.CommArea;
import com.mmd.mssp.domain.CommBusiness;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.service.CommBusinessService;

@Controller
public class CommBusinessController {

	private static final int PAGE_SIZE = 50;

	@Resource
	CommBusinessService businessService;
	
	@RequestMapping("/admin/comm/business/list.html")
	public String list(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		Paging<CommBusiness> paging = businessService.findAll(page,PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		return "/admin/comm/business/business.jsp";
	}
	
	@RequestMapping("/admin/comm/business/new.html")
	public String newarea(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		boolean isNew = true;
		
		request.setAttribute("isNew", isNew);
		return "/admin/comm/business/business_detail.jsp";
	}
	
	@RequestMapping("/admin/comm/business/edit-{id}")
	public String areaEdit(HttpServletRequest request,@PathVariable Integer id){
		
		boolean isNew = false;
		
		CommBusiness commBusiness = businessService.getCommBusinessById(id);
		request.setAttribute("commBusiness", commBusiness);
		
		request.setAttribute("isNew", isNew);
		return "/admin/comm/business/business_detail.jsp";
	}
	
	@RequestMapping(value="/admin/comm/business/save.html",method=RequestMethod.POST)
	public String save(Integer id, String name){
		CommBusiness commBusiness;
		Date createtime = new Date();
		if(id==null){//新增行业
			commBusiness = new CommBusiness();
			commBusiness.setCreatetime(createtime);
		}else{//编辑行业
			commBusiness = businessService.getCommBusinessById(id);
		}
		commBusiness.setName(name);
		businessService.save(commBusiness);
		return "redirect:/admin/comm/business/list.html";
	}
	
	@RequestMapping("/admin/comm/business/delete")
	public String delete(Integer id){
		
		CommBusiness commBusiness = businessService.getCommBusinessById(id);
		businessService.delete(commBusiness);
	
		return "redirect:/admin/comm/business/list.html";
		
	}
	
	@RequestMapping("/admin/comm/business/checkName")
	@ResponseBody
	public String checkName(HttpServletRequest request,Integer id){
		String name= request.getParameter("param");
		Map<String, String> map = new HashMap<String, String>();
		CommBusiness commBusiness1 = businessService.getCommBusinessById(id);
		CommBusiness commBusiness2 = businessService.getCommBusinessByName(name);
		if(commBusiness1==null){
			if(commBusiness2==null){
				map.put("info", "验证通过！");
				map.put("status", "y");
			}else{
				map.put("info", "该行业已存在！");
				map.put("status", "n");
			}
		}else{
			if(commBusiness2==null||commBusiness2.getId().equals(id)){
				map.put("info", "验证通过！");
				map.put("status", "y");
			}else{
				map.put("info", "该行业已存在！");
				map.put("status", "n");
			}
		}
		return JSONObject.toJSONString(map);
	}
}
