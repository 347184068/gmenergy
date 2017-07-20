<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>电量日报表</title>
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

	<form:form id="searchForm" modelAttribute="energyElecDay" action="${ctx}/energyelecday/energyElecDay/" method="post" class="breadcrumb form-search">
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
					value="<fmt:formatDate value="${not empty energyElecDay.dataTime ? energyElecDay.dataTime:now}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th colspan="2">日电量最大值(度)</th>
			<th>${maxDayElec.realData}</th>
			<th>出现时间(小时)</th>
			<th><fmt:formatDate value="${maxDayElec.dataTime}" type="both" /></th>
		</tr>
		<tr>
			<th colspan="2">日电量最小值(度)</th>
			<th>${minDayElec.realData}</th>
			<th>出现时间(小时)</th>
			<th><fmt:formatDate value="${minDayElec.dataTime}" type="both" /></th>
		</tr>
		<tr>
			<th colspan="2">日电量累计(度)</th>
			<th colspan="3">${sumDayElec}</th>
		</tr>
		<tr>
			<th colspan="2">日电量平均值(度)</th>
			<th colspan="3">${avgDayElec}</th>
		</tr>
		<tr>
			<th colspan="1">时间(小时)</th>
			<th colspan="1">电表显示值(度)</th>
			<th colspan="1">倍率</th>
			<th colspan="2">用电量(度)</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="elecHour">
			<tr>
				<td colspan="1"><fmt:formatDate value="${elecHour.dataTime}" type="both"/></td>
				<td colspan="1">${elecHour.data}</td>
				<td colspan="1">${elecHour.ratio}</td>
				<td colspan="2">${elecHour.realData}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>