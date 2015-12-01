package com.activiti.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.activiti.entity.Employee;
import com.activiti.service.EmployeeService;
@Controller
@RequestMapping("/employee")
public class EmployController {
	@Autowired
	@Qualifier("employeeService")
	private EmployeeService employeeService;
	
	   @RequestMapping(value ="/login")
	    public String logon(@RequestParam("username") String userName, @RequestParam("password") String password,HttpSession session) {
		    String username = userName;
			//2��ʹ���û�����Ϊ��ѯ��������ѯԱ������ȡ��ǰ�û�����Ӧ����Ϣ
			Employee emp = employeeService.findEmployeeByName(username);
			//3������ѯ�Ķ���Ωһ�����õ�Session��
			//SessionContext.setUser(emp,request);
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
		   System.out.println(employee.getManager());
		   employeeService.updateEmployee(employee);
		   System.out.println(employee.getManager());
		   return "redirect:../employee/employeeHome";
	   }
	   
}
