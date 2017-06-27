<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>用水分析报表展示</title>
    <meta name="decorator" content="default"/>
    <%@ page import="java.text.SimpleDateFormat" %>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <%@include file="/WEB-INF/views/include/echart.jsp" %>
    <script type="text/javascript">
        function findHref(it){
            //alert("....."+it);
            var date = document.getElementById("inDate").value;
            // alert(date);
            if(type==1){
                date+="-01";
            }
            if(it==1){
                $("#href_1").attr("href", "${ctx}/water/analysis/getBar?deviceId=" + deviceId + "&type=" + type + "&inDate=" + date  +"&params=1");
            }
            if(it==2){
                $("#href_1").attr("href", "${ctx}/water/analysis/getBar?deviceId=" + deviceId + "&type=" + type + "&inDate=" + date  +"&params=2");
            }

        }

        var deviceId = ${waterData.deviceId};
        var type = ${waterData.type};
        /*   var tab = ${tab};*/
        var tab = ${waterData.params};

        /*var date = document.getElementById("inDate").value;*/


        function findEchart() {
            var date = document.getElementById("inDate").value;
            var tab = document.getElementsByClassName("active")[0].attributes["id"].value;

            if (tab == 1) {
                if (type == 1) {
                    $("#queryByDate").attr("action", "${ctx}/water/analysis/getBar?deviceId=" + deviceId + "&type=" + type + "&inDate=" + date + "-01" +"&params=1");
                } else {
                    $("#queryByDate").attr("action", "${ctx}/water/analysis/getBar?deviceId=" + deviceId + "&type=" + type + "&inDate=" + date +"&params=1");
                }
            }
            if (tab == 2) {
                if (type == 1) {
                    $("#queryByDate").attr("action", "${ctx}/water/analysis/getBar?deviceId=" + deviceId + "&type=" + type + "&inDate=" + date + "-01" +"&params="+tab);
                } else {
                    $("#queryByDate").attr("action", "${ctx}/water/analysis/getBar?deviceId=" + deviceId + "&type=" + type + "&inDate=" + date +"&params="+tab);
                }
            }
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
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
<form:form id="queryByDate" modelAttribute="waterData" action="${ctx}/water/analysis/getBar" method="post" cssStyle="margin:0 0 10px;">
    <div class="btnClass" id="dateCondition">
        <%--<input type="text" class="Wdate" id="inDate" style="margin-top: 10px !important;"
               onclick="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy',maxDate:'%y-%M-%d'})"
               value="${inDate}"/>--%>

        <input id="inDate" name="inDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
               value="<fmt:formatDate value="${waterData.inDate}" pattern="yyyy-MM"/>"
               onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>

        <form:hidden path="deviceId"/>
        <form:hidden path="type"/>
        <form:hidden path="params"/>


        <%--<button onclick="findEchart()" class="btnStyle">查询</button>--%>

        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />


    </div>
</form:form>
<ul class="nav nav-tabs">
    <li class="active" id="1">
        <a href="${ctx}/water/analysis/getBar?deviceId=${waterData.deviceId}&type=${waterData.type}&params=1&inDate="  id="href_1">净累计流量</a>
    </li>
    <li id="2">
        <a href="${ctx}/water/analysis/getBar?deviceId=${waterData.deviceId}&type=${waterData.type}&params=2&inDate="  id="href_2">瞬时流量</a>
    </li>
    <li id="3">
        <a href="${ctx}/water/analysis/getBar?deviceId=${waterData.deviceId}&type=${waterData.type}&params=3&inDate="  id="href_3">消耗流量</a>
    </li>
</ul>
<sys:message content="${message}"/>
<div id="treeTable" class="table table-striped table-bordered table-condensed" style="width: 98%;">
    <div id="lineOption" style="height: 480px"></div>
    <div id="treeTableList" style="height:480px"></div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        var option = ${option};
        var option_line = ${option_line};
        if(option!=0 && option_line!=0){
            var char = echarts.init(document.getElementById("treeTableList"));
            char.clear();
            char.setOption(option);
            var char_line = echarts.init(document.getElementById("lineOption"));
            char_line.clear();
            char_line.setOption(option_line);

        }else{
            document.getElementById("treeTable").innerHTML="当前没有数据";
        }



    });
    // tab选项卡切换
    if (${waterData.params}==1)
    {

        $("#3").removeClass("active");
        $("#2").removeClass("active");
        $("#1").addClass("active");
    }
    if (${waterData.params}==2)
    {

        $("#1").removeClass("active");
        $("#3").removeClass("active");
        $("#2").addClass("active");
    }
    if (${waterData.params}==3)
    {
        $("#1").removeClass("active");
        $("#2").removeClass("active");
        $("#3").addClass("active");


    }



</script>
</body>
</html>