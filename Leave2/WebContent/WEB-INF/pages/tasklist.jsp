<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td height="24" bgcolor="#353c44"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		            
		                <td width="94%" valign="bottom"><span class="STYLE1">个人任务管理列表</span></td>
		              </tr>
		            </table></td>
		            <td><div align="right"><span class="STYLE1">
		              </span></div></td>
		          </tr>
		        </table></td>
		      </tr>
		    </table></td>
		  </tr>
		  <tr>
		    <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce" onmouseover="changeto()"  onmouseout="changeback()">
		      <tr>
		        <td width="15%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">任务ID</span></div></td>
		        <td width="25%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">任务名称</span></div></td>
		        <td width="20%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">创建时间</span></div></td>
		        <td width="20%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">办理人</span></div></td>
		        <td width="20%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">操作</span></div></td>
		      </tr>
		       <c:forEach items="${tasklist}" var="p">
		      		<tr>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center">${p.id }</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${p.name }</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${p.createTime }</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${p.assignee }</div></td>
				        <td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE21">
				        	<a href="../workflow/viewTaskForm?taskId=${p.id }">办理任务</a>
							<a target="_blank" href="../workflow/viewImage?deploymentId=${p.id }">查看当前流程图</a>
				        </div></td>
				    </tr> 
		      	</c:forEach>
		      
		    </table></td>
		  </tr>
	</table>
</body>
</html>