package com.activiti.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.activiti.entity.Employee;
import com.activiti.service.EmployeeService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping("/employee")
public class EmployController {
	@Autowired
	@Qualifier("employeeService")
	private EmployeeService employeeService;
	
	@Autowired
	private IdentityService identityService;
	
	@RequestMapping(value ="/login",method=RequestMethod.POST)
	    public String logon(String username, String password,HttpSession session) throws IOException {
		    String userName = username;
		    System.out.println(username+password);
		    boolean login = identityService.checkPassword(userName, password);
			//2：使用用户名作为查询条件，查询员工表，获取当前用户名对应的信息
			Employee emp = employeeService.findEmployeeByName(userName);
			//3：将查询的对象（惟一）放置到Session中
			session.setAttribute("user", emp);
			session.setAttribute("username", username);
			
	        return "/main";
     
	    }
	   
	   @RequestMapping("employeeHome")
	   public String employeeHome(HttpServletRequest request)
	   {
		   List<Employee> employees = employeeService.getAllEmployee();
		   request.setAttribute("employees", employees);
		   return "/employeeList";	
		   
	   }
	   
	   @RequestMapping("addEmployee")
	   public String addEmployee(Employee employee)
	   {
		   employeeService.addEmployee(employee);
		   return "redirect:../employee/employeeHome";
	   }
	   
	   @RequestMapping("deleteEmployee")
	   public String deleteEmployee(HttpServletRequest request)
	   {
		   long id = Long.parseLong(request.getParameter("id"));
		   employeeService.deleteEmployee(id);
		   return "redirect:../employee/employeeHome";		   
	   }
	   
	   @RequestMapping("updateEmployee")
	   public String updateEmployee(Employee employee){
		   employeeService.updateEmployee(employee);
		   return "redirect:../employee/employeeHome";
	   }
	   
}
