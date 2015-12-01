package com.activiti.dao;

import java.util.List;

import com.activiti.entity.Employee;

public interface EmployeeDao {

	Employee findEmployeeByName(String username);

	List<Employee> findEmployeeList();

	void saveEmployee(Employee employee);

	Employee findEmployById(long id);

	void deleteEmployeeById(Long id);

	void updateEmployee(Employee employee);

}
