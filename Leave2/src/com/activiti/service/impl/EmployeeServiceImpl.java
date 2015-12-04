package com.activiti.service.impl;

import java.util.List;

import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.activiti.dao.EmployeeDao;
import com.activiti.entity.Employee;
import com.activiti.service.EmployeeService;
@Service("employeeService")
public class EmployeeServiceImpl extends UserEntityManager implements EmployeeService {
	@Autowired
	@Qualifier("employeeDao")
	EmployeeDao employeeDao;

	//覆盖identityService中的方法
	//identityService主要用来在流程发起前设置发起人，记录在流程历史中 identityService.setAuthenticatedUserId("kitty")；
	//查询方法logger.info(historyService.createHistoricProcessInstanceQuery().startedBy("ketty").singleResult().getProcessDefinitionId());
	 @Override
	 public Boolean checkPassword(String username, String password) {
		 Employee employee = employeeDao.findEmployeeByName(username);
		 boolean login = employee.getPassword().equals(password);
		 System.out.print(login);
	     return login;
	 }
	 
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
