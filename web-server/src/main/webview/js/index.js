var mapChart = echarts.init(document.getElementById('contentMap'));
var myData = [
    {name: '海门', value: [121.15, 31.89, 90]},
    {name: '鄂尔多斯', value: [109.781327, 39.608266, 150]},
    {name: '宜宾', value: [104.56, 29.77, 600]},
    {name: '南溪', value: [106.56, 30.77, 100]},
    {name: '招远', value: [120.38, 37.35, 342]},
    {name: '舟山', value: [122.207216, 29.985295, 523]}
];
var option = {
    title: {
        text: '5G VR Course',
        subtext: 'Listening Courses',
        sublink: 'https://github.com/liujiakuan',
        left: 'center',
        textStyle: {
            color: 'tomato'
        }
    },
    visualMap: {
        type: 'continuous', // 连续型
        min: 0,       		// 值域最小值，必须参数
        max: 600,			// 值域最大值，必须参数
        calculable: true,	// 是否启用值域漫游
        inRange: {
            color: ['#50a3ba', '#eac736', '#d94e5d']
            // 指定数值从低到高时的颜色变化
        },
        textStyle: {
            color: '#fff'	// 值域控件的文本颜色
        }
    },
    geo: {
        map: 'china',

        itemStyle: {					// 定义样式
            normal: {					// 普通状态下的样式
                areaColor: '#323c48',
                borderColor: '#111'
            },
            emphasis: {					// 高亮状态下的样式
                areaColor: '#2a333d'
            }
        }
    },
    backgroundColor: '#404a59', 		// 图表背景色
    series: [
        {
            name: '销量', // series名称
            type: 'scatter', // series图表类型
            coordinateSystem: 'geo', // series坐标系类型
            data: myData, // series数据内容
            symbolSize: function (val) {
                return val[2] / 10;
            },
            label: {
                normal: {
                    formatter: '{b}',
                    position: 'right',
                    show: true
                },
                emphasis: {
                    show: true
                }
            },
            itemStyle: {
                normal: {
                    color: '#ddb926'
                }
            }
        }
    ],
    tooltip: {
        padding: 10,
        backgroundColor: '#222',
        borderColor: '#777',
        borderWidth: 1,
        formatter: function (obj) {
            var value = obj.value;
            return obj.name + ":" + value[2];
        }
    }
};
mapChart.setOption(option);