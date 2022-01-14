package com.wuxin.blog.controller.front.archive;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.service.ArchiveTitleService;
import com.wuxin.blog.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:19
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/archive")
public class ArchiveController {

    @Autowired
    private ArchiveTitleService archiveTitleService;


    @OperationLogger("归档")
    @GetMapping("/all")
    public Result findArchive(){
        return Result.ok(archiveTitleService.list());
    }


    @GetMapping("/list/{current}/{limit}")
    public Result findArchiveTitlePage(@PathVariable int current,@PathVariable int limit){
        return Result.ok(archiveTitleService.selectListByPage(current,limit));
    }

}
