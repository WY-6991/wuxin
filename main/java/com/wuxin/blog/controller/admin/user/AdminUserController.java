package com.wuxin.blog.controller.admin.user;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.constant.GlobalConstant;
import com.wuxin.blog.exception.CustomException;
import com.wuxin.blog.exception.UserException;
import com.wuxin.blog.mode.UserAccount;
import com.wuxin.blog.mode.UserEmailPassword;
import com.wuxin.blog.mode.UserPass;
import com.wuxin.blog.pojo.blog.User;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.*;
import com.wuxin.blog.enums.Message;
import com.wuxin.blog.utils.result.Result;
import com.wuxin.blog.utils.security.MySecurityUtils;
import com.wuxin.blog.utils.string.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private UserService userService;
    @Autowired
    private HobbyService hobbyService;
    @Autowired
    private ChatUrlService chatUrlService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BlogService blogService;


    @Autowired
    private RedisService redisService;


    @Autowired
    private MailService mailService;


    @OperationLogger("获取登录用户信息")
    @GetMapping("/find/login/user/info")
    public Result getLoginUserInfo() {
        User loginUser = MySecurityUtils.getUser();
        Map<String, Object> map = new HashMap<>();
        List<String> roles = new ArrayList<>();
        roles.add(roleService.find(loginUser.getRoleId()).getRoleName());
        // 用户信息
        map.put("name", URLEncoder.encode(loginUser.getUsername()));
        map.put("nickname", URLEncoder.encode(loginUser.getNickname()));
        map.put("introduction", loginUser.getMotto());
        map.put("userId", loginUser.getUserId());
        map.put("roles", roles);
        map.put("avatar", loginUser.getAvatar());
        return Result.ok(map);

    }

    @OperationLogger("获取登录用户详情信息")
    @GetMapping("/detail")
    public Result findLoginUserDetail() {
        Long userId = MySecurityUtils.getUser().getUserId();
        Map<Object, Object> map = new HashMap<>();
        // 获取用户基本信息
        map.put("user", userService.findUserDetail(userId));
        // 获取用户用户拓展信息
        map.put("chatUrl", chatUrlService.findChatUrlByUserId(userId));
        // 获取用户爱好信息
        map.put("hobbies", hobbyService.selectListByUserId(userId));
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
        return Result.ok(userService.finUserByKeywords(pageVo));
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
        return Result.ok("修改成功！");
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
        Long loginUserId = MySecurityUtils.getUserId();
        if(!loginUserId.equals(GlobalConstant.ADMIN_USER_ID)){
            return Result.ok("删除失败,该操作仅对开发者开放！");
        }

        userService.delUser(userId);
        blogService.delBlogByUserId(userId);
        return Result.ok("用户删除成功！");

    }

    /**
     * 修改密码
     */
    @RequiresRoles("root")
    @OperationLogger("修改密码")
    @PostMapping("/update/pass")
    public Result updatePass(@RequestBody UserPass user) {
        User loginUser = MySecurityUtils.getUser();
        boolean b = userService.updatePass(loginUser.getUserId(), user);
        if (b) {
            return Result.ok("密码修改成功！");
        }
        return Result.error("密码修改失败！");
    }

    /**
     * 验证用户输入邮箱是否正确
     * @param email 邮箱
     * @return message
     */
    @RequiresRoles("root")
    @OperationLogger("修改密码.邮箱校验")
    @GetMapping("/update/pass/valid/email")
    public Result validUserEmail(@RequestParam("email") String email) {
        User user = MySecurityUtils.getUser();
        if (user.getEmail().equals(email)) {
            mailService.sendMimeMail(email);
            return Result.ok("验证码已发送到邮箱中了,请及时查收！");
        }
        return Result.error("邮箱校验不通过！");

    }

    /**
     * 检验验证码是否输入正确
     */
    @OperationLogger("通过邮箱获取验证码方式修改密码")
    @GetMapping("/update/pass/by/email")
    public Result validUserCode(@RequestBody UserEmailPassword user) {
        boolean hasCode = redisService.hHasKey(RedisKey.EMAIL_CODE, user.getEmail());
        if (!hasCode) {
            return Result.error("获取不到邮箱验证码！请重试！");
        }
        String code = (String) redisService.hget(RedisKey.EMAIL_CODE, user.getEmail());
        if (!code.equals(user.getCode())) {
            return Result.error("验证不通过！");
        }
        boolean b = userService.updatePasswordByEmail(user.getEmail(), user.getPassword());
        if (!b) {
            return Result.error("修改失败！");
        }
        return Result.ok("修改成功");
    }


    @OperationLogger(value = "查看了用户角色表")
    @PostMapping("/role/list")
    public Result selectUserRoleList(@RequestBody PageVo pageVo) {
        return Result.ok(userService.selectUserRoleList(pageVo));
    }


    @OperationLogger(value = "查看了用户角色表")
    @RequiresRoles("root")
    @PutMapping("/update/role")
    public Result updateUserRole(@RequestBody User user) {
        userService.updateUserRole(user);
        return Result.ok("修改成功！");
    }


}
