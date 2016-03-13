package com.mmd.mssp.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmd.mssp.comm.ApiWarp;
import com.mmd.mssp.comm.Common;
import com.mmd.mssp.comm.Constants;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.UserCity;
import com.mmd.mssp.domain.UserProvince;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.UserCityRepository;
import com.mmd.mssp.repository.UserProvinceRepository;
import com.mmd.mssp.service.CommAgentService;
import com.mmd.mssp.service.CommCityService;
import com.mmd.mssp.service.CommProvinceService;
import com.mmd.mssp.service.CommRoleService;
import com.mmd.mssp.service.CommService;
import com.mmd.mssp.service.CommUserService;
import com.mmd.mssp.util.WebUtil;

@Controller
public class CommUserController {

	private static final int IS_NOT_DELETE = 0;
	private static final int IS_DELETE = 1;
	private static final int PAGE_SIZE = 50;

	@Resource
	CommUserService commUserService;
	@Resource
	CommRoleService commRoleService;
	@Resource
	CommAgentService commAgentService;
	@Resource
	CommProvinceService provinceService;
	@Resource
	CommCityService cityService;
	@Resource
	CommService commService;
	@Resource
	UserProvinceRepository userProvinceRepository;
	@Resource
	UserCityRepository userCityRepository;
	
	@RequestMapping("/admin/comm/user/list.html")
	public String list(HttpServletRequest request, @RequestParam(required=false) Integer page){
		Paging<CommUser> paging = commUserService.findByIsDelete(false, page,PAGE_SIZE);
		List<CommRole> roleList = commRoleService.listAll(false);
		request.setAttribute("roleList", roleList);
		request.setAttribute("pagelist", paging);
		return "/admin/comm/user/user.jsp";
	}
	
	@RequestMapping("//admin/comm/user/search")
	public String search(HttpServletRequest request, @RequestParam(required=false) Integer page,String loginName,Integer[] roleId) throws Exception{
		List<CommRole> roleList = commRoleService.listAll(false);
		Paging<CommUser> paging = commUserService.findBySearch(WebUtil.getHttpQueryParam(request, "loginName"), roleId, page, PAGE_SIZE);
		request.setAttribute("roleList", roleList);
		request.setAttribute("pagelist", paging);
		return "/admin/comm/user/user.jsp";
	}
	
	@RequestMapping("/admin/comm/user/new.html")
	public String newuser(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		List<CommRole> commRoleList = commRoleService.listAllNoAgent(false);
		List<CommAgent> agentList = new ArrayList<CommAgent>();
		List<CommAgent> agentList1 = commAgentService.listAll();
		List<CommProvince> provinceList = provinceService.listAll();
		List<CommCity> cityList = cityService.listAllCity(false);
		
		for (CommAgent commAgent : agentList1) {
			CommUser user = commUserService.findByCommAgent(commAgent);
			if(user == null){
				agentList.add(commAgent);
			}else{
				if(user.getIsDelete() == IS_DELETE){
					agentList.add(commAgent);
				}
			}
		}
		request.setAttribute("agentList", agentList);
		request.setAttribute("commRoleList", commRoleList);
		request.setAttribute("provinceList", provinceList);
		request.setAttribute("cityList", cityList);
		return "/admin/comm/user/user_new.jsp";
		
	}
	
	@RequestMapping(value="/admin/comm/user/newSave.html",method=RequestMethod.POST)
	public String newSave(String loginName,String password,String realName,String phone,String email,
			Integer roleId,Integer agentId,int isAdmin,Integer[] provinceId,Integer[] cityId){
		
		CommRole commRole = commRoleService.getCommRoleById(roleId);
		CommAgent agent = commAgentService.getById(agentId);
		
		Date createtime = new Date();
		CommUser commUser = new CommUser();
		
		commUser.setCommAgent(agent);
		commUser.setCommRole(commRole);
		commUser.setLoginName(loginName);
		commUser.setPassword(Common.pwdMd5(password));
		commUser.setRealName(realName);
		commUser.setPhone(phone);
		commUser.setEmail(email);
		commUser.setIsAdmin(isAdmin);
		commUser.setCreatetime(createtime);
		commUser.setIsDelete(IS_NOT_DELETE);
		commUserService.save(commUser);
		
		if(provinceId!=null){
			for(int i=0; i< provinceId.length; i++){
				UserProvince userProvince = new UserProvince();
				userProvince.setProvince(provinceService.getCommProvinceById(provinceId[i]));
				userProvince.setUser(commUser);
				userProvinceRepository.save(userProvince);
			}
		}
		if(cityId!=null){
			for(int i=0; i< cityId.length; i++){
				UserCity userCity = new UserCity();
				userCity.setCity(cityService.getCommCityById(cityId[i]));
				userCity.setUser(commUser);
				userCityRepository.save(userCity);
			}
		}
		return "redirect:/admin/comm/user/list.html";
	}
	
