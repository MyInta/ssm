<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 如果报错可能是缺少standard.jar -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script>
	function deletItems(){
		//提交form
		document.itemsForm.action="${pageContext.request.contextPath }/items/deletItems.action";
		document.itemsForm.submit();
	}
	function queryItems(){
		//提交form
		document.itemsForm.action="${pageContext.request.contextPath }/items/queryItems.action";
		document.itemsForm.submit();
	}
</script>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查询商品列表</title>
</head>
<body>
当前用户：${username }
<c:if test="${username!=null }">
    <a href="${pageContext.request.contextPath }/logout.action">退出</a>
</c:if>
<form name="itemsForm" action="${pageContext.request.contextPath }/items/queryItems.action" method="post">
    查询条件：
    <table width="100%" border=1>
        <tr>
        	<td>
        		商品名称：<input name="itemsCustom.name"/>
        		商品类型：
                <select name="itemtype">
                    <c:forEach items="${itemtypes}" var="itemtype">
                        <option value="${itemtype.key }">${itemtype.value }</option>
                    </c:forEach>
                </select>
        	</td>
            <td>
            	<input type="button" value="查询" onclick="queryItems()"/>
            	<input type="button" value="批量删除" onclick="deletItems()"/>
            </td>
        </tr>
    </table>
    商品列表：
    <table width="100%" border=1>
        <tr>
        	<td>选择</td>
            <td>商品名称</td>
            <td>商品价格</td>
            <td>生产日期</td>
            <td>商品描述</td>
            <td>操作</td>
        </tr>
        <c:forEach items="${itemsList }" var="items">
            <tr>
            	<td><input type="checkbox" name="items_id" value="${items.id }"></td>
                <td>${items.name }</td>
                <td>${items.price }</td>
                <td><fmt:formatDate value="${items.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${items.detail }</td>

                <td><a href="${pageContext.request.contextPath }/items/editItems.action?id=${items.id}">修改</a></td>

            </tr>
        </c:forEach>

    </table>
</form>
</body>

</html>