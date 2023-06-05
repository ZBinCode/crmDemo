new Vue({
    el:"#app",
    //存放点击某些功能之后的初始化数据
    //data里面只定义数据
    data:{
        //客户信息列表数据
        userList:[],
        //部门信息列表数据
        deptList:[],
        //初始化页码
        pageNum:1,
        //每一页要显示的数据
        pageSize:10,
        //总条数
        total:0,
        //根据页码来选择对应的条数
        pageSizes:[5,10,15,20,30,50],
        //搜索模块使用对象来保存数据（初始化）
        searchFormData:{
            username:undefined,
            tel:undefined,
            deptName:undefined,
            sex:undefined
        },
        //客户管理页面默认添加功能的弹出框是关闭的
        addUsersWinOpenStatus:false,
        //设置添加功能的弹出框的初始化数据
        addUsersFormData:{
            username:undefined,
            birthday:undefined,
            sex:undefined,
            tel:undefined,
            sal:undefined,
            profession:undefined,
            address:undefined,
            remark:undefined,
            deptId:undefined
        },
        //添加功能的校验数据
        formRules:{
            username:[
                //必填
                {required:true,message:"姓名必须填写!",trigger:"blur"},
                //按照指定的要求进行输入
                {min:2,max:10,message:"长度为2-10位",trigger:"blur"}
            ],
            birthday:[
                //必填
                {required:true,message:"生日必须填写!",trigger:"blur"}
            ],
            sex:[
                //必填
                {required:true,message:"性别必须填写!",trigger:"change"}
            ],
            tel:[
                //必填
                {required:true,message:"联系电话必须填写!",trigger:"blur"},
                //按照指定的要求进行输入
                {min:11,max:11,message:"手机号格式错误",trigger:"blur"}
            ],
            sal:[
                //必填
                {required:true,message:"薪资必须填写!",trigger:"blur"}
            ],
            profession:[
                //必填
                {required:true,message:"职业必须填写!",trigger:"change"}
            ],
            address:[
                //必填
                {required:true,message:"地址必须填写!",trigger:"blur"},
                //按照指定的要求进行输入
                {min:10,max:50,message:"地址格式错误",trigger:"blur"}
            ],
            deptId:[
                //必填
                {required:true,message:"部门必须填写!",trigger:"change"}
            ]
        },
        //将删除的id存放于初始化的数组对象中
        delIdArray:[],
        //默认关闭修改功能的弹出框
        editUsersWinOpenStatus:false,
        editUsersFormData:{
            id:undefined,
            username:undefined,
            birthday:undefined,
            sex:undefined,
            tel:undefined,
            sal:undefined,
            profession:undefined,
            address:undefined,
            remark:undefined,
            deptId:undefined
        }
    },
    //vue的生命周期
    created:function(){
        //只是方法的调用
        //通过分页查询客户信息
        this.getUsersByPage();
        //查询所有的部门信息
        this.getAllDepts();
    },
    //methods里面只定义方法
    methods:{
        //分页查询
        getUsersByPage:function(){
            axios({
                url:"/user/getUsersByPage.do",
                method:"POST",
                params:{
                    //页码
                    //在methods这个对象中，要去使用data对象中的pageNum
                    pageNum:this.pageNum,
                    //每一页显示的数据
                    pageSize:this.pageSize
                }
            }).then((result)=>{
                this.userList = result.data.data
                this.total = result.data.total
            })
        },
        //点击切换的页码
        currentChange:function(newPageNum){
            //将新的页码值赋值给pageNum
            this.pageNum = newPageNum
            //刷新页面效果
            this.getUsersByPage();
        },
        //根据新的页码，获取到当前页码对应的数据
        sizeChange:function(newPageSize){
            //将新的页码对应的新的数据传递给pageSize
            this.pageSize = newPageSize
            //刷新页面效果
            this.getUsersByPage();
        },
        //将数据库表里面的性别字段0和1的值，转变成男和女显示在页面
        sexFmt:function(row){
            //     条件成立         选择它
            return row.sex == "1" ? "男" : "女"
        },
        //对薪资的数据格式进行转换
        salFmt:function(row){
            //${}表示去获取html页面薪资字段的值
            return `￥${row.sal}`
        },
        professionFmt:function(row){
            var profession = ""
            switch (row.profession){
                case "1":
                    profession = "程序员"
                    break
                case "2":
                    profession = "工程师"
                    break
                case "3":
                    profession = "码农"
                    break
            }
            return profession
        },
        //全选
        selectAll:function(params){
            this.delIdArray = [];
            params.filter((item)=>{
                this.delIdArray.push(item.id)
            })
        },
        //单选
        selectOne:function(params){
            this.delIdArray = [];
            params.filter((item)=>{
                this.delIdArray.push(item.id)
            })
        },
        //查询部门列表的所有数据
        getAllDepts:function(){
            axios({
                url:"/dept/getDeptsByPage.do",
                method:"POST",
                params:{
                    pageNum: this.pageNum,
                    pageSize: this.pageSize
                }
            }).then((result)=>{
                this.deptList = result.data.data;
            });
        },
        //点击添加按钮，显示弹出框
        openAddWin:function(){
            this.addUsersWinOpenStatus = !this.addUsersWinOpenStatus
        },
        //关闭弹出框
        addUsersWinClose:function(){
            this.$refs['addUsersWinRef'].resetFields();
        },
        //弹出框里面的取消功能
        addUsersCancel:function(){
            this.$refs['addUsersWinRef'].resetFields();
        },
        //弹出框里面的确定功能
        addUsersOK:function(){
            this.$refs['addUsersWinRef'].validate((valid)=>{
                //弹出框里面的数据验证通过
                if(valid){
                    axios({
                        url:"/user/addUser.do",
                        method:"POST",
                        params:this.addUsersFormData
                    }).then((result)=>{
                        let code = result.data.code;
                        if(code == 200){
                            //添加成功之后，要关闭弹出框
                            this.addUsersWinOpenStatus = false;
                            //刷新数据
                            this.getUsersByPage();
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
        //批量删除
        delMany:function(){
            //删除之前，要先判断用户是否勾选了数据
            let len = this.delIdArray.length;
            //没有选中记录
            if(len == 0){
                this.$alert("请至少选中一条记录!","温馨提示");
            } else {
                //勾选了记录，提示用户是否是要删除记录
                this.$confirm("你确定要删除这些记录吗？","温馨提示").then(()=>{
                    //删除的逻辑
                    axios({
                        url:"/user/cutManyUser.do",
                        method:"POST",
                        params:{
                            id:this.delIdArray.join(",")  //多个id，使用逗号隔开
                        }
                    }).then((result)=>{
                        console.log(result);
                        let code = result.data.code;
                        if(code == 200){
                            //批量删除成功
                            this.getUsersByPage();
                            //删除成功的通知
                            this.$notify({
                                title:"温馨提示",
                                message:"成功的删除了一批记录",
                                type:"success"
                            })
                        } else {
                            let msg = result.data.msg;
                            //删除失败的通知
                            this.$notify({
                                title:"温馨提示",
                                message:msg + "，删除失败",
                                type:"danger"
                            })
                        }
                    });
                });
            }
        },
        //搜索功能
        //如果没有选择条件，那么搜索出来的就是全部数据
        //入宫有选择条件，那么搜索出来的就是按照条件所指定的内容
        //可以指定一个条件的搜索，也可以指定两个条件的搜索，还可以指定三个条件的搜索，也可以指定四个条件的搜索
        searchUsers:function(){
            axios({
                url:"/user/getUserBySearch.do",
                method:"POST",
                params:this.searchFormData
            }).then((result)=>{
                let code = result.data.code;
                if(code == 200){
                    //按照条件搜索成功，显示按照条件搜索的那些数据
                    this.userList = result.data.data;
                    //按照条件来查询，也需要分页
                    //页码
                    this.pageNum = 1;
                    //每一页显示的数据
                    this.pageSize = 10;
                    this.total = result.data.total;
                } else {
                    //失败的通知
                    let msg = result.data.msg;
                    this.$notify({
                        title:"温馨提示",
                        message:msg + "，搜索失败",
                        type:"danger"
                    })
                }
            })
        },
        //单个删除
        delOne:function(row){
            //删除是根据id来删的
            let id = row.id;
            this.$confirm("你确定要删除这条记录吗?","温馨提示").then(()=>{
                axios({
                    url:"/user/cutOneUser.do",
                    method:"POST",
                    params:{
                        id:id
                    }
                }).then((result)=>{
                    let code = result.data.code;
                    if(code == 200){
                        //删除成功，刷新数据
                        this.getUsersByPage();
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
        },
        //修改
        openEditWin:function(row){
            //显示弹出框
            this.editUsersWinOpenStatus = !this.editUsersWinOpenStatus
            //弹出框内默认显示当前行的数据
            this.editUsersFormData = row;
        },
        //关闭修改功能的弹出框
        editUsersWinClose:function(){
            this.$refs['editUsersWinRef'].resetFields();
        },
        //修改功能的弹出框中的取消功能
        editUsersCancel:function(){
            this.$refs['editUsersWinRef'].resetFields();
        },
        //修改功能的弹出框中的确定功能
        editUsersOK:function(){
            //在点击确定之前，需要先验证输入的数据正确与否
            this.$refs['editUsersWinRef'].validate((valid)=>{
                if(valid){
                    axios({
                        url:"/user/editUser.do",
                        method:"POST",
                        params:this.editUsersFormData
                    }).then((result)=>{
                        let code = result.data.code;
                        if(code == 200){
                            //修改数据成功
                            this.editUsersWinOpenStatus = false;
                            //刷新数据
                            this.getUsersByPage();
                            //通知
                            this.$notify({
                                title:"温馨提示",
                                message:"成功的编辑了一条记录",
                                type:"success"
                            })
                        } else {
                            let msg = result.data.msg;
                            this.$notify({
                                title:"温馨提示",
                                message:msg + "，修改失败",
                                type:"success"
                            })
                        }
                    })
                }
            });
        }
    }
})