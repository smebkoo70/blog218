package com.example.blog218.Controller;

import com.example.blog218.service.ArticleService;
import com.example.blog218.vo.ArticleVo;
import com.example.blog218.vo.Result;
import com.example.blog218.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//json数据交互
@RestController
@RequestMapping("/articles")
public class ArticleController {

    /**
     * 首页文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams)
    {
        return articleService.listArticle(pageParams);
    }

    @Autowired
    private ArticleService articleService;

    /**
     * 首页 最热文章
     * @return
     */
    @PostMapping("hot")
    public Result hotArticle()
    {
        int limit = 5;

        return articleService.hotArticle(limit);
    }

    /**
     * 首页 最新文章
     * @return
     */
    @PostMapping("new")
    public Result newArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 首页 文章归档
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long id) {
        ArticleVo articleVo = articleService.findArticleById(id);

        return Result.success(articleVo);
    }
}
