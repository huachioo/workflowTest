package com.activiti.dao;

import java.util.List;

import com.activiti.entity.Employee;
import com.activiti.entity.LeaveBill;

public interface LeaveBillDao {

	List<LeaveBill> findLeaveBillList(Employee employee);

	LeaveBill findLeaveBillById(Long id);

}
