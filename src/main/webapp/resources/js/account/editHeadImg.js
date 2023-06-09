new Vue({
    el:"#app",
    data:{
        imgUrl:undefined
    },
    methods:{
        /*文件发生改变时，用作图片的预览*/
        onChange:function(file){
            //获取到上传图片的大小
            let flag = file.size/1024/1024 > 1;
            if(flag){
                //显示错误
                this.$alert("文件上传的大小最大只能为1M");
                return false;
            }
            //raw表示上传的图片是原图片，是没有经过任何处理的
            this.imgUrl = URL.createObjectURL(file.raw);
        },
        //后端返回给前端
        onSuccess:function(result){
            //获取状态码
            let code = result.code;
            if(code == 200){
                //显示通知
                this.$notify({
                    title:"温馨提示",
                    message:"上传成功",
                    type:"success"
                })
                //显示头像
                this.imgUrl = result.data;
                //获取到本地缓存对象
                let userObj = JSON.parse(window.localStorage.getItem("user"));
                //将imgUrl的值保存到本地缓存imgUrl对象中
                userObj.imgUrl = this.imgUrl;
                window.localStorage.setItem("user",JSON.stringify(userObj));
                //下载图片，并显示在页面上
                window.document.getElementById("headImg").src =
                    "/download/downloadImg.do?imgUrl=" + this.imgUrl;
            } else {
                //失败的通知
                let msg = result.msg;
                this.$notify({
                    title:"温馨提示",
                    message:msg + "上传失败",
                    type:"danger"
                })
            }
        },
        //点击按钮，触发文件上传的方法
        uploadHeadImg:function(){
            this.$refs['uploadImgRef'].submit();
        }
    }
})