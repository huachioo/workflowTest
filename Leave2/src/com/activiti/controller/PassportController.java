package com.activiti.controller;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.activiti.entity.Employee;
import com.activiti.service.EmployeeService;

@Controller
@RequestMapping("passport")
public class PassportController {
	
	@Autowired
	@Qualifier("employeeService")
	private EmployeeService employeeService;
	
	@RequestMapping(value="/returnMain")
	public Object returnMain() {
			return "main";
	}
	
	@RequestMapping("addLeaveBill")
	public Object addLeaveBill()
	{
		return "addLeaveBill";
	}
	 
	@RequestMapping(value="/workflow")
	public Object defineWorkFlow(HttpServletRequest request) {
			return "defineWorkFlow";
	}
	
	@RequestMapping(value="/addEmployee")
	public Object addEmployee(HttpServletRequest request) {
			return "addEmployee";
	}
	
	@RequestMapping("/updateEmployee")
	public ModelAndView updateEmployee(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("updateEmployee");
		long id = Long.parseLong(request.getParameter("id"));
		Employee employee = employeeService.findEmployeeByID(id);
		mav.addObject("employee", employee);
		return mav;
	}

}
