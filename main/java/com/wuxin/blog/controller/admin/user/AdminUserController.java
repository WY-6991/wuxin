package com.wuxin.blog.controller.admin.user;

import com.wuxin.blog.pojo.User;
import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.BlogService;
import com.wuxin.blog.service.BlogCommentService;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.util.GlobalConstant;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Author: wuxin001
 * @Date: 2021/09/01/15:34
 * @Description: 用户管理
 */
@Slf4j
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    private UserService userService;


    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogCommentService blogCommentService;


    @PostMapping("/list")
    public Result findUser(@RequestBody PageVo pagevo) {
        log.info("分页查看用户信息 current={}，size={}", pagevo.getCurrent(), pagevo.getLimit(), pagevo.getKeywords());
        // return R.ok(userService.findUser(current, size));
        return R.ok(userService.finUserByKeywords(pagevo.getCurrent(), pagevo.getLimit(), pagevo.getKeywords()));
    }

    @PostMapping("/update")
    public Result updateUser(@RequestBody User user) {
        log.info("修改用户信息 user={}", user);
        User updateUser = userService.finUserById(user.getUserId());


        try {

            if (!updateUser.getUsername().equals(user.getUsername())) {

                if (!(userService.findUserByUsername(user.getUsername()) == null)) {
                    return R.error("该用户名已存在！");
                }

            }
            if (!updateUser.getEmail().equals(user.getEmail())) {
                if (!(userService.findUserByEmail(user.getEmail()) == null)) {
                    return R.error("该邮箱已存在");
                }
            }
            if (!updateUser.getPhone().equals(user.getPhone())) {
                if (!(userService.finUserByPhone(user.getPhone()) == null)) {
                    return R.error("该手机号已存在");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok(userService.updateUser(user));
    }

    @GetMapping("/del")
    public Result delUser(@RequestParam("userId") Long userId) {
        log.info("删除用户信息 userId={}", userId);
        if (userService.delUser(userId) == 1) {
            blogService.delBlogByUserId(userId);
            return R.ok("删除成功！");
        }
        return R.error("删除失败！");

    }


    /**
     * 验证密码
     *
     * @return success
     */
    @GetMapping("/repass")
    public Result repass(@RequestParam("password") String password) {
        Subject subject = SecurityUtils.getSubject();
        User principal = (User) subject.getPrincipal();
        UsernamePasswordToken token = new UsernamePasswordToken(principal.getUsername(), password);
        try {
            subject.login(token);//执行登录的方法，如果没有异常就说明ok了
            return R.ok("验证通过！");
        } catch (IncorrectCredentialsException e) {//密码不存在
            return R.error("密码输入错误,请重新输入！");
        } catch (LockedAccountException lae) {
            return R.error("账户已锁定！");
        } catch (ExcessiveAttemptsException eae) {
            return R.error("用户名或密码错误次数过多！");
        } catch (AuthenticationException ae) {
            return R.error("用户名或密码不正确！");
        }

    }

    /**
     * 修改密码
     *
     * @param username 用户名
     * @param password 密码
     * @param session  session
     * @return success
     */
    @GetMapping("/update/pass")
    public Result updatePass(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             HttpSession session) {
        User loginUser = (User) session.getAttribute(GlobalConstant.USER_LOGIN_SESSION);
        boolean b = userService.updatePass(username, password, loginUser);
        if (b) return R.ok("密码修改成功！");
        return R.error("密码修改失败！");
    }
}
