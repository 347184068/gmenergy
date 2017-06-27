<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>设备管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/devices/monitordevices/list?menu=${monitordevices.menu}">设备列表</a></li>
	<li><a href="${ctx}/devices/monitordevices/form?menu=${monitordevices.menu}">设备列表添加</a></li>
</ul>
<form:form id="searchForm" modelAttribute="monitordevices" action="${ctx}/devices/monitordevices?menu=${monitordevices.menu}" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<ul class="ul-form">
		<li><label>设备名称：</label>
			<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
		</li>
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		<li class="clearfix"></li>
	</ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>设备名称</th>
		<th>操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="monitordevices">
		<tr>
			<td><a href="${ctx}/devices/monitordevices/form?id=${monitordevices.id}">
				${monitordevices.name}
			</a></td>
			<td>
				<a href="${ctx}/devices/monitordevices/form?id=${monitordevices.id}">修改</a>
				<a href="${ctx}/devices/monitordevices/delete?id=${monitordevices.id}" onclick="return confirmx('确认要删除该生产单元吗？', this.href)">删除</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>