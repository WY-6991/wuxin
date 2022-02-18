package com.wuxin.blog.controller.admin.git;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.wuxin.blog.annotation.AccessLimit;
import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.exception.CustomException;
import com.wuxin.blog.mode.Github;
import com.wuxin.blog.pojo.blog.UploadPicture;
import com.wuxin.blog.pojo.system.GithubSetting;
import com.wuxin.blog.service.MySystemService;
import com.wuxin.blog.service.UploadPictureService;
import com.wuxin.blog.utils.KeyUtil;
import com.wuxin.blog.utils.result.Result;
import com.wuxin.blog.utils.string.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private MySystemService mySystemService;

    @Autowired
    private UploadPictureService uploadPictureService;


    /**
     * 图片上传
     *
     * @param file 上传文件
     * @return 文件地址
     */
    @AccessLimit(seconds = 60, limitCount = 10, msg = "操作频率过高！一分钟之后再试！")
    @RequiresRoles("root")
    @OperationLogger("上传图像")
    @PostMapping("/upload/user/avatar")
    public Result uploadImg(@RequestParam("file") MultipartFile file) throws IOException {
        return uploadFileUtil(file, 1);
    }

    /**
     * 文章图片上传，由于使用了vditor编辑器，返回图片对应的接口格式不同！所以新建了一个方法上传图片
     *
     * @param file files
     * @return url
     * @throws Exception error
     */
    @AccessLimit(seconds = 60, limitCount = 10, msg = "操作频率过高！一分钟之后再试！")
    @OperationLogger("上传图片")
    @PostMapping("/upload/blog/img")
    public Result uploadBlogImg(@RequestParam("file") MultipartFile file) throws Exception {
        return uploadFileUtil(file, 2);
    }


    @RequiresRoles("root")
    @OperationLogger("上传音频文件")
    @PostMapping("/upload/music")
    public Result uploadMusic(@RequestParam("file") MultipartFile file) throws Exception {
        return uploadFileUtil(file, 1);

    }

    @RequiresRoles("root")
    @OperationLogger("删除github仓库文件指定文件")
    @DeleteMapping("/delete/file")
    public Result deleteGithubFile(@RequestBody UploadPicture uploadPicture) throws Exception {
        if (StringUtils.isNull(uploadPicture)) {
            return Result.error("删除失败,获取不到github仓库文件记录");
        }
        try {
            Map<String, Object> map = new HashMap<>(8);
            map.put("sha", uploadPicture.getSha());
            map.put("message", "删除了:" + uploadPicture.getName());
            String mapStr = JSONUtil.toJsonStr(map);
            String body = HttpRequest.delete(uploadPicture.getRealUrl())
                    .header("Authorization", "token  " + getGithubSetting().getAccessToken())
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(mapStr)
                    .timeout(10000)
                    .execute()
                    .body();
            log.info("删除图片之后请求的信息:{}", body);
            // github仓库中文件删除失败之后,应当删除数据库中文件记录
            uploadPictureService.delete(uploadPicture.getId());
        } catch (HttpException e) {
            e.printStackTrace();
            // 如果有异常 抛出 说明文件删除失败
            throw new CustomException("文件删除失败！");
        }

        return Result.ok("删除成功");

    }


    @RequiresRoles("root")
    @OperationLogger("删除github文件")
    @PostMapping("/delete/records")
    public Result deleteFolder(@RequestBody Github github) throws Exception {

        String body;
        try {
            Map<String, Object> map = new HashMap<>(8);
            map.put("sha", github.getSha());
            map.put("message", github.getMessage());
            String mapStr = JSONUtil.toJsonStr(map);
            body = HttpRequest.delete(github.getUrl())
                    .header("Authorization", "token  " + getGithubSetting().getAccessToken())
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(mapStr)
                    .timeout(10000)
                    .execute()
                    .body();
            log.info("删除图片之后请求的信息:{}", body);
            return Result.ok(body);
        } catch (HttpException e) {
            e.printStackTrace();
            // 如果有异常 抛出 说明文件删除失败
            throw new CustomException("文件删除失败！");
        }


    }


    @OperationLogger("浏览github仓库文件")
    @GetMapping("/records")
    public Result viewGithub(@RequestParam(value = "url", required = false) String url) throws Exception {
        log.info("获取github仓库文件记录");

        try {
            // 判断github是否传入自定义url 如果url为null或者""
            if (StringUtils.isNull(url) || "".equals(url)) {
                url = getGithubSetting().getApi();

            }
            log.info("访问地址：url=>{}", url);
            log.info("update jsonObject={}", HttpRequest.get(url).header("accept", "application/vnd.github.v3+json").header("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Mobile Safari/537.36 Edg/97.0.1072.55").execute());
            return Result.ok("jsonObject");
        } catch (HttpException e) {
            e.printStackTrace();
            // 如果获取失败
            throw new CustomException("获取github仓库记录失败！");
        }


    }


    /**
     * 文件上传封装
     * 使用了hutool 提供的图片上传方法
     *
     * @param file 文件
     * @param type 1 表示图像 2 表示 文章 3表示音频文件
     * @return file message
     * @throws IOException error
     */
    public Result uploadFileUtil(MultipartFile file, Integer type) throws IOException {
        String originaFileName = file.getOriginalFilename();
        if (originaFileName == null) {
            return Result.error("上传失败！文件不存在！");
        }
        String suffix = originaFileName.substring(originaFileName.lastIndexOf("."));
        String fileName = KeyUtil.picId() + "/" + KeyUtil.IdUtils() + suffix;
        String paramImgFile = Base64.encode(file.getBytes());
        // 设置body
        Map<String, Object> map = new HashMap<>(8);
        map.put("content", paramImgFile);
        map.put("message", "文件名:" + originaFileName);
        String mapStr = JSONUtil.toJsonStr(map);

        try {
            // 上传图片到指定位置
            // String body = HttpRequest.put(this.url + fileName)
            String body = HttpRequest.put(getGithubSetting().getApi() + fileName)
                    // token 这里一定要有空格，否则会报错
                    .header("Authorization", "token  " + getGithubSetting().getAccessToken())
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(mapStr)
                    .timeout(10000)
                    .execute()
                    .body();


            // 处理文件上传结果
            handleUploadResult(body, fileName);

            log.info("上传地址 url ={}", getGithubSetting().getApi() + fileName);
            log.info("文件加速之后的访问地址 url ={}", getGithubSetting().getAccessApi() + fileName);

        } catch (HttpException e) {
            e.printStackTrace();
            throw new CustomException("图片上传失败");
        }

        if (type.equals(1)) {
            return Result.ok(getGithubSetting().getAccessApi() + fileName);
        }

        if (type.equals(2)) {
            Map<String, Object> uploadMap = new HashMap<>(8);
            Map<String, Object> succMap = new HashMap<>(8);
            uploadMap.put("succMap", succMap);
            uploadMap.put("errFiles", "error");
            succMap.put("name", fileName);
            succMap.put("url",getGithubSetting().getAccessApi() + fileName);
            uploadMap.put("errFiles", "");
            return Result.ok().put("data", succMap);
        }
        return Result.error("上传失败,文件上传超时！请重试！");


    }

    public void handleUploadResult(String body, String fileName) {
        // 将body转换为对象
        try {
            JSONObject obj = JSONUtil.parseObj(body);
            Object content = obj.get("content");
            JSONObject con = JSONUtil.parseObj(content);
            String sha = (String) con.get("sha");
            Integer size = (Integer) con.get("size");
            String gitUrl = (String) con.get("git_url");

            // 将文件上传记录存储起来
            UploadPicture uploadPicture = new UploadPicture();
            uploadPicture.setMessage("上传了" + fileName);
            uploadPicture.setName(fileName);
            uploadPicture.setGitUrl(fileName);
            uploadPicture.setRealUrl(getGithubSetting().getApi() + fileName);
            uploadPicture.setSize(size);
            uploadPicture.setSha(sha);
            uploadPicture.setUrl(getGithubSetting().getAccessApi() + fileName);
            uploadPicture.setGitUrl(gitUrl);
            uploadPictureService.add(uploadPicture);
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("文件记录保存失败！");
        }
    }


    /**
     * 读取github配置
     */
    public GithubSetting getGithubSetting(){
        return mySystemService.findGithubSetting(GithubSetting.GITHUB_REPOSITORY_ID);
    }




}
