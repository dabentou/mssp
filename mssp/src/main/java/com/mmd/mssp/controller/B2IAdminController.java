package com.mmd.mssp.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mmd.mssp.domain.B2iApplyTemplate;
import com.mmd.mssp.domain.IretailPropagandaType;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.service.B2iApplyTemplateService;

@Controller
public class B2IAdminController {
	
	private static final int PAGE_SIZE = 50;
	private static final int IS_DELETE = 1;//删除
	private static final int IS_NOT_DELETE = 0;//不删除
	
	@Resource
	B2iApplyTemplateService b2iApplyTemplateService;
	
	/**
	 * 申请模板维护列表
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/admin/b2i/template/list.html")
	public String list(HttpServletRequest request, @RequestParam(required=false) Integer page){
		Paging<B2iApplyTemplate> paging = b2iApplyTemplateService.findByIsDelete(Boolean.FALSE, page, PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		return "/admin/b2i/template/list.jsp";
	}
	
	
	/**
	 * 新增申请模板
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/admin/b2i/template/new.html")
	public String addTemplate(HttpServletRequest request, @RequestParam(required=false) Integer page){
		boolean isNew = true;
		request.setAttribute("isNew", isNew);
		return "/admin/b2i/template/template_detail.jsp";
	}
	
	/**
	 * 编辑申请模板
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/b2i/template/edit-{id}")
	public String editTemplate(HttpServletRequest request,@PathVariable Integer id){
		boolean isNew = false;
		B2iApplyTemplate b2iApplyTemplate = b2iApplyTemplateService.findById(id,Boolean.FALSE);
		request.setAttribute("b2iApplyTemplate", b2iApplyTemplate);
		request.setAttribute("isNew", isNew);
		return "/admin/b2i/template/template_detail.jsp";
	}
	
	/**
	 * 申请模板保存
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/admin/b2i/template/save",method=RequestMethod.POST)
	public String saveTemplate(HttpServletRequest request,B2iApplyTemplate applyTemplate){
		B2iApplyTemplate orgin = b2iApplyTemplateService.findById(applyTemplate.getId(),Boolean.FALSE);
		if(orgin==null){
			orgin = new B2iApplyTemplate();
			orgin.setCreateTime(new Date());
			orgin.setIsDelete(B2IAdminController.IS_NOT_DELETE);
		}
		BeanUtils.copyProperties(applyTemplate, orgin, "createTime","isDelete");
		b2iApplyTemplateService.save(orgin);
		return "redirect:/admin/b2i/template/list.html";
	}
	
	/**
	 * 删除申请模板
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/b2i/template/delete")
	public String deleteTemplate(Integer id){
		B2iApplyTemplate b2iApplyTemplate = b2iApplyTemplateService.findById(id,Boolean.FALSE);
		if(b2iApplyTemplate!=null){
			b2iApplyTemplate.setIsDelete(B2IAdminController.IS_DELETE);
			b2iApplyTemplateService.save(b2iApplyTemplate);
		}
		return "redirect:/admin/b2i/template/list.html";
	}
	
}
