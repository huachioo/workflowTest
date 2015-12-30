package com.activiti.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;
import javax.servlet.http.HttpSession;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.activiti.dao.IBase;
import com.activiti.dao.LeaveBillDao;
import com.activiti.dao.ReimburseBillDao;
import com.activiti.dao.impl.LeaveBillDaoImpl;
import com.activiti.entity.WorkFlow;
import com.activiti.service.WorkFlowService;
import com.activiti.util.WorkFlowReflect;
@Service
@Qualifier("workFlowService")
public class WorkFlowServiceImpl implements WorkFlowService{
    @Autowired
	RepositoryService repositoryService;
    @Autowired
    LeaveBillDao leaveBillDao;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;
    @Autowired
    FormService formService;
    @Autowired
    HistoryService historyService;
    @Autowired
    IBase ibase;
    @Autowired
    ReimburseBillDao reimburseBillDao;
    
    
	@Override
	public void saveNewDeploy(File file, String fileName) {
		// TODO Auto-generated method stub
		ZipInputStream zipInputStream = null;
		try {
			zipInputStream = new ZipInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repositoryService.createDeployment()//创建部署对象
						.name(fileName)//添加部署名称
						.addZipInputStream(zipInputStream)//
						.deploy();//完成部署
		
		System.out.println(fileName+"部署成功");
	}
	
	/**查询部署对象信息，对应表（act_re_deployment）*/
	@Override
	public List<Deployment> findDeploymentList() {
		List<Deployment> list = repositoryService.createDeploymentQuery()//创建部署对象查询
				.orderByDeploymenTime().asc()//
				.list();
        return list;
	}
	
	/**查询流程定义的信息，对应表（act_re_procdef）*/
	@Override
	public List<ProcessDefinition> findProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()//创建流程定义查询
				.orderByProcessDefinitionVersion().asc()//
				.list();
        return list;
	}
     //删除部署
	@Override
	public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
		// TODO Auto-generated method stub
		repositoryService.deleteDeployment(deploymentId, true);
		
	}
    //获取流程图片  
	@Override
	public InputStream findImageInputStream(String deploymentId, String imageName) {
		// TODO Auto-generated method stub
		return repositoryService.getResourceAsStream(deploymentId, imageName);
	}
	/**更新请假状态，启动流程实例，让启动的流程实例关联业务*/
	//启动流程
	@Override
	public void saveStartProcess(long ID,String userName,String formName) {
		//1：获取请假单ID，使用请假单ID，查询请假单的对象LeaveBill
				Long id = ID;
				Object object = null;
				object = WorkFlowReflect.getObject(id,formName);
				//object = this.findBillById(id, formName);
				//object = ibase.findBillById(id);
				WorkFlowReflect.setState(object, 1);
				
				String key = formName;
				Map<String, Object> variables = new HashMap<String,Object>();
				variables.put("inputUser", userName);//表示惟一用户
				String objId = key+"."+id;
				runtimeService.startProcessInstanceByKey(key,objId,variables);
//				LeaveBill leaveBill = leaveBillDao.findLeaveBillById(id);
//				//2：更新请假单的请假状态从0变成1（初始录入-->审核中）
//				leaveBill.setState(1);
//				//3：使用当前对象获取到流程定义的key（对象的名称就是流程定义的key）
//				String key = leaveBill.getClass().getSimpleName();
//				/**
//				 * 4：从Session中获取当前任务的办理人，使用流程变量设置下一个任务的办理人
//					    * inputUser是流程变量的名称，
//					    * 获取的办理人是流程变量的值
//				 */
//				Map<String, Object> variables = new HashMap<String,Object>();
//				variables.put("inputUser", userName);//表示惟一用户
//				variables.put("leaveBill",leaveBill);
//				/**
//				 * 5：	(1)使用流程变量设置字符串（格式：LeaveBill.id的形式），通过设置，让启动的流程（流程实例）关联业务
//		   				(2)使用正在执行对象表中的一个字段BUSINESS_KEY（Activiti提供的一个字段），让启动的流程（流程实例）关联业务
//				 */
//				//格式：LeaveBill.id的形式（使用流程变量）
//				String objId = key+"."+id;
//				variables.put("objId", objId);
//				//6：使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
//				runtimeService.startProcessInstanceByKey(key,objId,variables);
		
	}
	
	@Override
	public void saveStartProcess(Long ID, String userName, String formName, HashMap<String, Object> variables) {
		Long id = ID;
		Object object = null;
		object = WorkFlowReflect.getObject(id,formName);
		WorkFlowReflect.setState(object, 1);		
		String key = formName;
		Map<String,Object> a = new HashMap<String,Object>();
		variables.put("inputUser", userName);//表示惟一用户
		String objId = key+"."+id;
		runtimeService.startProcessInstanceByKey(key,objId,variables);
		
	}

	/**2：使用当前用户名查询正在执行的任务表，获取当前任务的集合List<Task>*/
	@Override
	public List<Task> findTaskListByName(String name) {
		List<Task> list = taskService.createTaskQuery()//
					.taskAssignee(name)//指定个人任务查询
					.orderByTaskCreateTime().asc()//
					.list();
		return list;
	}

	/**使用任务ID，获取当前任务节点中对应的Form key中的连接的值*/
	@Override
	public String findTaskFormKeyByTaskId(String taskId) {
		TaskFormData formData = formService.getTaskFormData(taskId);
		//获取Form key的值
		String url = formData.getFormKey();
		return url;
	}

	/**一：使用任务ID，查找请假单ID，从而获取请假单信息*/
	@Override
	public Object findBillByTaskId(String taskId) {
		//1：使用任务ID，查询任务对象Task
		Task task = taskService.createTaskQuery()//
						.taskId(taskId)//使用任务ID查询
						.singleResult();
		//2：使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
						.processInstanceId(processInstanceId)//使用流程实例ID查询
						.singleResult();
		//4：使用流程实例对象获取BUSINESS_KEY
		String buniness_key = pi.getBusinessKey();
		//5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询请假单对象（LeaveBill.1）
		String id = "";
		String formName = "";
		if(!buniness_key.equals(null)){
			//截取字符串，取buniness_key小数点的第2个值
			formName = buniness_key.split("\\.")[0];
			id = buniness_key.split("\\.")[1];
		}		
		Object object = WorkFlowReflect.getObject(Long.parseLong(id),formName);
		//object = this.findBillById(Long.parseLong(id),formName);
		return object;
		//查询请假单对象
		//使用hql语句：from LeaveBill o where o.id=1
		//LeaveBill leaveBill = (LeaveBill) leaveBillDao.findBillById(Long.parseLong(id));
		//return leaveBill;
	}

	/**二：已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中*/
	@Override
	public List<String> findOutComeListByTaskId(String taskId) {
		//返回存放连线的名称集合
		List<String> list = new ArrayList<String>();
		//1:使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//2：获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		//3：查询ProcessDefinitionEntiy对象
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		//使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
					.processInstanceId(processInstanceId)//使用流程实例ID查询
				    .singleResult();
		//获取当前活动的id
		String activityId = pi.getActivityId();
		//4：获取当前的活动
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
/*
 * 很关键！ 		通过activityImpl获取当前活动
 *               List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions(); 
 *               获取活动之后的节点
 *               PvmActivity为活动节点 PvmTransition为连线
 *               
 *               也就是说 在注入需要的流程变量时
 *               可以通过解析pvmTransition.getProperty("conditionText")的值
 *               得到需要的流程变量
 *               通过反射直接获取
 */
//		List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions(); 
//		List<PvmTransition> outTransitionsTemp = null; 
//		for(PvmTransition tr:outTransitions){     
//			System.out.println(tr.getProperty("name")+"dewq");
//            PvmActivity ac = tr.getDestination(); //获取线路的终点节点    
//            outTransitionsTemp = ac.getOutgoingTransitions(); 
//            for(PvmTransition pvm:outTransitionsTemp){
//            	System.out.println(pvm.getProperty("conditionText")+"dewq");
//            }
//            PvmActivity ac2 = outTransitionsTemp.get(0).getDestination();
//            String name2 = (String) ac2.getProperty("name");
//            
//            //outTransitionsTemp = ac.getIncomingTransitions()
//            System.out.println(name2+"dewq");
//			System.out.println(ac.getProperty("name")+"dewq");
//		}
		//5：获取当前活动完成之后连线的名称
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		if(pvmList!=null && pvmList.size()>0){
			for(PvmTransition pvm:pvmList){
				String name = (String) pvm.getProperty("name");
				if(StringUtils.isNotBlank(name) && (name.equals("同意")||name.equals("批准")||name.equals("驳回"))){
					list.add(name);
				}
				else{
					list.add("默认提交");
					break;
				}
			}
		}
		return list;
	}

	/**指定连线的名称完成任务*/
	@Override
	public void saveSubmitTask(WorkFlow workflow,HttpSession session) {
		//获取任务ID
		String taskId = workflow.getTaskId();
		//获取连线的名称
		String outcome = workflow.getOutcome();
		//批注信息
		String message = workflow.getComment();
		//获取表单ID
		Long id = workflow.getId();
		//获取表单名
		String formName = workflow.getFormName();
		/**
		 * 1：在完成之前，添加一个批注信息，向act_hi_comment表中添加数据，用于记录对当前申请人的一些审核信息
		 */
		//使用任务ID，查询任务对象，获取流程流程实例ID
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//使用任务ID查询
				.singleResult();	
		//获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		/**
		 * 注意：添加批注的时候，由于Activiti底层代码是使用：
		 * 		String userId = Authentication.getAuthenticatedUserId();
			    CommentEntity comment = new CommentEntity();
			    comment.setUserId(userId);
			  所有需要从Session中获取当前登录人，作为该任务的办理人（审核人），对应act_hi_comment表中的User_ID的字段，不过不添加审核人，该字段为null
			 所以要求，添加配置执行使用Authentication.setAuthenticatedUserId();添加当前任务的审核人
		 * */
		Authentication.setAuthenticatedUserId((String) session.getAttribute("username"));
		taskService.addComment(taskId, processInstanceId, message);
		
		
		/**
		 * 2：如果连线的名称是“默认提交”，那么就不需要设置，如果不是，就需要设置流程变量
		 * 在完成任务之前，设置流程变量，按照连线的名称，去完成任务
				 流程变量的名称：outcome
				 流程变量的值：连线的名称
		 */
	    Map<String,Object> variables = taskService.getVariables(taskId);
		//Map<String,Object> variables = new HashMap<String, Object>();
		if(outcome!=null && !outcome.equals("默认提交")){
			variables.put("outcome", outcome);
		}
		//variables.put("Input",1600);
		//3：使用任务ID，完成当前人的个人任务，同时流程变量
		System.out.println(variables+"test");
		
		taskService.complete( taskId, variables);
		//4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		
		/**
		 * 5：在完成任务之后，判断流程是否结束
   			如果流程结束了，更新请假单表的状态从1变成2（审核中-->审核完成）
		 */
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
						.processInstanceId(processInstanceId)//使用流程实例ID查询
						.singleResult();
		//流程结束了
		if(pi==null){
			System.out.println("流程结束");
			//更新请假单表的状态从1变成2（审核中-->审核完成）
			Object object = WorkFlowReflect.getObject(id,formName);
			//object = ibase.findBillById(id);
			//object = this.findBillById(id, formName);
			WorkFlowReflect.setState(object, 2);
//			LeaveBill bill = (LeaveBill) leaveBillDao.findBillById(id);
//			bill.setState(2);
		}
	}
	
	/**获取批注信息，传递的是当前任务ID，获取历史任务ID对应的批注*/
	@Override
	public List<Comment> findCommentByTaskId(String taskId) {
		List<Comment> list = new ArrayList<Comment>();
		//使用当前的任务ID，查询当前流程对应的历史任务ID
		//使用当前任务ID，获取当前任务对象
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//使用任务ID查询
				.singleResult();
		//获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
//		//使用流程实例ID，查询历史任务，获取历史任务对应的每个任务ID
//		List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()//历史任务表查询
//						.processInstanceId(processInstanceId)//使用流程实例ID查询
//						.list();
//		//遍历集合，获取每个任务ID
//		if(htiList!=null && htiList.size()>0){
//			for(HistoricTaskInstance hti:htiList){
//				//任务ID
//				String htaskId = hti.getId();
//				//获取批注信息
//				List<Comment> taskList = taskService.getTaskComments(htaskId);//对用历史完成后的任务ID
//				list.addAll(taskList);
//			}
//		}
		list = taskService.getProcessInstanceComments(processInstanceId);
		return list;
	}

	@Override
	public Object findBillById(String id, String formName) {
		Object object = WorkFlowReflect.getObject(Long.parseLong(id),formName);
		//object = ibase.findBillById(Long.parseLong(id));
		//object = this.findBillById(id, formName);
		return object;
	}
	
