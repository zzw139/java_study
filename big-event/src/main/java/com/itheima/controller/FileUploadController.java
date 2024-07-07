package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.utils.AliOssUtil;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {

        //把文件的内容存储到本地磁盘上
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);
        //优化获取用户M名字然后加名字
         Map<String, String> map = ThreadLocalUtil.get();
        String username = map.get("username");
        //保证文件的名字是唯一的,从而防止文件覆盖
        String filename = username+UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        String down_url="E:\\ai_study\\code\\vue\\big-event\\src\\assets\\";
        file.transferTo(new File(down_url+filename));
      //  String url = AliOssUtil.uploadFile(filename,file.getInputStream());
        return Result.success(down_url+filename);
    }
}
