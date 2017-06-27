<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>电量列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/elec/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});


		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/elec/queryList");
			$("#searchForm").submit();
			return false;
		}

	</script>
</head>
<body>

	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/elec/queryList">数据列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="elecPojo" action="${ctx}/elec/queryList" method="post" class="breadcrumb form-search ">
		 <ul class="ul-form">
			<li>
				<label>线路名称：</label>
				<form:input path="linesName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li>
				<label>查询时间：</label>
				<input id="requestTime"  name="requestTime" type="text"  maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${elecPojo.requestTime}" pattern="yyyy/MM/dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>序号</th>
			<th>线路名称</th>
			<th>电流</th>
			<th>电压</th>
			<th>功率因数</th>
			<th>瞬时值</th>
			<th>累计电量</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${list}" var="elecPojo" varStatus="item">
			<tr>
				<td>${item.index+1}</td>
				<td>${elecPojo.linesName}</td>
				<td>${elecPojo.electricity}</td>
				<td>${elecPojo.voltage}</td>
				<td>${elecPojo.powerFactor}</td>
				<td>${elecPojo.instantaneousValue}</td>
				<td>${elecPojo.cumulativePower}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>