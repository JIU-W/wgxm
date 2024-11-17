package com.itjn.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.itjn.common.Result;
import com.itjn.common.annotation.Log;
import com.itjn.common.enums.BusinessType;
import com.itjn.common.enums.ResultCodeEnum;
import com.itjn.exception.BusibessException;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

//文件上传和下载(本地)
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Value("${files.path}")
    private String basePath;


    /**
     * 更新用户头像(图片上传、文件上传)
     * @param file
     * @return
     */
    @Log(title = "上传用户头像", businessType = BusinessType.UPLOAD)
    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(MultipartFile file){

        /*String flag;
        synchronized (FileController.class) {
            //生成一个时间戳作为标志
            flag = System.currentTimeMillis() + "";
            //当前线程暂停执行1毫秒
            ThreadUtil.sleep(1L);
        }*/

        //原始文件名
        String originalFilename = file.getOriginalFilename();

        //fileName:新文件名
        String fileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        try {
            String filePath = basePath + "avatar/";

            if (!FileUtil.isDirectory(filePath)) {
                FileUtil.mkdir(filePath);
            }

            //将临时文件存储到指定目录
            file.transferTo(new File(filePath + fileName));
            //FileUtil.writeBytes(file.getBytes(), filePath + fileName);

            log.info("头像上传成功");
        } catch (IOException e) {
            log.error("头像上传失败",e);
            throw new BusibessException(ResultCodeEnum.AVATAR_UPLOAD_FAILED);
        }

        String avatarUrl = "/file/getAvatar/" + fileName;
        //其实就是给前端拼接一个“获取图片接口”的路径
        return Result.success(avatarUrl);
    }


    //获取图片(图片下载、预览)
    @Log(title = "获取用户头像", businessType = BusinessType.DOWNLOAD)
    @GetMapping("/getAvatar/{imageName}")
    public void getAvatar(@PathVariable String imageName, HttpServletResponse response){
        if(StrUtil.isBlank(imageName)){
            return;
        }

        //获取图片后缀
        String imageSuffix = imageName.substring(imageName.lastIndexOf("."));
        String filePath = basePath + "avatar/" + imageName;
        //去除后缀中的点
        imageSuffix = imageSuffix.replace(".", "");
        String contentType = "image/" + imageSuffix;
        response.setContentType(contentType);
        response.setHeader("Cache-Control", "max-age=2592000");

        try {
            //将图片文件的内容写入响应流，从而返回给客户端。
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(filePath);
            //输出流，通过输出流将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            log.error("获取头像失败",e);
            e.printStackTrace();
        }

    }

    //TODO 文件上传到阿里云


}
