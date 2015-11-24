package cn.itcast.ssh.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 璇峰亣鍗�
 */
public class LeaveBill implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//涓婚敭ID
	private Integer days;// 璇峰亣澶╂暟
	private String content;// 璇峰亣鍐呭
	private Date leaveDate = new Date();// 璇峰亣鏃堕棿
	private String remark;// 澶囨敞
	private Employee user;// 璇峰亣浜�
	
	private Integer state=0;// 璇峰亣鍗曠姸鎬� 0鍒濆褰曞叆,1.寮�濮嬪鎵�,2涓哄鎵瑰畬鎴�

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
