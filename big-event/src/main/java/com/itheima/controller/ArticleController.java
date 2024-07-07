package com.itheima.controller;

import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Result;
import com.itheima.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
/**
 * 处理与文章相关的HTTP请求。
 *
 * 该注解用于将处理方法与"/article"的URL模式关联起来，支持不同的HTTP请求方法（如GET、POST等）。
 * 通过这种方式，可以为每个HTTP操作（如查看、创建、更新、删除文章）提供专门的处理方法。
 */
@RequestMapping("/article")

public class ArticleController {
    @Autowired
    private ArticleService articleService;


     @PostMapping
    public Result add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success();
     }
    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state

    ) {
         PageBean<Article>  rs=articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(rs);
    }



}
