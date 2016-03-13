package com.mmd.mssp.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mmd.mssp.domain.Pannel;
import com.mmd.mssp.domain.ProductSeries;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.service.PannelService;

@Controller
public class PannelController {
	
	private static final int IS_NOT_DELETE = 0;
	private static final int IS_DELETE = 1;
	private static final int PAGE_SIZE = 50;

	@Resource
	PannelService pannelService;
	
	@RequestMapping("/admin/comm/pannel/list.html")
	public String list(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		Paging<Pannel> paging = pannelService.findByIsDelete(false, page,PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		return "/admin/comm/pannel/pannel.jsp";
	}
	
	@RequestMapping("/admin/comm/pannel/new.html")
	public String newpannel(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		return "/admin/comm/pannel/pannel_new.jsp";
		
	}
	
	@RequestMapping(value="/admin/comm/pannel/newSave.html",method=RequestMethod.POST)
	public String newSave(String name){
		
		Date createtime = new Date();
		Pannel pannel = new Pannel();
		pannel.setName(name);
		pannel.setCreatetime(createtime);
		pannel.setIsDelete(IS_NOT_DELETE);
		pannelService.save(pannel);
	
		return "redirect:/admin/comm/pannel/list.html";
		
	}
	
	@RequestMapping("/admin/comm/pannel/edit-{id}")
	public String pannelEdit(HttpServletRequest request,@PathVariable Integer id){
		
		Pannel pannel = pannelService.getPannelById(id);
		request.setAttribute("pannel", pannel);
				
		return "/admin/comm/pannel/pannel_edit.jsp";
	}
	
	@RequestMapping("/admin/comm/pannel/delete")
	public String delete(Integer id){
		
		Pannel pannel = pannelService.getPannelById(id);
		pannel.setIsDelete(IS_DELETE);
		pannelService.save(pannel);
	
		return "redirect:/admin/comm/pannel/list.html";
		
	}
	
	@RequestMapping(value="/admin/comm/pannel/editSave.html",method=RequestMethod.POST)
	public String editSave(Integer id,String name){
		
		Pannel pannel = pannelService.getPannelById(id);
		pannel.setName(name);
		pannelService.save(pannel);
	
		return "redirect:/admin/comm/pannel/list.html";
		
	}
}
