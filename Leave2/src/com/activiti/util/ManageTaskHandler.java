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
		//��ǰ�û�
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String name = (String) request.getSession().getAttribute("username");
		//WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext())				
		ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
		
		EmployeeService employeeService = (EmployeeService) ac.getBean("employeeService");
		System.out.println("��ȡ��ǰ�û�:"+name);
		System.out.println(employeeService);
		//ʹ�õ�ǰ�û�����ѯ�û�����ϸ��Ϣ
		Employee emp = employeeService.findEmployeeByName(name);
		//���ø�������İ�����
		delegateTask.setAssignee(emp.getManager().getName());
		
	}

}
