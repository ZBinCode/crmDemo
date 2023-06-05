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

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UploadBean uploadBean;

    @RequestMapping("/uploadFile.do")
    public Result uploadFile(MultipartFile headImg, HttpServletRequest request) throws IOException {
        if (headImg == null){
            return new Result(-1,"需要上传文件不能为空");
        }
        //获取上传文件的名字
        String oriName = headImg.getOriginalFilename();
        if ("".equals(oriName)){
            return new Result(-1,"上传的文件名不能为空");
        }
        //获取上传文件的后缀
        String ext = oriName.substring(oriName.lastIndexOf("."));
        // 动态获取上传的文件名
        String uuid = UUID.randomUUID().toString();
        // 指定文件上传的路径
        String saveDirPath = uploadBean.getBaseUrl();
        // 创建文件路径
        File file = new File(saveDirPath);
        if (!file.exists()){
            file.mkdir();
        }
        File finalFile = new File(saveDirPath + "/" + uuid + ext);
        // 文件上传
        headImg.transferTo(finalFile);
        // 上传成功之后，将图片地址保存到数据库
        HttpSession session = request.getSession();
        Result result = accountService.modifyUploadHeadImg(uuid + ext, session);
        if (result.getCode() == 200){
            result.setData(uuid + ext);
        }

        return result;
    }

}
