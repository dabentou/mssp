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
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.service.CommAreaService;

@Controller
public class CommAreaController {

	private static final int IS_NOT_DELETE = 0;
	private static final int IS_DELETE = 1;
	private static final int PAGE_SIZE = 50;

	@Resource
	CommAreaService commAreaService;
	
	@RequestMapping("/admin/comm/area/list.html")
	public String list(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		Paging<CommArea> paging = commAreaService.findByIsDelete(false, page,PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		return "/admin/comm/area/area.jsp";
	}
	
	@RequestMapping("/admin/comm/area/new.html")
	public String newarea(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		return "/admin/comm/area/area_new.jsp";
		
	}
	
	@RequestMapping(value="/admin/comm/area/newSave.html",method=RequestMethod.POST)
	public String newSave(String areaName){
		
		Date createtime = new Date();
		CommArea commArea = new CommArea();
		commArea.setAreaName(areaName);
		commArea.setCreatetime(createtime);
		commArea.setIsDelete(IS_NOT_DELETE);
		commAreaService.save(commArea);
	
		return "redirect:/admin/comm/area/list.html";
		
	}
	
	@RequestMapping("/admin/comm/area/edit-{id}")
	public String areaEdit(HttpServletRequest request,@PathVariable Integer id){
		
		CommArea commArea = commAreaService.getCommAreaById(id);
		request.setAttribute("commArea", commArea);
				
		return "/admin/comm/area/area_edit.jsp";
	}
	
	@RequestMapping("/admin/comm/area/delete")
	public String delete(Integer id){
		
		CommArea commArea = commAreaService.getCommAreaById(id);
		commArea.setIsDelete(IS_DELETE);
		commAreaService.save(commArea);
	
		return "redirect:/admin/comm/area/list.html";
		
	}
	
	@RequestMapping(value="/admin/comm/area/editSave.html",method=RequestMethod.POST)
	public String editSave(Integer id,String areaName){
		
		CommArea commArea = commAreaService.getCommAreaById(id);
		commArea.setAreaName(areaName);
		commAreaService.save(commArea);
	
		return "redirect:/admin/comm/area/list.html";
		
	}
	
	@RequestMapping("/admin/comm/area/checkName")
	@ResponseBody
	public String checkName(HttpServletRequest request,Integer id){
		String areaName= request.getParameter("param");
		Map<String, String> map = new HashMap<String, String>();
		CommArea commArea1 = commAreaService.getCommAreaById(id);
		CommArea commArea2 = commAreaService.getCommAreaByAreaName(areaName);
		if(commArea1==null){
			if(commArea2==null){
				map.put("info", "验证通过！");
				map.put("status", "y");
			}else{
				map.put("info", "该大区已存在！");
				map.put("status", "n");
			}
		}else{
			if(commArea2==null||commArea2.getId().equals(id)){
				map.put("info", "验证通过！");
				map.put("status", "y");
			}else{
				map.put("info", "该大区已存在！");
				map.put("status", "n");
			}
		}
		return JSONObject.toJSONString(map);
	}
}
