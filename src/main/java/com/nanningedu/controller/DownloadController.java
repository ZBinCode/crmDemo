package com.nanningedu.controller;

import com.nanningedu.common.Constants;
import com.nanningedu.common.UploadBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    private UploadBean uploadBean;

    public void transferDefaultImg(HttpServletRequest request){
        //获取默认图片存放的文件夹目录
        String saveDirPath = uploadBean.getBaseUrl();
        //如果默认图片存在，则不进行拷贝
        File file = new File(saveDirPath + "/" + Constants.DEFAULT_HEAD_IMG);
        if (file.exists()){
            return;
        }
        //如果默认图片不存在，则创建一个
        File file2 = new File(saveDirPath);
        if (!file2.exists()){
            file2.mkdir();
        }
        ServletContext application = request.getSession().getServletContext();
        String sourcePath = application.getRealPath("/resources/imagers" + Constants.DEFAULT_HEAD_IMG);
    }

}
