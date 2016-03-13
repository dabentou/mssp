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
import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.service.CommProvinceService;

@Controller
public class CommProvinceController {

	private static final int IS_NOT_DELETE = 0;
	private static final int IS_DELETE = 1;
	private static final int PAGE_SIZE = 50;
	
	@Resource
	CommProvinceService commProvinceService;
	
	@RequestMapping("/admin/comm/province/list.html")
	public String list(HttpServletRequest request, @RequestParam(required=false) Integer page){
		Paging<CommProvince> paging = commProvinceService.findByIsDelete(false, page,PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		return "/admin/comm/province/province.jsp";
	}
	
	@RequestMapping("/admin/comm/province/new.html")
	public String newProvince(HttpServletRequest request, @RequestParam(required=false) Integer page){
		return "/admin/comm/province/province_new.jsp";
	}
	
	@RequestMapping(value="/admin/comm/province/newSave.html",method = RequestMethod.POST)
	public String newSave(String provinceName){
		CommProvince commProvince = new CommProvince();
		commProvince.setProvince(provinceName);
		commProvince.setIsDelete(IS_NOT_DELETE);
		commProvinceService.save(commProvince);
		return "redirect:/admin/comm/province/list.html";
		
	}
	
	@RequestMapping("/admin/comm/province/delete")
	public String delete(Integer id){
		CommProvince commProvince = commProvinceService.getCommProvinceById(id);
		commProvince.setIsDelete(IS_DELETE);
		commProvinceService.save(commProvince);
		return "redirect:/admin/comm/province/list.html";
	}
	
	@RequestMapping("/admin/comm/province/checkName")
	@ResponseBody
	public String checkName(HttpServletRequest request){
		String name= request.getParameter("param");
		Map<String, String> map = new HashMap<String, String>();
		CommProvince commProvince = commProvinceService.getCommProvinceByProvinceName(name);
			if(commProvince==null){
				map.put("info", "验证通过！");
				map.put("status", "y");
			}else{
				map.put("info", "该省份已存在！");
				map.put("status", "n");
			}
		return JSONObject.toJSONString(map);
	}
	
	@RequestMapping("/admin/comm/province/edit-{id}")
	public String cityEdit(HttpServletRequest request,@PathVariable Integer id){
		CommProvince commProvince = commProvinceService.getCommProvinceById(id);
		request.setAttribute("commProvince", commProvince);
		return "/admin/comm/province/province_edit.jsp";
	}
	
}