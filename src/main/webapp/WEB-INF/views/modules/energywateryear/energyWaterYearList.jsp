<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<jsp:useBean id="now" class="java.util.Date" />
<html>
<head>
	<title>水量年报表</title>
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
	<form:form id="searchForm" modelAttribute="energyWaterYear" action="${ctx}/energywateryear/energyWaterYear/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>监测点：</label>
				<form:select items="${deviceList}"  itemLabel="name" itemValue="deviceId"
							 path="deviceId" htmlEscape="false" maxlength="11" class="input-medium"/>
				<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy" var="date"/>
			</li>
			<li><label>选择日期：</label>
				<input name="selectYear" type="text" maxlength="20" class="input-medium Wdate"
					   value="${not empty energyWaterYear.selectYear ? energyWaterYear.selectYear : date}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th colspan="2">年水量最大值(吨)</th>
			<th>${maxMonthWater.data}</th>
			<th>出现时间(月)</th>
			<th><fmt:formatDate value="${maxMonthWater.dataTime}" pattern="MM" type="both" /></th>
		</tr>
		<tr>
			<th colspan="2">年水量最小值(吨)</th>
			<th>${minMonthWater.data}</th>
			<th>出现时间(月)</th>
			<th><fmt:formatDate value="${minMonthWater.dataTime}" pattern="MM" type="both" /></th>
		</tr>
		<tr>
			<th colspan="2">年水量累计(吨)</th>
			<th colspan="3">${sumMonthWater}</th>
		</tr>
		<tr>
			<th colspan="2">年水量平均值(吨)</th>
			<th colspan="3">${avgMonthWater}</th>
		</tr>
		<tr>
			<th colspan="1">时间(月)</th>
			<th colspan="4">用水量(吨)</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="waterMonth">
			<tr>
				<td colspan="1"><fmt:formatDate value="${waterMonth.dataTime}"  pattern="MM" type="both"/></td>
				<td colspan="4">${waterMonth.data}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>