	@RequestMapping("/admin/comm/user/edit-{id}")
	public String userEdit(HttpServletRequest request,@PathVariable Integer id){
		
		CommUser commUser = commUserService.getCommUserById(id);
		List<UserProvince> userProvinceList = userProvinceRepository.getUserProvinceByUser(commUser);
		List<UserCity> userCityList = userCityRepository.getUserCityByUser(commUser);
		List<CommRole> commRoleList = commRoleService.listAllNoAgent(false);
		List<CommAgent> agentList = new ArrayList<CommAgent>();
		List<CommAgent> agentList1 = commAgentService.listAll();
		List<CommProvince> provinceList = provinceService.listAll();
		List<CommCity> cityList = cityService.listAllCity(false);
		
		agentList.add(commUser.getCommAgent());
		for (CommAgent commAgent : agentList1) {
			CommUser user = commUserService.findByCommAgent(commAgent);
			if(user == null){
				agentList.add(commAgent);
			}
		}
		request.setAttribute("agentList", agentList);
		request.setAttribute("commRoleList", commRoleList);
		request.setAttribute("userProvinceList", userProvinceList.isEmpty()?null:userProvinceList);
		request.setAttribute("userCityList", userCityList.isEmpty()?null:userCityList);
		request.setAttribute("provinceList", provinceList);
		request.setAttribute("cityList", cityList);
		request.setAttribute("commUser", commUser);
				
		return "/admin/comm/user/user_edit.jsp";
	}
	
	@RequestMapping("/admin/comm/user/delete")
	public String delete(Integer id){
		
		CommUser commUser = commUserService.getCommUserById(id);
		List<UserProvince> userProvinceList = userProvinceRepository.getUserProvinceByUser(commUser);
		List<UserCity> userCityList = userCityRepository.getUserCityByUser(commUser);
		for (UserCity userCity : userCityList) {
			userCityRepository.delete(userCity);
		}
		for (UserProvince userProvince : userProvinceList) {
			userProvinceRepository.delete(userProvince);
		}
		commUserService.delete(commUser);
	
		return "redirect:/admin/comm/user/list.html";
		
	}
	
	@RequestMapping(value="/admin/comm/user/editSave.html",method=RequestMethod.POST)
	public String editSave(Integer id,String loginName,String realName,String phone,String email,Integer roleId,Integer agentId,
			int isAdmin,Integer[] provinceId,Integer[] cityId){
		
		CommUser commUser = commUserService.getCommUserById(id);
		CommRole commRole = commRoleService.getCommRoleById(roleId);
		CommAgent agent = commAgentService.getById(agentId);
		
		commUser.setCommAgent(agent);
		commUser.setCommRole(commRole);
		commUser.setLoginName(loginName);
		commUser.setRealName(realName);
		commUser.setPhone(phone);
		commUser.setEmail(email);
		commUser.setIsAdmin(isAdmin);
		commUserService.save(commUser);
		
		List<UserProvince> userProvinceList = userProvinceRepository.getUserProvinceByUser(commUser);
		for (UserProvince userProvince : userProvinceList) {
			userProvinceRepository.delete(userProvince);
		}
		if(provinceId!=null){
			for(int i=0; i< provinceId.length; i++){
				UserProvince userProvince = new UserProvince();
				userProvince.setProvince(provinceService.getCommProvinceById(provinceId[i]));
				userProvince.setUser(commUser);
				userProvinceRepository.save(userProvince);
			}
		}
		
		List<UserCity> userCityList = userCityRepository.getUserCityByUser(commUser);
		for (UserCity userCity : userCityList) {
			userCityRepository.delete(userCity);
		}
		if(cityId!=null){
			for(int i=0; i< cityId.length; i++){
				UserCity userCity = new UserCity();
				userCity.setCity(cityService.getCommCityById(cityId[i]));
				userCity.setUser(commUser);
				userCityRepository.save(userCity);
			}
		}
	
		return "redirect:/admin/comm/user/list.html";
		
	}
	
