package com.activiti.service;

import java.util.List;

import com.activiti.entity.Employee;

public interface EmployeeService {

	Employee findEmployeeByName(String username);

	List<Employee> getAllEmployee();

	void addEmployee(Employee employee);

	void deleteEmployee(long id);

	void updateEmployee(Employee employee);

	Employee findEmployeeByID(long id);


}
