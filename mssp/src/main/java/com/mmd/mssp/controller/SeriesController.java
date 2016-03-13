package com.mmd.mssp.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mmd.mssp.domain.CommSeries;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.service.CommSeriesService;

@Controller
public class SeriesController {

	private static final int IS_NOT_DELETE = 0;
	private static final int IS_DELETE = 1;
	private static final int PAGE_SIZE = 50;

	@Resource
	CommSeriesService seriesService;
	
	@RequestMapping("/admin/comm/series/list.html")
	public String list(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		Paging<CommSeries> paging = seriesService.findByIsDelete(false, page,PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		return "/admin/comm/series/series.jsp";
	}
	
	@RequestMapping("/admin/comm/series/new.html")
	public String newseries(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		return "/admin/comm/series/series_new.jsp";
		
	}
	
	@RequestMapping(value="/admin/comm/series/newSave.html",method=RequestMethod.POST)
	public String newSave(String name){
		
		Date createtime = new Date();
		CommSeries commSeries = new CommSeries();
		commSeries.setName(name);
		commSeries.setCreatetime(createtime);
		commSeries.setIsDelete(IS_NOT_DELETE);
		seriesService.save(commSeries);
	
		return "redirect:/admin/comm/series/list.html";
		
	}
	
	@RequestMapping("/admin/comm/series/edit-{id}")
	public String seriesEdit(HttpServletRequest request,@PathVariable Integer id){
		
		CommSeries commSeries = seriesService.getCommSeriesById(id);
		request.setAttribute("commSeries", commSeries);
				
		return "/admin/comm/series/series_edit.jsp";
	}
	
	@RequestMapping("/admin/comm/series/delete")
	public String delete(Integer id){
		
		CommSeries commSeries = seriesService.getCommSeriesById(id);
		commSeries.setIsDelete(IS_DELETE);
		seriesService.save(commSeries);
	
		return "redirect:/admin/comm/series/list.html";
		
	}
	
	@RequestMapping(value="/admin/comm/series/editSave.html",method=RequestMethod.POST)
	public String editSave(Integer id,String name){
		
		CommSeries commSeries = seriesService.getCommSeriesById(id);
		commSeries.setName(name);
		seriesService.save(commSeries);
	
		return "redirect:/admin/comm/series/list.html";
		
	}
}
