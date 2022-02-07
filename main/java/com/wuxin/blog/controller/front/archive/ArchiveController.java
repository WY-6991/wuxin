package com.wuxin.blog.controller.front.archive;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.annotation.VisitLogger;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.service.ArchiveService;
import com.wuxin.blog.service.ArchiveTitleService;
import com.wuxin.blog.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @Autowired
    private ArchiveService archiveService;


    @VisitLogger(value = "访问我的归档",name = "归档页")
    @PostMapping("/list")
    public Result findArchive(@RequestBody PageVo pageVo){
        Result result = Result.ok();
        result.put("page",archiveTitleService.selectListByPage(pageVo.getCurrent(),pageVo.getLimit(), pageVo.getKeywords()));
        result.put("count",archiveService.selectCount());
        return result;
    }


}
