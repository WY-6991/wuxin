package com.wuxin.blog.controller.admin;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.wuxin.blog.util.GiteeImg.GiteeUserImg;
import com.wuxin.blog.util.KeyUtil;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wuxin001
 * @Date: 2021/09/16/17:41
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/gitee")
public class GiteeController {

    /**
     * 上传图片
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload/user/img")
    @ResponseBody
    public Result uploadImg(@RequestParam("file") MultipartFile file) throws Exception {
        log.info("请求成功");
        String originaFileName = file.getOriginalFilename();
        //上传图片不存在时
        if(originaFileName == null){
            return R.error("上传失败！图片文件不存在！");
        }

        String suffix = originaFileName.substring(originaFileName.lastIndexOf("."));
        //设置图片名字
        String fileName = "/user/"+KeyUtil.IdUtils() + "/" + KeyUtil.PicId() + suffix;

        String paramImgFile = Base64.encode(file.getBytes());
        //设置转存到Gitee仓库参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", GiteeUserImg.ACCESS_TOKEN);
        paramMap.put("message", GiteeUserImg.ADD_MESSAGE);
        paramMap.put("content", paramImgFile);

        //转存文件路径
        String targetDir = GiteeUserImg.PATH + fileName;
        //设置请求路径
        String requestUrl = String.format(GiteeUserImg.CREATE_REPOS_URL, GiteeUserImg.OWNER,
                GiteeUserImg.REPO_NAME, targetDir);
        log.info("请求Gitee仓库路径:{}",requestUrl);

        String resultJson = HttpUtil.post(requestUrl, paramMap);
        JSONObject jsonObject = JSONUtil.parseObj(resultJson);
        //表示操作失败
        if (jsonObject.getObj("commit") == null) {
            return R.error("提交失败！");

        }
        JSONObject content = JSONUtil.parseObj(jsonObject.getObj("content"));
        return R.ok("上传成功!图片访问地址:"+content.getObj("download_url"));
    }

    /**
     * 删除图片
     * @param imgPath 图片路径
     * @return
     * @throws Exception
     */
    @DeleteMapping("/del/img")
    @ResponseBody
    public Result delImg(@RequestParam(value = "imgPath") String imgPath) throws Exception {
        //路径不存在不存在时
        if(imgPath == null || imgPath.trim().equals("")){
            return R.error("上传失败！图片文件不存在！");
        }
        String path = imgPath.split("master/")[1];
        //上传图片不存在时
        if(path == null || path.trim().equals("")){
            return R.error("上传失败！图片文件不存在！");
        }
        //设置请求路径
        String requestUrl = String.format(GiteeUserImg.GET_IMG_URL, GiteeUserImg.OWNER,
                GiteeUserImg.REPO_NAME, path);
        log.info("请求Gitee仓库路径:{}",requestUrl);

        //获取图片所有信息
        String resultJson = HttpUtil.get(requestUrl);

        JSONObject jsonObject = JSONUtil.parseObj(resultJson);
        if (jsonObject.isEmpty()) {
            log.error("Gitee服务器未响应,message:{}",jsonObject);
            return R.create(500,"服务器未响应！");
        }
        //获取sha,用于删除图片
        String sha = jsonObject.getStr("sha");
        //设置删除请求参数
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("access_token", GiteeUserImg.ACCESS_TOKEN);
        paramMap.put("sha", sha);
        paramMap.put("message", GiteeUserImg.DEl_MESSAGE);
        //设置删除路径
        requestUrl = String.format(GiteeUserImg.DEL_IMG_URL, GiteeUserImg.OWNER,
                GiteeUserImg.REPO_NAME, path);
        log.info("请求Gitee仓库路径:{}",requestUrl);
        //删除文件请求路径
        resultJson = HttpRequest.delete(requestUrl).form(paramMap).execute().body();
        HttpRequest.put(requestUrl).form(paramMap).execute().body();
        jsonObject = JSONUtil.parseObj(resultJson);
        //请求之后的操作
        if(jsonObject.getObj("commit") == null){
            log.error("Gitee服务器未响应,message:{}",jsonObject);
            return R.create(500,"服务器未响应！");

        }
        return R.ok("删除成功");
    }



    /**
     * 上传图片
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload/blog/img")
    @ResponseBody
    public Result uploadBlogImg(@RequestParam("file") MultipartFile file) throws Exception {
        log.info("请求成功");
        String originaFileName = file.getOriginalFilename();
        //上传图片不存在时
        if(originaFileName == null){
            return R.error("上传失败！图片文件不存在！");
        }

        String suffix = originaFileName.substring(originaFileName.lastIndexOf("."));
        //设置图片名字
        String fileName = "/blog/"+KeyUtil.IdUtils() + "/" + KeyUtil.PicId() + suffix;

        String paramImgFile = Base64.encode(file.getBytes());
        //设置转存到Gitee仓库参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", GiteeUserImg.ACCESS_TOKEN);
        paramMap.put("message", GiteeUserImg.ADD_MESSAGE);
        paramMap.put("content", paramImgFile);

        //转存文件路径
        String targetDir = GiteeUserImg.PATH + fileName;
        //设置请求路径
        String requestUrl = String.format(GiteeUserImg.CREATE_REPOS_URL, GiteeUserImg.OWNER,
                GiteeUserImg.REPO_NAME, targetDir);
        log.info("请求Gitee仓库路径:{}",requestUrl);

        String resultJson = HttpUtil.post(requestUrl, paramMap);
        JSONObject jsonObject = JSONUtil.parseObj(resultJson);
        //表示操作失败
        if (jsonObject.getObj("commit") == null) {
            return R.error("提交失败！");

        }
        JSONObject content = JSONUtil.parseObj(jsonObject.getObj("content"));
        return R.ok("上传成功!图片访问地址:"+content.getObj("download_url"));
    }
}
