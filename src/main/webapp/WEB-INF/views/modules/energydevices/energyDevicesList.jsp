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
    <li class="active"><a href="${ctx}/energydevices/energyDevices?type=1">设备列表</a></li>
    <li><a href="${ctx}/energydevices/energyDevices/form">设备添加</a></li>
</ul>
	<form:form id="searchForm" modelAttribute="energyDevices" action="${ctx}/energydevices/energyDevices/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>设备ID：</label>
                <form:input path="deviceId" htmlEscape="false" maxlength="255" class="input-medium"/>
            </li>
            <li><label>名称：</label>
                <form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
            </li>
            <li><label>设备类型：</label>
                <form:select path="type" class="input-medium">
                    <form:options items="${fns:getDictList('devices_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
                <th>设备ID</th>
                <th>名称</th>
                <th>设备类型</th>
				<th>月限额</th>
				<th>年限额</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="energyDevices">
			<tr>
                <td><a href="${ctx}/energydevices/energyDevices/form?id=${energyDevices.id}">
                        ${energyDevices.deviceId}
                </a></td>
                <td>${energyDevices.name}
                </td>
                <td>
                        ${fns:getDictLabels(energyDevices.type,'devices_type','1')}
                </td>
				<td>
					${energyDevices.monthLimit}
				</td>
				<td>
					${energyDevices.yearLimit}
				</td>
				<td>
    				<a href="${ctx}/energydevices/energyDevices/form?id=${energyDevices.id}">修改</a>
					<a href="${ctx}/energydevices/energyDevices/delete?id=${energyDevices.id}" onclick="return confirmx('确认要删除该设备吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>