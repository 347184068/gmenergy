<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>检测历史</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#btnSubmit").click(function () {
                if(validateChooseDate()){
                    $("#searchForm").attr("action","${ctx}/energyelecmonitor/energyElecRawdata/rawDataReport");
                    $("#searchForm").submit();
                }
            });
            $("#btnExport").click(function () {
                if(validateChooseDate()){
                    top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
                        if(v=="ok"){
                            $("#searchForm").attr("action","${ctx}/energyelecmonitor/energyElecRawdata/rawDataExport");
                            $("#searchForm").submit();
                        }
                    },{buttonsFocus:1});
                    top.$('.jbox-body .jbox-icon').css('top','55px');
                }
            });

            function validateChooseDate() {
                var endDate = $("#endTime").val();
                if (endDate == "" || endDate == null) {
                    alert("请选择时间！");
                    return false;
                }
                return true;
            }
        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").attr("action","${ctx}/energyelecmonitor/energyElecRawdata/rawDataReport");
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
            <li><label>选择时间：</label>
                <input id="endTime" name="endTime" type="text" maxlength="20" class="input-medium Wdate"
                       value="<fmt:formatDate value="${not empty energyElecRawdata.endTime ? energyElecRawdata.endTime:now}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
            </li>
            <li class="btns">
                <input id="btnSubmit" class="btn btn-primary" style="width: 28px;" value="查询"/>
                <input id="btnExport" class="btn btn-primary" style="width: 75px;" value="导出Excel"/>
            </li>
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
        <c:forEach items="${page.list}" var="elecRaw">
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
    <div class="pagination">${page}</div>
</body>
</html>