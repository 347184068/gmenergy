<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>电量月报表</title>
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

	<style>
		#contentTable th,#contentTable td{
			padding:10px 0;
			text-align: center;
			background: transparent;
		}
		#contentTable th{
			background: #F9f9f9;
		}
		.pagination{
			float: right;
		}
	</style>
</head>
<body>
	<form:form id="searchForm" modelAttribute="energyElecMonth" action="${ctx}/energyelecmonth/energyElecMonth/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>监测设备：</label>
				<form:select items="${deviceList}"  itemLabel="name" itemValue="deviceId"
							 path="deviceId" htmlEscape="false" maxlength="11" class="input-medium"/>
				<c:set var="now" value="<%=new Date()%>" />
			</li>
			<li><label>选择日期：</label>
				<input name="dataTime" type="text" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${not empty energyElecMonth.dataTime ? energyElecMonth.dataTime : now}" pattern="yyyy-MM"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th colspan="2">月电量最大值(度)</th>
			<th>${maxMonthElec.realData}</th>
			<th>出现时间(日)</th>
			<th><fmt:formatDate value="${maxMonthElec.dataTime}" pattern="dd" type="both" /></th>
		</tr>
		<tr>
			<th colspan="2">月电量最小值(度)</th>
			<th>${minMonthElec.realData}</th>
			<th>出现时间(日)</th>
			<th><fmt:formatDate value="${minMonthElec.dataTime}" pattern="dd" type="both" /></th>
		</tr>
		<tr>
			<th colspan="2">月电量累计(度)</th>
			<th colspan="3">${sumMonthElec}</th>
		</tr>
		<tr>
			<th colspan="2">月电量平均值(度)</th>
			<th colspan="3">${avgMonthElec}</th>
		</tr>
		<tr>
			<th colspan="1">时间(日)</th>
			<th colspan="1">电表显示值(度)</th>
			<th colspan="1">倍率</th>
			<th colspan="2">用电量(度)</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="elecDay">
			<tr>
				<td colspan="1"><fmt:formatDate value="${elecDay.dataTime}" pattern="dd" type="both"/></td>
				<td colspan="1">${elecDay.data}</td>
				<td colspan="1">${elecDay.ratio}</td>
				<td colspan="2">${elecDay.realData}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>