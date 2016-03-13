package com.mmd.mssp.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mmd.mssp.domain.B2CCondition;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.service.B2CConditionService;

@Controller
public class B2CConditionController {

	private static final int PAGE_SIZE = 50;
	
	@Resource
	B2CConditionService conditionService;
	
	@RequestMapping("/admin/b2c/condition/list.html")
	public String list(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		Paging<B2CCondition> paging = conditionService.findAll(page,PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		return "/admin/b2c/condition/condition.jsp";
	}
	
	@RequestMapping("/admin/b2c/condition/new.html")
	public String newCondition(HttpServletRequest request, @RequestParam(required=false) Integer page){
		return "/admin/b2c/condition/condition_detail.jsp";
	}
	
	@RequestMapping(value="/admin/b2c/condition/save",method=RequestMethod.POST)
	public String save(Integer id,String name,int type){
		B2CCondition condition = new B2CCondition();
		if(id!=null){
			condition = conditionService.getConditionById(id);
			condition.setName(name);
			condition.setType(type);
		}else{
			condition.setName(name);
			condition.setType(type);
			condition.setCreatetime(new Date());
		}
		conditionService.save(condition);
		return "redirect:/admin/b2c/condition/list.html";
	}
	
	@RequestMapping("/admin/b2c/condition/edit-{id}")
	public String conditionEdit(HttpServletRequest request,@PathVariable Integer id){
		B2CCondition condition = conditionService.getConditionById(id);
		request.setAttribute("condition", condition);
		return "/admin/b2c/condition/condition_detail.jsp";
	}
	
	@RequestMapping("/admin/b2c/condition/delete")
	public String delete(Integer id){
		B2CCondition condition = conditionService.getConditionById(id);
		conditionService.delete(condition);
		return "redirect:/admin/b2c/condition/list.html";
		
	}
}
