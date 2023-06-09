new Vue({
    el:"#app",
    data:{
        //创建一个初始化为空的对象
        option:undefined
    },
    //vue的生命周期函数
    created:function(){
        //调用方法
        this.getCharts();
    },
    methods:{
        getCharts:function(){
            axios({
                url:"/workChart/queryTranByChart.do",
                method:"POST",
                params:{}
            }).then((data)=>{
                var tranArr = [];
                for(var j = 0;j < data.length;j++){
                    tranArr.push(data.item.name);
                }
                var myChart = echarts.init(document.getElementById("main"));
                //准备数据以及配置项
                option = {
                    //标题（漏斗图）
                    title:{
                        text:"funnel"
                    },
                    //鼠标放到图片上的某一块区域，显示其对应的提示内容
                    tooltip:{
                        /*
                        * trigger对应的值有三个
                        *   none 表示什么都不触发
                        *   item 表示生成饼图或者漏斗图
                        *   axis 表示柱状图等
                        * */
                        trigger:"item",
                        //提示内容就按照formatter所定义的格式来显示
                        //{a}表示系列名 {b}数据名  {c}数据对应的值
                        formatter:"{a}<br/>{b} : {c}"
                    },
                    //echarts的工具箱
                    toolbox:{
                        //feature表示为toolbox的配置项
                        feature:{
                            //打开数据的视图
                            dataView:{
                                readonly:false
                            },
                            //还原原始图表
                            restore:{},
                            //保存数据视图的图片
                            saveAsImage:{}
                        }
                    },
                    //图例，每一个图表最多只能生成一种图例
                    legend:{
                        data:tranArr
                    },
                    //驱动图表生成的数据内容的数组，数组中的每一个项为一个系列（series）的选项以及数据
                    series:[
                        {
                            name: "Expected",
                            type: "funnel",
                            left: "10%",
                            width: "80%",
                            //设置需要展示的label的样式
                            label: {
                                formatter: "{b} Expected"
                            },
                            //设置标签指示线的样式
                            labelLine: {
                                show: false
                            },
                            //设置透明的效果（0-1）
                            itemStyle: {
                                opacity: 0.7
                            },
                            //强调样式
                            emphasis: {
                                label: {
                                    //内容居中显示
                                    position: "inside",
                                    formatter: "{b} Expected : {c}"
                                }
                            },
                            //封装数据
                            data:[
                                {name:"01资质审查",value:1},
                                {name:"04确定决策者",value:1},
                                {name:"07成交",value:3},
                                {name:"08丢失的线索",value:3},
                                {name:"09因竞争丢失关闭",value:5}
                            ]
                        }
                    ]
                };
                //渲染视图
                myChart.setOption(option);
            });
        }
    }
})