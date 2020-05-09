package com.springboot.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springboot.web.service.TodoService;

@Controller
@RequestMapping("/dm/*")
public class DMController {
	
	@Autowired
	TodoService service;
	
	@RequestMapping(value="/dmlist.do", method = RequestMethod.GET)
	public String showDmList(ModelMap model,HttpServletRequest request, HttpSession session){
		String name = (String) model.get("name");
		//model.put("todos", service.retrieveTodos(name));
		model.put("name", session.getAttribute("name").toString());
		model.put("role", session.getAttribute("role").toString());
		model.put("page", "DM_Manager");
		return "list-todos";
	}

}
