package com.wuxin.blog.controller.admin.common;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.wuxin.blog.util.KeyUtil;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wuxin001
 * @Date: 2021/09/19/0:27
 * @Description: 使用github作为图床
 */
@Slf4j
@RestController
@RequestMapping("/github")
public class GitHubController {

    @Value("${github.bucket.access_token}")
    private String token;

    @Value("${github.bucket.api}")
    private String url;

    @Value("${github.bucket.jsdelivr.api}")
    private String jsdelivrUrl;




    @PostMapping("/upload/user/avatar")
    public Result uploadImg(@RequestParam("file") MultipartFile file) throws Exception {
        log.info("图片上传中....file={}", file);
        // public Result uploadImg(@RequestBody MultipartFile file) throws Exception {
        String originaFileName = file.getOriginalFilename();
        //上传图片不存在时
        if (originaFileName == null) {
            return R.error("上传失败！图片文件不存在！");
        }

        String suffix = originaFileName.substring(originaFileName.lastIndexOf("."));
        //设置图片名字  20210801/fileName.suffix
        String fileName = KeyUtil.PicId()+"/"+KeyUtil.IdUtils() + suffix;

        // 转码之后的图片
        String paramImgFile = Base64.encode(file.getBytes());

        // 设置body
        HashMap<String, Object> map = new HashMap<>();
        map.put("content",paramImgFile);
        map.put("message","commit a user avatar");
        String mapStr = JSONUtil.toJsonStr(map);

        try {
            // 发送请求
            String put = HttpRequest.put(this.url+fileName)
                    .header("Authorization","token  "+this.token)
                    .header("Accept","application/json")
                    .header("Content-Type","application/json")
                    .body(mapStr)
                    .timeout(15000) // 20s
                    .execute()
                    .body();


            // 请求之后的地址
            log.info("图片上传地址 url ={}",this.url+fileName);
            log.info("图片加速之后的访问地址 url ={}",this.jsdelivrUrl+fileName);

            // 将加速访问的地址返回给前台
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("name",fileName);
            hashMap.put("url",this.jsdelivrUrl);
            // return R.ok(hashMap);
            return R.ok(this.jsdelivrUrl+fileName);
        } catch (HttpException e) {
            e.printStackTrace();
            return R.error("上传失败！异常信息！");
        }

    }


    @PostMapping("/upload/blog/img")
    public Result uploadBlogImg(@RequestParam("file") MultipartFile file) throws Exception {
        // public Result uploadImg(@RequestBody MultipartFile file) throws Exception {
        String uploadFileName = file.getOriginalFilename();

        log.info("文件名：{}",uploadFileName);
        //上传图片不存在时
        if (uploadFileName == null) {
            return R.error("上传失败！文件不存在！");
        }

        String suffix = uploadFileName.substring(uploadFileName.lastIndexOf("."));
        //设置图片名字  /blog/202108/fileName.suffix
        String fileName = KeyUtil.PicId()+"/"+KeyUtil.IdUtils() + suffix;

        // 转码之后的图片
        String paramImgFile = Base64.encode(file.getBytes());

        // 设置body
        HashMap<String, Object> map = new HashMap<>();
        map.put("content",paramImgFile);
        map.put("message","commit a blog file");
        String mapStr = JSONUtil.toJsonStr(map);


        Map<String, Object> uploadMap = new HashMap<>();
        Map<String, Object> succMap = new HashMap<>();
        uploadMap.put("succMap",succMap);

        try {
            // 发送请求
            String put = HttpRequest.put(this.url+fileName)
                    .header("Authorization","token  "+this.token)
                    .header("Accept","application/json")
                    .header("Content-Type","application/json")
                    .body(mapStr)
                    .timeout(15000) // 10s
                    .execute()
                    .body();

            // 请求之后的地址
            // log.info("request url ={}",this.url+fileName);
            log.info("jsdelivrUrl url ={}",this.jsdelivrUrl+fileName);
            // 将加速访问的地址返回给前台

            succMap.put("name",uploadFileName);
            succMap.put("url",this.jsdelivrUrl+fileName);
            // succMap.put("url",this.jsdelivrUrl+fileName);

            uploadMap.put("errFiles",null);
            return R.uploadSuccess(uploadMap);
        } catch (HttpException e) {
            e.printStackTrace();
            uploadMap.put("errFiles",null);
            return R.uploadFail(uploadMap);
        }

    }


}
