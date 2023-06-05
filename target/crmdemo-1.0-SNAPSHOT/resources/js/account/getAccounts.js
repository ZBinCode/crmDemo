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
        //页码
        //每一页显示的数据的条数
        //总记录数
        //根据页码选择需要显示的条数
        //账号的启用和禁用
        status:true,
        //点击 账号列表 默认添加的弹出框是关闭的
        //初始化弹出框对象里面的数据
        //校验弹出框里面输入的数据
        //判断是否是超级管理员
        isRoot:role
    },
    //vue的生命周期函数
    created:function(){
        //调用方法：展示列表里面的数据

    },
    methods:{
        //分页查询展示列表的数据
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
        }
    }
})
