package com.wuxin.blog.controller.front.user;


import cn.hutool.jwt.JWTUtil;
import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.constant.GlobalConstant;
import com.wuxin.blog.constant.HttpStatus;
import com.wuxin.blog.exception.UserException;
import com.wuxin.blog.mode.LoginBody;
import com.wuxin.blog.mode.LoginEmail;
import com.wuxin.blog.mode.LoginUser;
import com.wuxin.blog.mode.UserAccount;
import com.wuxin.blog.pojo.blog.User;
import com.wuxin.blog.service.MailService;
import com.wuxin.blog.service.RoleService;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.utils.result.Result;
import com.wuxin.blog.utils.security.MySecurityUtils;
import com.wuxin.blog.utils.string.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
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


@Slf4j
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MailService mailService;


    /**
     * @param user 前台获取的用户名和密码
     * @return 查询结果
     */
    @OperationLogger("登录")
    @PostMapping("/login")
    public Result userLogin(@RequestBody LoginBody user) throws UnsupportedEncodingException {
        // 处理前端接受数据 存在中文问题
        log.info("输入的用户名1:{}", user.getUsername());
        //解码
        // String username  = URLDecoder.decode(user.getUsername());
        String username = URLDecoder.decode(user.getUsername(), "utf-8");
        log.info("输入的用户名2:{}", username);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, user.getPassword());
        log.info("token=>" + token);
        // JWTUtil.createToken()
        try {
            subject.login(token);
            Map<String, Object> map = new HashMap<>();
            map.put("username", URLEncoder.encode(token.getUsername()));
            map.put("token", token);
            log.info("登录的用户信息username ={},token={}", user.getUsername(), token);
            return Result.ok(map);
            // return Result.ok().put("username",token.getUsername()).put("token",token);
        } catch (UnknownAccountException e) {
            return Result.error("用户名错误！");
        } catch (IncorrectCredentialsException e) {
            return Result.error("密码错误！");
        } catch (LockedAccountException lae) {
            return Result.error("账户已锁定！");
        } catch (AuthenticationException ae) {
            return Result.error("用户名或密码不正确！");
        }

    }


    @OperationLogger("获取登录用户信息")
    @GetMapping("/find/login/user/info")
    public Result getLoginUserInfo() {
        User loginUser = MySecurityUtils.getUser();
        if (StringUtils.isNull(loginUser)) {
            throw new UserException("用户未登录！");
        }
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


    /**
     * 获取验证码
     *
     * @param email   email
     * @param session session
     * @return code
     */
    @OperationLogger("邮箱验证码登录")
    @GetMapping("/login/get/email/code")
    public Result loginEmail(@RequestParam(value = "email", required = false) String email, HttpSession session) {
        // 查看用户邮箱是否存在
        User user = userService.findUserByEmail(email);
        if (user == null) {
            return Result.error("邮箱不存在，请重新输入！");
        }
        try {
            mailService.sendMimeMail(email, session);
            String code = (String) session.getAttribute(GlobalConstant.USER_VALID_CODE);
            return Result.ok(code);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("error！");
        }

    }


    /**
     * 通过邮箱方式登录
     *
     * @param email   email
     * @param code    code
     * @param session session
     * @return message
     */
    @OperationLogger("获取登录验证码")
    @GetMapping("/login/to/email/code")
    public Result loginToEmail(@RequestParam("email") String email, @RequestParam("code") String code, HttpSession session) {
        log.info("用户输入验证码和邮箱 email:{},code:{}", email, code);
        String sessionEmail = (String) session.getAttribute(GlobalConstant.USER_VALID_EMAIL);
        String sessionCode = (String) session.getAttribute(GlobalConstant.USER_VALID_CODE);
        // 判断用户输入的信息和session信息是否一致 session的有效时间10分钟
        if (email.equals(sessionEmail) && code.equals(sessionCode)) {
            // 将用户信息从数据库中获取到session中
            User sessionUser = userService.findUserByEmail(sessionEmail);
            session.setAttribute(GlobalConstant.USER_LOGIN_SESSION, sessionUser);
            Map<String, Object> map = new HashMap<>();
            map.put("username", sessionUser.getUsername());
            return Result.ok(map);
        }
        return Result.error("验证失败！");

    }


    /**
     * 注册
     *
     * @param user 注册用户
     * @return success
     */
    @OperationLogger("根据blogId获取全部评论")
    @PostMapping(value = "/register")
    public Result registerUser(@RequestBody User user, HttpSession session) {
        if (StringUtils.isNull(user)) {
            return Result.error("获取不到用户信息");
        }
        //用户名是否注册
        User userByUsername = userService.findUserByUsername(user.getUsername());
        if (StringUtils.isNull(userByUsername)) {
            return Result.error("该用户名已经被注册了");
        }
        // 邮箱是否注册
        User user1 = userService.findUserByEmail(user.getEmail());
        if (StringUtils.isNull(user1)) {
            return Result.error("该邮箱已经被注册了");
        }
        // 验证码是否过期
        String code = (String) session.getAttribute(GlobalConstant.USER_VALID_CODE);
        String email = (String) session.getAttribute(GlobalConstant.USER_VALID_EMAIL);
        if (user.getEmail().equals(email) && user.getCode().equals(code)) {
            Long i = userService.addUser(user);
            if (i == 0) {
                return Result.error("注册失败！请检测验证码是否过期或者其他信息有错误");
            }
            return Result.ok("注册成功！");
        }
        return Result.error("注册失败！用户信息错误或验证码过期");

    }


    /**
     * 注册时候获取验证码
     *
     * @param user    user
     * @param session session有效时间位10分钟
     * @return code
     */
    @OperationLogger("通过邮箱方式注册")
    @PostMapping("/register/email")
    public Result sendEmail(@RequestBody LoginEmail user, HttpSession session) {
        // 邮箱是否已经注册
        User userByEmail = userService.findUserByEmail(user.getEmail());
        log.info("email user ={}", userByEmail);
        if (userByEmail != null) {
            return Result.error("邮箱已经注册了");
        }
        try {
            mailService.sendMimeMail(user.getEmail(), session);
            String code = (String) session.getAttribute(GlobalConstant.USER_VALID_CODE);
            // 获取用户token信息
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("error！");
        }

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


    @GetMapping("/login/user/test")
    public Result loginUser(){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if(StringUtils.isNull(principal)){
            throw new UserException("用户未登录！");
        }
        return Result.ok(principal);
    }


}