//	private Object findBillById(long id, String formName) {
//		if(formName.equals("LeaveBill")){
//			return leaveBillDao.findBillById(id);
//		}
//		else if(formName.equals("ReimburseBill")){
//			return reimburseBillDao.findBillById(id);
//		}
//		return null;
//	}

	@Override
	public List<Comment> findCommentByBillId(String id, String formName) {
		String objId = formName + "." + id;
		
		/**1:使用历史的流程实例查询，返回历史的流程实例对象，获取流程实例ID*/
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()//对应历史的流程实例表
						.processInstanceBusinessKey(objId)//使用BusinessKey字段查询
						.singleResult();
		//流程实例ID
		String processInstanceId = hpi.getId();
//		/**2:使用历史的流程变量查询，返回历史的流程变量的对象，获取流程实例ID*/
//		HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery()//对应历史的流程变量表
//						.variableValueEquals("objId", objId)//使用流程变量的名称和流程变量的值查询
//						.singleResult();
//		//流程实例ID
		List<Comment> list = taskService.getProcessInstanceComments(processInstanceId);
		return list;
	}

	/**1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象*/
	@Override
	public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
		//使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		//查询流程定义的对象
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//创建流程定义查询对象，对应表act_re_procdef 
					.processDefinitionId(processDefinitionId)//使用流程定义ID查询
					.singleResult();
		return pd;
	
	}

	/**
	 * 二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中
		 map集合的key：表示坐标x,y,width,height
		 map集合的value：表示坐标对应的值
	 */
	@Override
	public Map<String, Object> findCoordingByTask(String taskId) {
		//存放坐标
		Map<String, Object> map = new HashMap<String,Object>();
		//使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//获取流程定义的ID
		String processDefinitionId = task.getProcessDefinitionId();
		//获取流程定义的实体对象（对应.bpmn文件中的数据）
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
		//流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//使用流程实例ID，查询正在执行的执行对象表，获取当前活动对应的流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//创建流程实例查询
					.processInstanceId(processInstanceId)//使用流程实例ID查询
					.singleResult();
		//获取当前活动的ID
		String activityId = pi.getActivityId();
		//获取当前活动对象
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);//活动ID
		//获取坐标
		map.put("x", activityImpl.getX());
		map.put("y", activityImpl.getY());
		map.put("width", activityImpl.getWidth());
		map.put("height", activityImpl.getHeight());
		return map;
	}
	
	//获取历史记录
	@Override
	public List<HistoricProcessInstance> historyProcess(){
		List<HistoricProcessInstance> his = historyService.createHistoricProcessInstanceQuery().finished().list();
		return his;
	}
	
}
