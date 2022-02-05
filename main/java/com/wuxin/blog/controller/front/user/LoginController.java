package com.wuxin.blog.controller.front.user;


import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.constant.GlobalConstant;
import com.wuxin.blog.constant.HttpStatus;
import com.wuxin.blog.exception.UserException;
import com.wuxin.blog.mode.LoginBody;
import com.wuxin.blog.mode.UserEmailPassword;
import com.wuxin.blog.mode.UserRegister;
import com.wuxin.blog.pojo.blog.User;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.MailService;
import com.wuxin.blog.service.RoleService;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.utils.result.Result;
import com.wuxin.blog.utils.security.JWTUtils;
import com.wuxin.blog.utils.security.MySecurityUtils;
import com.wuxin.blog.utils.string.StringUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.StringValue;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private UserService userService;


    @Autowired
    private MailService mailService;


    @Autowired
    private RedisService redisService;

    /**
     * @param user 前台获取的用户名和密码
     * @return 查询结果
     */
    @OperationLogger("登录")
    @PostMapping("/login")
    public Result userLogin(@RequestBody LoginBody user) throws UnsupportedEncodingException {
        // 处理用户名是中文带来报错问题
        String username = URLDecoder.decode(user.getUsername(), "utf-8");
        UsernamePasswordToken token = new UsernamePasswordToken(username, user.getPassword());
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            log.info("登录的用户信息username ={},token={}", user.getUsername(), token);
            // 派发签名
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("username", username);
            String jwtToken = JWTUtils.createToken(hashMap);
            return Result.ok(jwtToken);
        } catch (LockedAccountException e) {
            return Result.error("账户已锁定！");
        } catch (AuthenticationException ae) {
            return Result.error("用户名或密码不正确！");
        }
    }


    /**
     * 登录用户获取验证码
     */
    @OperationLogger("邮箱验证码登录")
    @GetMapping("/login/get/email/code")
    public Result loginEmail(@RequestParam(value = "email") String email) {
        // 查看用户邮箱是否存在
        User user = userService.findUserByEmail(email);
        if (StringUtils.isNull(user)) {
            return Result.error("邮箱不存在，请重新输入！");
        }
        try {
            mailService.sendMimeMail(email);
            return Result.ok("验证码已成功发送到邮箱！请及时查收,有效期10分钟！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("验证码发送失败！");
        }

    }


    /**
     * 通过邮箱方式登录
     */
    @OperationLogger("通过邮箱和验证码方式登录")
    @PostMapping("/login/to/email/code")
    public Result loginToEmail(@RequestBody UserEmailPassword emailUser) {
        boolean b = redisService.hHasKey(RedisKey.EMAIL_CODE, emailUser.getEmail());
        if (!b) {
            return Result.error("获取不到验证码，请重试！");
        }
        String emailCode = (String) redisService.hget(RedisKey.EMAIL_CODE, emailUser.getEmail());
        if (!emailCode.equals(emailUser.getCode())) {
            return Result.error("验证码校验失败！请重试！");
        }

        User userByEmail = userService.findUserByEmail(emailUser.getEmail());

        // 这里还是走上面登录方法，不然后期获取不到登录用户信息!
        UsernamePasswordToken token = new UsernamePasswordToken(userByEmail.getUsername(), userByEmail.getPassword());
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            // 派发签名
            HashMap<String, String> map = new HashMap<>();
            map.put("username", userByEmail.getUsername());
            return Result.ok(JWTUtils.createToken(map));
        } catch (LockedAccountException e) {
            return Result.error("账户已锁定！");
        } catch (AuthenticationException ae) {
            return Result.error("用户名或密码不正确！");
        }


    }


    /**
     * 注册用户获取验证码
     */
    @OperationLogger("获取邮箱验证码")
    @PostMapping("/register/user/code")
    public Result sendEmail(@RequestBody UserRegister user) {
        User username = userService.findUserByUsername(user.getUsername());
        if (StringUtils.isNotNull(username)) {
            return Result.error("该用户名已注册！");
        }
        User email = userService.findUserByEmail(user.getEmail());
        if (StringUtils.isNotNull(email)) {
            return Result.error("该邮箱已注册！");
        }
        try {
            mailService.sendMimeMail(user.getEmail());
            return Result.ok("验证码已发送到你的邮箱了，请及时查收！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("邮箱发送失败！");
        }

    }


    /**
     * 注册
     */
    @OperationLogger("用户注册")
    @PostMapping(value = "/register/user")
    public Result registerUser(@RequestBody UserRegister user) {
        boolean b = redisService.hHasKey(RedisKey.EMAIL_CODE, user.getEmail());
        if (!b) {
            return Result.error("获取不到验证码，请重试！");
        }
        String emailCode = (String) redisService.hget(RedisKey.EMAIL_CODE, user.getEmail());
        if (!emailCode.equals(user.getCode())) {
            return Result.error("验证码校验失败！请重试！");
        }
        Long i = userService.addUser(UserRegister.getUser(user));
        if (i == 0) {
            return Result.error("注册失败！请检测验证码是否过期或者其他信息有错误");
        }
        return Result.ok("注册成功！");

    }


    /**
     * 用户访问需要admin/** 下的资源需要登录认证
     *
     * @return 提示用户需要登录
     */
    @OperationLogger("提示未登录的用户需要登录！")
    @GetMapping("/to/login")
    public Result toLogin() {
        return Result.error(HttpStatus.UNAUTHORIZED, "无法访问,请登录之后再试！");
    }

    /**
     * 用户访问需要admin/** 下的资源需要登录认证
     *
     * @return 提示用户需要登录
     */
    @OperationLogger("提示未登录的用户需要登录！")
    @GetMapping("/no/role")
    public Result noRole() {
        return Result.error(HttpStatus.UNAUTHORIZED, "没有权限访问");
    }


    /**
     * 退出
     */
    @OperationLogger("退出登录！")
    @GetMapping("/logout")
    public Result loginOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.ok("注销成功！");
    }


}
