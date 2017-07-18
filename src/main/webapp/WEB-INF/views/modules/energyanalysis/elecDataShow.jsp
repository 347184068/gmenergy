<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>电量日报表</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <%@include file="/WEB-INF/views/include/echart.jsp" %>
    <script type="text/javascript">
        $(function () {
            $("#dateSelect").change(function () {
                var selectValue = $("#dateSelect").val();
                var $startTime = $("#startTime");
                var $endTime = $("#endTime");
                var date = "yyyy-MM-dd";
                if (selectValue == 0) {
                    date = "yyyy-MM-dd";
                } else if (selectValue == 1) {
                    date = "yyyy-MM";
                } else if (selectValue == 2) {
                    date = "yyyy";
                }
                $startTime.removeAttr("onclick").unbind("click").click(function () {
                    WdatePicker({dateFmt: date, isShowClear: false});
                });
                $endTime.removeAttr("onclick").unbind("click").click(function () {
                    WdatePicker({dateFmt: date, isShowClear: false});
                });
            });

        });
        function validateChooseDate() {
            var startDate = $("#startTime").val();
            var endDate = $("#endTime").val();
            if (startDate == "" || startDate == null) {
                alert("请选择开始时间！");
                return false;
            }
            if (endDate == "" || endDate == null) {
                alert("请选择结束时间！");
                return false;
            }
            var start = new Date(startDate.replace("-", "/").replace("-", "/"));
            var end = new Date(endDate.replace("-", "/").replace("-", "/"));
            if (start <= end) {
                return true;
            } else {
                alert("结束时间必须晚于开始时间！");
                return false;
            }
        }
        function findEchart() {
            var deviceId = $("#deviceId").val();
            var type = $("#type").val();
            var dateType = $("#dateSelect").val();
            var startTime = $("#startTime").val();
            var endTime = $("#endTime").val();
            if (validateChooseDate()) {
                $.post("${ctx}/energyelecday/energyElecDay/getElecDayChart",
                        {deviceId: deviceId, dataTime: dataTime},
                        function (result) {
                            if (result.flag == false) {
                                $("#treeTableList").html(result.msg);
                            } else {
                                var psLineChar = echarts.init(document.getElementById("treeTableList"));
                                psLineChar.clear();
                                psLineChar.setOption(result.option);
                            }
                        }
                );
            }

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
<form:form id="searchForm" class="breadcrumb form-search">
    <ul class="ul-form">
        <li><label>监测点：</label>
            <select name="deviceId" class="input-medium">
                <c:forEach var="device" items="${deviceList}">
                    <option value="${device.deviceId}">${device.name}</option>
                </c:forEach>
            </select>
            <c:set var="now" value="<%=new Date()%>"/>
        </li>
        <li>
            <label>类型:</label>
            <select id="type" name="type">
                <option value="0">同比</option>
                <option value="1">环比</option>
            </select>
        </li>
        <li>
            <label>日期类型:</label>
            <select id="dateSelect" name="dateType">
                <option value="0">日</option>
                <option value="1">月</option>
                <option value="2">年</option>
            </select>
        </li>
        <li><label>对比时间：</label>
            <input id="startTime" name="startTime" type="text" maxlength="20" class="input-medium Wdate"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li><label>对比时间：</label>
            <input id="endTime" name="endTime" type="text" maxlength="20" class="input-medium Wdate"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li class="btns"><a onclick="findEchart()" class="btnStyle">查询</a></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<div id="treeTable" class="table table-striped table-bordered table-condensed" style="width: 98%;">
    <div id="treeTableList" style="height:480px"></div>
</div>
</body>
</html>