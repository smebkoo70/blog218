package com.example.blog218.service;

import com.example.blog218.vo.Result;
import com.example.blog218.vo.params.PageParams;

public interface ArticleService {

    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

}
