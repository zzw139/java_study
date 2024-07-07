package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {

        //查询用户
        User u = userService.findByUserName(username);
        if (u == null) {
            //没有占用
            //注册
            userService.register(username, password);
            return Result.success();
        } else {
            //占用
            return Result.error("用户名已被占用");
        }
    }

    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        User loginUser = userService.findByUserName(username);
        if (loginUser == null) {
            return Result.error("用户名不存在");
        }


        //判断密码是否正确  loginUser对象中的password是密文
        if(Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            Map<String,Object> claims=new HashMap<>();
            claims.put("username",loginUser.getUsername());
            claims.put("id",loginUser.getId());
            String token= JwtUtil.genToken(claims);
            //redis
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            ops.set(token,token,1, TimeUnit.HOURS);//这个token是token就解决了唯一性和防止窃取的功能
            return Result.success(token);
        }
        return Result.error("密码错误1");
    }
    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/) {
//        Map<String,Object> map= JwtUtil.parseToken(token);
//        String username = (String) map.get("username");
        Map<String,Object> map =ThreadLocalUtil.get();
        String username=(String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }
   // 基本信息
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam   String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token){

       String old_pwd=params.get("old_pwd");
       String new_pwd=params.get("new_pwd");
       String re_pwd=params.get("re_pwd");

       //判断合法性
        if(!StringUtils.hasLength(old_pwd)||!StringUtils.hasLength(new_pwd)||!StringUtils.hasLength(re_pwd)){
            return Result.error("参数不合法");
        }
        //获得密码
         Map<String,Object> map=ThreadLocalUtil.get();
         String username=(String) map.get("username");
         User LoginUser=userService.findByUserName(username);
         //判断旧密码是否正确
         if (!Md5Util.getMD5String(old_pwd).equals(LoginUser.getPassword())){
             return Result.error("旧密码错误");
         }
         //两次是否相同
        if (!new_pwd.equals(re_pwd)){
            return Result.error("两次密码不一致");
        }
       userService.updatePwd(new_pwd);
        //删除redis中对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);

        return Result.success();
    }
    }

