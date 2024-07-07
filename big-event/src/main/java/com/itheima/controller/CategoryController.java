package com.itheima.controller;


import com.itheima.pojo.Category;
import com.itheima.pojo.Result;
import com.itheima.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping//("/add")
    public Result add(@RequestBody @Validated Category category) {
        categoryService.add(category);
        return Result.success();
    }
    @GetMapping
    public Result<List<Category>> list() {
        List<Category> categoryList = (List<Category>) categoryService.list();
        return Result.success(categoryList);
    }

    @GetMapping("/detail")
    public Result<Category> findById(Integer id) {
        Category ct=categoryService.findById(id);
        return Result.success(ct);
    }

    //更新
    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category) {
        categoryService.update(category);
        return Result.success();
    }

    //根据id删除自己写
    @DeleteMapping
    public Result delete(Integer id) {
        categoryService.deleteById(id);
        return Result.success();
    }

}
