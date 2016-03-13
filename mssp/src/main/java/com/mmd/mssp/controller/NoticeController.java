package com.mmd.mssp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NoticeController {

	@RequestMapping("/iretail/notice/download.html")
	public String list(HttpServletRequest request){
		return "/iretail/notice/download.jsp";
	}
}
