package com.wuxin.blog.controller.admin.archive;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.enums.BusinessType;
import com.wuxin.blog.pojo.blog.Archive;
import com.wuxin.blog.pojo.blog.ArchiveTitle;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.service.ArchiveService;
import com.wuxin.blog.service.ArchiveTitleService;
import com.wuxin.blog.utils.KeyUtil;
import com.wuxin.blog.utils.result.Result;
import com.wuxin.blog.utils.string.StringUtils;
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


    @OperationLogger(value = "添加归档",type = BusinessType.INSERT)
    @RequiresRoles("root")
    @PostMapping("/add")
    public Result addArchive(@RequestBody Archive archive) {
        //判断blog是否添加到归档中...
        Archive checkArchive = archiveService.findArchive(archive);
        if (StringUtils.isNotNull(checkArchive)) {
            return Result.error("添加失败,改博客已添加到归档类哦！");
        } else {
            // 根据当前时间生成一个titleId
            archive.setArchiveTitle(KeyUtil.getArchiveTitle());
            // 判断archiveTitle 是不是存在
            ArchiveTitle archiveTitle = archiveTitleService.selectArchiveTitle(archive.getArchiveTitle());
            if (StringUtils.isNull(archiveTitle)) {
                archiveTitleService.add(archive.getArchiveTitle());
            }
            // 归档添加
            archiveService.add(archive);
            return Result.ok("添加成功！");
        }

    }

    @OperationLogger(value = "修改归档",type = BusinessType.UPDATE)
    @RequiresRoles("root")
    @PostMapping("/update")
    public Result updateArchive(@RequestBody Archive archive) {
        archiveService.update(archive);
        return Result.ok("修改成功！");
    }


    @OperationLogger(value = "删除归档",type = BusinessType.DELETE)
    @RequiresRoles("root")
    @DeleteMapping("/del/{archiveId}")
    public Result delArchive(@PathVariable Long archiveId) {
        archiveService.delete(archiveId);
        return Result.ok("删除成功！");
    }


    @OperationLogger(value = "归档列表",type = BusinessType.SELECT)
    @PostMapping("/list")
    public Result delArchive(@RequestBody PageVo pageVo) {
        return Result.ok(archiveService.selectListByPage(pageVo.getCurrent(), pageVo.getLimit(),pageVo.getKeywords(),pageVo.getStart(),pageVo.getEnd()));
    }

}
