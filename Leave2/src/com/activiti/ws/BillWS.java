package com.activiti.ws;

import java.util.List;

import javax.jws.WebService;

import com.activiti.entity.LeaveBill;
import com.activiti.entity.ReimburseBill;
import com.fasterxml.jackson.core.JsonProcessingException;

@WebService
public interface BillWS {
	LeaveBill addLeaveBill();
	ReimburseBill addReimburseBill();
	
	List<Object> QueryList(String userName,String formName);
	String QueryListforJson(String userName,String formName) throws JsonProcessingException;
}
