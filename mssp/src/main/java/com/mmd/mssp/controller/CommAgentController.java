package com.mmd.mssp.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmd.mssp.comm.ApiWarp;
import com.mmd.mssp.comm.Common;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.DescSetting;
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
public class CommAgentController {

	private static final int IS_NOT_DELETE = 0;
	private static final int IS_DELETE = 1;
	private static final int PAGE_SIZE = 50;

	@Resource
	CommAgentService commAgentService;
	@Resource
	CommCityService commCityService;
	@Resource
	CommRoleService commRoleService;
	@Resource
	CommUserService commUserService;
	@Resource
	CommProvinceService provinceService;
	@Resource
	UserProvinceRepository userProvinceRepository;
	@Resource
	UserCityRepository userCityRepository;
	
	@Resource
	CommService commService;
	
	@RequestMapping("/admin/comm/agent/list.html")
	public String list(HttpServletRequest request, @RequestParam(required=false) Integer page){
		List<CommProvince> provinceList = provinceService.listAll();
		List<CommCity> cityList = commCityService.listAllCity(false);
		Paging<CommAgent> paging = commAgentService.findByIsDelete(false, page,PAGE_SIZE);
		request.setAttribute("provinceList", provinceList);
		request.setAttribute("cityList", cityList);
		request.setAttribute("pagelist", paging);
		return "/admin/comm/agent/agent.jsp";
	}
	
	@RequestMapping("/admin/comm/agent/search")
	public String search(HttpServletRequest request,
			@RequestParam(required = false) Integer page, String irName,
			Integer[] provinceId, Integer[] cityId, Integer[] verifyStatus,
			Integer[] irStatus) throws Exception {
		List<CommProvince> provinceList = provinceService.listAll();
		List<CommCity> cityList = commCityService.listAllCity(false);
		Paging<CommAgent> paging = commAgentService.findBySearch(WebUtil.getHttpQueryParam(request, "irName"), provinceId, cityId, verifyStatus, irStatus, page, PAGE_SIZE);
		request.setAttribute("provinceList", provinceList);
		request.setAttribute("cityList", cityList);
		request.setAttribute("pagelist", paging);
		return "/admin/comm/agent/agent.jsp";
	}
	
