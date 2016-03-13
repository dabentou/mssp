package com.mmd.mssp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mmd.mssp.comm.Constants;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommCustomer;
import com.mmd.mssp.service.CommCustomerService;
import com.mmd.mssp.service.CommService;

@Controller
public class CommCustomerController {

	@Resource
	CommService commService;
	
	@Resource
	CommCustomerService customerService;
	
	@RequestMapping("/b2i/customer/new.html")
	public String newarea(HttpServletRequest request){
		return "/b2i/customer_new.jsp";
	}
	
	@RequestMapping("/b2i/customer/list.html")
	public String  listCustomer(HttpServletRequest request){
		CommAgent agent = commService.findCurrentAgent(request);
		List<CommCustomer> list = customerService.listAllByAgentAndType(agent, Constants.B2I_CUSTOMER);
		request.setAttribute("list", list);
		return "/b2i/customer_list.jsp";
	}
	
	@RequestMapping("/b2i/customer/edit")
	public String  editCustomer(HttpServletRequest request,Integer id){
		CommCustomer customer = customerService.getCustomerById(id);
		request.setAttribute("customer", customer);
		return "/b2i/customer_edit.jsp";
	}
	
	@RequestMapping(value="/b2i/customer/editSave",method=RequestMethod.POST)
	public String editSave(HttpServletRequest request,Integer id,String name,String person,String phone,String mobile,String zipcode,String address){
		CommCustomer customer = customerService.getCustomerById(id);
		customer.setName(name);
		customer.setPerson(person);
		customer.setPhone(phone);
		customer.setMobile(mobile);
		customer.setZipcode(zipcode);
		customer.setAddress(address);
		customerService.save(customer);
		return "redirect:/b2i/customer/list.html";
	}
	
	@RequestMapping("/b2i/customer/deleate")
	public String  deleteCustomer(HttpServletRequest request,Integer id){
		CommCustomer customer = customerService.getCustomerById(id);
		customerService.delete(customer);
		return "redirect:/b2i/customer/list.html";
	}
	
	@RequestMapping(value="/b2i/customer/save",method=RequestMethod.POST)
	public String newSave(HttpServletRequest request,String name,String person,String phone,String mobile,String zipcode,String address){
			CommAgent agent = commService.findCurrentAgent(request);
			CommCustomer customer = new CommCustomer();
			customer.setName(name);
			customer.setPerson(person);
			customer.setPhone(phone);
			customer.setMobile(mobile);
			customer.setZipcode(zipcode);
			customer.setAddress(address);
			customer.setAgent(agent);
			customer.setType(Constants.B2I_CUSTOMER);
			customerService.save(customer);
		return "redirect:/b2i/customer/list.html";
	}
	
	//B2B客户增删改查
	@RequestMapping("/b2b/customer/new.html")
	public String b2bcusAdd(HttpServletRequest request){
		return "/b2b/customer_new.jsp";
	}
	
	@RequestMapping("/b2b/customer/list.html")
	public String  listB2bCustomer(HttpServletRequest request){
		CommAgent agent = commService.findCurrentAgent(request);
		List<CommCustomer> list = customerService.listAllByAgentAndType(agent, Constants.B2B_CUSTOMER);
		request.setAttribute("list", list);
		return "/b2b/customer_list.jsp";
	}
	
	@RequestMapping("/b2b/customer/edit")
	public String  editB2bCustomer(HttpServletRequest request,Integer id){
		CommCustomer customer = customerService.getCustomerById(id);
		request.setAttribute("customer", customer);
		return "/b2b/customer_edit.jsp";
	}
	
	@RequestMapping(value="/b2b/customer/editSave",method=RequestMethod.POST)
	public String editB2bSave(HttpServletRequest request,Integer id,String name,String person,String phone,String mobile,String zipcode,String address){
		CommCustomer customer = customerService.getCustomerById(id);
		customer.setName(name);
		customer.setPerson(person);
		customer.setPhone(phone);
		customer.setMobile(mobile);
		customer.setZipcode(zipcode);
		customer.setAddress(address);
		customerService.save(customer);
		return "redirect:/b2b/customer/list.html";
	}
	
	@RequestMapping("/b2b/customer/deleate")
	public String  deleteB2bCustomer(HttpServletRequest request,Integer id){
		CommCustomer customer = customerService.getCustomerById(id);
		customerService.delete(customer);
		return "redirect:/b2b/customer/list.html";
	}
	
	@RequestMapping(value="/b2b/customer/save",method=RequestMethod.POST)
	public String newB2bSave(HttpServletRequest request,String name,String person,String phone,String mobile,String zipcode,String address){
			CommAgent agent = commService.findCurrentAgent(request);
			CommCustomer customer = new CommCustomer();
			customer.setName(name);
			customer.setPerson(person);
			customer.setPhone(phone);
			customer.setMobile(mobile);
			customer.setZipcode(zipcode);
			customer.setAddress(address);
			customer.setAgent(agent);
			customer.setType(Constants.B2B_CUSTOMER);
			customerService.save(customer);
		return "redirect:/b2b/customer/list.html";
	}
}
