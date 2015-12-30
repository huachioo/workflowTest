<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
			<a href="../passport/addReimburseBill">报销申请</a>
			<a href="../reimburseBill/reimburseBillHome">查询报销申请</a><br>
			<a href="../passport/addLeaveBill">请假申请</a>
			<a href="../leaveBill/leaveBillHome">查询请假申请</a><br>
			<a href="../workflow/tasklist">查看当前任务</a>
			<a href="../workflow/historicProcessInstance">查看历史记录</a>
			<a href="../employee/employeeHome">查看员工</a><br>
			<a href="../workflow/deployHome">查询部署流程</a><br>
			部署流程
			<form action="../workflow/read" method="post">
			   流程名 <input type="text" id="fileName" name="fileName"/><br>
			  流程地址   <input type="file" id="fileAddress" name="fileAddress"/>
			    <input type="submit" value="部署流程" /> 
			</form>
</body>
</html>