package com.example.blog218.utils;

import com.alibaba.fastjson.JSON;
import com.example.blog218.common.aop.LogAnnotation;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 64597
 */
@Component
public class QiniuUtils {

    //public static  final String url = "https://static.mszlu.com/";
    public static  final String url = "http://rr9g943ky.bkt.clouddn.com/";

    @Value("${qiniu.accessKey}")
    private  String accessKey;
    @Value("${qiniu.secretKey}")
    private  String accessSecretKey;
    //@LogAnnotation(module="七牛云的上传操作",operator="获取上传信息")
    public  boolean upload(MultipartFile file,String fileName){

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());

        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //String bucket = "mszlu";
        String bucket = "rainmovie";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            byte[] uploadBytes = file.getBytes();
            Auth auth = Auth.create(accessKey, accessSecretKey);
            String upToken = auth.uploadToken(bucket);
                Response response = uploadManager.put(uploadBytes, fileName, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        return false;
    }
}
