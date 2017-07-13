<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>水量日报表</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <%@include file="/WEB-INF/views/include/echart.jsp" %>
    <script type="text/javascript">
        function findEchart() {
            var deviceId = $("#deviceId").val();
            var dataTime = $("#dataTime").val();
            $.post("${ctx}/energywaterday/energyWaterDay/getWaterDayChart",
                    {deviceId: deviceId, dataTime: dataTime},
                    function (result) {
                        if (result.flag == false) {
                            $("#treeTableList").html(result.msg);
                        } else {
                            var psLineChar = echarts.init(document.getElementById("treeTableList"));
                            psLineChar.clear();
                            psLineChar.setOption(result.option);
                        }
                    });

        }
    </script>
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
    </style>
</head>
<body>
<form:form id="searchForm" modelAttribute="energyWaterDay" class="breadcrumb form-search">
    <ul class="ul-form">
        <li><label>监测点：</label>
            <form:select items="${deviceList}" id="deviceId" itemLabel="name" itemValue="deviceId"
                         path="deviceId" htmlEscape="false" maxlength="11" class="input-medium"/>
            <c:set var="now" value="<%=new Date()%>"/>
        </li>
        <li><label>选择日期：</label>
            <input id="dataTime" name="dataTime" type="text" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${not empty energyWaterDay.dataTime ? energyWaterDay.dataTime:now}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li class="btns"><a onclick="findEchart()" class="btnStyle">查看</a></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<div id="treeTable" class="table table-striped table-bordered table-condensed" style="width: 98%;">
    <div id="treeTableList" style="height:480px"></div>
</div>
</body>
</html>