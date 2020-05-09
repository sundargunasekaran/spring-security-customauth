package com.springboot.web.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.springboot.web.dbcon.DBcon;
import com.springboot.web.model.UserModel;

public class CustomAuthenticationProvider implements AuthenticationProvider {

/*	@Autowired
    private UserService userService;
	@Autowired
	DBcon dbcon;*/
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try { 
		String username = authentication.getName();
	        // You can get the password here
	        String password = authentication.getCredentials().toString();
	        
	        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
	        UserModel user = this.getLoginUserDetails(username, password);
			System.out.println(user.getRole());
			
	        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_SUPERADMIN"));
	        if (user.getUsername() != null) {
	            Authentication auth = new UsernamePasswordAuthenticationToken(username,password,grantedAuthorities);

	            return auth;
	        }
	       /* UserDetails user = userService.loadUserByUsername(username);
	        // Your custom authentication logic here
	        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
	        if (username.equals("admin") && password.equals("password")) {
	            Authentication auth = new UsernamePasswordAuthenticationToken(username,password,authorities);

	            return auth;
	        }*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return null;
	}
	
	public UserModel getLoginUserDetails(String userid, String password) throws SQLException {
		Connection con = new DBcon().dbConnect();
		UserModel u = new UserModel();
		Statement stmt=con.createStatement();  
		System.out.println("CALLING");
		ResultSet rs=stmt.executeQuery("select user_id,password,email_id from cmn_users where user_id = '"+userid+"' and password = '"+password+"'");  
		if(rs.next()){
			u.setUsername(rs.getString("user_id"));
			u.setPassword(rs.getString("password"));
			u.setEmail(rs.getString("email_id"));
			u.setRole("SUPERADMIN");
		}else{
			con.close();
			return null;
		}
		con.close();
		return u;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
