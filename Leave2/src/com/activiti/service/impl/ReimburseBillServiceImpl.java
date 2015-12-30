package com.activiti.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.activiti.dao.ReimburseBillDao;
import com.activiti.entity.Employee;
import com.activiti.entity.LeaveBill;
import com.activiti.entity.ReimburseBill;
import com.activiti.service.ReimburseBillService;
@Service
@Qualifier("reimburseBillService")
public class ReimburseBillServiceImpl implements ReimburseBillService {
	@Autowired
	ReimburseBillDao reimburseBillDao;

	@Override
	public void addReimburseBill(ReimburseBill reimburseBill) {
		// TODO Auto-generated method stub
		reimburseBillDao.saveReimburseBill(reimburseBill);
		
	}

	@Override
	public List<ReimburseBill> findreimburseBillList(Employee employee) {
		// TODO Auto-generated method stub
		List<ReimburseBill> list = reimburseBillDao.findReimburseBillList(employee);
		return list;
	}

}
