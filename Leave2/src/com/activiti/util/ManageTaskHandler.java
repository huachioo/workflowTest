package com.activiti.util;

import javax.servlet.http.HttpServletRequest;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.activiti.entity.Employee;
import com.activiti.service.EmployeeService;
@Component
public class ManageTaskHandler implements TaskListener{

	/**
	 * 
	 */
	@Autowired
	EmployeeService employeeService;
	
	private static final long serialVersionUID = 1L;
	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		//当前用户
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String name = (String) request.getSession().getAttribute("username");
		//WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext())				
		ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
		
		EmployeeService employeeService = (EmployeeService) ac.getBean("employeeService");
		System.out.println("获取当前用户:"+name);
		System.out.println(employeeService);
		//使用当前用户名查询用户的详细信息
		Employee emp = employeeService.findEmployeeByName(name);
		//设置个人任务的办理人
		delegateTask.setAssignee(emp.getManager().getName());
		
	}

}
