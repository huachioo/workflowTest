package com.activiti.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


public class LeaveBill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//id
	private Integer days;// 请假时间
	private String content;// 请假原因
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date leaveDate; // 请假日期
	
	private String remark;//备注
	private Employee user;// 请假用户	
	private Integer state=0;// 表单状态 0未执行 1执行中2已完成
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Employee getUser() {
		return user;
	}

	public void setUser(Employee user) {
		this.user = user;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
}
