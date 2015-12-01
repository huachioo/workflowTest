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
	<form id="inputForm" action="../employee/updateEmployee?id=${employee.id}" method="post" class="form-horizontal">
		<fieldset>
			<legend><small>添加用户</small></legend>
			<table border="1">
			<tr>
				<td>用户名：</td>
				<td><input type="text" id="name" name="name" value="${employee.name }" /></td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><input type="text" id="password" name="password" value="${employee.password }" /></td>
			</tr>
			<tr>
				<td>邮箱：</td>
				<td><input type="text" id="email" name="email" value="${employee.email }" /></td>
			</tr>
			<tr>
				<td>角色：</td>
				<td><input type="text" id="role" name="role" value="${employee.role }" /></td>
			</tr>
			<tr>
				<td>
					<button type="submit">提交</button>
					<button type="reset">重置</button>
				</td>
			</tr>
			
		</table>
		</fieldset>
	</form>
	</div>
</body>
</html>