package com.activiti.controller;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.activiti.entity.Employee;
import com.activiti.entity.LeaveBill;
import com.activiti.entity.WorkFlow;
import com.activiti.service.WorkFlowService;
import javafx.scene.chart.PieChart.Data;

@Controller
@RequestMapping("workflow")
public class WorkFlowController {

	@Autowired
	@Qualifier("workFlowService")
	WorkFlowService workFlowService;
	
	
	
	/**
	 * 部署管理首页显示
	 * @return
	 */
	@RequestMapping("/deployHome")
	public ModelAndView deployHome(){
		ModelAndView mav = new ModelAndView("defineWorkFlow");
		//1:查询部署对象信息，对应表（act_re_deployment）
		List<Deployment> depList = workFlowService.findDeploymentList();
		//2:查询流程定义的信息，对应表（act_re_procdef）
		List<ProcessDefinition> pdList = workFlowService.findProcessDefinitionList();
		//放置到上下文对象中
		//request.getSession().setAttribute("depList", depList);
		//request.getSession().setAttribute("pdList", pdList);
		mav.addObject("depList", depList);
		mav.addObject("pdList", pdList);
		//Deployment deployment = depList.get(0);
		//deployment.get
		return mav;
	}
	
	//部署流程定义
	@RequestMapping("/read")
	public String newdeploy(){
		WorkFlow workFlow = new WorkFlow();
		//获取页面传递的值
		//1：获取页面上传递的zip格式的文件，格式是File类型
		File file = new File("D:/workspace/Leave2/resource/bpmn2.zip");
		System.out.println(file);
		//文件名称
		String fileName = "请假";
		workFlow.setFile(file);
		workFlow.setFilename("请假");
		//完成部署
		workFlowService.saveNewDeploy(file,fileName);
		return "main";		
	}
	
	/**
	 * 删除部署信息
	 */
	@RequestMapping("/delDeployment")
	public String delDeployment(HttpServletRequest request){
		//1：获取部署对象ID
		String deploymentId = request.getParameter("deploymentId");
		//2：使用部署对象ID，删除流程定义
		workFlowService.deleteProcessDefinitionByDeploymentId(deploymentId);
		return "defineWorkFlow";
	}
	
	/**
	 * 查看流程图
	 * @throws Exception 
	 */
	@RequestMapping("/viewImage")
	public String viewImage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//1：获取页面传递的部署对象ID和资源图片名称
		//部署对象ID
		String deploymentId = request.getParameter("deploymentId");
		//资源图片名称
		String imageName = request.getParameter("imageName");
		//2：获取资源文件表（act_ge_bytearray）中资源图片输入流InputStream
		InputStream in = workFlowService.findImageInputStream(deploymentId,imageName);
		//3：从response对象获取输出流
		OutputStream out = response.getOutputStream();
		//4：将输入流中的数据读取出来，写到输出流中
		for(int b=-1;(b=in.read())!=-1;){
			out.write(b);
		}
		out.close();
		in.close();
		//将图写到页面上，用输出流写
		return null;
	}
	
	// 启动流程
	@RequestMapping("/startProcess")
	public String startProcess(HttpServletRequest request,HttpSession session){
		//更新请假状态，启动流程实例，让启动的流程实例关联业务
		long leaveBillId = Long.parseLong(request.getParameter("id"));
		Employee Emp = (Employee) session.getAttribute("user");
		workFlowService.saveStartProcess(leaveBillId,Emp.getName());
		return "main";
	}
	
	@RequestMapping("tasklist")
	public String listTask(HttpSession session,HttpServletRequest request){
		//1：从Session中获取当前用户名
		String name = (String) session.getAttribute("username");
		//2：使用当前用户名查询正在执行的任务表，获取当前任务的集合List<Task>
		List<Task> tasklist = workFlowService.findTaskListByName(name); 
		request.setAttribute("tasklist", tasklist);
		return "tasklist";
	}
	
	/**
	 * 打开任务表单
	 */
	@RequestMapping("viewTaskForm")
	public String viewTaskForm(HttpServletRequest request){
		//任务ID
		String taskId = (String) request.getParameter("taskId");
		//获取任务表单中任务节点的url连接
		String url = workFlowService.findTaskFormKeyByTaskId(taskId);
		url = url+ "?taskId="+taskId;
		return "redirect:"+url;
	}
	
	// 准备表单数据
	@RequestMapping("audit")
	public String audit(HttpServletRequest request){
		//获取任务ID
		String taskId = request.getParameter("taskId");
		/**一：使用任务ID，查找请假单ID，从而获取请假单信息*/
		LeaveBill leaveBill = workFlowService.findLeaveBillByTaskId(taskId);
		request.setAttribute("taskId", taskId);
		request.setAttribute("leaveBill", leaveBill);
		/**二：已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中*/
		List<String> outcomeList = workFlowService.findOutComeListByTaskId(taskId);
		request.setAttribute("outcomeList", outcomeList);
		/**三：查询所有历史审核人的审核信息，帮助当前人完成审核，返回List<Comment>*/
		List<Comment> commentList = workFlowService.findCommentByTaskId(taskId);
		request.setAttribute("commentList", commentList);
		return "taskform";
	}
	
	/**
	 * 提交任务
	 */
	@RequestMapping("submitTask")
	public String submitTask(WorkFlow workflow,HttpSession session){
		workFlowService.saveSubmitTask(workflow,session);
		return "tasklist";
	}
	
//	// 查看历史的批注信息
//	@RequestMapping("viewHisComment")
//	public String viewHisComment(){
//		//获取清单ID
//		Long id = workflowBean.getId();
//		//1：使用请假单ID，查询请假单对象，将对象放置到栈顶，支持表单回显
//		LeaveBill leaveBill = leaveBillService.findLeaveBillById(id);
//		ValueContext.putValueStack(leaveBill);
//		//2：使用请假单ID，查询历史的批注信息
//		List<Comment> commentList = workflowService.findCommentByLeaveBillId(id);
//		ValueContext.putValueContext("commentList", commentList);
//		return "viewHisComment";
//	}
}
