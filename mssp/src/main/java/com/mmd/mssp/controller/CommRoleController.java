package com.mmd.mssp.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mmd.mssp.domain.CommPermission;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommRolePermission;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.CommPermissionRepository;
import com.mmd.mssp.service.CommRolePermissionService;
import com.mmd.mssp.service.CommRoleService;

@Controller
public class CommRoleController {

	private static final int IS_NOT_DELETE = 0;
	private static final int IS_DELETE = 1;
	private static final int PAGE_SIZE = 50;

	@Resource
	CommRoleService commRoleService;
	
	@Resource
	CommRolePermissionService commRolePermissionService;
	
	@Resource
	CommPermissionRepository commPermissionRepository;
	
	@RequestMapping("/admin/comm/role/list.html")
	public String list(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		Paging<CommRole> paging = commRoleService.findByIsDelete(false, page,PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		return "/admin/comm/role/role.jsp";
	}
	
	@RequestMapping("/admin/comm/role/new.html")
	public String newrole(HttpServletRequest request, @RequestParam(required=false) Integer page){
		return "/admin/comm/role/role_new.jsp";
	}
	
	@RequestMapping("/admin/comm/role/goto_permission-{id}")
	public String gotoPermission(HttpServletRequest request, @PathVariable Integer id){
		//EliteRole role = eliteRoleService.getById(roleId);
		CommRole role = commRoleService.getCommRoleById(id);
		//查询当前角色所拥有的权限
		//List<EliteMemberPermission> perimintionList = eliteMemberPermissionService.findByEliteRoleId(roleId);
		List<CommRolePermission> perimintionList = commRolePermissionService.findByCommRoleId(role);
		int[] permitionIds = null;
		if(perimintionList != null){
			//定义长度为size的数组，permitionIds[0]对应数据库表elite_permission中的第一个权限，以此类推，默认值为0，即不拥有相应的权限
			int size = commRolePermissionService.getCounts();
			permitionIds = new int[size];
			//遍历perimintionList，并把permitionIds相应位置的值 设为1，以表示拥有该权限
			for (CommRolePermission memberPermission : perimintionList) {
				permitionIds[(int) (memberPermission.getCommPermission().getId()-1)] = 1;
			}

		}
		
		request.setAttribute("permitionIds", permitionIds);
		request.setAttribute("role",role);
		return "/admin/comm/role/role_permission.jsp";
	}
	
	
	
	@RequestMapping(value="/admin/comm/role/newSave.html",method=RequestMethod.POST)
	public String newSave(String roleName){
		Date createtime = new Date();
		CommRole commRole = new CommRole();
		commRole.setRoleName(roleName);
		commRole.setCreatetime(createtime);
		commRole.setIsDelete(IS_NOT_DELETE);
		commRoleService.save(commRole);
	
		return "redirect:/admin/comm/role/list.html";
		
	}
	
	@RequestMapping("/admin/comm/role/edit-{id}")
	public String roleEdit(HttpServletRequest request,@PathVariable Integer id){
		
		CommRole commRole = commRoleService.getCommRoleById(id);
		request.setAttribute("commRole", commRole);
				
		return "/admin/comm/role/role_edit.jsp";
	}
	
	@RequestMapping("/admin/comm/role/delete")
	public String delete(Integer id){
		
		CommRole commRole = commRoleService.getCommRoleById(id);
		commRole.setIsDelete(IS_DELETE);
		commRoleService.save(commRole);
	
		return "redirect:/admin/comm/role/list.html";
		
	}
	
	@RequestMapping(value="/admin/comm/role/editSave.html",method=RequestMethod.POST)
	public String editSave(Integer id,String roleName){
		
		CommRole commRole = commRoleService.getCommRoleById(id);
		commRole.setRoleName(roleName);
		commRoleService.save(commRole);
	
		return "redirect:/admin/comm/role/list.html";
		
	}
	
	@RequestMapping(value="/admin/comm/role/permissionSave",method=RequestMethod.POST)
	public String savePermition(HttpServletRequest request,Integer roleId, Integer[] permissionId){
		
		//根据当前roleId获取角色
		//EliteRole role = eliteRoleService.getById(roleId);
		CommRole role = commRoleService.getCommRoleById(roleId);
		//删除原来的
		//eliteMemberPermissionService.delete(eliteMemberPermissionService.findByEliteRoleId(roleId));
		commRolePermissionService.delete(commRolePermissionService.findByCommRoleId(role));
		//根据当前permitionIds获取所有的权限
		List<CommPermission> permitionOfRole = null;
		if(permissionId != null){
			List<Integer> list = Arrays.asList(permissionId);
			permitionOfRole = commPermissionRepository.findByIds(list);
		}
		//保存角色_权限
		if(permitionOfRole != null){
			for (CommPermission permission : permitionOfRole) {
				commRolePermissionService.save(new CommRolePermission(permission,role));
			}
			
		}
		return "redirect:/admin/comm/role/list.html";
	}
}
