package com.springboot.web.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.springboot.web.dbcon.DBcon;

@Service
public class LoginService {

	@Autowired
	DBcon dbcon;

	public boolean validateUser(String userid, String password) throws SQLException {
		Connection con = dbcon.dbConnect();
		//step3 create the statement object  
		Statement stmt=con.createStatement();  
		//step4 execute query  
		ResultSet rs=stmt.executeQuery("select * from cmn_users where user_id = '"+userid+"' and password = '"+password+"'");  
		if(rs.next()){
			con.close();
			return true;
		}else{
			return true;
		}
		
	}
	
	public void test1(){
		System.out.println("printing test message");
	}

}
