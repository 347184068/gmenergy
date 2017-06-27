<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>报表展示</title>
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
    <script type="text/javascript">

        function findEchart(){
            var deviceId = $("#deviceId").val();
            var inDate = $("#inDate").val();

            $.post("${ctx}/power/analysis/getRealTimeElec",{deviceId:deviceId,inDate:inDate,type:'${elecDataAmount.type}'},function(result){
                if(result.flag==false){
                    $("#treeTableList").html(result.msg);
                }else{
                    var psLineChar = echarts.init(document.getElementById("treeTableList"));
                    psLineChar.clear();
                    psLineChar.setOption(result.option);
                }
            });

        }
    </script>
</head>
<body>
<form:form id="queryByDate" modelAttribute="elecDataAmount" action="" method="post" cssStyle="margin:0 0 10px;">
    <div class="btnClass" id="dateCondition">
       监测单元:
        <form:select items="${deviceList}"  itemLabel="name" itemValue="id"
                     path="deviceId" htmlEscape="false" maxlength="11" class="input-xlarge  "/>
        <c:set var="now" value="<%=new Date()%>" />
       选择时间：

        <c:choose>
            <c:when test="${elecDataAmount.type==1}">
                <input id="inDate"  name="inDate" type="text"  maxlength="20" class="input-medium Wdate "
                       value="<fmt:formatDate value="${not empty elecDataAmount.inDate? elecDataAmount.inDate:now}" pattern="yyyy-MM"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
            </c:when>
            <c:when test="${elecDataAmount.type==2}">
                <input id="inDate"  name="inDate" type="text"  maxlength="20" class="input-medium Wdate "
                       value="<fmt:formatDate value="${not empty elecDataAmount.inDate? elecDataAmount.inDate:now}" pattern="yyyy-MM"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
            </c:when>
            <c:otherwise>
                <input id="inDate"  name="inDate" type="text"  maxlength="20" class="input-medium Wdate "
                       value="<fmt:formatDate value="${not empty elecDataAmount.inDate? elecDataAmount.inDate:now}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            </c:otherwise>
        </c:choose>

        <a onclick="findEchart()" class="btnStyle">查询</a>
    </div>
</form:form>
<span style="font-size: larger;">电量</span>

<sys:message content="${message}"/>

<div id="treeTable" class="table table-striped table-bordered table-condensed" style="width: 98%;">
    <div id="treeTableList" style="height:480px"></div>
</div>
<script>
    findEchart();
</script>
</body>
</html>