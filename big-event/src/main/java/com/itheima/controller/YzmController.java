package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class YzmController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/yzm")
    public Result<String> yzm()
    {
        String yzm = CodeUtil.generateRandomCode();

        System.out.println("yzm = " + yzm);
        //存入redis
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("yzm",yzm,1, TimeUnit.MINUTES);//这个token是token就解决了唯一性和防止窃取的功能
        return Result.success(yzm);
    }
//    @PostMapping("/yzm")
//    public Result<String> Syzm(@RequestBody Map<String,String>  params)
//    {
//        String U_yzm = params.get("yzm");
//        System.out.println(U_yzm);
//        System.out.println(params);
//       //查看redis中是否有验证码
//        ValueOperations<String, String> ops = redisTemplate.opsForValue();
//        String R_yzm = ops.get("yzm");
//        if (R_yzm ==null){return Result.error("验证码已过期");}
//        if (R_yzm.equals(U_yzm)){return Result.success("成功");}
//         else  {return Result.error("验证码错误");}
//    }
    @PostMapping("/yzm")
    public Result<String> Syzm(String yzm )
    {
        System.out.println(yzm);
        //查看redis中是否有验证码
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String R_yzm = ops.get("yzm");
        if (R_yzm ==null){return Result.error("验证码已过期");}
        if (R_yzm.equals(yzm)){return Result.success("成功");}
        else  {return Result.error("验证码错误");}
    }

}