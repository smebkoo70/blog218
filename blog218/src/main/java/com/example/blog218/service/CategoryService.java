package com.example.blog218.service;

import com.example.blog218.vo.CategoryVo;
import com.example.blog218.vo.Result;


public interface CategoryService {

    CategoryVo findCategoryById(Long id);

    Result findAll();

    Result findAllDetail();

    Result categoriesDetailById(Long id);
}
