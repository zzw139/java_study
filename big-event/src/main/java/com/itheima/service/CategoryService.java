package com.itheima.service;

import com.itheima.pojo.Category;

import java.util.List;

public interface CategoryService {
    //新增
    void add(Category category);

    List<Category> list();

    Category findById(Integer id);

    void update(Category category);

    void deleteById(Integer id);
}
