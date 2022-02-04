package com.wuxin.blog.controller.admin.category;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.pojo.blog.Tag;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.service.TagService;
import com.wuxin.blog.enums.Message;
import com.wuxin.blog.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: wuxin001
 * @Date: 2021/09/01/15:30
 * @Description: 标签管理
 */
@Slf4j
@RestController
@RequestMapping("/admin/tag")
public class AdminTagController {

    @Resource
    private TagService tagService;


    /**
     * 标签搜索
     *
     * @return success
     */
    @OperationLogger("查看文章标签")
    @PostMapping("/list")
    public Result selectTag(@RequestBody PageVo pageVo) {
        return Result.ok(tagService.selectListByPage(pageVo.getCurrent(), pageVo.getLimit(), pageVo.getKeywords()));
    }


    /**
     * 添加标签
     *
     * @param tag tagDTO
     * @return success
     */
    @OperationLogger("添加文章标签")
    @RequiresRoles("root")
    @PostMapping("/add")
    public Result addTag(@RequestBody Tag tag) {
        Tag tagByName = tagService.findTagByName(tag.getName());
        if (tagByName != null) {
            return Result.error("该标签名已经存在");
        }
        tagService.add(tag);
        return Result.ok(Message.ADD_SUCCESS.getMessage());
    }

    /**
     * 修改标签名
     *
     * @param tag tagDTO
     * @return success
     */
    @OperationLogger("修改文章标签")
    @RequiresRoles("root")
    @PostMapping("/update")
    public Result updateTag(@RequestBody Tag tag) {
        Tag tagByName = tagService.findTagByName(tag.getName());
        if (tagByName != null) {
            return Result.error("该标签名已经存在");
        }
        tagService.update(tag);
        return Result.ok(Message.UPDATE_SUCCESS.getMessage());
    }


    /**
     * 修改标签颜色
     *
     * @param tag tagDTO
     * @return success
     */
    @OperationLogger("修改文章标签颜色")
    @RequiresRoles("root")
    @PostMapping("/update/color")
    public Result updateTagColor(@RequestBody Tag tag) {
        tagService.update(tag);
        return Result.ok(Message.UPDATE_SUCCESS.getMessage());
    }

    /**
     * 删除标签
     *
     * @param tagId tagID
     * @return success
     */
    @OperationLogger("删除文章标签")
    @RequiresRoles("root")
    @GetMapping("/del/{tagId}")
    public Result delTag(@PathVariable("tagId") Long tagId) {
        tagService.delete(tagId);
        return Result.ok(Message.DEL_SUCCESS.getMessage());
    }


}
