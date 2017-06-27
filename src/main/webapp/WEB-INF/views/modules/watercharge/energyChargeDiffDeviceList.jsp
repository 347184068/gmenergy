<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>监测点水费计算管理</title>
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
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var unitPrice = $("#unitPrice").val();
			//alert(deviceId+"---"+"---"+startTime+"---"+endTime+"--"+unitPrice);

			if(deviceId=="" || deviceId==null || typeof(deviceId)=="undefined"){
				alert("请选择监测点！");
				return false;
			}
			if(startTime=="" || startTime==null || typeof (startTime)=="undefined"){
				alert("请选择开始时间！");
				return false;
			}
			if(endTime=="" || endTime==null || typeof (endTime)=="undefined"){
				alert("请选择结束时间！");
				return false;
			}
			if(unitPrice=="" || unitPrice==null || typeof (unitPrice)=="undefined"){
				alert("请输入水费单价！");
				return false;
			}

			//alert(",,,,,after,,return false......");

			$("#searchForm").attr('action',"${ctx}/water/charge/getDiffDeviceChartInfo");
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
<form:form id="searchForm" modelAttribute="energyCharge"  action="" method="post" class="breadcrumb form-search ">
	<ul class="ul-form2">
		<li>
			<label>监测点：</label>
			<sys:treeselect id="device" name="deviceId" value="${energyCharge.deviceId}" labelName="dataAmount.deviceId" labelValue="${deviceName}"
							title="监测点" url="/power/analysis/treeData?menu=water" cssClass="input-small" allowClear="true" checked="true"/>
		</li>
		<li>&nbsp;&nbsp;&nbsp;开始时间点：
			<input type="text" class="Wdate" id="startTime" name="startTime"
				   onclick="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM',maxDate:'#F{$dp.$D(\'endTime\')}'})"
				   value="${startDate}"/>
		</li>
		<li>&nbsp;&nbsp;&nbsp;结束时间点：
			<input type="text" class="Wdate" id="endTime" name="endTime"
				   onclick="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM',minDate:'#F{$dp.$D(\'startTime\')}'})"
				   value="${endDate}"/>
		</li>
		<li>&nbsp;&nbsp;&nbsp;请输入单价：
			<input type="text"  id="unitPrice" name="unitPrice" value="${energyCharge.unitPrice}"/>
		</li>
		<li class="btns">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return getChart()"/>
		</li>
		<li class="clearfix"></li>
	</ul>
	<%--</div>--%>
</form:form>
<sys:message content="${message}"/>
<div id="treeTable" class="table table-striped table-bordered table-condensed" style="width: 98%;">

	<div id="elecChart" style="height: 480px;width: 40%;float: left;margin-left: 5%"></div>
	<div id="chargeChart" style="height:480px;width: 40%;float: left;margin-left: 9%"></div>
</div>

<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>序号</th><th>监测点</th><th>耗水量</th><th>总水费(元)</th>
	</tr>
	</thead>
	<tbody>
	<c:choose>
		<c:when test="${empty list}">
			<tr><td colspan="4">当前没有数据</td></tr>
		</c:when>
	</c:choose>
	<c:forEach items="${list}" var="energyCharge" varStatus="status">
		<tr>
			<td>${status.count}</td>
			<td>${energyCharge.deviceName}</td>
			<td>${energyCharge.jljll}</td>
			<td>${energyCharge.totalPrice}</td>
		</tr>
	</c:forEach>
	</tbody>
</table>

<script type="text/javascript">
	if(${empty list}){
		document.getElementById("treeTable").style.display="none";
	}else{
		var option_elec = ${option_elec};
		var psElecChar = echarts.init(document.getElementById("elecChart"));
		psElecChar.clear();
		psElecChar.setOption(option_elec);

		var option_charge = ${option_charge};
		var psChargeChar = echarts.init(document.getElementById("chargeChart"));
		psChargeChar.clear();
		psChargeChar.setOption(option_charge);
	}
</script>
</body>
</html>