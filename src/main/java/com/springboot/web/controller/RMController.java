package com.springboot.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/rm/*")
public class RMController {

	
	@RequestMapping(value="/rmlist.do", method = RequestMethod.GET)
	@PreAuthorize("hasAutority('RM_MANAGER')")
	public String showDmList(ModelMap model,HttpServletRequest request, HttpSession session){
		String name = (String) model.get("name");
		//model.put("todos", service.retrieveTodos(name));
		model.put("name", session.getAttribute("name").toString());
		model.put("role", session.getAttribute("role").toString());
		model.put("page", "RM_Manager");
		return "list-todos";
	}
}
