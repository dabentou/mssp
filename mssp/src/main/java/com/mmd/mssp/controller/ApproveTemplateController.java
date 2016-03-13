package com.mmd.mssp.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mmd.mssp.comm.ApiWarp;
import com.mmd.mssp.domain.ApproveTemplate;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.vo.ApproveTemplateType;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.service.ApproveTemplateService;
import com.mmd.mssp.service.CommRoleService;
import com.mmd.mssp.service.CommUserService;
import com.mmd.mssp.service.ProcessStepService;
import com.mmd.mssp.util.SysConfig;




@Controller
public class ApproveTemplateController {
	
	private static final int IS_NOT_DELETE = 0;
	private static final int IS_DELETE = 1;
	private static final int PAGE_SIZE = 50;
	
	@Resource
	ApproveTemplateService approveTemplateService;
	
	@Resource
	CommRoleService commRoleService;
	
	@Resource
	ProcessStepService processStepService;
	
	@Resource
	CommUserService commUserService;

	@RequestMapping("/admin/comm/approvetemplate/list.html")
	public String templateList(HttpServletRequest request,@RequestParam(required=false) Integer page){
		Paging<ApproveTemplate> paging = approveTemplateService.findAll(page,PAGE_SIZE);
		request.setAttribute("paging", paging);
		return "/admin/comm/template/list.jsp";
	}
	
	@RequestMapping("/admin/comm/approvetemplate/new.html")
	public String templateAdd(HttpServletRequest request){
		List<ApproveTemplateType> listStatusValue = SysConfig.getTemplateStatusValues();
		request.setAttribute("listStatusValue", listStatusValue);
		return "/admin/comm/template/approve_template_add.jsp";
		
	}
	
	@RequestMapping("/admin/comm/approvetemplate/edit")
	public String templateEdit(HttpServletRequest request,Integer id){
		ApproveTemplate approveTemplate = approveTemplateService.fandById(id);
		List<ProcessStep> list = processStepService.findByTemp(approveTemplate);
		List<ApproveTemplateType> listStatusValue = SysConfig.getTemplateStatusValues();
		request.setAttribute("listStatusValue", listStatusValue);
		request.setAttribute("stepList", list);
		request.setAttribute("rowCount", list.size());
		request.setAttribute("approveTemplate", approveTemplate);
		return "/admin/comm/template/approve_template_add.jsp";
		
	}
	
	@RequestMapping("/admin/comm/approvetemplate/delete")
	public String templateDelete(HttpServletRequest request,Integer id){
		ApproveTemplate approveTemplate = approveTemplateService.fandById(id);
		if(approveTemplate!=null){
			List<ProcessStep> list = processStepService.findByTemp(approveTemplate);
			for(int i=0;i<list.size(); i++){
				ProcessStep processStep = list.get(i);
				processStep.setPnextId(null);
				processStep.setRnextId(null);
				processStepService.save(processStep);
			}
			processStepService.deleteList(list);
			approveTemplateService.delete(approveTemplate);
		}
		return "redirect:/admin/comm/approvetemplate/list.html";
		
	}
	
	@RequestMapping("/admin/comm/approvetemplate/search")
	public String templateSearch(HttpServletRequest request,String typeName,@RequestParam(required=false) Integer page){
		Paging<ApproveTemplate> paging = approveTemplateService.findByTypeName("%"+typeName+"%", page, PAGE_SIZE);
		request.setAttribute("paging", paging);
		return "/admin/comm/template/list.jsp";
		
	}
	
	@RequestMapping("/admin/comm/approvetemplate/detail.html")
	public String templateDetail(HttpServletRequest request,Integer id){
		ApproveTemplate approveTemplate = approveTemplateService.fandById(id);
		List<CommRole> list = commRoleService.listAll(Boolean.FALSE);
		List<CommUser> userList = commUserService.listAll(Boolean.FALSE);
		List<ProcessStep> proStepList = processStepService.findByTemp(approveTemplate);
		Map<ProcessStep, List<CommUser>> proStepMap = new TreeMap<ProcessStep, List<CommUser>>();
		for(int i=0; i<proStepList.size(); i++){
			ProcessStep processStep = proStepList.get(i);
			String emailUserIds = processStep.getEmailUserIds();
			List<CommUser> emailUser = new ArrayList<CommUser>();
			if(emailUserIds!=null){
				String[] emailUserIdArr = emailUserIds.split(",");
				for(int j=0; j<emailUserIdArr.length; j++){
					if("".equals(emailUserIdArr[j])){
						emailUserIdArr[j] = "0";
					}
					CommUser commUser = commUserService.getCommUserById(Integer.parseInt(emailUserIdArr[j]));
					emailUser.add(commUser);
				}
			}
			proStepMap.put(processStep, emailUser);
		}
		request.setAttribute("userList", userList);
		request.setAttribute("proStepMap", proStepMap);
		request.setAttribute("prStepPassOrReject", proStepList);
		request.setAttribute("roleList", list);
		request.setAttribute("id", id);
		return "/admin/comm/template/approve_template_detail.jsp";
		
	}
	
