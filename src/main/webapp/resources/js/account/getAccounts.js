//判断登录的人的角色
//userObject是json字符串对象
var userObject = window.localStorage.getItem("user");
//userObj是javascript对象
let userObj = JSON.parse(userObject);
//超级管理员
var role = userObj.role == 1;
new Vue({
    el:"#app",
    //初始化数据
    data:{
        //列表数据
        accountList:[],
        //页码
        pageNum:1,
        //每一页显示的数据的条数
        pageSize:10,
        //总记录数
        total:0,
        //根据页码选择需要显示的条数
        pageSizes:[5,10,15,20,30,50],
        //账号的启用和禁用
        status:true,
        //点击 账号列表 默认添加的弹出框是关闭的
        addAccountWinOpenStatus:false,
        //初始化弹出框对象里面的数据
        addAccountFormData:{
            username:undefined
        },
        //校验弹出框里面输入的数据
        addAccountWinRules:{
            username:[
                //必填
                {required:true,message:"姓名必须填写!",trigger:"blur"},
                //按照指定的要求进行输入
                {min:2,max:10,message:"长度为2-10位",trigger:"blur"}
            ]
        },
        //判断是否是超级管理员
        isRoot:role
    },
    //vue的生命周期函数
    created:function(){
        //调用方法：展示列表里面的数据
        this.getAccountsByPage();
    },
    methods:{
        //分页查询展示列表的数据
        getAccountsByPage:function(){
            axios({
                url:"/account/getAccountsByPage.do",
                method:"POST",
                params:{
                    //页码
                    //在methods这个对象中，要去使用data对象中的pageNum
                    pageNum:this.pageNum,
                    //每一页显示的数据
                    pageSize:this.pageSize
                }
            }).then((result)=>{
                this.accountList = result.data.data
                this.total = result.data.total
            })
        },
        //点击切换的页码
        currentChange:function(newPageNum){
            //将新的页码值赋值给pageNum
            this.pageNum = newPageNum
            //刷新页面效果
            this.getAccountsByPage();
        },
        //根据新的页码，获取到当前页码对应的数据
        sizeChange:function(newPageSize){
            //将新的页码对应的新的数据传递给pageSize
            this.pageSize = newPageSize
            //刷新页面效果
            this.getAccountsByPage();
        },
        updateTimeFmt:function (row){
            return row.updateTime
        },
        statusFmt:function (row){
            return row.status == "1" ? "启用" : "禁用"
        },
        //启用于禁用的方法
        switchChange:function(row){
            axios({
                url:"/account/editAccountStatus.do",
                method:"POST",
                //根据id获取到数据，然后再对数据里面的状态进行修改
                params:{
                    status:row.status,
                    id:row.id
                }
            }).then((result)=>{
                let code = result.data.code;
                if(code == 200){
                    this.$notify({
                        title:"温馨提示",
                        message:"修改状态成功",
                        type:"success"
                    })
                } else {
                    //失败
                    let msg = result.data.msg;
                    this.$notify({
                        title:"温馨提示",
                        message:msg + "状态修改失败",
                        type:"danger"
                    })
                }
                //刷新数据

            });
        },
        //点击添加按钮，弹出添加框
        openAddAccountWin:function (){
            this.addAccountWinOpenStatus = !this.addAccountWinOpenStatus;
        },
        //关闭弹出框
        addAccountWinClose:function (){
            this.$refs['addAccountWinRef'].resetFields();
        },
        //弹出框里面的取消功能
        addAccountCancel:function (){
            this.$refs['addAccountWinRef'].resetFields();
        },
        //弹出框里面的确定功能
        addAccountOK:function (){
            this.$refs['addAccountWinRef'].validate((valid)=>{
                console.log(this.addAccountFormData.username)
                //弹出框里面的数据验证通过
                if(valid){
                    axios({
                        url:"/account/addAccount.do",
                        method:"POST",
                        params: {
                            username:this.addAccountFormData.username
                        }
                    }).then((result)=>{
                        let code = result.data.code;
                        if(code == 200){
                            //添加成功之后，要关闭弹出框
                            this.addAccountWinOpenStatus = false;
                            //刷新数据
                            this.getAccountsByPage();
                            //使用通知提示添加成功
                            this.$notify({
                                title:"温馨提示",
                                message:"成功的添加了一条记录",
                                type:"success"
                            })
                        } else {
                            let msg = result.data.msg;
                            this.$notify({
                                title:"温馨提示",
                                message:msg + "，添加失败",
                                type:"danger"
                            })
                        }
                    })
                }
            });
        },

        // 删除用户
        delOneAccount:function (row){
            //删除是根据id来删的
            let id = row.id;
            this.$confirm("你确定要删除这条记录吗?","温馨提示").then(()=>{
                axios({
                    url:"/account/cutOneAccount.do",
                    method:"POST",
                    params:{
                        id:id
                    }
                }).then((result)=>{
                    let code = result.data.code;
                    if(code == 200){
                        //删除成功，刷新数据
                        this.getAccountsByPage();
                        //通知
                        this.$notify({
                            title:"温馨提示",
                            message:"成功的删除了一条记录",
                            type:"success"
                        })
                    } else {
                        //删除失败的通知
                        let msg = result.data.msg;
                        this.$notify({
                            title:"温馨提示",
                            message:msg + "，删除失败",
                            type:"danger"
                        })
                    }
                });
            });
        }


    }
})
