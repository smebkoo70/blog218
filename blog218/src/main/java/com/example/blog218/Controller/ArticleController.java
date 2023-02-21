package com.example.blog218.Controller;

import com.example.blog218.service.ArticleService;
import com.example.blog218.vo.Result;
import com.example.blog218.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
