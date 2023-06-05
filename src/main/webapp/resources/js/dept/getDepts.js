new Vue({
    el: '#app',
    data:{
        //定义账号列表数据
        deptDataArray:[],
        // 页码
        pageNum:1,
        //每一页显示的数据
        pageSize:5,
        // 总条数
        total:0,
        //根据页面显示不同的条数
        pageSizes:[1,2,5,10],
        // 添加功能的弹出框的打开与关闭状态
        addDeptWinOpenStatus:false,
        //对添加数据进行校验
        addDeptFormatRules:{
            name:[
                //必填
                //trigger表示触发器，blue表示失去焦点
                {required:true,message:"部门名称必须填写！",trigger:"blur"},
                //要按照规定的要求来进行填写
                {min:4,max:10,message: "输入的长度必须是3-10位之间",trigger: "blur"}
            ],
            loc:[
                //必填
                //trigger表示触发器，blue表示失去焦点
                {required:true,message:"部门地址必须填写！",trigger:"blur"},
                //要按照规定的要求来进行填写
                {min:10,max:20,message: "输入的长度必须是10-20位之间",trigger: "blur"}
            ]
        },
        //对弹出框的数据对象里面的数据进行初始化
        addDeptFormatData:{
            name:undefined,
            loc: undefined
        }
    },
    //vue的生命周期函数
    created:function (){
        //调用方法
        this.getDeptsByPage();
    },
    methods:{
        getDeptsByPage:function () {
            axios({
                url:"/dept/getDeptsByPage.do",
                method:"POST",
                params:{
                    pageNum: this.pageNum,
                    pageSize: this.pageSize
                }
            }).then((result)=>{
                this.deptDataArray = result.data.data
                this.total  = result.data.total
            })
        },
        // 点击切换的页码
        currentChange: function (newPageNum) {
            // 将新的页码赋值给pageNum
            this.pageNum = newPageNum
            // 刷新页面效果
            this.getDeptsByPage();
        },
        // 根据新的页码，获取到当前页码对应的数据
        sizeChange: function (newPageSize) {
            // 将新的页面数据大小赋值给pageSize
            this.pageSize = newPageSize
            // 刷新页码
            this.getDeptsByPage();
        },
        //添加功能
        openAddDeptWin:function (){
            this.addDeptWinOpenStatus = !this.addDeptWinOpenStatus
        },
        //关闭弹出框
        addDeptWinClose:function (){
            this.$refs['addDeptWinRef'].resetFields();
        },
        //取消
        resetAddDeptWin:function (){
            this.$refs['addDeptWinRef'].resetFields();
        },
        //确定的功能
        addDeptOK:function (){
            //点击确定按钮之前，要先校验输入的数据是否正确
            this.$refs['addDeptWinRef'].validate((valid)=>{
                //校验结果是正确的
                if (valid){
                    axios({
                        url: "/dept/addDept.do",
                        method:"POST",
                        params:this.addDeptFormatData
                    }).then((result)=>{
                        let code = result.data.code();
                        if (code == 200){
                            //关闭弹出框
                            this.addDeptWinOpenStatus = false;
                            this.getDeptsByPage();
                            //添加成功的通知
                            this.$notify({
                                title:"温馨提示",
                                message:"添加成功",
                                type:"success"
                            })
                        }else {
                            //添加失败
                            this.$notify({
                                title:"温馨提示",
                                message:result.data.msg + "，添加失败",
                                type:"danger"
                            })
                        }
                    })
                }
            })
        }
    }

});