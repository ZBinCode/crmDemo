package com.nanningedu.controller;

import com.nanningedu.common.Result;
import com.nanningedu.common.UploadBean;
import com.nanningedu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

//文件上传
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UploadBean uploadBean;

    @RequestMapping("/uploadFile.do")
    public Result uploadFile(MultipartFile headImg, HttpServletRequest request) throws IOException {
        if(headImg == null){
            return new Result(-1,"需要上传的文件不能为空");
        }
        //获取上传文件的名字(指的是 帅哥 这两个字)
        String oriName = headImg.getOriginalFilename();
        if("".equals(oriName)){
            return new Result(-1,"上传的文件的文件名不能为空");
        }
        //获取到上传文件的后缀（指的是 .jpg）
        //从 帅哥 这两个字后面的 . 开始截取，一直截取到末尾
        String ext = oriName.substring(oriName.lastIndexOf("."));
        //动态获取上传的文件名（4e5dbf52-b293-4078-aa89-9657a68f8d7c 是保存到本地盘符去的名字）
        String uuid = UUID.randomUUID().toString();
        //指定文件上传的路径 （D:/devTools/upload）
        String saveDirPath = uploadBean.getBaseUrl();
        //创建文件路径
        File file = new File(saveDirPath);
        //不存在此文件
        if(!file.exists()){
            //就创建
            file.mkdirs();
        }
        //包含图片的一个绝对路径
        //D:/devTools/upload/4e5dbf52-b293-4078-aa89-9657a68f8d7c.jpg
        File finalFile = new File(saveDirPath + "/" + uuid + ext);
        //文件上传
        headImg.transferTo(finalFile);
        //上传成功之后，将图片地址保存到数据库中
        HttpSession session = request.getSession();
        Result result = accountService.modifyUploadHeadImg(uuid + ext,session);
        if(result.getCode() == 200){
            result.setData(uuid + ext);
        }
        return result;
    }

}
