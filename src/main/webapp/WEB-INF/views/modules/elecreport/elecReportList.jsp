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

	</script>
</head>
<body>

	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/elec/list">数据列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="elecPojo" action="${ctx}/elec/list" method="post" class="breadcrumb form-search ">
		<ul class="ul-form">

			<li>
				<label>时间间隔：</label>
				<input type="text" name="timeInterval" id="timeInterval" htmlEscape="false"
					   maxlength="50" class="input-medium" onchange="setTimeInterval()" value="<%=request.getSession().getAttribute("timeInterval")%>"/>

				<input id="requestTime"  name="requestTime" type="hidden"  maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${elecPojo.requestTime}" pattern="yyyy/MM/dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',isShowClear:false});"/>


			<%--<form:input path="timeInterval" htmlEscape="false" maxlength="50" class="input-medium"
						onchange="setTimeInterval()"/>--%>
			minutes
			</li>
			<li class="btns">
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>线路名称</th>
			<th>电流</th>
			<th>电压</th>
			<th>功率因数</th>
			<th>瞬时值</th>
			<th>累计电量</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${list}" var="elecPojo">
			<tr>
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

<script>
	var time = $("#timeInterval").val();

	function setTimeInterval(){
		time = $("#timeInterval").val();

	}

	if(time=="" || time==null || typeof (time)=="undefined"){
		time=5
	}

	/*setInterval(function(){
		$("#searchForm").attr("action","${ctx}/elec/list");
		$("#searchForm").submit();
	},time*60*1000);*/
</script>
</body>
</html>