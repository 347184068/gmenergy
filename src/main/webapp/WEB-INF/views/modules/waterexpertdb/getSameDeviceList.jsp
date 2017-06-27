<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
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
		.btnStyle {
			background-color: #328aa4;
			color: #ffffff;
			border-radius: 20px;
			border: 4px;
			padding-left: 20px;
			padding-right: 20px;
			padding-top: 7px;
			padding-bottom: 7px;
		}
	</style>
	<script type="text/javascript">
		function validateCondition(deviceId,startTime,endTime){
			var startMonth ;
			if(deviceId=="" || deviceId==null || typeof(deviceId)=="undefined"){
				alert("请选择监测点！");
				return false;
			}
			if(startTime=="" || startTime==null || typeof (startTime)=="undefined"){
				alert("请选择开始时间！");
				return false;
			}else{
				startMonth = new Date(startTime).getMonth();
			}
			if(endTime=="" || endTime==null || typeof (endTime)=="undefined"){
				alert("请选择结束时间！");
				return false;
			}else{
				var endMonth = new Date(endTime).getMonth();

				if(startMonth!=endMonth){
					alert("结束时间与开始时间在同一月度区间！");
					return false;
				}
			}
			return true;
		}
		function getChart(){

			var compareType=$("#compareType").val();
			var deviceId = $("#deviceId").val();
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var type = $("#type").val();
			if(validateCondition(deviceId,startTime,endTime)==false){
				return false;
			}else{
				$.post("${ctx}/water/expertdb/getSameDeviceChart",{deviceId:deviceId,compareType:compareType,startTime:startTime,endTime:endTime,type:type},
						function(result){
							if(result.flag==false){
								$("#contentChart").html(result.msg);
							}else{
								var psLineChar = echarts.init(document.getElementById("contentChart"));
								psLineChar.clear();
								psLineChar.setOption(result.option);
							}
						});
			}


		}

	</script>
</head>
<body>

<form:form id="searchForm"  action="" modelAttribute="contrastData" method="post" class="breadcrumb form-search ">
	<ul class="ul-form2">
		<li>
			<label>对比类型</label>
			<form:select path="compareType" htmlEscape="false" maxlength="11" class="input-xlarge  digits">
			<form:option value="1">同比</form:option>
			<form:option value="2">环比</form:option>
			</form:select>
		</li>
		<li>
			<label>监测点：</label>

			<form:select items="${deviceList}"  itemLabel="name" itemValue="id"
						 path="deviceId" htmlEscape="false" maxlength="11" class="input-xlarge  "/>
		</li>
		<li>&nbsp;&nbsp;&nbsp;开始时间点：
			<c:set var="now" value="<%=new Date()%>" />
			<input id="startTime"  name="startTime" type="text"  maxlength="20" class="input-medium Wdate "
				   value="<fmt:formatDate value="${not empty contrastDataAmount.startTime?contrastDataAmount.startTime:now}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		</li>
		<li>&nbsp;&nbsp;&nbsp;结束时间点：
			<c:set var="now" value="<%=new Date()%>" />
			<input id="endTime"  name="endTime" type="text"  maxlength="20" class="input-medium Wdate "
				   value="<fmt:formatDate value="${not empty contrastDataAmount.endTime?contrastDataAmount.endTime:now}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		</li>

		<li>
			<label>类型</label>
			<form:select path="type" htmlEscape="false" maxlength="11" class="input-xlarge  digits">
				<form:option value="1">净累计流量</form:option>
				<form:option value="2">瞬时流量</form:option>
			</form:select>
		</li>

		<li class="btns">
			<a onclick="return getChart()" class="btnStyle">查询</a>
		</li>
	</ul>
	<%--</div>--%>
</form:form>
<div id="contentTable" class="table table-striped table-bordered table-condensed" style="width: 98%;">
	<div id="contentChart" style="height:380px">
	</div>
</div>

</body>
</html>
