<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>valueStack Demo</title>
</head>
<body>
	<s:debug></s:debug>
	<!-- 获得string字符串 -->
	<s:property value="name"/>
	<!-- 获取对象内属性 -->
	<s:property value="user.name"/>
	<s:property value="user.password"/>
	<s:property value="user.address"/>
	<!-- 获得list对象内属性 -->
	<!-- 方法一 -->
	获取list属性的第一种方式：<br/>
	<s:property value="list[0].name"/>
	<s:property value="list[0].password"/>
	<s:property value="list[0].address"/>
	<br/>
	<s:property value="list[1].name"/>
	<s:property value="list[1].password"/>
	<s:property value="list[1].address"/>
	<br/>
	<!-- 方法二 补充知识，注释使用 
		当注释标签时，得用%这个符号，无为HTML注释，有则jsp注释-->
	<%-- <s:iterator> --%>
	获取list属性的第二种方式：<br/>
	<s:iterator value="list">
		<s:property value="name"/>
		<s:property value="password"/>
		<s:property value="address"/>
		<br/>
	</s:iterator>
	<!-- 方法三 补充知识
		因为获取到数据会被放入context中，故从中取需要有# -->
	获取list属性的第三种方式：<br/>
	<s:iterator value="list" var="user">
		<s:property value="#user.name"/>
		<s:property value="#user.password"/>
		<s:property value="#user.address"/>
		<br/>
	</s:iterator>
	
	<!-- 获取set方式注入的值栈值 直接用s:property value=""形式即可
		但如果是push形式注入的，则用到 value="[0].top"表示取数组中第一个值，即栈顶值 -->
</body>
</html>