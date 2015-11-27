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
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.internal.compiler.parser.CommitRollbackParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.activiti.dao.LeaveBillDao;
import com.activiti.entity.LeaveBill;
import com.activiti.entity.WorkFlow;
import com.activiti.service.WorkFlowService;
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
		repositoryService.createDeployment()//�����������
						.name(fileName)//��Ӳ�������
						.addZipInputStream(zipInputStream)//
						.deploy();//��ɲ���
		
		System.out.println(fileName+"����ɹ�");
	}
	
	/**��ѯ���������Ϣ����Ӧ��act_re_deployment��*/
	@Override
	public List<Deployment> findDeploymentList() {
		List<Deployment> list = repositoryService.createDeploymentQuery()//������������ѯ
				.orderByDeploymenTime().asc()//
				.list();
        return list;
	}
	
	/**��ѯ���̶������Ϣ����Ӧ��act_re_procdef��*/
	@Override
	public List<ProcessDefinition> findProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()//�������̶����ѯ
				.orderByProcessDefinitionVersion().asc()//
				.list();
        return list;
	}
     //ɾ������
	@Override
	public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
		// TODO Auto-generated method stub
		repositoryService.deleteDeployment(deploymentId, true);
		
	}
    //��ȡ����ͼƬ  
	@Override
	public InputStream findImageInputStream(String deploymentId, String imageName) {
		// TODO Auto-generated method stub
		return repositoryService.getResourceAsStream(deploymentId, imageName);
	}
	/**�������״̬����������ʵ����������������ʵ������ҵ��*/
	//��������
	@Override
	public void saveStartProcess(long leaveBillId,String userName) {
		//1����ȡ��ٵ�ID��ʹ����ٵ�ID����ѯ��ٵ��Ķ���LeaveBill
				Long id = leaveBillId;
				LeaveBill leaveBill = leaveBillDao.findLeaveBillById(id);
				//2��������ٵ������״̬��0���1����ʼ¼��-->����У�
				leaveBill.setState(1);
				//3��ʹ�õ�ǰ�����ȡ�����̶����key����������ƾ������̶����key��
				String key = leaveBill.getClass().getSimpleName();
				/**
				 * 4����Session�л�ȡ��ǰ����İ����ˣ�ʹ�����̱���������һ������İ�����
					    * inputUser�����̱��������ƣ�
					    * ��ȡ�İ����������̱�����ֵ
				 */
				Map<String, Object> variables = new HashMap<String,Object>();
				variables.put("inputUser", userName);//��ʾΩһ�û�
				/**
				 * 5��	(1)ʹ�����̱��������ַ�������ʽ��LeaveBill.id����ʽ����ͨ�����ã������������̣�����ʵ��������ҵ��
		   				(2)ʹ������ִ�ж�����е�һ���ֶ�BUSINESS_KEY��Activiti�ṩ��һ���ֶΣ��������������̣�����ʵ��������ҵ��
				 */
				//��ʽ��LeaveBill.id����ʽ��ʹ�����̱�����
				String objId = key+"."+id;
				variables.put("objId", objId);
				//6��ʹ�����̶����key����������ʵ����ͬʱ�������̱�����ͬʱ������ִ�е�ִ�ж�����е��ֶ�BUSINESS_KEY���ҵ�����ݣ�ͬʱ�����̹���ҵ��
				runtimeService.startProcessInstanceByKey(key,objId,variables);
		
	}

	/**2��ʹ�õ�ǰ�û�����ѯ����ִ�е��������ȡ��ǰ����ļ���List<Task>*/
	@Override
	public List<Task> findTaskListByName(String name) {
		List<Task> list = taskService.createTaskQuery()//
					.taskAssignee(name)//ָ�����������ѯ
					.orderByTaskCreateTime().asc()//
					.list();
		return list;
	}

	/**ʹ������ID����ȡ��ǰ����ڵ��ж�Ӧ��Form key�е����ӵ�ֵ*/
	@Override
	public String findTaskFormKeyByTaskId(String taskId) {
		TaskFormData formData = formService.getTaskFormData(taskId);
		//��ȡForm key��ֵ
		String url = formData.getFormKey();
		return url;
	}

	/**һ��ʹ������ID��������ٵ�ID���Ӷ���ȡ��ٵ���Ϣ*/
	@Override
	public LeaveBill findLeaveBillByTaskId(String taskId) {
		//1��ʹ������ID����ѯ�������Task
		Task task = taskService.createTaskQuery()//
						.taskId(taskId)//ʹ������ID��ѯ
						.singleResult();
		//2��ʹ���������Task��ȡ����ʵ��ID
		String processInstanceId = task.getProcessInstanceId();
		//3��ʹ������ʵ��ID����ѯ����ִ�е�ִ�ж������������ʵ������
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
						.processInstanceId(processInstanceId)//ʹ������ʵ��ID��ѯ
						.singleResult();
		//4��ʹ������ʵ�������ȡBUSINESS_KEY
		String buniness_key = pi.getBusinessKey();
		//5����ȡBUSINESS_KEY��Ӧ������ID��ʹ������ID����ѯ��ٵ�����LeaveBill.1��
		String id = "";
		if(!buniness_key.equals(null)){
			//��ȡ�ַ�����ȡbuniness_keyС����ĵ�2��ֵ
			id = buniness_key.split("\\.")[1];
		}
		//��ѯ��ٵ�����
		//ʹ��hql��䣺from LeaveBill o where o.id=1
		LeaveBill leaveBill = leaveBillDao.findLeaveBillById(Long.parseLong(id));
		return leaveBill;
	}

	/**������֪����ID����ѯProcessDefinitionEntiy���󣬴Ӷ���ȡ��ǰ�������֮����������ƣ������õ�List<String>������*/
	@Override
	public List<String> findOutComeListByTaskId(String taskId) {
		//���ش�����ߵ����Ƽ���
		List<String> list = new ArrayList<String>();
		//1:ʹ������ID����ѯ�������
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//ʹ������ID��ѯ
					.singleResult();
		//2����ȡ���̶���ID
		String processDefinitionId = task.getProcessDefinitionId();
		//3����ѯProcessDefinitionEntiy����
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		//ʹ���������Task��ȡ����ʵ��ID
		String processInstanceId = task.getProcessInstanceId();
		//ʹ������ʵ��ID����ѯ����ִ�е�ִ�ж������������ʵ������
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
					.processInstanceId(processInstanceId)//ʹ������ʵ��ID��ѯ
				    .singleResult();
		//��ȡ��ǰ���id
		String activityId = pi.getActivityId();
		//4����ȡ��ǰ�Ļ
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		//5����ȡ��ǰ����֮�����ߵ�����
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		if(pvmList!=null && pvmList.size()>0){
			for(PvmTransition pvm:pvmList){
				String name = (String) pvm.getProperty("name");
				if(StringUtils.isNotBlank(name)){
					list.add(name);
				}
				else{
					list.add("Ĭ���ύ");
				}
			}
		}
		return list;
	}

	/**ָ�����ߵ������������*/
	@Override
	public void saveSubmitTask(WorkFlow workflow,HttpSession session) {
		//��ȡ����ID
		String taskId = workflow.getTaskId();
		//��ȡ���ߵ�����
		String outcome = workflow.getOutcome();
		//��ע��Ϣ
		String message = workflow.getComment();
		//��ȡ��ٵ�ID
		Long id = workflow.getId();
		/**
		 * 1�������֮ǰ�����һ����ע��Ϣ����act_hi_comment����������ݣ����ڼ�¼�Ե�ǰ�����˵�һЩ�����Ϣ
		 */
		//ʹ������ID����ѯ������󣬻�ȡ��������ʵ��ID
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//ʹ������ID��ѯ
				.singleResult();	
		//��ȡ����ʵ��ID
		String processInstanceId = task.getProcessInstanceId();
		/**
		 * ע�⣺�����ע��ʱ������Activiti�ײ������ʹ�ã�
		 * 		String userId = Authentication.getAuthenticatedUserId();
			    CommentEntity comment = new CommentEntity();
			    comment.setUserId(userId);
			  ������Ҫ��Session�л�ȡ��ǰ��¼�ˣ���Ϊ������İ����ˣ�����ˣ�����Ӧact_hi_comment���е�User_ID���ֶΣ��������������ˣ����ֶ�Ϊnull
			 ����Ҫ���������ִ��ʹ��Authentication.setAuthenticatedUserId();��ӵ�ǰ����������
		 * */
		Authentication.setAuthenticatedUserId((String) session.getAttribute("username"));
		taskService.addComment(taskId, processInstanceId, message);
		
		/**
		 * 2��������ߵ������ǡ�Ĭ���ύ������ô�Ͳ���Ҫ���ã�������ǣ�����Ҫ�������̱���
		 * ���������֮ǰ���������̱������������ߵ����ƣ�ȥ�������
				 ���̱��������ƣ�outcome
				 ���̱�����ֵ�����ߵ�����
		 */
		Map<String, Object> variables = new HashMap<String,Object>();
		if(outcome!=null && !outcome.equals("Ĭ���ύ")){
			variables.put("outcome", outcome);
		}
		
		//3��ʹ������ID����ɵ�ǰ�˵ĸ�������ͬʱ���̱���
		taskService.complete(taskId, variables);
		//4�����������֮����Ҫָ����һ������İ����ˣ�ʹ���ࣩ-----�Ѿ��������
		
		/**
		 * 5�����������֮���ж������Ƿ����
   			������̽����ˣ�������ٵ����״̬��1���2�������-->�����ɣ�
		 */
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
						.processInstanceId(processInstanceId)//ʹ������ʵ��ID��ѯ
						.singleResult();
		//���̽�����
		if(pi==null){
			//������ٵ����״̬��1���2�������-->�����ɣ�
			LeaveBill bill = leaveBillDao.findLeaveBillById(id);
			bill.setState(2);
		}
	}
	
	/**��ȡ��ע��Ϣ�����ݵ��ǵ�ǰ����ID����ȡ��ʷ����ID��Ӧ����ע*/
	@Override
	public List<Comment> findCommentByTaskId(String taskId) {
		List<Comment> list = new ArrayList<Comment>();
		//ʹ�õ�ǰ������ID����ѯ��ǰ���̶�Ӧ����ʷ����ID
		//ʹ�õ�ǰ����ID����ȡ��ǰ�������
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//ʹ������ID��ѯ
				.singleResult();
		//��ȡ����ʵ��ID
		String processInstanceId = task.getProcessInstanceId();
//		//ʹ������ʵ��ID����ѯ��ʷ���񣬻�ȡ��ʷ�����Ӧ��ÿ������ID
//		List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()//��ʷ������ѯ
//						.processInstanceId(processInstanceId)//ʹ������ʵ��ID��ѯ
//						.list();
//		//�������ϣ���ȡÿ������ID
//		if(htiList!=null && htiList.size()>0){
//			for(HistoricTaskInstance hti:htiList){
//				//����ID
//				String htaskId = hti.getId();
//				//��ȡ��ע��Ϣ
//				List<Comment> taskList = taskService.getTaskComments(htaskId);//������ʷ��ɺ������ID
//				list.addAll(taskList);
//			}
//		}
		list = taskService.getProcessInstanceComments(processInstanceId);
		for(Comment comment:list)
		{
			System.out.println(comment.getTime());
		}
		return list;
	}
	
}
