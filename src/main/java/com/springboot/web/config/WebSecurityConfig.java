package com.springboot.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springboot.web.service.CustomAuthenticationProvider;
import com.springboot.web.service.UserDetailsServiceImp;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/*@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/welcome").hasAnyRole("USER", "ADMIN")
				.antMatchers("/getEmployees").hasAnyRole("USER", "ADMIN").antMatchers("/addNewEmployee")
				.hasAnyRole("ADMIN").anyRequest().authenticated()
				.and().formLogin().loginPage("/login").permitAll()
				.and().logout().permitAll();

		http.csrf().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
		authenticationMgr.inMemoryAuthentication().withUser("admin").password("admin").authorities("ROLE_USER").and()
				.withUser("javainuse").password("javainuse").authorities("ROLE_USER", "ROLE_ADMIN");
	}*/
/*
	@Override
	public void configure(AuthenticationManagerBuilder auth)
			throws Exception {

		// in-memory authentication
		// auth.inMemoryAuthentication().withUser("pankaj").password("pankaj123").roles("USER");

		// using custom UserDetailsService DAO
		// auth.userDetailsService(new AppUserDetailsServiceDAO());

		// using JDBC
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx
				.lookup("java:/comp/env/jdbc/MyLocalDB");

		final String findUserQuery = "select username,password,enabled "
				+ "from Employees " + "where username = ?";
		final String findRoles = "select username,role " + "from Roles "
				+ "where username = ?";
		
		auth.jdbcAuthentication().dataSource(ds)
				.usersByUsernameQuery(findUserQuery)
				.authoritiesByUsernameQuery(findRoles);
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
                // Spring Security should completely ignore URLs ending with .html
                .antMatchers("/*.html");
    }
*/


	@Bean
	  public UserDetailsService userDetailsService() {
	    return new UserDetailsServiceImp();
	  };
	  
	  @Bean
	  public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  };
	  
	 /* @Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/**");
		}*/
	  
	 @Override
	  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	  }
	  
	 /* @Override
	    @Autowired
	    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.authenticationProvider(new CustomAuthenticationProvider());
	    }*/
	  
	 /* @Override
	   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	      auth.inMemoryAuthentication()
	      .withUser("admin").password("admin123").roles("USER");
	   }*/
	  
	  
	  
	   @Override
	   protected void configure(HttpSecurity http) throws Exception {
	    String temp = "DM:DM_MANAGER,RM:RM_MANAGER,RM:GUESTS_MANAGER";
		 String[] roles = temp.split(",");
		 for(String a : roles){
			 String url = a.split(":")[0];
			 String role = a.split(":")[1];
	    	 http.authorizeRequests().antMatchers("/"+url.toLowerCase()+"/**").hasRole(role.toUpperCase()).anyRequest().authenticated();
	    	// http.authorizeRequests().antMatchers("/dm/**").hasRole("DM_MANAGER").anyRequest().authenticated();
	     }
		  		   
		   //http.authorizeRequests().anyRequest().hasAnyRole("USER","SUPERADMIN");
		  http.authorizeRequests().antMatchers("/dm/**").hasRole("DM_MANAGER").anyRequest().authenticated();
		  http.authorizeRequests().antMatchers("/todo/**").permitAll()
	      .and()
	      .authorizeRequests().antMatchers("/login**").permitAll()
	      .and()
	      .formLogin()
	      .loginPage("/login") // Specifies the login page URL
	      .loginProcessingUrl("/login.do") // Specifies the URL,which is used 
	                                     //in action attribute on the <from> tag
	    //  .usernameParameter("username")  // Username parameter, used in name attribute
	                                    // of the <input> tag. Default is 'username'.
	   //   .passwordParameter("password")  // Password parameter, used in name attribute
	                                    // of the <input> tag. Default is 'password'.
	      .successHandler((req,res,auth)->{    //Success handler invoked after successful authentication
	    	  String role = "";
	    	  for (GrantedAuthority authority : auth.getAuthorities()) {
	            System.out.println(authority.getAuthority());
	            role = authority.getAuthority();
	         }
	         System.out.println(auth.getName()+":::"+auth.getDetails()+"-:::-"+auth.getPrincipal());
	         req.getSession().setAttribute("name", auth.getName());
	         req.getSession().setAttribute("role", role);
	         res.sendRedirect("/todo/list-todos.do"); // Redirect user to index/home page
	      })
//	    .defaultSuccessUrl("/")   // URL, where user will go after authenticating successfully.
	                                // Skipped if successHandler() is used.
	      .failureHandler((req,res,exp)->{  // Failure handler invoked after authentication failure
	         String errMsg="";
	         if(exp.getClass().isAssignableFrom(BadCredentialsException.class)){
	            errMsg="Invalid username or password.";
	         }else{
	            errMsg="Unknown error - "+exp.getMessage();
	         }
	         req.getSession().setAttribute("message", errMsg);
	        // req.getSession().invalidate();
	         res.sendRedirect("/login"); // Redirect user to login page with error message.
	      })
//	    .failureUrl("/login?error")   // URL, where user will go after authentication failure.
	                                    //  Skipped if failureHandler() is used.
	      .permitAll() // Allow access to any URL associate to formLogin()
	      .and()
	      .logout()
	      .logoutUrl("/logout.do")   // Specifies the logout URL, default URL is '/logout'
	      .logoutSuccessHandler((req,res,auth)->{   // Logout handler called after successful logout 
	         req.getSession().setAttribute("message", "You are logged out successfully.");
	         res.sendRedirect("/login.do"); // Redirect user to login page with message.
	      })
//	    .logoutSuccessUrl("/login") // URL, where user will be redirect after successful
	                                  //  logout. Ignored if logoutSuccessHandler() is used.
	      .permitAll() // Allow access to any URL associate to logout()
	      .and()
	      .csrf().disable(); // Disable CSRF support
	   }
	
}