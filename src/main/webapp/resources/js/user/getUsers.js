new Vue({
    el: "#app",
    // 存放点击之后的初始化数据
    data: {
        // 客户信息列表数据
        userList: [],
        // 部门信息列表数据
        deptList: [],
        // 初始化页码
        pageNum: 1,
        // 每一页要显示的数据
        pageSize: 10,
        // 总条数
        total: 0,
        // 存放多选中的id列表
        delIdArray: [],
        // 根据页码来选择对应的条数
        pageSizes: [5, 10, 15, 20, 30, 50],
        // 搜索模块使用对象来保存数据（初始化）
        searchFormData: {
            username: undefined,
            tel: undefined,
            deptName: undefined,
            sex: undefined
        },
        // 客户管理页面默认弹出框是关闭的
        addUsersWinOpenStatus: false,
        // 设置添加功能的弹出框的初始数据
        addUsersFormData: {
            username: undefined,
            birthday: undefined,
            sex: undefined,
            tel: undefined,
            sal: undefined,
            profession: undefined,
            address: undefined,
            remark: undefined,
            deptId: undefined
        },
        // 添加功能的校验数据
        formRules: {
            username: [
                // 必填项
                {required: true, message:"姓名必须填写！",trigger:"blur"},
                // 按照指定的要求进行输入
                {min:2,max:10, message:"长度为2-10位！",trigger:"blur"}
            ],
            birthday: [
                // 必填项
                {required: true, message:"生日必须填写！",trigger:"blur"}
            ],
            sex: [
                // 必填项
                {required: true, message:"性别必须填写！",trigger:"change"}
            ],
            tel: [
                // 必填项
                {required: true, message:"联系电话必须填写！",trigger:"blur"},
                // 按照指定的要求进行输入
                {min:11,max:11, message:"手机号格式错误！",trigger:"blur"}
            ],
            sal: [
                // 必填项
                {required: true, message:"薪资必须填写！",trigger:"blur"}
            ],
            profession: [
                // 必填项
                {required: true, message:"职业必须填写！",trigger:"blur"}
            ],
            address: [
                // 必填项
                {required: true, message:"地址必须填写！",trigger:"blur"},
                // 按照指定的要求进行输入
                {min:10,max:50, message:"地址格式错误！",trigger:"blur"}
            ],
            deptId: [
                // 必填项
                {required: true, message:"部门必须填写！",trigger:"change"},
            ]
        },
        // 修改页面默认关闭
        editUsersWinOpenStatus: false,
        // 修改功能的初始化数据
        editUsersFormData: {
            id:undefined,
            username: undefined,
            birthday: undefined,
            sex: undefined,
            tel: undefined,
            sal: undefined,
            profession: undefined,
            address: undefined,
            remark: undefined,
            deptId: undefined
        },
    },
    // vue的生命周期
    created: function () {
        // 只是方法的调用
        // 通过分页查询客户信息
        this.getUsersByPage();
        // 查询所有的部门信息
        this.getAllDepts();
    },
    // methods里面只定义方法
    methods: {
        // 分页查询
        getUsersByPage: function () {
            axios({
                url: "/user/getUsersByPage.do",
                method: "POST",
                params: {
                    // 页码
                    pageNum: this.pageNum,
                    // 每一页显示的数据
                    pageSize: this.pageSize
                }
            }).then((result) => {
                this.userList = result.data.data
                this.total = result.data.total
            })
        },
        // 点击切换的页码
        currentChange: function (newPageNum) {
            // 将新的页码赋值给pageNum
            this.pageNum = newPageNum
            // 刷新页面效果
            this.getUsersByPage();
        },
        // 根据新的页码，获取到当前页码对应的数据
        sizeChange: function (newPageSize) {
            // 将新的页面数据大小赋值给pageSize
            this.pageSize = newPageSize
            // 刷新页码
            this.getUsersByPage();
        },
        // 将性别 0和1 转换为 男和女
        sexFmt: function (row) {
            return row.sex == "1" ? "男" : "女"
        },
        // 对薪资的数据格式进行转换
        salFmt: function (row) {
            // ${}表示去获取html页码薪资字段的值
            return `￥${row.sal}`
        },
        professionFmt: function (row) {
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
        // 全选
        selectAll:function (params){
            this.delIdArray = [];
            params.filter((item)=>{
                this.delIdArray.push(item.id)
            })
        },
        // 单选
        selectOne:function (params){
            this.delIdArray = []
            params.filter((item)=>{
                this.delIdArray.push(item.id)
            })
        },
        // 获取部门信息
        getAllDepts:function (){
            axios({
                url:"/dept/getDeptsByPage.do",
                method:"POST",
                params:{
                    // 页码
                    pageNum: this.pageNum,
                    // 每一页显示的数据
                    pageSize: this.pageSize
                }
            }).then((result)=>{
                this.deptList = result.data.data;
            })
        },
        // 点击添加按钮，显示弹出框
        openAddWin:function (){
            this.addUsersWinOpenStatus = !this.addUsersWinOpenStatus
        },
        // 关闭弹出框
        addUserWinClose:function (){
            this.$refs['addUserWinRef'].resetFields();
        },
        // 弹出框里面的取消功能
        addUsersCancel: function (){
            this.$refs['addUserWinRef'].resetFields();
        },
        addUsersOK: function (){
            this.$refs['addUserWinRef'].validate((valid)=>{
                //弹出框里面的数据验证通过
                if(valid){
                    axios({
                        url:"/user/addUser.do",
                        method:"POST",
                        params:this.addUsersFormData
                    }).then((result)=>{
                        let code = result.data.code;
                        if (code == 200){
                            // 添加成功之后，要关闭弹出框
                            this.addUsersWinOpenStatus = false;
                            // 刷新数据
                            this.getUsersByPage();
                            // 添加成功使用通知提示
                            this.$notify({
                                title:"温馨提示",
                                message:"成功的添加了一条记录",
                                type:"success"
                            })
                        }else {
                            let msg = result.data.msg;
                            // 添加失败通知提示
                            this.$notify({
                                title:"温馨提示",
                                message:msg + "，添加失败",
                                type:"danger"
                            })
                        }
                    })
                }
            })
        },
        // 批量删除
        delMany:function (){
            // 删除之前要先判断用户是否勾选了数据
            let len = this.delIdArray.length;
            //没有选中记录
            if (len == 0){
                this.$alert("请至少选中一条记录！","温馨提示");
            }else {
                //勾选了记录，提示用户是否要删除记录
                this.$confirm("你确定要删除这些记录吗？","温馨提示").then(()=>{
                    //删除的逻辑
                    axios({
                        url:"/user/cutManyUser.do",
                        method:"POST",
                        params:{
                            id:this.delIdArray.join(",")    //多个id之前使用逗号隔开
                        }
                    }).then((result)=>{
                        let code = result.data.code;
                        if (code == 200){
                            //批量删除成功，刷新页面
                            this.getUsersByPage();
                            //通知用户删除成功
                            this.$notify({
                                title:"温馨提示",
                                message:"成功的删除了一批记录",
                                type:"success"
                            })
                        }else {
                            //删除失败的通知
                            this.$notify({
                                title:"温馨提示",
                                message:result.data.msg + "，删除失败",
                                type:"danger"
                            })
                        }
                    });
                });
            }
        },
        //搜索功能
        //如果没有输入条件，那么搜索出来的就是全部数据
        //如果有选择条件，那么搜索出来的就是按照条件所指定的内容
        //可以指定一个条件或者多个条件的搜索
        searchUsers:function (){
            axios({
                url:"/user/getUsersBySearch.do",
                method:"POST",
                params:this.searchFormData
            }).then((result)=>{
                let code = result.data.code
                // 按照条件搜索成功，显示按照条件搜索之后的数据
                if (code == 200){
                    this.userList = result.data.data;
                    //按照条件来查询，也需要分页
                    //页码
                    this.pageNum = 1;
                    //每一页显示的数据
                    this.pageSize = 10;
                    this.total = result.data.total;
                }else{
                    //失败的通知
                    this.$notify({
                        title:"温馨提示",
                        message:result.data.msg + ",搜索失败",
                        type:"danger"
                    })
                }
            })
        },
        //单个删除
        delOne:function (row){
            //根据id删除
            let id = row.id;
            this.$confirm("你确定要删除这条记录吗？","温馨提示").then(()=>{
                axios({
                    url:"/user/cutOneUser.do",
                    method:"POST",
                    params:{
                        id:id
                    }
                }).then((result)=>{
                    let code = result.data.code;
                    if (code == 200){
                        //删除成功，刷新页面
                        this.getUsersByPage();
                        //通知用户删除成功
                        this.$notify({
                            title:"温馨提示",
                            message:"成功的删除了一条记录",
                            type:"success"
                        })
                    }else {
                        //删除失败的通知
                        this.$notify({
                            title:"温馨提示",
                            message:result.data.msg + "，删除失败",
                            type:"danger"
                        })
                    }
                });
            })

        },
        //修改弹出框
        openEditWin:function (row){
            //显示弹出框
            this.editUsersWinOpenStatus = !this.editUsersWinOpenStatus
            //弹出框内默认显示当前行的数据
            this.editUsersFormData = row;
        },
        //关闭修改功能的弹出框
        editUsersWinClose:function (){
            this.$refs['editUserWinRef'].resetFields();
        },
        //修改功能的弹出框中的取消功能
        editUsersCancel:function (){
            this.$refs['editUserWinRef'].resetFields();
        },
        //修改功能的弹出框中的确定功能
        editUsersOK:function (){
            this.$confirm("你确定要修改这条记录吗？","温馨提示").then(()=>{
                this.$refs['editUserWinRef'].validate((valid)=>{
                    if (valid){
                        axios({
                            url:"/user/editUser.do",
                            method:"POST",
                            params:this.editUsersFormData
                        }).then((result)=>{
                            let code = result.data.code;
                            if (code == 200){
                                //修改数据成功
                                this.getUsersByPage();
                                //通知用户修改成功
                                this.$notify({
                                    title:"温馨提示",
                                    message:"成功的编辑了一条记录",
                                    type:"success"
                                })
                            }else{
                                let msg = result.data.msg;
                                this.$notify({
                                    title:"温馨提示",
                                    message:msg + "，修改失败",
                                    type:"danger"
                                })
                            }
                        })
                    }
                })
            })
        }



    }
})