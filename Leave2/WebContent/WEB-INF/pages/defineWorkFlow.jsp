<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
 <script src="../js/jquery-1.8.1.min.js" type="text/javascript"></script>
</head>
<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce" onmouseover="changeto()"  onmouseout="changeback()">
		      <tr>
		        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">ID</span></div></td>
		        <td width="60%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">流程名称</span></div></td>
		        <td width="20%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">发布时间</span></div></td>
		        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">操作</span></div></td>
		      </tr>
		            <c:forEach items="${depList}" var="p">
		      		<tr>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center">${p.id }</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${p.name }</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${p.deploymentTime }</div></td>
				        <td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE21">
				        	<a href="../workflow/delDeployment?deploymentId=${p.id }">删除</a>
				        </div></td>
				    </tr> 		        
		      </c:forEach>
		    </table>
		    
		    <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce" onmouseover="changeto()"  onmouseout="changeback()">
		      <tr>
		        <td width="12%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">ID</span></div></td>
		        <td width="18%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">名称</span></div></td>
		        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">流程定义的KEY</span></div></td>
		        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">流程定义的版本</span></div></td>
		        <td width="15%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">流程定义的规则文件名称</span></div></td>
		        <td width="15%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">流程定义的规则图片名称</span></div></td>
		        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">部署ID</span></div></td>
		        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">操作</span></div></td>
		      </tr>
		      <c:forEach items="${pdList}" var="p">
		      		<tr>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center">${p.id }</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${p.name }</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${p.key }</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center">${p.version }</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center">${p.resourceName }</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center">${p.diagramResourceName }</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center">${p.deploymentId }</div></td>
				        <td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE21">
				        	<a target="_blank" href="../workflow/viewImage?deploymentId=${p.deploymentId }&imageName=${p.diagramResourceName }">查看流程图</a> 
					 	</div></td>
				    </tr> 
		      </c:forEach>
		        
		      
		    </table>
</body>
</html>