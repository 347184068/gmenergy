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
	</style>
	<script type="text/javascript">

		function getChart(type){
			//(type);
			var deviceId = $("#deviceId").val();
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var start_compare = $("#start").val();
			var end_compare = $("#end").val();
			//alert(deviceId+"---"+"---"+startTime+"---"+endTime+"--"+type+"---"+start_compare+"--"+end_compare);

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


			//alert(",,,,,after,,return false......");

			$("#searchForm").attr('action',"${ctx}/expertdb/contrastDataAmount/getOneDeviceEwoIntervalChart?type=2");
			var action = $("#searchForm").attr('action');

			$("#searchForm").validate({
				submitHandler: function (form) {
					//loading('正在提交，请稍等...');
					form.submit();
				}
			});
		}


		function setHref(type){
			var deviceId = $("#deviceId").val();
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();

			var start_compare = $("#start").val();
			var end_compare = $("#end").val();

			//alert(deviceId+"---"+"---"+startTime+"---"+endTime+"--"+type+"---"+start_compare+"--"+end_compare);


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

			//alert(",,,,,after,,return false......");

			var link = "${ctx}/expertdb/contrastDataAmount/getOneDeviceEwoIntervalChart?type=2"
					+"&startTime="+startTime
					+"&endTime="+endTime+"&deviceId="+deviceId+"&start="+start_compare+"&end="+end_compare;
			$("#load").attr("href",link);
			$("#elec").attr("href",link);
			var href1 = $("#load").attr("href");
			var href2= $("#elec").attr("href");
		}

	</script>
</head>
<body>

<form:form id="searchForm"  action="" method="post" class="breadcrumb form-search ">
	<ul class="ul-form2">
		<li>
			<label>监测点：</label>
			<sys:treeselect id="device" name="deviceId" value="${deviceId}" labelName="dataAmount.deviceId" labelValue="${deviceName}"
							title="监测点" url="/power/analysis/treeData?menu=elec" cssClass="input-small" allowClear="true"/>
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
		<li>&nbsp;&nbsp;&nbsp;请选择对比时间区间 &nbsp;&nbsp; 开始时间点：
			<input type="text" class="Wdate" id="start" name="start"
				   onclick="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'end\')}'})"
				   value="${start}"/>
		</li>
		<li>&nbsp;&nbsp;&nbsp;结束时间点：
			<input type="text" class="Wdate" id="end" name="end"
				   onclick="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd',minDate:'#F{$dp.$D(\'start\')}'})"
				   value="${end}"/>
		</li>
		<li class="btns">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return getChart(${type})"/>
		</li>
		<li class="clearfix"></li>
	</ul>
	<%--</div>--%>
</form:form>

<ul class="nav nav-tabs">
	<%--<li class="active" id="load_li">
		<a onclick="return setHref(1)" href="" id="load">负荷</a>
	</li>--%>
	<li id="elec_li" class="active">
		<a onclick="return  setHref(2)" href="" id="elec">电量</a>
	</li>
</ul>

<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<tbody>
		<div id="contentChart" style="height:500px"></div>
		<div id="content_Contrast" style="height:500px"></div>

	</tbody>
</table>
<script type="text/javascript">

	var option = ${option};
	//alert("-------"+JSON.stringify(option));
	var psChar = echarts.init(document.getElementById("contentChart"));
	psChar.clear();
	psChar.setOption(option);

	var option_contrast = ${option_compare};
	//alert("-------"+JSON.stringify(option_contrast));
	var psChar_contrast = echarts.init(document.getElementById("content_Contrast"));
	psChar_contrast.clear();
	psChar_contrast.setOption(option_contrast);

	var type = ${type};
	//alert(type+"---type--");
	// tab选项卡切换
	/*if (type==1)
	{
		if ($("#elec_li").hasClass("active")) {
			$("#elec_li").removeClass("active");
			$("#load_li").addClass("active");
		}
	}
	if (type==2)
	{
		if ($("#load_li").hasClass("active")) {
			$("#load_li").removeClass("active");
			$("#elec_li").addClass("active");
		}
	}*/
</script>
</body>
</html>
