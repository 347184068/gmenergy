<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html ng-app="myApp" ng-controller="OldCtrl">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <title>订单管理</title>
    <link href="${ctxStatic}/cssStyle/bootstrap.css" rel="stylesheet">
    <!-- inject:base:css -->
    <link rel="stylesheet" href="${ctxStatic}/cssStyle/base.css">
    <!-- endinject -->
</head>
<body>
<div class="container-fluid">
    <div class="row weui-group">
        <div class="col-md-3 weui-group-list">
            <div class="panel weui-group-bule">
                <div class="panel-heading text-center">基本情况</div>
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item"><div class="weui-title">主变台数</div>3台</li>
                        <li class="list-group-item"><div class="weui-title">主变容量</div>4120kvb</li>
                        <li class="list-group-item"><div class="weui-title">检测分路个数</div>37个</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-md-3 weui-group-list">
            <div class="panel weui-group-red">
                <div class="panel-heading text-center">电量概况</div>
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item"><div class="weui-title">主变台数</div>3台</li>
                        <li class="list-group-item"><div class="weui-title">主变容量</div>4120kvb</li>
                        <li class="list-group-item"><div class="weui-title">检测分路个数</div>37个</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-md-3 weui-group-list">
            <div class="panel weui-group-yellow">
                <div class="panel-heading text-center">负荷情况</div>
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item"><div class="weui-title">主变台数</div>3台</li>
                        <li class="list-group-item"><div class="weui-title">主变容量</div>4120kvb</li>
                        <li class="list-group-item"><div class="weui-title">检测分路个数</div>37个</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-md-3 weui-group-list">
            <div class="panel weui-group-green">
                <div class="panel-heading text-center">安全情况</div>
                    <div>
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><div class="weui-title">主变台数</div>3台</li>
                                <li class="list-group-item"><div class="weui-title">主变容量</div>4120kvb</li>
                                <li class="list-group-item"><div class="weui-title">检测分路个数</div>37个</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<div id="shuju"></div>
<div id="new"></div>
<div id="zhexian"></div>
<div id="zhexiandata">
    最大值：3515ms<br />
    最小值：3522ms<br />
    管理人：admin<br />
    功&nbsp;&nbsp;&nbsp;&nbsp;率：3515ms<br />
    时&nbsp;&nbsp;&nbsp;&nbsp;间：2016年11月1日<br />
</div>
</div>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
<script type="text/javascript">
    var dom = document.getElementById("shuju");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        title: {
            text: '发电站月发电量图'
        },
        tooltip: {},
        legend: {
            data:['发电量']
        },
        xAxis: {
            data: ["1号电站","2号电站","3号电站","4号电站","5号电站","6号电站","7号电站","8号电站"]
        },
        yAxis: {},
        series: [{
            name: '电量',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20, 10, 20]
        }]
    };;
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
</script>
<script type="text/javascript">
    var dom = document.getElementById("new");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    app.title = '嵌套环形图';
	option = {
	    tooltip: {
	        trigger: 'item',
	        formatter: "{a} <br/>{b}: {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        x: 'right',
	        data:['1号电站','2号电站','3号电站','4号电站']
	    },
	    series: [
	        {
	            name:'访问来源',
	            type:'pie',
	            selectedMode: 'single',
	            radius: [0, '30%'],

	            label: {
	                normal: {
	                    position: 'inner'
	                }
	            },
	            labelLine: {
	                normal: {
	                    show: false
	                }
	            },
	            data:[
	                {value:335, name:'1号电站'},
	                {value:679, name:'2号电站'},
	                {value:1548, name:'3号电站'}
	            ]
	        },
	        {
	            name:'访问来源',
	            type:'pie',
	            radius: ['40%', '55%'],

	            data:[
	                {value:335, name:'1号电站'},
	                {value:310, name:'2号电站'},
	                {value:234, name:'3号电站'},
	                {value:135, name:'4号电站'}
	            ]
	        }
	    ]
	};
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
</script>
<script type="text/javascript">
	function randomData() {
	    now = new Date(+now + oneDay);
	    value = value + Math.random() * 21 - 10;
	    return {
	        name: now.toString(),
	        value: [
	            [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
	            Math.round(value)
	        ]
	    }
	}
    var dom = document.getElementById("zhexian");
    var myChart = echarts.init(dom);
    var app = {};
    var data = [];
var now = +new Date(1997, 9, 3);
var oneDay = 24 * 3600 * 1000;
var value = Math.random() * 1000;
for (var i = 0; i < 1000; i++) {
    data.push(randomData());
}

option = {
    title: {
        text: '月发电量时间图'
    },
    tooltip: {
        trigger: 'axis',
        formatter: function (params) {
            params = params[0];
            var date = new Date(params.name);
            return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
        },
        axisPointer: {
            animation: false
        }
    },
    xAxis: {
        type: 'time',
        splitLine: {
            show: false
        }
    },
    yAxis: {
        type: 'value',
        boundaryGap: [0, '100%'],
        splitLine: {
            show: false
        }
    },
    series: [{
        name: '模拟数据',
        type: 'line',
        showSymbol: false,
        hoverAnimation: false,
        data: data
    }]
};

app.timeTicket = setInterval(function () {

    for (var i = 0; i < 5; i++) {
        data.shift();
        data.push(randomData());
    }

    myChart.setOption({
        series: [{
            data: data
        }]
    });
}, 1000);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
</script>
<!-- inject:base:js -->
<!-- endinject -->
</body>
</html>