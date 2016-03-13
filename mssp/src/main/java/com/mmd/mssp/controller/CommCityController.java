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
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.service.CommAreaService;
import com.mmd.mssp.service.CommCityService;
import com.mmd.mssp.service.CommService;

@Controller
public class CommCityController {
	
	private static final int IS_NOT_DELETE = 0;
	private static final int IS_DELETE = 1;
	private static final int PAGE_SIZE = 50;

	@Resource
	CommCityService commCityService;
	
	@Resource
	CommAreaService commAreaService;
	
	@Resource
	CommService commService;
	
	@RequestMapping("/admin/comm/city/list.html")
	public String list(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		Paging<CommCity> paging = commCityService.findByIsDelete(false, page,PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		return "/admin/comm/city/city.jsp";
	}
	
	@RequestMapping("/admin/comm/city/new.html")
	public String newcity(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		List<CommArea> commAreaList = commAreaService.listAll(false);
		request.setAttribute("commAreaList", commAreaList);
		
		return "/admin/comm/city/city_new.jsp";
		
	}
	
	@RequestMapping(value="/admin/comm/city/newSave.html",method = RequestMethod.POST)
	public String newSave(String cityName,String cityCode,Integer area_id){
		
		CommArea commArea = commAreaService.getCommAreaById(area_id);
		
		Date createtime = new Date();
		CommCity commCity = new CommCity();
		commCity.setCityName(cityName);
		commCity.setCityCode(cityCode);
		commCity.setCommArea(commArea);
		commCity.setCreatetime(createtime);
		commCity.setIsDelete(IS_NOT_DELETE);
		commCityService.save(commCity);
	
		return "redirect:/admin/comm/city/list.html";
		
	}
	
	@RequestMapping("/admin/comm/city/edit-{id}")
	public String cityEdit(HttpServletRequest request,@PathVariable Integer id){
		
		List<CommArea> commAreaList = commAreaService.listAll(false);
		CommCity commCity = commCityService.getCommCityById(id);
		
		request.setAttribute("commAreaList", commAreaList);
		request.setAttribute("commCity", commCity);
				
		return "/admin/comm/city/city_edit.jsp";
	}
	
	@RequestMapping("/admin/comm/city/delete")
	public String delete(Integer id){
		
		CommCity commCity = commCityService.getCommCityById(id);
		commCity.setIsDelete(IS_DELETE);
		commCityService.save(commCity);
	
		return "redirect:/admin/comm/city/list.html";
		
	}
	
	@RequestMapping(value="/admin/comm/city/editSave.html",method=RequestMethod.POST)
	public String editSave(Integer id,String cityName,String cityCode,Integer area_id){
		
		CommCity commCity = commCityService.getCommCityById(id);
		CommArea commArea = commAreaService.getCommAreaById(area_id);
		
		commCity.setCityName(cityName);
		commCity.setCityCode(cityCode);
		commCity.setCommArea(commArea);
		commCityService.save(commCity);
	
		return "redirect:/admin/comm/city/list.html";
		
	}
	
	@RequestMapping("/admin/comm/city/checkName")
	@ResponseBody
	public String checkName(HttpServletRequest request,Integer id){
		String name= request.getParameter("param");
		Map<String, String> map = new HashMap<String, String>();
		CommCity commCity1 = commCityService.getCommCityById(id);
		CommCity commCity2 = commCityService.getCommCityByCityName(name);
		if(commCity1==null){
			if(commCity2==null){
				map.put("info", "验证通过！");
				map.put("status", "y");
			}else{
				map.put("info", "该城市已存在！");
				map.put("status", "n");
			}
		}else{
			if(commCity2==null||commCity2.getId().equals(id)){
				map.put("info", "验证通过！");
				map.put("status", "y");
			}else{
				map.put("info", "该城市已存在！");
				map.put("status", "n");
			}
		}
		return JSONObject.toJSONString(map);
	}
}
