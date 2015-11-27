package com.activiti.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("passpord")
public class PasspordController {
	@RequestMapping(value="/workflow")
	public Object Welcome(HttpServletRequest request) {
			return "defineWorkFlow";
	}

}
