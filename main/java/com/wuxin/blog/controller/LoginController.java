package com.wuxin.blog.controller;


import com.wuxin.blog.service.LoginLogService;
import com.wuxin.blog.pojo.User;
import com.wuxin.blog.service.MailService;
import com.wuxin.blog.service.RoleService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @Autowired
    private LoginLogService loginLogService;


    /**
     * @param user 前台获取的用户名和密码
     * @return 查询结果
     */
    @PostMapping("/login")
    public Result userLogin(@RequestBody User user,HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        log.info("token=>" + token);
        try {
            subject.login(token);//执行登录的方法，如果没有异常就说明ok了
            log.info("登录的用户信息username ={},password={},token={}", user.getUsername(), user.getPassword(), token);
            Map<String, Object> map = new HashMap<>();
            map.put("username",token.getUsername());
            return R.ok(map);
        } catch (UnknownAccountException e) { //用户名不存在
            return R.error("用户名错误！");
        } catch (IncorrectCredentialsException e) {//密码不存在
            return R.error("密码错误！");
        } catch (LockedAccountException lae) {
            return R.error("账户已锁定！");
        } catch (ExcessiveAttemptsException eae) {
            return R.error("用户名或密码错误次数过多！");
        } catch (AuthenticationException ae) {
            return R.error("用户名或密码不正确！");
        }

    }



    // private LoginLog handleLog(HttpServletRequest request, boolean status, String description) {
    //     String username = currentUsername.get();
    //     currentUsername.remove();
    //     String ip = IpAddressUtils.getIpAddress(request);
    //     String userAgent = request.getHeader("User-Agent");
    //     LoginLog log = new LoginLog(username, ip, status, description, userAgent);
    //     return log;
    // }





    @GetMapping("/login/user/info/username")
    public Result getLoginUserInfo(@RequestParam("username") String username,HttpSession session) {
        if(username.isEmpty()) return R.error("获取不到用户信息");
        Map<String, Object> map = new HashMap<>();
        List<String> strings = new ArrayList<>();
        log.info("login/user/info 请求的用户信息 username={}",username);
        User user = (User) session.getAttribute(GlobalConstant.USER_LOGIN_SESSION);
        if(user == null) return R.error("未登录");
        User userByUsername = userService.findUserByUsername(username);
        log.info("登录之后的session中用户信息={}",user);
        strings.add(roleService.findRoleById(userByUsername.getRoleId()).getRoleName());
        map.put("roles",strings);
        map.put("nickname",userByUsername.getNickname());
        map.put("introduction",userByUsername.getMotto());
        map.put("avatar",userByUsername.getAvatar());
        map.put("name",userByUsername.getUsername());
        map.put("user",userByUsername);
        return R.ok(map);

    }


    @PostMapping("/login/to/email")
    public Result loginToEmail(@RequestBody User user, HttpSession session) {
        System.out.println(user);
        System.out.println(user.getEmail() + "," + user.getCode());
        String code = (String) session.getAttribute("code");
        String email = (String) session.getAttribute("email");
        System.out.println("session code=>" + code);


        // 判断用户输入的信息和session信息是否一致 session的有效时间10分钟
        if (user.getEmail().equals(email) && user.getCode().equals(code)) {
            // 将用户信息从数据库中获取到session中
            User sessionUser = userService.findUserByEmail(email);
            session.setAttribute(GlobalConstant.USER_LOGIN_SESSION, sessionUser);
            return R.ok("验证通过!");
        }
        return R.error("验证失败！");

    }


    /**
     * 注册
     *
     * @param user 注册用户
     * @return success
     */
    @PostMapping(value = "/register")
    public Result addUser(@RequestBody User user, HttpSession session) {
        if (user == null) return R.error("error");
        //用户名是否注册
        User userByUsername = userService.findUserByUsername(user.getUsername());
        if (userByUsername != null) {
            return R.error("该用户名已经被注册了");
        }
        // 邮箱是否注册
        User user1 = userService.findUserByEmail(user.getEmail());
        if (user1 != null) {
            return R.error("该邮箱已经被注册了");
        }
        // 验证码是否过期
        String code = (String) session.getAttribute("code");
        String email = (String) session.getAttribute("email");
        if (user.getEmail().equals(email) && user.getCode().equals(code)) {
            int i = userService.addUser(user);
            if (i == 0) {
                return R.error("注册失败！请检测验证码是否过期或者其他信息有错误");
            }
            return R.ok("success");
        }
        return R.error("注册失败！请检测验证码是否过期或者其他信息有错误");

    }


    /**
     * 登录获取验证码
     *
     * @param user    user
     * @param session session
     * @return code
     */
    @PostMapping("/login/email")
    public Result loginEmail(@RequestBody User user, HttpSession session) {

        // 查看用户邮箱是否存在
        User userByEmail = userService.findUserByEmail(user.getEmail());
        if (userByEmail == null) {
            return R.error("邮箱不存在，请检查");
        }
        try {
            mailService.sendMimeMail(user.getEmail(), session);
            String code = (String) session.getAttribute("code");
            return R.ok(code);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("error！");
        }

    }


    /**
     * 注册时候获取验证码
     *
     * @param user    user
     * @param session session有效时间位10分钟
     * @return code
     */
    @PostMapping("/register/email")
    public Result sendEmail(@RequestBody User user, HttpSession session) {

        // 邮箱是否已经注册
        User userByEmail = userService.findUserByEmail(user.getEmail());
        log.info("email user ={}", userByEmail);
        if (userByEmail != null) {
            return R.error("邮箱已经注册了");
        }
        try {
            mailService.sendMimeMail(user.getEmail(), session);
            String code = (String) session.getAttribute("code");
            return R.ok(code);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("error！");
        }

    }


    /**
     *退出
     *
     * @return result
     */
    @GetMapping("/logout")
    public Result loginOut() {
        log.info("logout 执行...");
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return R.ok("注销成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("注销失败！");
        }
    }


    @GetMapping("/session/user/info")
    public Result sessionUser(HttpSession session) {
        Object attribute = session.getAttribute(GlobalConstant.USER_LOGIN_SESSION);
        return R.ok(attribute);
    }


}
