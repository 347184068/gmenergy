<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>电量实时列表</title>
    <meta name="decorator" content="default"/>

    <style>
        #contentTable th, #contentTable td {
            padding: 10px 0;
            text-align: center;
            background: transparent;
        }

        #contentTable th {
            background: #F9f9f9;
        }

        .pagination {
            float: right;
        }
    </style>
</head>
<body>
<form:form id="searchForm" modelAttribute="energyElecRawdata"
           action="${ctx}/energyelecmonitor/energyElecRawdata/getRealData" method="post"
           class="breadcrumb form-search">
    <ul class="ul-form">
        <li><label>监测设备：</label>
            <form:select items="${deviceList}" itemLabel="name" itemValue="deviceId"
                         path="deviceId" htmlEscape="false" maxlength="11" class="input-medium"/>
            <c:set var="now" value="<%=new Date()%>"/>
        </li>
        <li><label>获取条数：</label>
            <input id="count" name="count" value="${energyElecRawdata.count eq null ? "" : energyElecRawdata.count}" placeholder="默认10条" type="text" maxlength="20" class="input-small"/>
            条
        </li>
        <li><label>刷新频率：</label>
            <input id="timeInterval" name="timeInterval" type="text" maxlength="20" class="input-small" value="${energyElecRawdata.timeInterval eq null ? "" : energyElecRawdata.timeInterval}"/>
            秒
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" style="width: 56px;" value="手动刷新"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>电表号</th>
        <th>电表名称</th>
        <th>时间(小时)</th>
        <th>电表显示电量(度)</th>
        <th>倍率</th>
        <th>用电量(度)</th>
        <th>A相电压(伏)</th>
        <th>B相电压(伏)</th>
        <th>C相电压(伏)</th>
        <th>A相电流(安)</th>
        <th>B相电流(安)</th>
        <th>C相电流(安)</th>
        <th>功率因数</th>
        <th>瞬时有功功率(瓦)</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${rawdatas}" var="elecRaw">
        <tr>
            <td>${elecRaw.deviceId}</td>
            <td>${elecRaw.deviceName}</td>
            <td><fmt:formatDate value="${elecRaw.dataTime}" type="both"/></td>
            <td>${elecRaw.rawData}</td>
            <td>${elecRaw.ratio}</td>
            <td>${elecRaw.realData}</td>
            <td>${elecRaw.aU}</td>
            <td>${elecRaw.bU}</td>
            <td>${elecRaw.cU}</td>
            <td>${elecRaw.aI}</td>
            <td>${elecRaw.bI}</td>
            <td>${elecRaw.cI}</td>
            <td>${elecRaw.pF}</td>
            <td>${elecRaw.p}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<script type="text/javascript">
    $(document).ready(function () {
        $("#btnSubmit").click(function () {
            var count = $("#count").val();
            if(count > 30){
                alert("最大显示30条最新数据");
                return;
            }
            $("#searchForm").submit();
        });
    });
    var intervalTime = $("#timeInterval").val();
    if(intervalTime=="" || intervalTime==null || typeof (intervalTime)=="undefined"){
        intervalTime = 10;
    }
    var timer  = setInterval(function () {
        $("#searchForm").submit();
    },intervalTime*1000);
</script>
</body>
</html>