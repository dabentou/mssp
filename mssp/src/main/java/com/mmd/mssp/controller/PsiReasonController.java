package com.mmd.mssp.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mmd.mssp.domain.PsiReason;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.service.PsiReasonService;

@Controller
public class PsiReasonController {

	private static final int IS_NOT_DELETE = 0;
	private static final int IS_DELETE = 1;
	private static final int PAGE_SIZE = 50;

	@Resource
	PsiReasonService psiReasonService;
	
	@RequestMapping("/admin/psi/reason/list.html")
	public String list(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		Paging<PsiReason> paging = psiReasonService.findByIsDelete(false, page,PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		return "/admin/psi/reason/reason.jsp";
	}
	
	@RequestMapping("/admin/psi/reason/new.html")
	public String newreason(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		return "/admin/psi/reason/reason_new.jsp";
		
	}
	
	@RequestMapping(value="/admin/psi/reason/newSave.html",method=RequestMethod.POST)
	public String newSave(String reason){
		
		Date createtime = new Date();
		PsiReason psiReason = new PsiReason();
		psiReason.setReason(reason);
		psiReason.setCreatetime(createtime);
		psiReason.setIsDelete(IS_NOT_DELETE);
		psiReasonService.save(psiReason);
	
		return "redirect:/admin/psi/reason/list.html";
		
	}
	
	@RequestMapping("/admin/psi/reason/edit-{id}")
	public String reasonEdit(HttpServletRequest request,@PathVariable Integer id){
		
		PsiReason psiReason = psiReasonService.getPsiReasonById(id);
		request.setAttribute("psiReason", psiReason);
				
		return "/admin/psi/reason/reason_edit.jsp";
	}
	
	@RequestMapping("/admin/psi/reason/delete")
	public String delete(Integer id){
		
		PsiReason psiReason = psiReasonService.getPsiReasonById(id);
		psiReason.setIsDelete(IS_DELETE);
		psiReasonService.save(psiReason);
	
		return "redirect:/admin/psi/reason/list.html";
		
	}
	
	@RequestMapping(value="/admin/psi/reason/editSave.html",method=RequestMethod.POST)
	public String editSave(Integer id,String reason){
		
		PsiReason psiReason = psiReasonService.getPsiReasonById(id);
		psiReason.setReason(reason);
		psiReasonService.save(psiReason);
	
		return "redirect:/admin/psi/reason/list.html";
		
	}
}
