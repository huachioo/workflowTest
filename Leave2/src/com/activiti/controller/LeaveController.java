package com.activiti.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.activiti.entity.Leave;
import com.activiti.util.UserUtil;

@Controller
@RequestMapping("leave")
public class LeaveController {
	
//	 @Autowired
//	 private IdentityService identityService;
	 @Autowired
	 private RuntimeService runtimeService;
	
	 @RequestMapping("/apply")
	 public String  apply( HttpSession session) {		
	     return "apply";
	 }
	 
	 @RequestMapping("/start")
	 public String  start(Leave leave, HttpSession session) {		
		 User user = UserUtil.getUserFromSession(session);
		 //leave.setUserId(user.getId());
         Map<String, Object> variables = new HashMap<String, Object>();
         variables.put("leave", leave);
         String businessKey = user.getId().toString();
    //     identityService.setAuthenticatedUserId(user.getId());
         
//         ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave", businessKey, variables);
//         redirectAttributes.addFlashAttribute("message", "流程已启动，流程ID：" + processInstance.getId());
		 return "main";
	 }

}