	@RequestMapping(value="/admin/comm/approvetemplate/post",method=RequestMethod.POST)
	public String newSave(HttpServletRequest request,ApproveTemplate approveTemplate,String[] operateStatus,Integer[] processStepId,Integer[] statusValue ) throws IllegalAccessException, InvocationTargetException{
		ApproveTemplate orgin = approveTemplateService.fandById(approveTemplate.getId());
		if(orgin==null){
			orgin = new ApproveTemplate();
		}
		BeanUtils.copyProperties(approveTemplate, orgin, "id");
		/**
		 * sysId暂时未定，统一存null
		 */
		orgin.setSysId(null);
		approveTemplateService.save(orgin);
		if(approveTemplate.getId()==0){//新增
			int count = processStepId==null?0:processStepId.length;
			for(int i=0; i<count; i++){
				ProcessStep processStep = new ProcessStep();
				processStep.setOperateStatus(operateStatus[i]);
				processStep.setTemp(orgin);
				/**
				 * statusValue暂时存为 步骤序号
				 */
				processStep.setStatusValue(statusValue[i]);
				processStepService.save(processStep);
			}
		}else{//修改
			List<ProcessStep> list = processStepService.findByTemp(approveTemplate);
			if(list.size()==0){//增加步骤
				int count = processStepId==null?0:processStepId.length;
				for(int i=0; i<count; i++){
					ProcessStep processStep = new ProcessStep();
					processStep.setOperateStatus(operateStatus[i]);
					processStep.setStatusValue(statusValue[i]);
					processStep.setTemp(orgin);
					processStepService.save(processStep);
				}
			}else{
				for(int i=0; i<list.size(); i++){
					for(int j=0; j<processStepId.length; j++){
						int sourePrStepId = list.get(i).getId();
						int orginPrStempId = processStepId[j];
						if(orginPrStempId==0){//增加步骤
							ProcessStep processStep = new ProcessStep();
							processStep.setOperateStatus(operateStatus[j]);
							processStep.setStatusValue(statusValue[j]);
							processStep.setTemp(orgin);
							ProcessStep processStep_new = processStepService.save(processStep);
							processStepId[j] = processStep_new.getId();//防止二次循环时又增加
							continue;
						}
						if(sourePrStepId==orginPrStempId){
							ProcessStep processStep = list.get(i);
							processStep.setOperateStatus(operateStatus[j]);
							processStep.setStatusValue(statusValue[j]);
							processStepService.save(processStep);
							continue;
						}
					}
				}
			}
		
		}
		return "redirect:/admin/comm/approvetemplate/list.html";
	}
	
	@RequestMapping(value="/admin/comm/approvetemplate/detail/post",method=RequestMethod.POST)
	@ResponseBody
	public String templateDetailSave(HttpServletRequest request,String templateDetail,Integer id){
		ApiWarp apiWarp = new ApiWarp();
		ApproveTemplate approveTemplate = approveTemplateService.fandById(id);
		if(approveTemplate==null){
			return apiWarp.addError("未找到该类型模板id="+id).toJsonString();
		}
		JSONArray jsonArray = JSON.parseArray(templateDetail);
		for(int i=0; i<jsonArray.size(); i++){
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			Integer proStepId = jsonObject.getInteger("proStepId");
			Integer roleId = jsonObject.getInteger("roleId");
			Integer pnextId = jsonObject.getInteger("pnextId");
			Integer rnextId = jsonObject.getInteger("rnextId");
			Integer isFirst = jsonObject.getInteger("isFirst");
			Integer isAutoApprove = jsonObject.getInteger("isAutoApprove");
			String emailUsers = jsonObject.getString("emailUsers");
			ProcessStep processStep = processStepService.findById(proStepId);
			if(processStep!=null){
				CommRole commRole = commRoleService.getCommRoleById(roleId);
				ProcessStep pnext = processStepService.findById(pnextId);
				ProcessStep rnext = processStepService.findById(rnextId);
				processStep.setCommRole(commRole);
				processStep.setPnextId(pnext);
				processStep.setRnextId(rnext);
				processStep.setIsFirst(isFirst);
				processStep.setIsAutoApprove(isAutoApprove);
				processStep.setEmailUserIds(emailUsers);
				processStepService.save(processStep);
			}
		}
		return apiWarp.putData(approveTemplate).toJsonString();
	}
	
	@RequestMapping(value="/admin/comm/processStep/delete",method=RequestMethod.POST)
	@ResponseBody
	public String processStepDelete(HttpServletRequest request,Integer pStepId){
		ApiWarp apiWarp = new ApiWarp();
		ProcessStep processStep = processStepService.findById(pStepId);
		ProcessStep prStepDelete = processStepService.isDelProcessStep(processStep.getTemp(), processStep);//查看该步骤是否设置为  通过或驳回关联操作
		if(prStepDelete!=null){
			return apiWarp.addError("该步骤已被设置为通过或驳回的后续操作，暂不可删除！name="+processStep.getOperateStatus()).toJsonString();
		}
		processStepService.delete(processStep);
		return apiWarp.putData(processStep).toJsonString();
	}
}
