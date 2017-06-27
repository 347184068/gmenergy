<%@ taglib prefix="sys" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用水报表</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/echart.jsp"%>
	<style type="text/css">
		.form-search .ul-form2 {margin:0;overflow:hidden;}
		.form-search .ul-form2 li{float:left;list-style:none;height:35px;line-height:35px;}
		.form-search .ul-form2 li label{width:100px;text-align:right;}
		.form-search .ul-form2 li span label{width:auto;}
		.form-search .ul-form2 li.clearfix{float:none;}
		.form-search .ul-form2 li.btns{padding-left:10px;}
		.form-search .ul-form2 li.btns .btn{margin-right:5px;}
	</style>
	<script type="text/javascript">
		function getChart(){
			var deviceId = $("#deviceId").val();
			//alert("deviceId----"+deviceId);
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var monitorType = $("#monitorType").val();


			$("#searchForm").attr('action',"${ctx}/limitwarn/overLimit/getOneDeviceOverLimitByCondition");
			var action = $("#searchForm").attr('action');
			$("#searchForm").validate({
				submitHandler: function (form) {
					loading('正在提交，请稍等...');
					form.submit();
				}
			})






		}

	</script>
</head>
<body>
<form:form id="searchForm" modelAttribute="overLimit"  action="" method="post" class="breadcrumb form-search ">
	<ul class="ul-form2">
		<li>
			<label>监测点：</label>
			<sys:treeselect id="device" name="deviceId" value="${overLimit.deviceId}"
							labelName="overLimit.deviceId" labelValue="${deviceName}"
							title="监测点" url="/power/analysis/treeData" cssClass="input-small" allowClear="true"/>
		</li>
		<li>&nbsp;&nbsp;&nbsp;开始时间点：
			<input type="text" class="Wdate" id="startTime" name="startTime"
				   onclick="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})"
				   value="${startDate}"/>
		</li>
		<li>&nbsp;&nbsp;&nbsp;结束时间点：
			<input type="text" class="Wdate" id="endTime" name="endTime"
				   onclick="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})"
				   value="${endDate}"/>
		</li>
		<li>
			<select id="monitorType" name="monitorType">
				<c:forEach items="${typeList}" var="monitorType">
					<option value="${monitorType.itemType}">${monitorType.itemValue}</option>
				</c:forEach>
			</select>
		</li>
		<li class="btns">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return getChart()"/>
		</li>
		<li class="clearfix"></li>
	</ul>
	<%--</div>--%>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>序号</th><th>监测点名称</th><th>净累计流量</th>
		<th>瞬时流量</th><th>压力</th><th>压力下限报警</th>
		<th>压力上限报警</th><th>测控箱开门报警</th>
		<th>时间</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${waterDataList}" var="limit" varStatus="status">
		<tr>
			<td>${status.count}</td>
			<td>${limit.deviceName}</td>
			<td>${limit.monitorType}</td>
			<td>${limit.indate}</td>
			<td>00:00</td><td>23:45</td>
			<td>${limit.standardValue}</td>
			<td>${limit.lowerLimit}%</td>
			<td>${limit.upperLimit}%</td>
			<td>
				<c:if test="${limit.limitPercent}>0">
					高于上限
				</c:if>
				<c:if test="${limit.limitPercent}<0">
					低于下限
				</c:if>
					${limit.limitPercent}%
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>

<script type="text/javascript">
	var selectType = ${overLimit.monitorType};
	$("#monitorType option[value='"+selectType+"'").attr("selected", true);
</script>
</body>
</html>