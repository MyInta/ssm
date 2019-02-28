<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/user/regist.css'/>">
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>	
	<script type="text/javascript" src="<c:url value='/jsps/js/user/regist.js'/>"></script>	
<title>注册页面</title>
</head>
<body>
	<div id="divMain">
		<div id="divTitle">
			<span id="spanTitle">新用户注册</span>
		</div>
		<div id="divBody">
		<form action="<c:url value='/UserServlet'/>" method="post" id="registForm">
			<input type="hidden" name="method" value="regist"/>
			<table id="tableForm">
			<tr>
				<td class="tdText">用户名：</td>
				<td class="tdInput"><input type="text" name="loginname" id="loginname"  class="inputClass" value="${form.loginname}" /></td>
				<td class="tdError"><label class="labelError" id="loginnameError">${errors.loginname }</label></td>
			</tr>
			<tr>
				<td class="tdText">登陆密码：</td>
				<td><input type="password" name="loginpass" id="loginpass" class="inputClass" value="${form.loginpass}"/></td>
				<td><label class="labelError" id="loginpassError">${errors.loginpass }</label></td>
			</tr>
			<tr>
				<td class="tdText">确认密码：</td>
				<td><input type="password" name="reloginPass" id="reloginPass" class="inputClass" value="${form.reloginPass}"/></td>
				<td><label class="labelError" id="reloginPassError">${errors.reloginpass }</label></td>
			</tr>
			<tr>
				<td class="tdText">Email：</td>
				<td><input type="text" name="email" id="email" class="inputClass" value="${form.email}"/></td>
				<td><label class="labelError" id="emailError">${errors.email }</label></td>
			</tr>
			<tr>
				<td class="tdText">图形验证码：</td>
				<td><input type="text" name="verifyCode" id="verifyCode" class="inputClass" value="${form.verifyCode}"/></td>
				<td><label class="labelError" id="verifyCodeError">${errors.verifycode }</label></td>
			</tr>
			<tr>
				<td class="tdText"></td>
				<td><div id="divVerifyCode"><img id="imgVerifyCode" src="<c:url value='/VerifyCodeServlet'/>"/></div></td>
				<td><a href="javascript:_hyz()">换一张</a></td>
			</tr>
			<tr>
				<td class="tdText"></td>
				<td><input type="image" src="<c:url value='/images/regist1.jpg'/>"  id="submitBtn"/></td>
				<td><label></label></td>
			</tr>
		</table>
		</form>
		</div>
	</div>
</body>
</html>