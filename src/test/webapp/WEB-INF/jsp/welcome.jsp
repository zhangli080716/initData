<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 引入 ECharts 文件 -->
<script src="./echarts.js"></script>
<script src="./jquery-3.2.1.min.js"></script>
<script type="text/javascript">
var seriesData=[];
var maxMapValue;
$.ajax({
	url:'getbjAreaData',
	type:'post',
	dataType:'json',
	sync:true,
	success :function(data){
		var dataList=data.seriesList;
		maxMapValue=data.maxMapValue;
		for(var i=0;i<dataList.length;i++){
			seriesData.push({
				'name':dataList[i].name,
				'type':dataList[i].type,
				'mapType':dataList[i].mapType,
				'roam':false,
				'label' : {
					normal : {
						show : true
					},
					emphasis : {
						show : true
					}
				},
				'data':dataList[i].dataList
			})
		}
	}
});

</script>

</head>
<body>
	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
	<div id="main" style="width: 100%; height: 800px;" align="center"></div>
</body>
<script type="text/javascript">
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('main'));
	var name = "beijing";
	
	$.get('./' + name + '.json', function(geoJson) {
		echarts.registerMap(name, geoJson);
		var option = {
			title : {
				text : '北京农残检测分布图',
				left : 'center',
				textStyle : {
					color : 'black'
				}
			},
			tooltip : {
				trigger : 'item'
			},
			legend : {
				orient : 'vertical',
				left : 'left',
				data : [ '瘦肉精', '农药残留' ]
			},
			visualMap : {
				min : 0,
				max : maxMapValue,
				left : 'left',
				top : 'bottom',
				text : [ '高', '低' ], // 文本，默认为数值文本	
				calculable : false
			},
			series :seriesData
		}
		myChart.setOption(option);
	});
	myChart.on('click', function (params) {
	    window.location.href="dashboard?areaName="+params.name; 
	});
</script>
</html>
