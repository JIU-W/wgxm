package com.itjn.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.itjn.common.Result;
import com.itjn.common.enums.ResultCodeEnum;
import com.itjn.exception.BusibessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

//文件上传和下载(本地)
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Value("${avatar.path}")
    private String basePath;


    //文件上传接口
    @PostMapping("/uploadFile")
    public Result uploadFile(MultipartFile file){

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
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        try {
            String filePath = basePath + "/avatar/";

            if (!FileUtil.isDirectory(filePath)) {
                FileUtil.mkdir(filePath);
            }

            //将临时文件存储到指定目录
            file.transferTo(new File(filePath + fileName));
            //FileUtil.writeBytes(file.getBytes(), filePath + fileName);

            log.info("文件上传成功");
        } catch (IOException e) {
            log.error("文件上传失败");
            throw new BusibessException(ResultCodeEnum.FILE_UPLOAD_FAILED);
        }

        String avatarUrl = "/avatar" + fileName;
        return Result.success(avatarUrl);
    }

    //获取图片(图片下载、预览)

    //TODO 文件上传到阿里云


}
