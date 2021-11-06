package com.wuxin.blog.aop;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.wuxin.blog.pojo.UploadPicture;
import com.wuxin.blog.service.UploadPictureService;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class UploadPictureAvatarAop {


    @Autowired
    private UploadPictureService uploadPictureService;

    // 指定切入点
    @Pointcut("execution(* com.wuxin.blog.controller.admin.common.GitHubController.uploadImg(..))")
    public void log() {
        log.info("图片上传中....");
    }

    @Before("log()")
    public void doLoginBefore() {
        log.info("图片上传之前");
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void loginAfterReturn(Result result) {
        log.info("user avatar image:{}",result);
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
            uploadPicture.setUrl(result.getResult().toString());
            uploadPicture.setName("avatar");
            uploadPicture.setMessage("上传了一张头像");
            uploadPictureService.addUploadPicture(uploadPicture);
            log.info("上传成功！！！");

        } else {
            log.info("上传失败！！！");
        }


    }


}
