package com.activiti.controller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.activiti.entity.Employee;
import com.activiti.entity.WorkFlow;
import com.activiti.service.WorkFlowService;
import com.activiti.util.WorkFlowReflect;



@Controller
@RequestMapping("workflow")
public class WorkFlowController {

	@Autowired
	@Qualifier("workFlowService")
	WorkFlowService workFlowService;
	
	
	
	/**
	 * ���������ҳ��ʾ
	 * @return
	 */
	@RequestMapping("/deployHome")
	public ModelAndView deployHome(){
		ModelAndView mav = new ModelAndView("defineWorkFlow");
		//1:��ѯ���������Ϣ����Ӧ��act_re_deployment��
		List<Deployment> depList = workFlowService.findDeploymentList();
		//2:��ѯ���̶������Ϣ����Ӧ��act_re_procdef��
		List<ProcessDefinition> pdList = workFlowService.findProcessDefinitionList();
		//���õ������Ķ�����
		//request.getSession().setAttribute("depList", depList);
		//request.getSession().setAttribute("pdList", pdList);
		mav.addObject("depList", depList);
		mav.addObject("pdList", pdList);
		//Deployment deployment = depList.get(0);
		//deployment.get
		return mav;
	}
	
	//�������̶���
	@RequestMapping("/read")
	public String newdeploy(HttpServletRequest request){
		//��ȡҳ�洫�ݵ�ֵ
		//1����ȡҳ���ϴ��ݵ�zip��ʽ���ļ�����ʽ��File����
		//�ļ�����
		String fileName = request.getParameter("fileName");
		String fileAddress = request.getParameter("fileAddress");
		File file = new File(fileAddress);
		//��ɲ���
		workFlowService.saveNewDeploy(file,fileName);
		return "main";		
	}
	
	/**
	 * ɾ��������Ϣ
	 */
	@RequestMapping("/delDeployment")
	public String delDeployment(HttpServletRequest request){
		//1����ȡ�������ID
		String deploymentId = request.getParameter("deploymentId");
		//2��ʹ�ò������ID��ɾ�����̶���
		workFlowService.deleteProcessDefinitionByDeploymentId(deploymentId);
		return "defineWorkFlow";
	}
	
