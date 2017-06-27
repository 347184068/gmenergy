<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日报表统计管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出日报表数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/monthamount/monthAmount/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
	<style>
		#contentTable th,#contentTable td{
			padding:10px 0;
			text-align: center;
			background: transparent;
		}
		#contentTable th{
			background: #F9f9f9;
		}
		#searchForm{
			background: transparent;
		}
		.pagination{
			float: right;
		}
	</style>
</head>
<body>

	<form:form id="searchForm" modelAttribute="monthAmount" action="${ctx}/monthamount/monthAmount/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>监测点：</label>
				<form:select items="${deviceList}"  itemLabel="name" itemValue="id"
							 path="deviceId" htmlEscape="false" maxlength="11" class="input-xlarge  "/>
				<c:set var="now" value="<%=new Date()%>" />

			</li>
			<li><label>选择日期</label>
				<input name="indate" type="text"  maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${not empty monthAmount.indate ? monthAmount.indate:now}" pattern="yyyy-MM"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" /></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<%--<li class="btns"><input id="btnExport" class="btn" type="button" value="导出报表"/></li>--%>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<shiro:hasPermission name="monthamount:monthAmount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
			<tr>
				<th colspan="2">月电量最大值</th>
				<th>${maxMonthAmount.electricity}</th>
				<th>出现时间</th>
				<th><fmt:formatDate value="${maxMonthAmount.indate}"  type="both"  /></th>
			</tr>
			<tr>
				<th colspan="2">月电量最小值</th>
				<th>${minMonthAmount.electricity}</th>
				<th>出现时间</th>
				<th><fmt:formatDate value="${minMonthAmount.indate}"  type="both" /></th>
			</tr>
			<tr>
				<th colspan="2">月电量累计</th>
				<th colspan="3">${allMonthAmount }</th>
			</tr>
			<tr>
				<th>最大值</th>
				<th>最小值</th>
				<th>平均值</th>
				<th>用电量</th>
				<th>时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="monthAmount">
			<tr>
				<td>${monthAmount.maxload}</td>
				<td>${monthAmount.minload}</td>
				<td>${monthAmount.avgload}</td>
				<td>${monthAmount.electricity}</td>
				<td><fmt:formatDate value="${monthAmount.indate}"  type="both"  /></td>
				<shiro:hasPermission name="monthamount:monthAmount:edit"><td>
    				<a href="${ctx}/monthamount/monthAmount/form?id=${monthAmount.id}">修改</a>
					<a href="${ctx}/monthamount/monthAmount/delete?id=${monthAmount.id}" onclick="return confirmx('确认要删除该日报表统计吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>