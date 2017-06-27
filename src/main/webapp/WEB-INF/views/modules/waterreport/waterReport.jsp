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
		var type = ${waterData.type};
		//alert(type);
		function getChart(){
			var deviceId = $("#deviceId").val();
			//alert("deviceId----"+deviceId);
			var inDate = $("#inDate").val();

			$("#searchForm").attr('action',"${ctx}/water/report/getReportList?type="+type+"&inDate="+inDate);
			var action = $("#searchForm").attr('action');
			$("#searchForm").validate({
				submitHandler: function (form) {
					loading('正在提交，请稍等...');
					form.submit();
				}
			});
		}

	</script>
</head>
<body>
<form:form id="searchForm" modelAttribute="waterData"  action="${ctx}/water/report/getReportList?type=${waterData.type}" method="post" class="breadcrumb form-search ">
	<ul class="ul-form2">
		<li>
			<label>监测点：</label>
			<sys:treeselect id="device" name="deviceId" value="${waterData.deviceId}"
							labelName="overLimit.deviceId" labelValue="${deviceName}"
							title="监测点" url="/power/analysis/treeData?menu=water" cssClass="input-small" allowClear="true"/>
		</li>
		<li>&nbsp;&nbsp;&nbsp;选择时间：
			<c:choose>
				<c:when test="${waterData.type==3}">
					<input name="inDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
						   value="<fmt:formatDate value="${waterData.inDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</c:when>
				<c:otherwise>
					<input name="inDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
						   value="<fmt:formatDate value="${waterData.inDate}" pattern="yyyy-MM"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
				</c:otherwise>
			</c:choose>

		</li>
		<li class="btns">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
		</li>
		<li class="clearfix"></li>
	</ul>
	<%--</div>--%>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>序号</th><th>净累计流量</th>
		<th>瞬时流量</th><th>压力</th><th>压力下限报警</th>
		<th>压力上限报警</th><th>测控箱开门报警</th>
		<th>时间</th>
	</tr>
	</thead>
	<tbody>
	<c:choose>
		<c:when test="${empty waterDataList}">
			<tr><td colspan="8">当前没有数据</td></tr>
		</c:when>
		<c:otherwise>
			<c:forEach items="${waterDataList}" var="waterData" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td>${waterData.jljll}</td>
					<td>${waterData.ssll}</td>
					<td>${waterData.pressure}</td>
					<td>${waterData.presslowWarn}</td>
					<td>${waterData.pressupWarn}</td>
					<td>${waterData.ccxkmbj}</td>
					<td><fmt:formatDate value="${waterData.inDate}" type="both"/></td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>

	</tbody>
</table>


</body>
</html>