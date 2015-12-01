package com.activiti.service.impl;

import java.util.List;
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
		Employee employee = employeeDao.findEmployeeByName(username);
		return employee;
	}

	@Override
	public List<Employee> getAllEmployee() {
		// TODO Auto-generated method stub
		List<Employee> employees = employeeDao.findEmployeeList();
		return employees;
	}

	@Override
	public void addEmployee(Employee employee) {
		Employee manager = null;
		if(employee.getRole().equals("user"))
		{
			manager = employeeDao.findEmployById(2);
		}
		else if(employee.getRole().equals("manager")){
		   	manager = employeeDao.findEmployById(1);
		}
		employee.setManager(manager);
		employeeDao.saveEmployee(employee);
		
	}
	
	@Override
	public void deleteEmployee(long id){
		employeeDao.deleteEmployeeById(id);
	}
	
	@Override
	public void updateEmployee(Employee employee){
		Employee manager = null;
		if(employee.getRole().equals("user"))
		{
			manager = employeeDao.findEmployById(2);
		}
		else if(employee.getRole().equals("manager")){
		   	manager = employeeDao.findEmployById(1);
		}
		employee.setManager(manager);
		employeeDao.updateEmployee(employee);
	}

	@Override
	public Employee findEmployeeByID(long id) {
		Employee employee = employeeDao.findEmployById(id);
		return employee;
	}


}
