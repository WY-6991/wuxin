package com.wuxin.blog.controller.admin.archive;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.pojo.blog.Archive;
import com.wuxin.blog.pojo.blog.ArchiveTitle;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.service.ArchiveService;
import com.wuxin.blog.service.ArchiveTitleService;
import com.wuxin.blog.utils.KeyUtil;
import com.wuxin.blog.utils.result.Result;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:54
 * @Description: 归档
 */
@RestController
@RequestMapping("/admin/archive")
public class AdminArchiveController {

    @Resource
    private ArchiveService archiveService;

    @Resource
    private ArchiveTitleService archiveTitleService;


    @OperationLogger("添加归档")
    @PostMapping("/add")
    public Result addArchive(@RequestBody Archive archive) {
        //判断blog是否添加到归档中...
        Archive checkArchive = archiveService.findArchiveByBlogId(archive.getBlogId());
        // 根据当前时间生成一个titleId
        archive.setArchiveTitle(KeyUtil.getArchiveTitle());
        if (checkArchive != null) {
            return Result.error("添加失败,改博客已添加到归档类哦！");
        } else {
            // 判断archiveTitle 是不是存在
            ArchiveTitle archiveTitle = archiveTitleService.selectArchiveTitle(archive.getArchiveTitle());
            if (archiveTitle == null) {
                // 创建 archivetitle
                archiveTitleService.add(archive.getArchiveTitle());
            }
            archiveService.add(archive);
            return Result.ok("添加成功！");
        }

    }

    @OperationLogger("修改归档")
    @PostMapping("/update")
    public Result updateArchive(@RequestBody Archive archive) {
        archiveService.update(archive);
        return Result.ok("修改成功！");
    }

    @OperationLogger("删除归档")
    @RequiresRoles("root")
    @PostMapping("/del/{archiveId}")
    public Result delArchive(@PathVariable Long archiveId) {
        archiveService.delete(archiveId);
        return Result.ok("删除成功！");
    }


    @OperationLogger("获取全部归档信息")
    @PostMapping("/list")
    public Result delArchive(@RequestBody PageVo pageVo) {
        return Result.ok(archiveService.selectListByPage(pageVo.getCurrent(), pageVo.getLimit()));
    }

}
