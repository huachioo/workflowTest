<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="../leaveBill/addLeaveBill">添加请假申请</a><br>
<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce" onmouseover="changeto()"  onmouseout="changeback()">
		      <tr>
		        <td width="5%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">ID</span></div></td>
		        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">请假人</span></div></td>
		        <td width="5%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">请假天数</span></div></td>
		        <td width="15%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">请假事由</span></div></td>
		        <td width="20%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">请假备注</span></div></td>
		        <td width="15%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">请假时间</span></div></td>
		        <td width="5%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">请假状态</span></div></td>
		        <td width="25%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">操作</span></div></td>
		      </tr>
		            <c:forEach items="${leaveBillList}" var="p">
		      		<tr>
				         <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center">${p.id }</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${p.user.name }</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${p.days }</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${p.content }</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${p.remark }</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${p.leaveDate }</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19">
				        	<div align="center">
				        	<c:if test="${p.state==0}">
				 					未审核
				 				</c:if >
				 				<c:if test="${p.state==1}">
				 					审核中
				 				</c:if >
				 				<c:if test="${p.state==2}">
				 					已完成
				 				</c:if >
			            	</div>
			            </td>
			            <td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE21">
				        	<c:if test="${p.state==0}">
			        			<a href="../leaveBill/updateLeaveBill" >修改</a>
								<a href="../leaveBill/deleteLeaveBill?id=${p.id}" >删除</a>
								<a href="../workflow/startProcess?id=${p.id }&formName=LeaveBill" >申请请假</a>
			        		</c:if>
			 				<c:if test="${p.state==1}">
			 					<a href="../workflow/viewHisComment?id=${p.id }&formName=LeaveBill" >查看审核记录</a>
			 				</c:if>
			 				<c:if test="${p.state==2}">
			 					<a href="../leaveBill/deleteLeaveBill?id=${p.id}" >删除</a>
			 					<a href="../workflow/viewHisComment?id=${p.id }&formName=LeaveBill" >查看审核记录</a>
			 				</c:if>
				        	
						</div></td>
				       
				    </tr> 		        
		      </c:forEach>
		    </table>
		    <a href="../passport/returnMain">返回主页</a><br>
</body>
</html>