package com.activiti.ws.impl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.activiti.entity.Employee;
import com.activiti.entity.LeaveBill;
import com.activiti.entity.ReimburseBill;
import com.activiti.service.EmployeeService;
import com.activiti.util.WorkFlowReflect;
import com.activiti.ws.BillWS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/BillWS")
public class BillWSImpl implements BillWS{
	@Autowired
	@Qualifier("employeeService")
	EmployeeService employeeService;

	public LeaveBill addLeaveBill(){
		return null;
	}
	
	public ReimburseBill addReimburseBill(){
		return null;	
	}

	@RequestMapping(value ="/login",method=RequestMethod.POST)
	public String QueryListforJson(String userName,String formName) throws JsonProcessingException{
		List<Object> queryList = new ArrayList<Object>();
		Employee emp = employeeService.findEmployeeByName(userName);
		Hibernate.initialize(WorkFlowReflect.getList(emp, formName));
		queryList = WorkFlowReflect.getList(emp, formName);
		ObjectMapper mapper = new ObjectMapper();  
		String jsonfromList = mapper.writeValueAsString(queryList); 
		//String jsonfromList ="asd";
		System.out.println(jsonfromList);
		return jsonfromList;
	}
	
	public List<Object> QueryList(String userName,String formName){
		List<Object> queryList = new ArrayList<Object>();
		Employee emp = employeeService.findEmployeeByName(userName);
		Hibernate.initialize(WorkFlowReflect.getList(emp, formName));
		queryList = WorkFlowReflect.getList(emp, formName);
		return queryList;
	}
}
