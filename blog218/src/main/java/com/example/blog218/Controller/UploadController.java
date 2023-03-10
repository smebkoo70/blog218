package com.example.blog218.Controller;

import com.example.blog218.utils.QiniuUtils;
import com.example.blog218.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("upload")
public class UploadController {
    @Autowired
    private QiniuUtils qiniuUtils;



    //@RequestParam spring里 用来接收文件
    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile file){
        //原始文件名称 比如 aa.png  就能拿到png，UUID保证生成唯一的文件名称
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        boolean upload = qiniuUtils.upload(file, fileName);
        //上传文件到哪里？七牛云  云服务器 按量付费  速度快 把图片 发到离用户最近的服务器上。
        //降低我们自身的带宽消耗
        if (upload){
            return Result.success(QiniuUtils.url + fileName);
        }
        return Result.fail(20001,"上传失败");
    }
}
