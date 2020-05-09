package com.springboot.web.model;

public class UserModel {

	private int id;
	private String username;
	private String password;
	private String role;
	private String dept;
	private String email;
	
	public UserModel(){
		id = -1;
		username = "";
		role = "";
		email = "";
		dept = "";
	}
	public UserModel(String username, String password, String roles) {
	    this.username = username;
	    this.password = password;
	    this.role = roles;
	  }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
