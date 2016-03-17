package com.activiti.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


public class CommentforWS {
	
	private String userId;
	private String fullMessage;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date time;
	private String id;
	private String taskId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFullMessage() {
		return fullMessage;
	}
	public void setFullMessage(String fullMessage) {
		this.fullMessage = fullMessage;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


}
