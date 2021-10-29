package com.wuxin.blog.controller;

import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.ArchiveService;
import com.wuxin.blog.service.BlogService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:19
 * @Description:
 */
@Slf4j
@RestController
public class ArchiveController {

    @Autowired
    private ArchiveService archiveService;


    @GetMapping("/find/archive")
    public Result findArchive(@RequestBody PageVo pageVo){
        return R.ok(archiveService.findArchive(pageVo.getCurrent(),pageVo.getLimit()));
    }
}
