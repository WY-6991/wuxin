package com.wuxin.blog.controller.admin.archive;

import com.wuxin.blog.pojo.Archive;
import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.ArchiveService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:54
 * @Description: 归档
 */
@RestController
@RequestMapping("/admin/archive")
public class AdminArchiveController {

    @Autowired
    private ArchiveService archiveService;


    @PostMapping("/add")
    public Result addArchive(@RequestBody Archive archive){
        archiveService.addArchive(archive);
        return R.ok("添加成功！");
    }

    @PostMapping("/update")
    public Result updateArchive(@RequestBody Archive archive){
        archiveService.updateArchive(archive);
        return R.ok("修改成功！");
    }

    @PostMapping("/del")
    public Result delArchive(@RequestParam("archiveId")Long archiveId){
        archiveService.delArchive(archiveId);
        return R.ok("删除成功！");
    }


    @PostMapping("/list")
    public Result delArchive(@RequestBody PageVo pageVo){
        return R.ok( archiveService.findArchive(pageVo.getCurrent(),pageVo.getLimit()));
    }

}
