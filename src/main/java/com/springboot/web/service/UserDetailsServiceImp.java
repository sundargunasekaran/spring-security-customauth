package com.springboot.web.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;

import com.springboot.web.dbcon.DBcon;
import com.springboot.web.model.UserModel;

public class UserDetailsServiceImp implements UserDetailsService {
	
	@Autowired
	DBcon dbcon;
	@Autowired
	private HttpServletRequest request;
	
	  @Override
	  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	    /*Here we are using dummy data, you need to load user data from
	     database or other third party application*/
	    UserModel user = findUserbyUername(username);
        String password = request.getParameter("password"); 
        System.out.println("--->?? "+password);
	    dbcon.printProperties();
	    UserBuilder builder = null;
	    if (user != null) {
	      builder = org.springframework.security.core.userdetails.User.withUsername(username);
	      builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
	      builder.roles(user.getRole());
	    } else {
	      throw new UsernameNotFoundException("User not found.");
	    }

	    return builder.build();
	  }

	  private UserModel findUserbyUername(String username) {
	    if(username.equalsIgnoreCase("admin")) {
	    	UserModel u = new UserModel();
	    	u.setUsername(username);
			u.setPassword("password");
			u.setRole("SUPERADMIN");
	      return u;
	    }
	    if(username.equalsIgnoreCase("rmadmin")) {
	    	UserModel u = new UserModel();
	    	u.setUsername(username);
			u.setPassword("password");
			u.setRole("RM_MANAGER");
	      return u;
	    }
	    if(username.equalsIgnoreCase("dmadmin")) {
	    	UserModel u = new UserModel();
	    	u.setUsername(username);
			u.setPassword("password");
			u.setRole("DM_MANAGER");
	      return u;
	    }
	    if(username.equalsIgnoreCase("guests")) {
	    	UserModel u = new UserModel();
	    	u.setUsername(username);
			u.setPassword("password");
			u.setRole("GUESTS_MANAGER");
	      return u;
	    }

	    return null;
	  }
	}