package com.activiti.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.activiti.entity.Employee;

public class SessionContext {

	public static final String GLOBLE_USER_SESSION = "globle_user";
	
	public static void setUser(Employee user,HttpSession session){
		if(user!=null){
			session.setAttribute(GLOBLE_USER_SESSION, user);
		}else{
			session.removeAttribute(GLOBLE_USER_SESSION);
		}
	}
	
	public static Employee get(HttpServletRequest request){
		return (Employee) request.getSession().getAttribute(GLOBLE_USER_SESSION);
		
	}
}