	/**
	 * �鿴����ͼ
	 * @throws Exception 
	 */
	@RequestMapping("/viewImage")
	public String viewImage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//1����ȡҳ�洫�ݵĲ������ID����ԴͼƬ����
		//�������ID
		String deploymentId = request.getParameter("deploymentId");
		//��ԴͼƬ����
		String imageName = request.getParameter("imageName");
		//2����ȡ��Դ�ļ���act_ge_bytearray������ԴͼƬ������InputStream
		InputStream in = workFlowService.findImageInputStream(deploymentId,imageName);
		//3����response�����ȡ�����
		OutputStream out = response.getOutputStream();
		//4�����������е����ݶ�ȡ������д���������
		for(int b=-1;(b=in.read())!=-1;){
			out.write(b);
		}
		out.close();
		in.close();
		//��ͼд��ҳ���ϣ��������д
		return null;
	}
	
	// ��������
	@RequestMapping("/startProcess")
	public String startProcess(HttpServletRequest request,HttpSession session){
		//�������״̬����������ʵ����������������ʵ������ҵ��
		long id = Long.parseLong(request.getParameter("id"));//���ݱ�Id
		Employee Emp = (Employee) session.getAttribute("user");//�����û���Ϣ
		String formName = request.getParameter("formName");//���ݱ���
		Map<String, String[]> save = request.getParameterMap();//���ݱ���
		HashMap<String,Object>variables = new HashMap<String,Object>();
		Object object = null;
		object = WorkFlowReflect.getObject(id,formName); //�������ɶ���
		for (String key : save.keySet()) {
			System.out.println(key);
			if(!key.equals("id")&&!key.equals("formName")){
				System.out.println(key);
				int value = WorkFlowReflect.doMethod(object, key);
				variables.put(key, value);
			}
	    }
		if(variables.isEmpty()){
			workFlowService.saveStartProcess(id,Emp.getName(),formName);
		}else{
			workFlowService.saveStartProcess(id,Emp.getName(),formName,variables);
		}
		return "main";
	}
	
	
	@RequestMapping("tasklist")
	public String listTask(HttpSession session,HttpServletRequest request){
		//1����Session�л�ȡ��ǰ�û���
		String name = (String) session.getAttribute("username");
		//2��ʹ�õ�ǰ�û�����ѯ����ִ�е��������ȡ��ǰ����ļ���List<Task>
		List<Task> tasklist = workFlowService.findTaskListByName(name); 
		request.setAttribute("tasklist", tasklist);
		return "tasklist";
	}
	
	/**
	 * �������
	 */
	@RequestMapping("viewTaskForm")
	public String viewTaskForm(HttpServletRequest request){
		//����ID
		String taskId = (String) request.getParameter("taskId");
		//��ȡ�����������ڵ��url����
		String url = workFlowService.findTaskFormKeyByTaskId(taskId);
		url = url+ "?taskId="+taskId;
		return "redirect:"+url;
	}
	
	// ׼��������
	@RequestMapping("audit")
	public String audit(HttpServletRequest request){
		//��ȡ����ID
		String taskId = request.getParameter("taskId");
		/**һ��ʹ������ID��������ٵ�ID���Ӷ���ȡ��ٵ���Ϣ*/
		Object object = workFlowService.findBillByTaskId(taskId);		
		request.setAttribute("taskId", taskId);
		request.setAttribute("object", object);
		/**������֪����ID����ѯProcessDefinitionEntiy���󣬴Ӷ���ȡ��ǰ�������֮����������ƣ������õ�List<String>������*/
		List<String> outcomeList = workFlowService.findOutComeListByTaskId(taskId);
		request.setAttribute("outcomeList", outcomeList);
		/**������ѯ������ʷ����˵������Ϣ��������ǰ�������ˣ�����List<Comment>*/
		List<Comment> commentList = workFlowService.findCommentByTaskId(taskId);
		request.setAttribute("commentList", commentList);
		String formName = object.getClass().getSimpleName();
		return "taskForm"+formName;
	}
	
	/**
	 * �ύ����
	 */
	@RequestMapping("submitTask")
	public String submitTask(WorkFlow workflow,HttpSession session){
		workFlowService.saveSubmitTask(workflow,session);
		return "tasklist";
	}
	
	// �鿴��ʷ����ע��Ϣ
	@RequestMapping("viewHisComment")
	public String viewHisComment(HttpServletRequest request){
		//��ȡ�嵥ID
		String id = request.getParameter("id");
		String formName = request.getParameter("formName");
		Object object = workFlowService.findBillById(id,formName);
		List<Comment> commentList = workFlowService.findCommentByBillId(id,formName);
		request.setAttribute("object", object);
		request.setAttribute("commentList", commentList);
		return "hisComment"+formName;
//		//1��ʹ����ٵ�ID����ѯ��ٵ����󣬽�������õ�ջ����֧�ֱ�����
//		LeaveBill leaveBill = leaveBillService.findLeaveBillById(Long.parseLong(id));
//		ValueContext.putValueStack(leaveBill);
//		//2��ʹ����ٵ�ID����ѯ��ʷ����ע��Ϣ
//		List<Comment> commentList = workflowService.findCommentByLeaveBillId(id);
//		ValueContext.putValueContext("commentList", commentList);
//		return "viewHisComment";
	}
	
	/**
	 * �鿴��ǰ����ͼ���鿴��ǰ��ڵ㣬��ʹ�ú�ɫ�Ŀ��ע��
	 */
	@RequestMapping("viewNowImage")
	public String viewCurrentImage(HttpServletRequest request){
		//����ID
		String taskId = request.getParameter("taskId");
		/**һ���鿴����ͼ*/
		//1����ȡ����ID����ȡ�������ʹ����������ȡ���̶���ID����ѯ���̶������
		ProcessDefinition pd = workFlowService.findProcessDefinitionByTaskId(taskId);
		//workflowAction_viewImage?deploymentId=<s:property value='#deploymentId'/>&imageName=<s:property value='#imageName'/>
		request.setAttribute("deploymentId", pd.getDeploymentId());
		request.setAttribute("imageName", pd.getDiagramResourceName());
		/**�����鿴��ǰ�����ȡ���ڻ��Ӧ������x,y,width,height����4��ֵ��ŵ�Map<String,Object>��*/
		Map<String, Object> map = workFlowService.findCoordingByTask(taskId);
		System.out.println(map);
		request.setAttribute("acs", map);
		return "image";
	}
	
	@RequestMapping("historicProcessInstance")
	public String historicProcessInstance(HttpServletRequest request){
		List<HistoricProcessInstance> his = workFlowService.historyProcess();
		request.setAttribute("historyProcessList", his);
		return "historyProcessList";
	}
}
