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
import com.activiti.entity.ReimburseBill;
import com.activiti.service.ReimburseBillService;

@Controller
@RequestMapping("reimburseBill")
public class ReimburseBillController {
	@Autowired
	@Qualifier("reimburseBillService")
	ReimburseBillService reimburseBillService;
	//���
    @RequestMapping("/addReimburseBill")
    public String addLeaveBill(HttpSession session,ReimburseBill reimburseBill){
    	reimburseBill.setState(0);
    	Employee employee = (Employee) session.getAttribute("user");
    	reimburseBill.setEmployee(employee);
    	reimburseBillService.addReimburseBill(reimburseBill);
    	return "redirect:../reimburseBill/reimburseBillHome";
    }
	//��ѫ
	@RequestMapping("/reimburseBillHome")
	public ModelAndView reimburseBillHome(HttpSession session){
		ModelAndView mav = new ModelAndView("reimburseBillList");
		Employee employee = (Employee) session.getAttribute("user");
		//1����ѯ���е������Ϣ����Ӧa_leavebill��������List<LeaveBill>
		List<ReimburseBill> reimburseBillList = reimburseBillService.findReimburseBillList(employee); 
		mav.addObject("reimburseBillList", reimburseBillList);
		return mav;
	}

}
