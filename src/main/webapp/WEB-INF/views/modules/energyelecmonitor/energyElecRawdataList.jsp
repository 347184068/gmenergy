<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>监控信息管理</title>
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
    </style>
    <script type="text/javascript">
        var timer = null;
        var psLineChar;
        function findEchart() {
            if(timer!=null){
                clearInterval(timer);
            }
            var deviceId = $("#deviceId").val();
            $.post(
                "${ctx}/energyelecmonitor/energyElecRawdata/getRealData",
                {deviceId: deviceId},
                function (result) {
                    if (result.flag == false) {
                        $("#treeTableList").html(result.msg);
                    } else {
                        psLineChar = echarts.init(document.getElementById("treeTableList"));
                        psLineChar.clear();
                        psLineChar.setOption(result.option);
                    }
                });
            timer  =  setInterval(function () {
                $.post(
                    "${ctx}/energyelecmonitor/energyElecRawdata/getRealData",
                    {deviceId: deviceId},
                    function (result) {
                        if (result.flag == false) {
                            $("#treeTableList").html(result.msg);
                        } else {
                            if(!psLineChar){
                                return;
                            }
                            psLineChar.setOption(result.option);
                        }
                    });
            },1000*60);
        }
    </script>
</head>
<body>
<form:form id="searchForm" modelAttribute="energyElecRawdata">
    <div class="btnClass" id="dateCondition">
        监测单元:
        <form:select id ="deviceId" items="${deviceList}" itemLabel="name" itemValue="deviceId"
                     path="deviceId" htmlEscape="false" maxlength="11" class="input-medium"/>
        <a onclick="findEchart()" class="btnStyle">监控</a>
    </div>
</form:form>
<sys:message content="${message}"/>
<div id="treeTable" class="table table-striped table-bordered table-condensed" style="width: 98%;">
    <div id="treeTableList" style="height:480px"></div>
</div>
</body>
</html>