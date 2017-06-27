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
	<link rel="stylesheet" href="${ctxStatic }/cssStyle/bootstrap-table.css">
	<link rel="stylesheet" href="${ctxStatic}/cssStyle/bootstrap-table-fixed-columns.css" />
	<script type="text/javascript" src="${ctxStatic}/js/bootstrap-table.js"></script>
	<script src="${ctxStatic}/js/bootstrap-table-fixed-columns.js"></script>
	<style>
		.fixed-table-body-columns td,.fixed-table-body td{
			padding: 5px 0;
			line-height: 30px;
			text-align: center;
		}
		.fixed-table-toolbar{
			display: none;
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/dayamount/dayAmount/linelist">分段统计列表</a></li>
		<shiro:hasPermission name="dayamount:dayAmount:edit"><li><a href="${ctx}/dayamount/dayAmount/form">日报表统计添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="dayAmount" action="${ctx}/dayamount/dayAmount/linelist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>选择日期</label>
				<input name="startdate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${dayAmount.indate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" /></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnExport" class="btn" type="button" value="导出报表"/></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable"  data-show-columns="true" class="table table-striped table-bordered table-condensed">
	</table>
	<div class="pagination">${page}</div>
	<script>
    var $table = $('#contentTable');

    $(function () {
        buildTable($table, 20, 20);
    });
    Array.prototype.indexOf = function(val) {
    	for (var i = 0; i < this.length; i++) {
	    	if (this[i].id == val) return this[i];
	    	}
	    	return -1;
    	};
	function detail(list){
		var shebeilist=[];
		for(var i=0;i<list.length;i++){
			var nowshebei=shebeilist.indexOf(list[i].deviceid.id)
			if(nowshebei==-1){
				var shebei={};
				shebei.name=list[i].deviceid.name;
				shebei.id=list[i].deviceid.id;
				shebei.allcount=list[i].electricity;
				shebei.chirden=[];
				var shebeidata={};
				shebeidata.electricity=list[i].electricity;
				shebeidata.indate=FormatDate(new Date(list[i].indate.time));
				shebei.chirden.push(shebeidata);
				shebeilist.push(shebei);
			}else{
				var shebei=nowshebei;
				shebei.allcount=shebei.allcount+list[i].electricity;
				var shebeidata={};
				shebeidata.electricity=list[i].electricity;
				shebeidata.indate=FormatDate(new Date(list[i].indate.time));
				shebei.chirden.push(shebeidata);
			}
		}
		console.log(shebeilist);
		return shebeilist;
	}
	function FormatDate (strTime) {
	    var date = new Date(strTime);
	    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	}
    function buildTable($el, cells, rows) {
        var i, j, row,
                columns = [],
                data = [];
		var daylist=${daylist}
		var datalist=detail(${datalist});
        for (i = 0; i < daylist.length; i++) {
            columns.push({
                field: 'field' + i,
                title: daylist[i],
                sortable: true
            });
        }
        for (i = 0; i < datalist.length; i++) {
            row = {};
            row['field0']=datalist[i].name;
            row['field1']='全天';
            row['field2']=datalist[i].allcount;
            var count=0;
            var number=0;
            while(number<30){
            	if(count!=datalist[i].chirden.length){
            		var chirden=datalist[i].chirden[count];
	            	if(new Date(chirden.indate).getTime()==new Date(daylist[number+3]).getTime()){
	            		row['field' + (number+3)]= chirden.electricity;
	            		count=count+1;
	            	}
            	}
            	number=number+1;
            }
            data.push(row);
        }
        $el.bootstrapTable('destroy').bootstrapTable({
        	height:400,
            columns: columns,
            data: data,
            toolbar: '.toolbar',
            fixedColumns: true,
            fixedNumber:3
        });
    }
</script>
</body>
</html>