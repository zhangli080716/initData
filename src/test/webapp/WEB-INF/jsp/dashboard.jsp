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
var dayType=[];
var maxMapValue;
$.ajax({
	url:'getMeatData',
	type:'post',
	dataType:'json',
	data:{
		dayType:7,
		dayType:'${areaName}'
	},
	sync:false,
	success :function(data){
		var dataList=data.seriesList;
		maxMapValue=data.maxMapValue;
		dayType=data.dayType;
		for(var i=0;i<dataList.length;i++){
			seriesData.push({
				'name':dataList[i].name,
				'type':dataList[i].type,
				'data':dataList[i].data,
				'markLine': {
		                lineStyle:{
		                    normal:{
		                        type: 'solid',
		                        color:'red'
		                    }
		                },
		                data: [
		                    {yAxis:3},
		                ]
		            }
			})
		}
	}
});

</script>

</head>
<body>
	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
	<div id="main" style="width: 90%; height: 400px;" align="center"></div>
	<div id="main1" style="width: 90%; height: 400px;" align="center"></div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('main'));
	var myChart1 = echarts.init(document.getElementById('main1'));
		var option = {
				title : {
					text : '北京猪肉瘦肉精检测统计',
					left : 'center'
				},
				tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    legend: {
			    	orient : 'vertical',
					left : 'left',
			        data:['沙丁胺醇','盐酸克伦特罗','莱克多巴胺']
			    },
			    
			    xAxis : [
			        {
			            type : 'category',
			            data : dayType
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series :seriesData
		}
		myChart.setOption(option);
		myChart1.setOption(option);
});

</script>
</html>
