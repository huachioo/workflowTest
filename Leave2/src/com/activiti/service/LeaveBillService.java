package com.activiti.service;

import java.util.List;

import com.activiti.entity.Employee;
import com.activiti.entity.LeaveBill;

public interface LeaveBillService {

	List<LeaveBill> findLeaveBillList(Employee employee);

}
