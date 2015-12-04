package com.activiti.service.impl;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.activiti.service.EmployeeService;
public class EmployeeManagerFactory implements SessionFactory{

	private EmployeeService employeeService;
	 
	@Autowired
    public void setUserEntityManager(EmployeeService employeeService) {  
        this.employeeService = employeeService;  
    }  
	
	@Override
	public Class<?> getSessionType() {
		// TODO Auto-generated method stub
		return UserIdentityManager.class;
	}

	@Override
	public Session openSession() {
		// TODO Auto-generated method stub
		return (Session) employeeService;
	}

}
