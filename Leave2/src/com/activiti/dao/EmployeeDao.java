package com.activiti.dao;

import com.activiti.entity.Employee;

public interface EmployeeDao {

	Employee findEmployeeByName(String username);

}
