package com.wuxin.blog.aop;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONSupport;
import cn.hutool.json.JSONUtil;
import com.wuxin.blog.pojo.UploadPicture;
import com.wuxin.blog.service.UploadPictureService;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class UploadPictureAop {


    @Autowired
    private UploadPictureService uploadPictureService;

    // 指定切入点
    @Pointcut("execution(* com.wuxin.blog.controller.admin.common.GitHubController.uploadBlogImg(..))")
    public void log() {
        log.info("图片上传中....");
    }

    @Before("log()")
    public void doLoginBefore() {


        log.info("图片上传之前");

    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void loginAfterReturn(Result result) {
        addUploadPicture(result);
    }


    @After("log()")
    public void doLoginAfter() {
        log.info("图片上传结束之后。。。");
    }

    public void addUploadPicture(Result result) {
        UploadPicture uploadPicture = new UploadPicture();
        // 如果上传成功！
        if (result.getCode() == 200) {
            JSON parse = JSONUtil.parse(result.getData());
            Object succMap = parse.getByPath("succMap");
            JSON p = JSONUtil.parse(succMap);
            log.info("p url:{},name:{}", p.getByPath("url"), p.getByPath("name"));
            String name = (String) p.getByPath("name");
            String url = (String) p.getByPath("url");
            uploadPicture.setUrl(url);
            uploadPicture.setName(name);
            uploadPicture.setMessage("上传了一张图片");
            uploadPictureService.addUploadPicture(uploadPicture);
            log.info("上传成功！！！");

        } else {
            log.info("上传失败！！！");
        }


    }


}
