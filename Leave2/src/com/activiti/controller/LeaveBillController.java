package com.activiti.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.activiti.entity.Employee;
import com.activiti.entity.LeaveBill;
import com.activiti.service.LeaveBillService;


@Controller
@RequestMapping("leaveBill")
public class LeaveBillController {
	@Autowired
	@Qualifier("leaveBillService")
	LeaveBillService leaveBillService;
	
	@RequestMapping("/leaveBillHome")
	public ModelAndView leaveBillHome(HttpSession session){
		ModelAndView mav = new ModelAndView("leaveBillList");
		Employee employee = (Employee) session.getAttribute("user");
		//1：查询所有的请假信息（对应a_leavebill），返回List<LeaveBill>
		List<LeaveBill> leaveBillList = leaveBillService.findLeaveBillList(employee); 
		mav.addObject("leaveBillList", leaveBillList);
		return mav;
	}
	
	/**
	 * 添加请假申请
	 * 
	 */
	@RequestMapping("/addLeaveBill")
	public String input(HttpSession session){
		
		return "input";
	}
	

}
