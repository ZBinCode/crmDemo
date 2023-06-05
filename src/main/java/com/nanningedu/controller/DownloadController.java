package com.nanningedu.controller;

import com.nanningedu.common.Constants;
import com.nanningedu.common.UploadBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    private UploadBean uploadBean;

    @ModelAttribute //该注解在方法上使用，会在其他方法执行之前先被调用
    public void transferDefaultImg(HttpServletRequest request) throws IOException {
        //获取默认图片存放的文件夹目录
        String saveDirPath = uploadBean.getBaseUrl();
        //如果默认图片存在，则不进行拷贝
        File file = new File(saveDirPath + "/" + Constants.DEFAULT_HEAD_IMG);
        if(file.exists()){
            return;
        }
        //如果默认图片不存在，则创建
        File file2 = new File(saveDirPath);
        if(!file2.exists()){
            file2.mkdirs();
        }
        ServletContext application = request.getSession().getServletContext();
        String sourcePath = application.getRealPath("/resources/images/"+Constants.DEFAULT_HEAD_IMG);
        //文件输入流
        FileInputStream fis = new FileInputStream(sourcePath);
        //输出流
        FileOutputStream fos = new FileOutputStream(saveDirPath + "/" + Constants.DEFAULT_HEAD_IMG);
        //字节
        byte[] bt = new byte[1024];
        //初始化
        int len = -1;
        while ((len = fis.read(bt)) != -1){
            fos.write(bt,0,len);
        }
        //关闭流
        fis.close();
        fos.close();
    }

    //图片的下载
    @RequestMapping("downloadImg.do")
    public void downloadImg(String imgUrl, HttpServletResponse response) throws IOException {
        String saveDirPath = uploadBean.getBaseUrl();
        FileInputStream fis = new FileInputStream(saveDirPath + "/" + imgUrl);
        int len = -1;
        byte[] bt = new byte[1024];
        ServletOutputStream fos = response.getOutputStream();
        while ((len = fis.read(bt)) != -1){
            fos.write(bt,0,len);
        }
        fis.close();
        fos.close();
    }

}
