package com.wuxin.blog.controller.admin.category;

import com.wuxin.blog.pojo.Tag;
import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.TagService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2021/09/01/15:30
 * @Description: 标签管理
 */
@Slf4j
@RestController
@RequestMapping("/admin/tag")
public class AdminTagController {

    @Autowired
    private TagService tagService;


    /**
     * 标签搜索
     * @return success
     */
    @PostMapping("/list")
    public Result selectTag(@RequestBody PageVo pageVo) {
        log.info("默认执行搜索tag搜索内容：keywords={},current={},limit={}",pageVo.getKeywords(),pageVo.getCurrent(),pageVo.getLimit());
        return R.ok( tagService.findTagByPage(pageVo.getCurrent(),pageVo.getLimit(), pageVo.getKeywords()));
    }



    /**
     * 添加标签
     *
     * @param tag tagDTO
     * @return success
     */
    @PostMapping("/add")
    public Result addTag(@RequestBody Tag tag) {
        Tag tagByName = tagService.findTagByName(tag.getName());
        if (tagByName != null) return R.error("该标签名已经存在");
        if (tagService.addTag(tag) == 1) return R.ok("success");
        return R.error("error");
    }

    /**
     * 修改标签名
     *
     * @param tag tagDTO
     * @return success
     */
    @PostMapping("/update")
    public Result updateTag(@RequestBody Tag tag) {
        log.info("修改标签信息=>tag={},tagId=>{}", tag, tag.getTagId());
        Tag tagByName = tagService.findTagByName(tag.getName());
        if (tagByName != null) return R.error("该标签名已经存在");
        if (tagService.updateTag(tag)) return R.ok("success");
        return R.error("修改数据失败！");
    }


    /**
     * 修改标签颜色
     *
     * @param tag tagDTO
     * @return success
     */
    @PostMapping("/update/color")
    public Result updateTagColor(@RequestBody Tag tag) {
        log.info("修改标签颜色=>tag={},tagId=>{}", tag, tag.getTagId());
        if (tagService.updateTag(tag)) return R.ok("修改成功！");
        return R.error("修改数据失败！");
    }

    /**
     * 删除标签
     *
     * @param tagId tagID
     * @return success
     */
    @GetMapping("/del/{tagId}")
    public Result delTag(@PathVariable("tagId") Long tagId) {
        if (tagService.delTag(tagId) == 1) return R.ok("删除成功！");
        return R.error("删除失败！");
    }




}
