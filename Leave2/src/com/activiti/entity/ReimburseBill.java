package com.activiti.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ReimburseBill implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private int input;//金额
	private String content;// 报销内容
	private Employee employee;//报销人
	private String remark;//备注
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date reimburseDate; // 日期
	private Integer state=0;// 表单状态 0未执行 1执行中2已完成
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getInput() {
		return input;
	}
	public void setInput(int input) {
		this.input = input;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getreimburseDate() {
		return reimburseDate;
	}
	public void setreimburseDate(Date reimburseDate) {
		this.reimburseDate = reimburseDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	

}
