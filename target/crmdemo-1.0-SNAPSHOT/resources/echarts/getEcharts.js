new Vue({
    el:"#app",
    data:{
        //创建一个初始化为空的对象
        option:undefined
    },
    //vue的生命周期函数
    created:function (){
        //调用方法
        this.getCharts();
    },
    methods:{
        getCharts:function (){
            axios({
                url:"/workChart/queryTranByChart.do",
                method:"POST",
                params:{}
            }).then((data)=>{
                var tranArr = [];
                for (var j = 0;j < data.length;j++){
                    tranArr.push(data.item.name);
                }
                var myChart = echarts.init(document.getElementById("main"));
                //准备数据以及配置项
                option = {
                    //标题
                    title:{

                    }
                }
            });
        }
    }
})