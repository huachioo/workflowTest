package com.activiti.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.activiti.dao.EmployeeDao;
import com.activiti.entity.Employee;
import com.activiti.service.EmployeeService;
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	@Qualifier("employeeDao")
	EmployeeDao employeeDao;

	@Override
	public Employee findEmployeeByName(String username) {
		// TODO Auto-generated method stub
		System.out.println(username);
		Employee employee = employeeDao.findEmployeeByName(username);
		return employee;
	}

}
