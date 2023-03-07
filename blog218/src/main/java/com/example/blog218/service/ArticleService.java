package com.example.blog218.service;

import com.example.blog218.vo.ArticleVo;
import com.example.blog218.vo.Result;
import com.example.blog218.vo.params.PageParams;

public interface ArticleService {




    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    Result newArticles(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    /**
     *
     * @param id
     * @return
     */
    ArticleVo findArticleById(Long id);
}
