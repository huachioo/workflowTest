package com.activiti.controller;

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
			System.out.println(emp.getName());
			System.out.println(emp.getEmail());
	        return "/main";
     
	    }

}
