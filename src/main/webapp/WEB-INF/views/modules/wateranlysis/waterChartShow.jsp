<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>用水分析报表展示</title>
    <meta name="decorator" content="default"/>
    <%@ page import="java.util.Date" %>
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

</head>
<body>

<form:form id="queryByDate" modelAttribute="waterData"  cssStyle="margin:0 0 10px;">

<ul class="ul-form2" id="dateCondition">
    监测单元:
    <form:select items="${deviceList}"  itemLabel="name" itemValue="id"
                 path="deviceId" htmlEscape="false" maxlength="11" class="input-xlarge  "/>
    <c:set var="now" value="<%=new Date()%>" />
    选择时间：
    <c:choose>
        <c:when test="${waterData.type==4}">
            <input id="inDate" name="inDate" type="text"  maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${not empty waterData.inDate? waterData.inDate:now}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </c:when>
        <c:otherwise>
            <input id="inDate" name="inDate" type="text"  maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${not empty waterData.inDate? waterData.inDate:now}" pattern="yyyy-MM"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
        </c:otherwise>
    </c:choose>

    选项:
    <form:select path="params" htmlEscape="false" maxlength="11" class="input-xlarge  digits">
        <form:option value="1">净累计流量</form:option>
        <form:option value="2">瞬时流量</form:option>
    </form:select>
    <button onclick="findEchart()" class="btnStyle">查询</button>
    </ul>

</form:form>

<sys:message content="${message}"/>
<div id="treeTable" class="table table-striped table-bordered table-condensed" style="width: 98%;">
    <div id="lineOption" style="height: 480px"></div>
    <%--<div id="treeTableList" style="height:480px"></div>--%>
</div>
<script type="text/javascript">
    function findEchart() {
        var deviceId = $("#deviceId").val();
        var inDate = $("#inDate").val();
        var params = $("#params").val();
        $.post("${ctx}/water/analysis/getWaterData",{deviceId:deviceId,inDate:inDate,params:params,type:'${waterData.type}'},function(result){
            if(result.flag==false){
               $("#treeTable").html(result.msg);
            }else{
                /*var char = echarts.init(document.getElementById("treeTableList"));
                char.clear();
                char.setOption(result.option);*/
                var char_line = echarts.init(document.getElementById("lineOption"));
                char_line.clear();
                char_line.setOption(result.otherOption);
            }
        });
    }
    findEchart();
</script>
</body>
</html>