	@RequestMapping("/user/login")
	public String login(HttpServletRequest request){
		
		String loginName=request.getParameter("loginName");
		String password = request.getParameter("password");
		if(StringUtils.isBlank(loginName)||StringUtils.isBlank(password)){
			request.setAttribute("message","用户名或密码不能为空！");
			return "/login.jsp";
		}
		UsernamePasswordToken  token = new UsernamePasswordToken(loginName, password);  
		token.setRememberMe(true);
		Subject currentUser = SecurityUtils.getSubject();  
		try{
			currentUser.login(token);
		}catch(UnknownAccountException e){
			request.setAttribute("message", "用户名不存在！");
			return "/login.jsp";
		}catch(IncorrectCredentialsException e){
			request.setAttribute("message", "密码错误，登录失败！");
			return "/login.jsp";
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("message", "登录失败！");
			return "/login.jsp";
		}
		CommUser user = commUserService.findByLoginNameAndPassword(loginName,Common.pwdMd5(password));
		if(user==null){
			request.setAttribute("message","用户名或密码有误，请重新输入！");
			return "/login.jsp";
		}else{
			request.getSession().setAttribute(Constants.USER_KEY, user);
			if(user.getIsAdmin().equals(CommUser.IS_ADMIN)){
				return "/admin/admin_index.jsp";
			}else{
				List<CommAgent> agentList = commService.listCurrentAgent(request);
				request.getSession().setAttribute(Constants.PSI_TO_DO_NUM, commService.countPsiToDo(user.getCommRole()));
				List<CommProvince> cpList = commService.listCurrentProvince(request);
				if(cpList==null||cpList.size()<=0){
					request.getSession().setAttribute(Constants.IRETAIL_TO_DO_NUM, 0);
				}else{
					request.getSession().setAttribute(Constants.IRETAIL_TO_DO_NUM, commService.countIretailToDo(user.getCommRole(), commService.listCurrentProvince(request)));
				}
				if(agentList!=null&&agentList.size()>0){
					request.getSession().setAttribute(Constants.B2B_TO_DO_NUM, commService.countb2bToDo(agentList,commService.findCurrentUserByRequest(request).getCommRole()));
					request.getSession().setAttribute(Constants.B2I_TO_DO_NUM, commService.countb2iToDo(user.getCommRole(),agentList));
					request.getSession().setAttribute(Constants.B2C_TO_DO_NUM, commService.countb2cToDo(user.getCommRole(),agentList));
					
				}else{
					request.getSession().setAttribute(Constants.B2B_TO_DO_NUM, 0);
					request.getSession().setAttribute(Constants.B2I_TO_DO_NUM, 0);
					request.getSession().setAttribute(Constants.B2C_TO_DO_NUM,0);
				}
				return "/index.jsp";
			}
		}
	}
	
	@RequestMapping("/admin/logout")
	public String adminLogout(HttpServletRequest request){
		request.getSession().removeAttribute(Constants.USER_KEY);
		return "/login.jsp";
	}
	
	@RequestMapping("/logout")
	public String Logout(HttpServletRequest request){
		request.getSession().removeAttribute(Constants.USER_KEY);
		return "/login.jsp";
	}
	
	/**
	* @author: sheng.tian
	* @Description: 修改密码
	* @param @param request
	* @param @return   
	* @return String   
	* @throws
	 */
	@RequestMapping("/updatepwd.html")
	public String updatePWD(HttpServletRequest request){
		return "/updatepwd.jsp";
	}
	
	@RequestMapping("/updatepwd")
	@ResponseBody
	public String update(HttpServletRequest request){
		CommUser user = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		ApiWarp warp = new ApiWarp();
		try {
			if(user==null){
				warp.addError("当前用户已失效，请重新登陆！");
				return warp.toJsonString();
			}
			String newPwd = request.getParameter("password");
			if(StringUtils.isBlank(newPwd)){
				warp.addError("请输入新密码！");
				return warp.toJsonString();
			}
			user.setPassword(Common.pwdMd5(newPwd));
			commUserService.save(user);
			request.getSession().removeAttribute(Constants.USER_KEY);
		} catch (Exception e) {
			warp.addError("修改失败！");
		}
		return warp.toJsonString();
	}
	
}
