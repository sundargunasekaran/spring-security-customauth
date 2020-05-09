package com.springboot.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.springboot.web.service.TodoService;

@Controller
@RequestMapping("/todo/*")
@SessionAttributes("name")
public class TodoController {
	
	@Autowired
	TodoService service;
	
	@RequestMapping(value="/list-todos.do", method = RequestMethod.GET)
	public String showTodos(ModelMap model,HttpServletRequest request, HttpSession session){
		String name = (String) model.get("name");
		//model.put("todos", service.retrieveTodos(name));
		model.put("name", session.getAttribute("name").toString());
		model.put("role", session.getAttribute("role").toString());
		model.put("page", "home");
		if(session.getAttribute("role") != null && (session.getAttribute("role").toString().toLowerCase().contains("rm") || session.getAttribute("role").toString().toLowerCase().contains("guests") )){
			model.put("url","rm");
		}else{
			model.put("url","dm");
		}

		return "list-todos";
	}
}
