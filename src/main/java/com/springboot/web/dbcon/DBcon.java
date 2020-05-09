package com.springboot.web.dbcon;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.DataSource;

import org.apache.commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = "classpath:myapplication.properties")
public class DBcon implements EnvironmentAware {
	
    private Environment environment;
	@Override
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }
	
	/*@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
	    return new PropertySourcesPlaceholderConfigurer();
	}*/
    
    public void printProperties(){
        System.out.println("--> "+environment.getProperty("app.domain"));
    	String className = environment.getProperty("spring.oracleDatasource.driver-class-name");
        String dbUrl = environment.getProperty("spring.oracleDatasource.url");
        
        System.out.println(className);          // It will return properties value from application.properties file, If key will not available than it will return null
        System.out.println(dbUrl);
    	
    }
    
    /*@Bean
    public DataSource getDataSource() {
      DataSource dataSource = new DataSource();
      dataSource.setDriverClassName(env.getProperty("mysql.driver"));
      dataSource.setUrl(env.getProperty("mysql.jdbcUrl"));
      dataSource.setUsername(env.getProperty("mysql.username"));
      dataSource.setPassword(env.getProperty("mysql.password"));
      return dataSource;
    }*/
    
	public Connection dbConnect(){
		printProperties();
		String className = environment.getProperty("spring.oracleDatasource.driver-class-name");
        String dbUrl = environment.getProperty("spring.oracleDatasource.url");
        String username = environment.getProperty("spring.oracleDatasource.username");
        String password = environment.getProperty("spring.oracleDatasource.password");
		Connection con = null;
		try{  
			//step1 load the driver class  
			Class.forName(className);  
			  
			//step2 create  the connection object  
			con=DriverManager.getConnection( dbUrl,username,password);  
			  
			/*//step3 create the statement object  
			Statement stmt=con.createStatement();  
			  
			//step4 execute query  
			ResultSet rs=stmt.executeQuery("select * from cmn_users");  
			while(rs.next())  
			System.out.println(rs.getString("user_name"));  
			  
			//step5 close the connection object  
			con.close();  */
			  
			}catch(Exception e){ 
				e.printStackTrace();
			}
		return con; 
	}
}
