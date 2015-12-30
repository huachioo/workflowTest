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
	<form id="inputForm" action="../reimburseBill/addReimburseBill" method="post" class="form-horizontal">
		<fieldset>
			<legend><small>添加报销单</small></legend>
			<table border="1">
			<tr>
				<td>报销金额：</td>
				<td><input type="text" id="input" name="input" /></td>
			</tr>
			<tr>
				<td>报销内容：</td>
				<td><input type="text" id="content" name="content" /></td>
			</tr>
			<tr>
				<td>报销日期：</td>
				<td><input type="text" id="reimburseDate" name="reimburseDate" /></td>
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