<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ognl demo</title>
</head>
<body>
	<!-- 使用ognl struts2标签计算字符串长度
		value内值为ognl表达式
	 -->
	<s:property value="'haha'.length()"/>
</body>
</html>