	@RequestMapping("/admin/iretail/iretailstore/list.html")
	public String IretailStoreList(HttpServletRequest request, @RequestParam(required=false) Integer page){
		Paging<CommAgent> paging = commAgentService.findIretailStore(false, page,PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		return "/admin/iretail/store/iretail_store.jsp";
	}
	
	@RequestMapping("/admin/iretail/iretailstore/new.html")
	public String newIretail(HttpServletRequest request){
		List<CommProvince> provinceList = provinceService.listAll();
		List<CommCity> commCityList = commCityService.listAllCity(false);
		List<CommAgent> commAgentList = commAgentService.fandAll(false);
		request.setAttribute("commCityList", commCityList);
		request.setAttribute("provinceList", provinceList);
		request.setAttribute("commAgentList", commAgentList);
		return "/admin/iretail/store/iretail_store_new.jsp";
		
	}
	
	@RequestMapping("/admin/iretail/iretailstore/edit-{id}")
	public String storeEdit(HttpServletRequest request,@PathVariable Integer id){
		
		CommAgent commAgent = commAgentService.getById(id);
		List<CommCity> commCityList = commCityService.listAllCity(false);
		List<CommAgent> agentList = commAgentService.listAll();
		List<CommProvince> provinceList = provinceService.listAll();
		
		CommUser commUser = commUserService.findByCommAgent(commAgent);
		List<UserProvince> userProvinceList = userProvinceRepository.getUserProvinceByUser(commUser);
		
		request.setAttribute("commCityList", commCityList);
		request.setAttribute("agentList", agentList);
		request.setAttribute("commUser", commUser);
		request.setAttribute("commAgent", commAgent);
		request.setAttribute("provinceList", provinceList);
		request.setAttribute("userProvince", userProvinceList.isEmpty()?null:userProvinceList.get(0));
		return "/admin/iretail/store/iretail_store_edit.jsp";
	}
	
	@RequestMapping(value="/admin/comm/iretailstore/editSave.html",method=RequestMethod.POST)
	public String iretailEditSave(Integer id,String irName,Integer agentId,String phone,String email,String address,Integer isLoss,String closeMsg,Integer verifyStatus){
		CommAgent commAgent = commAgentService.getById(id);
		commAgent.setIrName(irName);
		commAgent.setpId(agentId);
		commAgent.setProvince(commAgentService.getById(agentId).getProvince());
		commAgent.setCommCity(commAgentService.getById(agentId).getCommCity());
		commAgent.setPhone(phone);
		commAgent.setEmail(email);
		commAgent.setVerifyStatus(verifyStatus);
		commAgent.setIrStatus(isLoss);
		if(isLoss == 2){
			commAgent.setCloseMsg(closeMsg);
		}
		commAgentService.save(commAgent);
		return "redirect:/admin/iretail/iretailstore/list.html";
	}
	
	@RequestMapping(value="/admin/iretail/iretailstore/newSave.html",method=RequestMethod.POST)
	public String iretailNewSave(String irName,Integer commAgentId,String phone,String email,String address){
		CommAgent agent = commAgentService.getById(commAgentId);
		Date createtime = new Date();
		CommAgent commAgent = new CommAgent();
		commAgent.setIrName(irName);
		commAgent.setpId(agent.getId());
		commAgent.setScore(0);
		commAgent.setIrStatus(0);
		commAgent.setIrLevel(6);
		commAgent.setVerifyStatus(0);
		commAgent.setType(0);
		commAgent.setProvince(agent.getProvince());
		commAgent.setCommCity(agent.getCommCity());
		commAgent.setAddress(address);
		commAgent.setPhone(phone);
		commAgent.setEmail(email);
		commAgent.setCreatetime(createtime);
		commAgent.setIsDelete(IS_NOT_DELETE);
		commAgentService.save(commAgent);
		return "redirect:/admin/iretail/iretailstore/list.html";
	}
	
	@RequestMapping("/admin/iretail/iretailstore/delete")
	public String deleteIretailStore(Integer id){
		
		CommAgent commAgent = commAgentService.getById(id);
		CommUser commUser = commUserService.findByCommAgent(commAgent);
		if(commUser!=null){
			UserProvince userProvince = userProvinceRepository.getUserProvinceByUser(commUser).get(0);
			UserCity userCity = userCityRepository.getUserCityByUser(commUser).get(0);
			userProvinceRepository.delete(userProvince);
			userCityRepository.delete(userCity);
			commUserService.delete(commUser);
		}
		commAgent.setIsDelete(IS_DELETE);
		commAgentService.save(commAgent);
	
		return "redirect:/admin/iretail/iretailstore/list.html";
		
	}
	
	@RequestMapping("/admin/iretail/iretailstore/search")
	public String SearchIretailStore(HttpServletRequest request,String irName,Integer storeId, @RequestParam(required=false) Integer page){
		Paging<CommAgent> paging = commAgentService.searchIretailStoreByNameOrId(irName,storeId, page,PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		request.setAttribute("irName", irName);
		request.setAttribute("storeId", storeId);
		return "/admin/iretail/store/iretail_store.jsp";
	}
	
	@RequestMapping("/admin/comm/agent/new.html")
	public String newarea(HttpServletRequest request){
		List<CommProvince> provinceList = provinceService.listAll();
		List<CommCity> commCityList = commCityService.listAllCity(false);
		
		request.setAttribute("commCityList", commCityList);
		request.setAttribute("provinceList", provinceList);
		return "/admin/comm/agent/agent_new.jsp";
		
	}
	
	@RequestMapping(value="/admin/comm/agent/newSave.html",method=RequestMethod.POST)
	public String newSave(String irName,String loginName,String realName,Integer provinceId,Integer cityId,String phone,String email,String address){
		
		Date createtime = new Date();
		String password = "123456";
		CommAgent commAgent = new CommAgent();
		CommUser commUser = new CommUser();
		UserProvince userProvince = new UserProvince();
		UserCity userCity = new UserCity();
		CommRole commRole = commRoleService.getCommRoleById(CommRole.DAILISHANG);
		CommProvince province = provinceService.getCommProvinceById(provinceId);
		CommCity commCity = commCityService.getCommCityById(cityId);
		
		commAgent.setIrName(irName);
		commAgent.setScore(0);
		commAgent.setIrStatus(0);
		commAgent.setIrLevel(5);
		commAgent.setVerifyStatus(1);
		commAgent.setType(0);
		commAgent.setProvince(province);
		commAgent.setCommCity(commCity);
		commAgent.setAddress(address);
		commAgent.setCreatetime(createtime);
		commAgent.setIsDelete(IS_NOT_DELETE);
		
		commAgentService.save(commAgent);
		
		commUser.setLoginName(loginName);
		commUser.setPassword(Common.pwdMd5(password));
		commUser.setRealName(realName);
		commUser.setCommRole(commRole);
		commUser.setPhone(phone);
		commUser.setEmail(email);
		commUser.setIsAdmin(0);
		commUser.setCommAgent(commAgent);
		commUser.setIsDelete(IS_NOT_DELETE);
		commUser.setCreatetime(createtime);
		commUserService.save(commUser);
		
		userProvince.setProvince(province);
		userProvince.setUser(commUser);
		userProvinceRepository.save(userProvince);
		
		userCity.setCity(commCity);
		userCity.setUser(commUser);
		userCityRepository.save(userCity);
		
		return "redirect:/admin/comm/agent/list.html";
		
	}
	
	@RequestMapping("/admin/comm/agent/edit-{id}")
	public String agentEdit(HttpServletRequest request,@PathVariable Integer id){
		
		CommAgent commAgent = commAgentService.getById(id);
		List<CommCity> commCityList = commCityService.listAllCity(false);
		List<CommAgent> agentList = commAgentService.listAll();
		List<CommProvince> provinceList = provinceService.listAll();
		
		CommUser commUser = commUserService.findByCommAgent(commAgent);
		List<UserProvince> userProvinceList = userProvinceRepository.getUserProvinceByUser(commUser);
		
		request.setAttribute("commCityList", commCityList);
		request.setAttribute("agentList", agentList);
		request.setAttribute("commUser", commUser);
		request.setAttribute("commAgent", commAgent);
		request.setAttribute("provinceList", provinceList);
		request.setAttribute("userProvince", userProvinceList.isEmpty()?null:userProvinceList.get(0));
		
		if(commAgent.getIrLevel()==5){
			return "/admin/comm/agent/agent_edit.jsp";
		}else{
			return "/admin/comm/agent/sec_agent_edit.jsp";
		}
				
	}
	
	@RequestMapping(value="/admin/comm/agent/editSave.html",method=RequestMethod.POST)
	public String editSave(Integer id,String irName,String loginName,String realName,Integer provinceId,Integer cityId,Integer userId,String phone,String email,String address){
		
		CommAgent commAgent = commAgentService.getById(id);
		CommProvince province = provinceService.getCommProvinceById(provinceId);
		CommCity commCity = commCityService.getCommCityById(cityId);
		
		
		CommUser commUser = commUserService.getCommUserById(userId);
		UserProvince userProvince = userProvinceRepository.getUserProvinceByUser(commUser).get(0);
		UserCity userCity = userCityRepository.getUserCityByUser(commUser).get(0);
				
		commUser.setLoginName(loginName);
		commUser.setRealName(realName);
		commUser.setPhone(phone);
		commUser.setEmail(email);
		commUserService.save(commUser);
		
		commAgent.setIrName(irName);
		commAgent.setAddress(address);
		commAgent.setProvince(province);
		commAgent.setCommCity(commCity);
		commAgentService.save(commAgent);
		
		userProvince.setProvince(province);
		userProvince.setUser(commUser);
		userProvinceRepository.save(userProvince);
		
		userCity.setCity(commCity);
		userCity.setUser(commUser);
		userCityRepository.save(userCity);
	
		return "redirect:/admin/comm/agent/list.html";
	}
	
	@RequestMapping("/admin/comm/agent/delete")
	public String delete(Integer id){
		
		CommAgent commAgent = commAgentService.getById(id);
		CommUser commUser = commUserService.findByCommAgent(commAgent);
		if(commUser!=null){
			UserProvince userProvince = userProvinceRepository.getUserProvinceByUser(commUser).get(0);
			UserCity userCity = userCityRepository.getUserCityByUser(commUser).get(0);
			userProvinceRepository.delete(userProvince);
			userCityRepository.delete(userCity);
			commUserService.delete(commUser);
		}
		commAgent.setIsDelete(IS_DELETE);
		commAgentService.save(commAgent);
	
		return "redirect:/admin/comm/agent/list.html";
		
	}
	//二级代理商增删改
	@RequestMapping("/admin/comm/secagent/new.html")
	public String newsecarea(HttpServletRequest request){
		
		List<CommAgent> agentList = commAgentService.listAll();
		
		request.setAttribute("agentList", agentList);
		return "/admin/comm/agent/sec_agent_new.jsp";
		
	}
	
	@RequestMapping(value="/admin/comm/scoagent/newSave",method=RequestMethod.POST)
	public String secSave(String irName,Integer pId, String phone,String email,String address,Integer isUser,String loginName,String realName){
		
		CommAgent commAgent = new CommAgent();
		
		Date createtime = new Date();
		String password = "123456";
		CommAgent pAgent = commAgentService.getById(pId);
		CommRole commRole = commRoleService.getCommRoleById(3);
		CommCity commCity = pAgent.getCommCity();
		CommProvince province = pAgent.getProvince();
		
		commAgent.setIrName(irName);
		commAgent.setScore(0);
		commAgent.setIrStatus(0);
		commAgent.setIrLevel(7);//5总代，7二级代理
		commAgent.setpId(pId);
		commAgent.setVerifyStatus(1);
		commAgent.setType(0);
		commAgent.setPhone(phone);
		commAgent.setEmail(email);
		commAgent.setProvince(province);
		commAgent.setCommCity(commCity);
		commAgent.setAddress(address);
		commAgent.setCreatetime(createtime);
		commAgent.setIsDelete(IS_NOT_DELETE);
		
		commAgentService.save(commAgent);
		
		if(isUser==1){
			CommUser commUser = new CommUser();
			UserProvince userProvince = new UserProvince();
			UserCity userCity = new UserCity();
			commUser.setCommAgent(commAgent);
			commUser.setPassword(Common.pwdMd5(password));
			commUser.setCommRole(commRole);
			commUser.setLoginName(loginName);
			commUser.setRealName(realName);
			commUser.setPhone(phone);
			commUser.setEmail(email);
			commUser.setCreatetime(createtime);
			commUser.setIsAdmin(0);
			commUser.setIsDelete(IS_NOT_DELETE);
			commUserService.save(commUser);

			userProvince.setProvince(province);
			userProvince.setUser(commUser);
			userProvinceRepository.save(userProvince);
			
			userCity.setCity(commCity);
			userCity.setUser(commUser);
			userCityRepository.save(userCity);
		}
		return "redirect:/admin/comm/agent/list.html";
	}
	
	@RequestMapping(value="/admin/comm/scoagent/editSave",method=RequestMethod.POST)
	public String secEditSave(Integer id,String irName,Integer pId, String phone,String email,String address,Integer isUser,String loginName,String realName,Integer userId){
		
		CommAgent commAgent = commAgentService.getById(id);
		
		Date createtime = new Date();
		String password = "123456";
		CommAgent pAgent = commAgentService.getById(pId);
		CommRole commRole = commRoleService.getCommRoleById(3);
		CommProvince province = pAgent.getProvince();
		CommCity commCity = pAgent.getCommCity();
		
		commAgent.setIrName(irName);
		commAgent.setpId(pId);
		commAgent.setPhone(phone);
		commAgent.setEmail(email);
		commAgent.setProvince(province);
		commAgent.setCommCity(commCity);
		commAgent.setAddress(address);
		
		commAgentService.save(commAgent);
		CommUser commUser = new CommUser();
		UserProvince userProvince = new UserProvince();
		UserCity userCity = new UserCity();
		if(isUser==1){
			if(userId!=null){
				commUser = commUserService.getCommUserById(userId);
				commUser.setLoginName(loginName);
				commUser.setRealName(realName);
			}else{
				commUser.setCommAgent(commAgent);
				commUser.setPassword(Common.pwdMd5(password));
				commUser.setCommRole(commRole);
				commUser.setLoginName(loginName);
				commUser.setRealName(realName);
				commUser.setPhone(phone);
				commUser.setEmail(email);
				commUser.setCreatetime(createtime);
				commUser.setIsAdmin(0);
				commUser.setIsDelete(IS_NOT_DELETE);
				commUserService.save(commUser);
			}
			userProvince.setProvince(province);
			userProvince.setUser(commUser);
			userProvinceRepository.save(userProvince);
			
			userCity.setCity(commCity);
			userCity.setUser(commUser);
			userCityRepository.save(userCity);
		}else{
			if(userId!=null){
				commUser = commUserService.getCommUserById(userId);
				commUserService.delete(commUser);
				userProvince = userProvinceRepository.getUserProvinceByUser(commUser).get(0);
				userProvinceRepository.delete(userProvince);
				userCity = userCityRepository.getUserCityByUser(commUser).get(0);
				userCityRepository.delete(userCity);
			}
		}
		return "redirect:/admin/comm/agent/list.html";
	}
	
	@RequestMapping(value="/admin/agent/isExists",method=RequestMethod.POST)
	@ResponseBody
	public String checkIsDesc(HttpServletRequest request,String province,String city){
		ApiWarp apiWarp = new ApiWarp();
		String agentName="";
		CommAgent agent=null;
		try {
				//验证城市（非Iretail系统）代理商
				if(province.equals("请选择")&&!city.equals("请选择")){
				agent=commService.findAgentByCity(commCityService.getCommCityById(Integer.parseInt(city)).getCityName());
				 //验证省份（Iretail系统）代理商	
				}else if(!province.equals("请选择")&&city.equals("请选择")){
					agent = commService.findAgentByProvince(provinceService.getCommProvinceById(Integer.parseInt(province)));
				}
				if(agent!=null){
					agentName=agent.getIrName();
				}
		} catch (Exception e) {
			apiWarp.addError("验证代理商发生异常");
			e.printStackTrace();
		}
		return apiWarp.putData(agentName).toJsonString();
	}
}
