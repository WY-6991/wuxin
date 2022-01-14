package com.wuxin.blog.controller.admin.user;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.constant.GlobalConstant;
import com.wuxin.blog.exception.CustomException;
import com.wuxin.blog.mode.UserAccount;
import com.wuxin.blog.pojo.blog.User;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.service.BlogService;
import com.wuxin.blog.service.ChatUrlService;
import com.wuxin.blog.service.HobbyService;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.enums.Message;
import com.wuxin.blog.utils.result.Result;
import com.wuxin.blog.utils.security.MySecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wuxin001
 * @Date: 2021/09/01/15:34
 * @Description: 用户管理
 */
@Slf4j
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    @Resource
    private UserService userService;
    @Resource
    private HobbyService hobbyService;
    @Resource
    private ChatUrlService chatUrlService;

    @Resource
    private BlogService blogService;


    @OperationLogger("获取登录用户详情信息")
    @GetMapping("/detail")
    public Result findLoginUserDetail() {
        Long userId = MySecurityUtils.getUser().getUserId();
        Map<Object, Object> map = new HashMap<>();
        // 获取用户基本信息
        map.put("user",userService.findUserDetail(userId));
        // 获取用户用户拓展信息
        map.put("chatUrl",chatUrlService.findChatUrlByUserId(userId));
        // 获取用户爱好信息
        map.put("hobbies",hobbyService.selectListByUserId(userId));
        return Result.ok(map);
    }


    /**
     * 用户列表
     *
     * @param pageVo DTO
     * @return 成功消息
     */
    @OperationLogger("查看用户列表")
    @PostMapping("/list")
    public Result findUser(@RequestBody PageVo pageVo) {
        return Result.ok(userService.finUserByKeywords(pageVo.getCurrent(), pageVo.getLimit(), pageVo.getKeywords()));
    }

    /**
     * 用户修改信息
     *
     * @param user user
     * @return 成功消息
     */
    @OperationLogger("修改用户信息")
    @RequiresRoles("root")
    @PostMapping("/update")
    public Result updateUser(@RequestBody User user) {
        log.info("修改用户信息 user={}", user);
        User updateUser = userService.finUserById(user.getUserId());
        try {
            if (!updateUser.getUsername().equals(user.getUsername())) {
                if ((userService.findUserByUsername(user.getUsername()) != null)) {
                    return Result.error("该用户名已存在！");
                }
            }

            if (!updateUser.getEmail().equals(user.getEmail())) {
                if ((userService.findUserByEmail(user.getEmail()) != null)) {
                    return Result.error("该邮箱已存在");
                }
            }
            if (!updateUser.getPhone().equals(user.getPhone())) {
                if ((userService.finUserByPhone(user.getPhone()) != null)) {
                    return Result.error("该手机号已存在");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("用户信息修改失败");
        }
        userService.updateUser(user);
        return Result.ok("修改成功！");
    }

    /**
     * 禁言或者解禁用户
     *
     * @param user user
     * @return 成功消息
     */
    @OperationLogger("修改用户状态")
    @RequiresRoles("root")
    @PostMapping("/update/status")
    public Result updateUserComment(@RequestBody User user) {
        userService.updateUser(user);
        return Result.ok(Message.UPDATE_SUCCESS.getMessage());
    }

    /**
     * 删除用户信息
     *
     * @param userId 删除用户信息id
     * @return 成功消息
     */
    @OperationLogger("删除用户")
    @RequiresRoles("root")
    @GetMapping("/del")
    public Result delUser(@RequestParam("userId") Long userId) {
        log.info("删除用户信息 userId={}", userId);
        userService.delUser(userId);
        blogService.delBlogByUserId(userId);
        return Result.ok(Message.DEL_SUCCESS.getMessage());

    }


    /**
     * 验证密码
     *
     * @return success
     */
    @OperationLogger("修改密码·密码验证")
    @GetMapping("/repass")
    public Result repass(@RequestParam("password") String password) {
        Subject subject = SecurityUtils.getSubject();
        User principal = (User) subject.getPrincipal();
        UsernamePasswordToken token = new UsernamePasswordToken(principal.getUsername(), password);
        try {
            //执行登录的方法，如果没有异常就说明ok了
            subject.login(token);
            return Result.ok("验证通过！");
        } catch (IncorrectCredentialsException e) {
            return Result.error("密码输入错误,请重新输入！");
        } catch (LockedAccountException lae) {
            return Result.error("账户已锁定！");
        } catch (ExcessiveAttemptsException eae) {
            return Result.error("用户名或密码错误次数过多！");
        } catch (AuthenticationException ae) {
            return Result.error("用户名或密码不正确！");
        }

    }

    /**
     * 修改密码
     *
     * @return success
     */
    @OperationLogger("修改密码")
    @GetMapping("/update/pass")
    public Result updatePass(@RequestBody UserAccount userAccount,
                             HttpSession session) {
        User loginUser = MySecurityUtils.getUser();
        // if(StringUtils.isNull(loginUser)){
        //     throw new
        // }

        boolean b = userService.updatePass(userAccount.getUsername(), userAccount.getPassword(), loginUser);
        if (b) {
            return Result.ok("密码修改成功！");
        }
        return Result.error("密码修改失败！");
    }

    /**
     * 验证用户输入邮箱是否正确
     *
     * @param email 邮箱
     * @return message
     */
    @OperationLogger("修改密码.邮箱校验")
    @GetMapping("/update/pass/valid/email")
    public Result validUserEmail(@RequestParam("email") String email, HttpSession session) {
        log.info("邮箱验证中...{}", email);
        User user = (User) session.getAttribute(GlobalConstant.USER_LOGIN_SESSION);
        if (user.getEmail().equals(email)) {
            return Result.ok("邮箱验证通过正在获取验证码...！");
        }
        return Result.error("邮箱输入错误");

    }

    /**
     * 检验验证码是否输入正确
     *
     * @param email   邮箱
     * @param code    code
     * @param session session
     * @return 成功消息
     */
    @OperationLogger("修改密码验证码校验")
    @GetMapping("/update/pass/valid/code")
    public Result validUserCode(@RequestParam("email") String email, @RequestParam("code") String code, HttpSession session) {
        String sessionCode = (String) session.getAttribute(GlobalConstant.USER_VALID_CODE);
        String sessionEmail = (String) session.getAttribute(GlobalConstant.USER_VALID_EMAIL);
        if (code.equals(sessionCode) && email.equals(sessionEmail)) {
            return Result.ok("验证通过");
        }
        return Result.error("验证不通过！");

    }

    /**
     * 实体用户
     * 需要参数 email,code,password
     *
     * @param user    user
     * @param session session
     * @return message
     */
    @OperationLogger("密码修改.邮箱修改")
    @PostMapping("/update/pass/by/email")
    public Result updatePassByEmail(@RequestBody User user, HttpSession session) {
        log.info("邮箱修改密码用户信息 email:{},code:{},password:{}", user.getEmail(), user.getCode(), user.getPassword());
        String email = (String) session.getAttribute(GlobalConstant.USER_VALID_EMAIL);
        String code = (String) session.getAttribute(GlobalConstant.USER_VALID_CODE);
        if (code.equals(user.getCode()) && email.equals(user.getEmail())) {
            User attribute = (User) session.getAttribute(GlobalConstant.USER_LOGIN_SESSION);
            boolean b = userService.updatePass(attribute.getUsername(), user.getPassword(), attribute);
            if (b) {
                //修改成功之后执行退出操作！
                return Result.ok("修改成功");
            }

            return Result.error("修改失败！");
        }
        return Result.error("邮箱和验证码不通过请重试！");


    }
}
