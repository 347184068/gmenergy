<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page import="java.util.Date" %>
<html>
<head>
    <title>水设备限额占比</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <%@include file="/WEB-INF/views/include/echart.jsp" %>
    <style>
        .btnClass {
            padding-top: 5px;
            padding-bottom: 3px;
        }

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
        .pStyle{
            font-size: 15px;
        }
    </style>
    <script type="text/javascript">

        function findEchart() {
            var deviceId = $("#deviceId").val();
            var inDate = $("#inDate").val();
            $.post("${ctx}/energydevices/energyDevices/showWaterLimit", {
                deviceId: deviceId,
                inDate: inDate
            }, function (result) {
                var psLineChar1 = echarts.init(document.getElementById("month"));
                var psLineChar2 = echarts.init(document.getElementById("year"));
                $("#monthCurrent").html(result.monthSum);
                $("#yearCurrent").html(result.yearSum);
                if (result.monthFlag == false) {
                    $("#monthLimit").html(result.monthMsg);
                    $("#monthPercent").html(result.monthMsg);
                } else {
                    $("#monthLimit").html(result.monthLimit);
                    $("#monthPercent").html(result.monthPercent);
                }
                if (result.yearFlag == false) {
                    $("#yearLimit").html(result.yearMsg);
                    $("#yearPercent").html(result.yearMsg);
                } else {
                    $("#yearLimit").html(result.yearLimit);
                    $("#yearPercent").html(result.yearPercent);
                }
                psLineChar1.clear();
                psLineChar2.clear();
                psLineChar1.setOption(result.monthOption);
                psLineChar2.setOption(result.yearOption);

            });
        }
    </script>
</head>
<body>
<form id="queryByDate" method="post" cssStyle="margin:0 0 10px;">
    <div class="btnClass" id="dateCondition">
        监测单元:
        <select name="deviceId" id="deviceId">
            <c:forEach items="${deviceList}" var="device">
                <option value="${device.deviceId}">${device.name}</option>
            </c:forEach>
        </select>
        <c:set var="now" value="<%=new Date()%>"/>
        选择时间：
        <input id="inDate" name="inDate" type="text" maxlength="20" class="input-medium Wdate "
               value="<fmt:formatDate value="${now}" pattern="yyyy-MM"/>"
               onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
        <a onclick="findEchart()" class="btnStyle">查询</a>
    </div>
</form>
<span style="font-size: larger;">限额占比</span>
<sys:message content="${message}"/>
<div id="treeTable" class="table table-striped table-bordered table-condensed" style="width: 98%;">
    <div class="container">
        <div class="row">
            <div id="month" style="height:480px" class="span6"></div>
            <div class="span6 text-left" style="margin-top: 100px; ">
                <p class="pStyle">月度实际消耗：<span id="monthCurrent" style="color: blue">0</span></p>
                <p class="pStyle">月度限额：<span id="monthLimit" style="color:red">0</span></p>
                <p class="pStyle">限额占比：<span id="monthPercent" style="color:red">0</span></p>
            </div>
        </div>
        <div class="row">
            <div id="year" style="height:480px" class="span6"></div>
            <div class="span6 text-left" style="margin-top: 100px; ">
                <p class="pStyle">年度实际消耗：<span id="yearCurrent" style="color: blue">0</span></p>
                <p class="pStyle">年度限额：<span id="yearLimit" style="color:red">0</span></p>
                <p class="pStyle">年额占比：<span id="yearPercent" style="color:red">0</span></p>
            </div>
        </div>

    </div>
</div>
<script>
    findEchart();
</script>
</body>
</html>