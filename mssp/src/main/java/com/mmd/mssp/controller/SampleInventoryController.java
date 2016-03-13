package com.mmd.mssp.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.SampleInventory;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.service.ProductService;
import com.mmd.mssp.service.SampleInventoryService;

@Controller
public class SampleInventoryController {
	
	private static final int PAGE_SIZE = 20;
	
	@Resource
	SampleInventoryService sampleInventoryService;
	
	@Resource
	ProductService productService;

	@RequestMapping("/admin/comm/sample/list.html")
	public String list(HttpServletRequest request, @RequestParam(required=false) Integer page){
		Paging<SampleInventory> paging = sampleInventoryService.findAll(page,PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		return "/admin/comm/sample/sample.jsp";
	}
	
	@RequestMapping("/admin/comm/sample/new.html")
	public String newcity(HttpServletRequest request, @RequestParam(required=false) Integer page){
		List<Product> productList = productService.listAll(false);
		request.setAttribute("productList", productList);
		return "/admin/comm/sample/sample_new.jsp";
		
	}
	
	@RequestMapping(value="/admin/comm/sample/newSave.html",method = RequestMethod.POST)
	public String newSave(Integer product_id,Integer sampleTotal,Integer sampleBalance){
		Product product = productService.getProductById(product_id);
		SampleInventory sampleInventory = new SampleInventory();
		sampleInventory.setProduct(product);
		sampleInventory.setTotal(sampleTotal);
		sampleInventory.setBalance(sampleBalance);
		sampleInventoryService.save(sampleInventory);
		return "redirect:/admin/comm/sample/list.html";
		
	}
	
	@RequestMapping("/admin/comm/sample/edit-{id}")
	public String cityEdit(HttpServletRequest request,@PathVariable Integer id){
		SampleInventory sampleInventory = sampleInventoryService.getSampleInventoryById(id);
		List<Product> productList = productService.listAll(false);
		request.setAttribute("productList", productList);
		request.setAttribute("sampleInventory", sampleInventory);
		return "/admin/comm/sample/sample_edit.jsp";
	}
	
	@RequestMapping(value="/admin/comm/sample/editSave.html",method=RequestMethod.POST)
	public String editSave(Integer id,Integer product_id,Integer sampletTotal,Integer sampleBalance){
		SampleInventory sampleInventory = sampleInventoryService.getSampleInventoryById(id);
		Product product = productService.getProductById(product_id);
		sampleInventory.setTotal(sampletTotal);
		sampleInventory.setBalance(sampleBalance);
		sampleInventoryService.save(sampleInventory);
		return "redirect:/admin/comm/sample/list.html";
	}
	
	@RequestMapping("/admin/comm/sample/delete")
	public String delete(Integer id){
		SampleInventory sampleInventory = sampleInventoryService.getSampleInventoryById(id);
		sampleInventoryService.delete(sampleInventory);
		return "redirect:/admin/comm/sample/list.html";
	}
	
	@RequestMapping("/admin/comm/sample/checkName")
	@ResponseBody
	public String checkName(HttpServletRequest request,Integer id){
		String productId= request.getParameter("param");
		Map<String, String> map = new HashMap<String, String>();
		SampleInventory sampleInventory1 = sampleInventoryService.getSampleInventoryById(id);
		SampleInventory sampleInventory2 = sampleInventoryService.getSampleInventoryByProductId(Integer.parseInt(productId));
		if(sampleInventory1==null){
			if(sampleInventory2==null){
				map.put("info", "验证通过！");
				map.put("status", "y");
			}else{
				map.put("info", "该样机已存在！");
				map.put("status", "n");
			}
		}else{
			if(sampleInventory2==null||sampleInventory2.getId().equals(id)){
				map.put("info", "验证通过！");
				map.put("status", "y");
			}else{
				map.put("info", "该样机已存在！");
				map.put("status", "n");
			}
		}
		return JSONObject.toJSONString(map);
	}
	
}
