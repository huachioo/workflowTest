package com.activiti.util;

import javax.servlet.http.HttpServletRequest;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.activiti.entity.Employee;
import com.activiti.service.EmployeeService;
public class ManageTaskHandler implements TaskListener{

	private static final long serialVersionUID = 1L;
	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println(delegateTask.getVariables()+"ddd");
		System.out.println("任务办理入分配");
		String userName = (String) delegateTask.getVariable("User");
		System.out.println("当前任务办理入"+userName);
		// TODO Auto-generated method stub
		//当前用户
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		//String name = (String) request.getSession().getAttribute("username");
		ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
		EmployeeService employeeService = (EmployeeService) ac.getBean("employeeService");
		//使用当前用户名查询用户的详细信息
		Employee emp = employeeService.findEmployeeByName(userName);
		System.out.println(emp.getName()+"emp");
		System.out.println(emp.getManager().getName()+"emp");
		//设置个人任务的办理人
		delegateTask.setAssignee(emp.getManager().getName());
		System.out.println(emp.getManager().getName()+"添加任务办理人");
		
	}

}
