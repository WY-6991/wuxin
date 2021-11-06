package com.wuxin.blog.controller.user;


import com.wuxin.blog.pojo.User;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.util.GlobalConstant;
import com.wuxin.blog.util.result.Result;
import com.wuxin.blog.util.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/***
* @Description: 用户
* @Author: wuxin001
* @Date: 2021/8/23 0023
*/
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;


    /**
     * 删除用户信息
     * @param userid 用户id
     * @return user
     */
    @GetMapping("/del/{userid}")
    public Result delUser(@PathVariable("userid") Long userid){
        int i = userService.delUser(userid);
        if(i==1){
            return R.ok("删除成功！");
        }
        return R.error("删除失败");
    }


    /**
     * 修改用户信息
     * 用户名、邮箱、和手机号不能重复
     * @param user 接受用户实体
     * @return result
     */
    @PostMapping("/update")
    public Result updateUser(@RequestBody User user){
        // 从数据库中获取该用户信息和接受用户信息对比
        User sqlUser = userService.finUserById(user.getUserId());
        // 检查用户是重复
        User userByUsername = userService.findUserByUsername(user.getUsername());
        if(userByUsername!=null){
            // 用户名是否修改
            if(!sqlUser.getUsername().equals(userByUsername.getUsername())){
                return R.error("该用户已存在！");
            }
        }
        // 邮箱是否重复
        User userByEmail = userService.findUserByEmail(user.getEmail());
        if(userByEmail!=null){
            // 邮箱是否修改
            if(!sqlUser.getEmail().equals(userByEmail.getEmail())){
                return R.error("该邮箱已存在！");
            }
        }
        // 用户名是否重复
        User userByPhone = userService.finUserByPhone(user.getPhone());
        if(userByPhone!=null){
            // 判断用户是否修改
            if(!sqlUser.getPhone().equals(userByPhone.getPhone())){
                return R.error("该邮箱已存在！");
            }
        }
        // 修改用户信息
        int i = userService.updateUser(user);
        if(i==1){
            return R.ok("修改成功！");
        }
        return R.error("修改失败，未知错误！");

    }

    /**
     * 分页查看用户
     * @param current 页码
     * @param size 大小
     * @return list
     */
    @GetMapping("/list/{current}/{size}")
    public Result findUser(@PathVariable("current") Integer current,@PathVariable("size") Integer size){
        return R.ok(userService.findUser(current,size));
    }


    /**
     * 我的信息
     * @return userInfo
     */
    @GetMapping("/admin/info")
    public Result findAdminUserInfo(){
        return R.ok(userService.findAdminUserInfo(GlobalConstant.adminUserId));
    }


    /**
     * 统计用户总数
     * @return result.count
     */
    @GetMapping("/count")
    public Result countUser(){
        return R.ok(userService.countUser());
    }



}
