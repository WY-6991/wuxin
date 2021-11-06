package com.wuxin.blog.controller.admin.user;


import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.CommentUserService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/comment/user")
public class AdminCommentUserController {


    @Autowired
    private CommentUserService commentUserService;

    @PostMapping("list")
    public Result getCommentUserList(@RequestBody PageVo pageVo){
        return R.ok(commentUserService.findCommentUser(pageVo.getCurrent(), pageVo.getLimit(), pageVo.getKeywords()));
    }
}
