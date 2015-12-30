package com.activiti.service;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import com.activiti.entity.LeaveBill;
import com.activiti.entity.WorkFlow;

public interface WorkFlowService {

	void saveNewDeploy(File file, String fileName);

	List<Deployment> findDeploymentList();

	List<ProcessDefinition> findProcessDefinitionList();

	void deleteProcessDefinitionByDeploymentId(String deploymentId);

	InputStream findImageInputStream(String deploymentId, String imageName);

	void saveStartProcess(long leaveBillId, String string, String formName);

	List<Task> findTaskListByName(String name);

	String findTaskFormKeyByTaskId(String taskId);

	List<String> findOutComeListByTaskId(String taskId);

	List<Comment> findCommentByTaskId(String taskId);

	void saveSubmitTask(WorkFlow workflow, HttpSession session);

	Object findBillByTaskId(String taskId);

	Object findBillById(String id, String formName);

	List<Comment> findCommentByBillId(String id, String formName);

	void saveStartProcess(Long id, String name, String formName, HashMap<String, Object> variables);

	ProcessDefinition findProcessDefinitionByTaskId(String taskId);

	Map<String, Object> findCoordingByTask(String taskId);

	List<HistoricProcessInstance> historyProcess();




}
