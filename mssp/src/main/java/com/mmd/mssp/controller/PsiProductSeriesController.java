package com.mmd.mssp.controller;


import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mmd.mssp.domain.ProductSeries;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.service.ProductSeriesService;

@Controller
public class PsiProductSeriesController {
	
	private static final int IS_NOT_DELETE = 0;
	private static final int IS_DELETE = 1;
	private static final int PAGE_SIZE = 50;

	@Resource
	ProductSeriesService productSeriesService;
	
	@RequestMapping("/admin/comm/proseries/list.html")
	public String list(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		Paging<ProductSeries> paging = productSeriesService.findByIsDelete(false, page,PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		return "/admin/comm/proseries/product_series_list.jsp";
	}
	
	@RequestMapping("/admin/comm/proseries/new.html")
	public String newproseries(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		return "/admin/comm/proseries/product_series_new.jsp";
		
	}
	
	@RequestMapping(value="/admin/comm/proseries/newSave.html",method=RequestMethod.POST)
	public String newSave(String name){
		
		Date createtime = new Date();
		ProductSeries productSeries = new ProductSeries();
		productSeries.setName(name);
		productSeries.setCreatetime(createtime);
		productSeries.setIsDelete(IS_NOT_DELETE);
		productSeriesService.save(productSeries);
	
		return "redirect:/admin/comm/proseries/list.html";
		
	}
	
	@RequestMapping("/admin/comm/proseries/edit-{id}")
	public String proseriesEdit(HttpServletRequest request,@PathVariable Integer id){
		
		ProductSeries productSeries = productSeriesService.getProductSeriesById(id);
		request.setAttribute("productSeries", productSeries);
				
		return "/admin/comm/proseries/product_series_edit.jsp";
	}
	
	@RequestMapping("/admin/comm/proseries/delete")
	public String delete(Integer id){
		
		ProductSeries productSeries = productSeriesService.getProductSeriesById(id);
		productSeries.setIsDelete(IS_DELETE);
		productSeriesService.save(productSeries);
	
		return "redirect:/admin/comm/proseries/list.html";
		
	}
	
	@RequestMapping(value="/admin/comm/proseries/editSave.html",method=RequestMethod.POST)
	public String editSave(Integer id,String name){
		
		ProductSeries productSeries = productSeriesService.getProductSeriesById(id);
		productSeries.setName(name);
		productSeriesService.save(productSeries);
	
		return "redirect:/admin/comm/proseries/list.html";
		
	}
}
