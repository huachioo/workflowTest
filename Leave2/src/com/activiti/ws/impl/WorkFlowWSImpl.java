package com.activiti.ws.impl;

import java.util.HashMap;
import java.util.List;

import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.activiti.entity.CommentforWS;
import com.activiti.entity.Employee;
import com.activiti.entity.LeaveBill;
import com.activiti.entity.ReimburseBill;
import com.activiti.entity.TaskforWs;
import com.activiti.entity.WorkFlow;
import com.activiti.service.EmployeeService;
import com.activiti.service.WorkFlowService;
import com.activiti.util.WorkFlowReflect;
import com.activiti.ws.WorkFlowWS;

@Controller
@RequestMapping("/WorkFlowWS")
public class WorkFlowWSImpl implements WorkFlowWS{
	@Autowired
	@Qualifier("workFlowService")
	WorkFlowService workFlowService;
	
	@Autowired
	@Qualifier("employeeService")
	EmployeeService employeeService;
	
	public LeaveBill addLeaveBill(){
		return null;
	}
	
	public ReimburseBill addReimburseBill(){
		return null;	
	}
	
	public List<TaskforWs> queryTaskList(String userName){
		List<TaskforWs> taskList = workFlowService.findTaskListByNameWS(userName);
		return taskList;
	}
	
	public Object getObject(String taskId){
		Object object = workFlowService.findBillByTaskId(taskId);
		return object;
	}
	
	public List<String> getOutcomeList(String taskId){
		List<String> outcomeList = workFlowService.findOutComeListByTaskId(taskId);
		return outcomeList;
	}
	
	public List<CommentforWS> getCommentList(String taskId){
		List<CommentforWS> commentList = workFlowService.findCommentByTaskIdWS(taskId);
		return commentList;
	}
	
	public String doTask(WorkFlow workflow,String userName){
		workFlowService.saveSubmitTask(workflow,userName);
		return "success";
		
	}
	
	
	public String addTask(Long Id,String formName,String userName,String[] input){
		Object object = null;
		object = WorkFlowReflect.getObject(Id,formName); //反射生成对象
		HashMap<String,Object>variables = new HashMap<String,Object>();
		if(input!=null){
			for(String st:input){
				int value = WorkFlowReflect.doMethod(object, st);
				variables.put(st, value);
			}
		}
		Employee emp = employeeService.findEmployeeByName(userName);
		if(variables.isEmpty()){
			workFlowService.saveStartProcess(Id,emp.getName(),formName);
		}else{
			workFlowService.saveStartProcess(Id,emp.getName(),formName,variables);
		}
		return "success";
	}

}
