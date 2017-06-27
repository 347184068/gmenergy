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
						$("#searchForm").attr("action","${ctx}/dayamount/dayAmount/export");
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
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/dayamount/dayAmount/list">日报表统计列表</a></li>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="dayAmount" action="" method="post" class="breadcrumb form-search">
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
							value="<fmt:formatDate value="${not empty dayAmount.indate?dayAmount.indate:now}" pattern="yyyy-MM-dd"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" /></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<%--<li class="btns"><input id="btnExport" class="btn" type="button" value="导出报表"/></li>--%>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th colspan="2">日电量最大值</th>
				<th>${maxDayAmount.electricity}</th>
				<th>出现时间</th>
				<th><fmt:formatDate value="${maxDayAmount.indate}" type="both" /></th>
			</tr>
			<tr>
				<th colspan="2">日电量最小值</th>
				<th>${minDayAmount.electricity}</th>
				<th>出现时间</th>
				<th><fmt:formatDate value="${minDayAmount.indate}" type="both" /></th>
			</tr>
			<tr>
				<th colspan="2">日电量累计</th>
				<th colspan="3">${allDayAmount }</th>
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
		<c:forEach items="${page.list}" var="dayAmount">
			<tr>
				<td>${dayAmount.maxload}</td>
				<td>${dayAmount.minload}</td>
				<td>${dayAmount.avgload}</td>
				<td>${dayAmount.electricity}</td>
				<td><fmt:formatDate value="${dayAmount.indate}" type="both"/></td>
				<shiro:hasPermission name="dayamount:dayAmount:edit"><td>
    				<a href="${ctx}/dayamount/dayAmount/form?id=${dayAmount.id}">修改</a>
					<a href="${ctx}/dayamount/dayAmount/delete?id=${dayAmount.id}" onclick="return confirmx('确认要删除该日报表统计吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>