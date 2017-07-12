<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>电量实时报表</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#btnSubmit").click(function () {
                if (validateChooseDate()) {
                    $("#searchForm").submit();
                }
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
                var startNum = parseInt(startDate.replace(/-/g, ''), 10);
                var endNum = parseInt(endDate.replace(/-/g, ''), 10);
                if (startNum<=endNum) {
                    return true;
                }else{
                    alert("结束时间必须晚于开始时间！");
                    return false;
                }
            }
        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
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
           action="${ctx}/energyelecmonitor/energyElecRawdata/rawDataReport" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>监测点：</label>
            <form:select items="${deviceList}" itemLabel="name" itemValue="deviceId"
                         path="deviceId" htmlEscape="false" maxlength="11" class="input-medium"/>
            <c:set var="now" value="<%=new Date()%>"/>
        </li>
        <li><label>开始时间：</label>
            <input id="startTime" name="startTime" type="text" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${not empty energyElecRawdata.startTime ? energyElecRawdata.startTime:now}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </li>
        <li><label>结束时间：</label>
            <input id="endTime" name="endTime" type="text" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${not empty energyElecRawdata.endTime ? energyElecRawdata.endTime:now}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" style="width: 28px;" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>时间(小时)</th>
        <th>用电量(度)</th>
        <th>A相电压</th>
        <th>B相电压</th>
        <th>C相电压</th>
        <th>A相电流</th>
        <th>B相电流</th>
        <th>C相电流</th>
        <th>功率因数</th>
        <th>瞬时有功功率</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="elecRaw">
        <tr>
            <td><fmt:formatDate value="${elecRaw.dataTime}" type="both"/></td>
            <td>${elecRaw.rawData}</td>
            <td>${elecRaw.aU}</td>
            <td>${elecRaw.bU}</td>
            <td>${elecRaw.cU}</td>
            <td>${elecRaw.aI}</td>
            <td>${elecRaw.bI}</td>
            <td>${elecRaw.cI}</td>
            <td>${elecRaw.p}</td>
            <td>${elecRaw.pF}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>