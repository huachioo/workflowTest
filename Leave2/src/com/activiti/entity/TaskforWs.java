package com.activiti.entity;

import java.util.Date;

import javax.xml.crypto.Data;

import org.springframework.format.annotation.DateTimeFormat;

public class TaskforWs{
    private String id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    private String assignee;
	public String getId() {
		return id;
	}
	public void setId(String string) {
		this.id = string;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date date) {
		this.createTime = date;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
    
    
}
