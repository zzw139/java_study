package com.itheima.service.impl;
import com.itheima.mapper.CategoryMapper;
import com.itheima.pojo.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.ThreadLocalUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public void add(Category category) {
         Map<String,Object> map=ThreadLocalUtil.get();
        Integer id=(Integer)map.get("id");
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(id);
        //这边要补时间和用户
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {

        //这边要根据id显示
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id=(Integer)map.get("id");
        List<Category> list=categoryMapper.list(id);
        return list;
    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.findById(id);
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }


}
