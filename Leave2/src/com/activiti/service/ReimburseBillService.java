package com.activiti.service;

import java.util.List;

import com.activiti.entity.Employee;
import com.activiti.entity.ReimburseBill;

public interface ReimburseBillService {

	void addReimburseBill(ReimburseBill reimburseBill);

	List<ReimburseBill> findReimburseBillList(Employee employee);

}
