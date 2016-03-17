package com.activiti.ws;

import java.util.List;

import javax.jws.WebService;

import com.activiti.entity.CommentforWS;
import com.activiti.entity.LeaveBill;
import com.activiti.entity.ReimburseBill;
import com.activiti.entity.TaskforWs;
import com.activiti.entity.WorkFlow;

@WebService
public interface WorkFlowWS {
	LeaveBill addLeaveBill();
	
	ReimburseBill addReimburseBill();
	
	List<TaskforWs> queryTaskList(String userName);	
	
	String addTask(Long Id,String formName,String userName,String[] input);
	 
	Object getObject(String taskId);
	
	List<String> getOutcomeList(String taskId);
	
	List<CommentforWS> getCommentList(String taskId);
	
	String doTask(WorkFlow workflow,String userName);

}
