package com.activiti.dao;

import java.util.List;

import com.activiti.entity.Employee;
import com.activiti.entity.ReimburseBill;

public interface ReimburseBillDao extends IBase{

	void saveReimburseBill(ReimburseBill reimburseBill);

	List<ReimburseBill> findReimburseBillList(Employee employee);

}
