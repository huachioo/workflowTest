<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="container showgrid">
	<form id="inputForm" action="../leaveBill/addLeaveBill" method="post" class="form-horizontal">
		<fieldset>
			<legend><small>添加请假单</small></legend>
			<table border="1">
			<tr>
				<td>请假时间：</td>
				<td><input type="text" id="days" name="days" /></td>
			</tr>
			<tr>
				<td>请假原因：</td>
				<td><input type="text" id="content" name="content" /></td>
			</tr>
			<tr>
				<td>请假日期：</td>
				<td><input type="text" id="leaveDate" name="leaveDate" /></td>
			</tr>
			<tr>
				<td>备注：</td>
				<td><input type="text" id="remark" name="remark" /></td>
			</tr>
			<tr>
				<td>
					<button type="submit">申请</button>
					<button type="reset">重置</button>
				</td>
			</tr>
			
		</table>
		</fieldset>
	</form>
	</div>
</body>
</html>