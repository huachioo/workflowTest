package com.activiti.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.activiti.dao.LeaveBillDao;
import com.activiti.entity.Employee;
import com.activiti.entity.LeaveBill;
import com.activiti.service.LeaveBillService;
@Service
@Qualifier("leaveBillService")
public class LeaveBillServiceImpl implements LeaveBillService {

	@Autowired
	@Qualifier("leaveBillDao")
	LeaveBillDao leaveBillDao;
	
	/**查询自己的请假单的信息*/
	@Override
	public List<LeaveBill> findLeaveBillList(Employee employee) {
		List<LeaveBill> list = leaveBillDao.findLeaveBillList(employee);
		return list;
	}

}
